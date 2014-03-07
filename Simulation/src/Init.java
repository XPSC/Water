import java.util.LinkedList;

public class Init {
   static LinkedList<Voxel> surface_init;
   
   static void Init(){
	   int j = Env.p_h/2;
	   surface_init = new LinkedList<Voxel>();
	   for(int i=0; i<Env.p_l; i++){
		   surface_init.add(new Voxel(i, j));
	   }
   }
   
   static LinkedList<Voxel>  getInitSurface() {
       if (surface_init == null){System.out.println("surface_init n'a pas été initialisé");}  
	   return surface_init;
	    }
}
