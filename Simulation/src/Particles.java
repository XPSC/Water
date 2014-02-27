import java.util.LinkedList;


public class Particles {
   
  //Contains both particles in the liquid surface and free particles (outside)
   static LinkedList<Particule> particles = new LinkedList<Particule> (); 
   // Kd tree stucture is used occasionally 
   static MyKDTree particlesKD = new MyKDTree();
   
   public static void add(Particule p){
	 		 particles.add(p);
	      }
   
   public static void remove(Particule p){
        particles.remove(p);
   }
   
 

    
}
