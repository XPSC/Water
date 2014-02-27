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
   }
   
}
