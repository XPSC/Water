import diewald_fluid.Fluid2D;

//++  Cette classe repr√©sente l'environnement dans lequel √©volue le fluide. ++//
//++  Elle contient donc les constantes physiques (g, viscosit√© etc...), les ++//
//++  constantes li√©es √† la simulation (resolution du quadrillage de        ++//
//++  l'espace, pas temporel de la simulation etc...)                       ++//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

public class Env {
	private static Fluid2D fluid;
	private static float t =  0.10f;                                                 //echantillonage temporelle de la simulation.
	private static int cellsize = 6;                                                   //resolution de la discretisation spatiale de l'espace.
	static int p_sub_res = 3;                                                //ratio donnant la resolution de l'isocontour (surface du fluide), qui est plus pr√©cise.
	private static int l = 100;                                                        //largeur de l'espace de simulation
	private static int h = 100;                                                        //hauteur de l'espace de simulation
	static Voxel[][] grille = new Voxel[h][l];                                                    //le cadrillage representant l'espace, chaque voxel etant une case, possedant des propri√©t√©s propres.
	static Vect[][] grille_isosurf = new Vect[l*p_sub_res][h*p_sub_res];                                             //grille sur laquelle sera calcul√©e la surface du fluide ( plus de precision)
	static float[] dens_field;
	static float[] velocity_fieldU;
	static float[] velocity_fieldV;
	
	
	// TO DO
	//   
	// ProblËmes conditions limites particles
	//  Faire distinction entre particules ‡ la surface et eloignÈs de la surface (2 bandes ?)
	// KDTree en double, mieux vaut en int
	// attention dÈcalages entre grille particule et grille diewald (interpolation bilinÈaire ?)
 	
	public static void init(Fluid2D fluidref){
		fluid = fluidref; 
		NarrowBand.init();
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
	

  
}
