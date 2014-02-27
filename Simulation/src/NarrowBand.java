/*
 * NarrowBand is the band around the surface where the level set method (phi calculation, runge kutta, particles) is applied
 * It is represented by a boolean array
 */
public class NarrowBand {
   static boolean[][] narrowband;
   
   /*
    * By default, it is the entire grid (1st step)
    */
   public static void init(){
	   narrowband = new boolean[Env.width()*Env.p_sub_res][Env.height()*Env.p_sub_res];
	    for(int i = 0; i<Env.width()*Env.p_sub_res; i++){
	    	for(int j = 0; j<Env.height()*Env.p_sub_res; j++){
	    		NarrowBand.narrowband[i][j]=true;
	    		}
	    	}
   }
   
   
}
