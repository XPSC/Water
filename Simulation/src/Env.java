import diewald_fluid.Fluid2D;

//++  Cette classe reprÃ©sente l'environnement dans lequel Ã©volue le fluide. ++//
//++  Elle contient donc les constantes physiques (g, viscositÃ© etc...), les ++//
//++  constantes liÃ©es Ã  la simulation (resolution du quadrillage de        ++//
//++  l'espace, pas temporel de la simulation etc...)                       ++//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

public class Env {
	private static Fluid2D fluid;
	private static float t =  0.10f;                                                 //echantillonage temporelle de la simulation.
	private static int cellsize = 20;                                                   //resolution de la discretisation spatiale de l'espace.
	static int p_sub_res = 5;  //ratio donnant la resolution de l'isocontour (surface du fluide), qui est plus prÃ©cise.
	static int p_cellsize = cellsize/p_sub_res;
	private static int l = 40;                                                        //largeur de l'espace de simulation
	private static int h = 40;                                                        //hauteur de l'espace de simulation
	static int p_l =  l*p_sub_res;
	static int p_h = h*p_sub_res;
	static Voxel[][] grille = new Voxel[h][l];                                                    //le cadrillage representant l'espace, chaque voxel etant une case, possedant des propriÃ©tÃ©s propres.
	static Vect[][] grille_isosurf = new Vect[l*p_sub_res][h*p_sub_res];                                             //grille sur laquelle sera calculÃ©e la surface du fluide ( plus de precision)
	static float[] dens_field;
	static float[] velocity_fieldU;
	static float[] velocity_fieldV;
	static int p_freq = 6; //frequency of apparition of particles below the surface (in the narrow band) after initialization
	
	static int[][] p_order_x = new int[p_sub_res][p_sub_res]; //util (see below)
	static int[][] p_order_y = new int[p_sub_res][p_sub_res]; 
	
	static double threshold_phi = 5;
	// Attention
	// Pour afficher au bon endroit, il faut ajouter 1*cellsize
	
	
	// TO DO
	//
	// préciser narrow band (phi, particules)
	// stabilité, robustesse
	//
	//  amélioration : interpolations -> for particles, for boundaries conditions ? for the p solver
	//
	// Level set (thins are maybe still to be done : )
	// Problèmes conditions limites particles
	//  Faire distinction entre particules à la surface et eloignés de la surface (2 bandes ?)
	// KDTree en double, mieux vaut en int
	// attention décalages entre grille particule et grille diewald (interpolation bilinéaire ?)
 	// for level set method, see http://cs.au.dk/~tgk/courses/LevelSets/LevelSet.pdf
	// 1. Bilinear interpolation (level set/particles)
	// 2. Create narrow band with queue -> only add new particles and calculate phi directly with particles
	// Is phi defined only by particles sufficient ? (normally phi is a signed distance function from water surface, which is not) maybe must use normalisation
	// example with fast marching method : http://www.ann.jussieu.fr/~frey/papers/levelsets/Sethian%20J.A.,%20Evolution,%20implementation%20and%20application%20of%20level%20sets%20and%20fast%20marching%20methods%20for%20advancing%20fronts.pdf
	// details : http://www2.imm.dtu.dk/pubdb/views/edoc_download.php/841/pdf/imm841.pdf
	// example with fast iterative method (simpler) : A Fast Iterative Method for Eikonal Equations
	// Runge Kutta narrow band
	//* delete one particle when same coordinates (redundant)
	
	public static void init(Fluid2D fluidref){
		//init fluid simul
		fluid = fluidref; 
		
		//util : init p_order
		for(int ki = 0; ki<p_sub_res; ki++){
			for(int kj = 0; kj<p_sub_res; kj++){
				p_order_x[ki][kj]=ki;
				p_order_y[ki][kj]=kj;
			}
		}		
		
		//init surface
		Init.Init();
		Particles.init();
		NarrowBand.init();
		
		//add particles below init surfaces
		Particles.addParticlesRange(NarrowBand.narrowBandToList(), p_freq);
		
		//init and calculate phi
		Phi.init();
		Particles.calcPhi();
	}
	
	public static float timeStep(){
		return t;
	}
	
	public static int width(){
       return l;
	}
	
	public static int height(){
	       return h;
		}
	
	public static int cellSize(){
	       return cellsize;
		}
		
	public static void calcDensityField(){
		dens_field = fluid.getBufferDensity_byRef(0);
	}
	
	public static void calcVelocityField(){
		velocity_fieldU = fluid.getBufferVelocityU_byRef();
		velocity_fieldV = fluid.getBufferVelocityV_byRef();
	}
	
	public static float[] getDensityField() {
		return dens_field;
	}
	
	public static float getVelocityValueU(int x, int y){
		return velocity_fieldU[fluid.IDX(x, y)];
	}
	
	public static float getVelocityValueV(int x, int y){
		return velocity_fieldV[fluid.IDX(x, y)];
	}
	
	public static float getDensityValue(int x, int y){
		return dens_field[fluid.IDX(x, y)];
	}
	
	// Util
	
	// shuffleOrder
	// shuffle the ordrer of treatment for the subdivisions of a cell
	
	//Knuth shuffle
	
   // to do
	
    // FOR DEBUGGING
	public static boolean isInside(int x, int y){
		return !((x<0) || (x>l-1) || (y<0) || (y>h-1)); 
	}
	public static boolean p_isInside(int x, int y){
		return !((x<0) || (x>p_l-1) || (y<0) || (y>p_h-1)); 		
	}
  
}
