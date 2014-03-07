import java.util.LinkedList;
import java.util.Queue;

/*
 * NarrowBand is the band around the surface where the level set method (phi calculation, runge kutta, particles) is applied
 * It is represented by a boolean array and a list.
 * To construct the narrow band with Manhattan distance, we use a queue from the phi = 0 line (right ?)
 */
public class NarrowBand {
   static boolean[][] narrowband;
   static LinkedList<Voxel> listvoxels;
   static Queue<Voxel> queue;
   
   /*
    * Initialization after Init is initialized (the surface at t = 0 must be defined)
    */
   public static void init(){
	   narrowband = new boolean[Env.width()*Env.p_sub_res][Env.height()*Env.p_sub_res];
	    for(int i = 0; i<Env.width()*Env.p_sub_res; i++){
	    	for(int j = 0; j<Env.height()*Env.p_sub_res; j++){
	    		NarrowBand.narrowband[i][j]=false;
	    		}
	    	}
	    
	    for(Voxel voxel : Init.getInitSurface()){
	    	narrowband[voxel.x][voxel.y]=true;
	    }
   }
   
   
}
