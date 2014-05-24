import java.util.LinkedList;


public class Particles {
   //Contains both particles in the liquid surface and free particles (outside)
   static LinkedList<Particule> particles = new LinkedList<Particule> (); 
   // Kd tree stucture is used occasionally 
   static MyKDTree particlesKD;
   // contains also a boolean 2d-vector to know in O(1) if there's a particle in (x, y) 
   static boolean[][] particlesVect;
      
   public static void init(){
	   particlesVect = new boolean[Env.width()*Env.p_sub_res][Env.height()*Env.p_sub_res];
	    for(int i = 0; i<Env.width()*Env.p_sub_res; i++){
	    	for(int j = 0; j<Env.height()*Env.p_sub_res; j++){
	    		particlesVect[i][j]=false;
	    		}
	    	}
   }
   
   public static void add(Particule p) {
	 		 particles.add(p);
	 		 particlesVect[p.x][p.y] = true;
	      }
   
   public static void remove(Particule p){
        particles.remove(p);
   }
   
   /**
    * add particles to cellList with frequency 1/p
    */
   // TO DO
  public static void addParticlesRange(LinkedList<Voxel> cellList, int p){
	  int i = 0;
	  for (Voxel vox : cellList){
		  if(i%p == 0)
			add(new Particule(vox.x, vox.y));
		  i++;
	  }	  
  }
  
  private static void toKDTree(){
	  particlesKD = new MyKDTree();
	  particlesKD.ConstructKdTree(particles);
  }  
  
  public static void calcPhi(LinkedList<Voxel> narrowband){
	  //� chaque pas reconstruire l'arbre ??
	  toKDTree();
	  Phi.calcPhi(particlesKD, Env.threshold_phi, narrowband);
  }
  
  
}
