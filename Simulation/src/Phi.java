import java.util.LinkedList;


public class Phi {
    public static double[][] phi;
    private static LinkedList<LinkedList<Vect>> zeroLine;
    
    public static void init(){
    	phi = new double[Env.p_l][Env.p_h];
    }
    
    @SuppressWarnings("static-access")
    /**
     * Phi(x) = min distance(x, pi € myKD) - threshold
     * @param myKD
     * @param threshold
     */
	public static void calcPhi(MyKDTree myKD, double threshold){
		for (int i = 0; i < Env.p_l; i++) {
			for (int j = 0; j < Env.p_h; j++) {
				phi[i][j] = myKD.phi(new double[] {(double) i, (double) j}) - threshold;
			}
		}
		///// DEBUG !!!!!
		modifyBorders();
		calcZeroLine();
    }
    
    public static void calcZeroLine(){
    	MarchingSquareMult m = new MarchingSquareMult(phi, Env.p_l, 0);
		zeroLine = m.doMarch();
    }
    
    public static LinkedList<LinkedList<Vect>> getZeroLine(){
    	return zeroLine;
    }
    
    // FOR DEBUGGING !!
    
    private static void modifyBorders(){
		for (int i = 0; i < Env.p_l; i++) {
		    	phi[i][0] = 10;
				phi[i][Env.p_h-1] = 10;
		}
		
		for (int j = 0; j < Env.p_h; j++) {
	    	phi[0][j] = 10;
			phi[Env.p_l-1][j] = 10;
	}
    }
        
}
