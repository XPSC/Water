import java.util.LinkedList;

public class Init {
   static LinkedList<Voxel> surface_init;
   
   static void Init(){
	   
   }
   
   static LinkedList<Voxel>  getInitSurface() {
       if (surface_init == null){System.out.println("surface_init n'a pas été initialisé");}  
	   return surface_init;
	    }
}
