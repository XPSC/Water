import java.util.LinkedList;


public class Phi {
    public static double[][] phi;
    private static LinkedList<LinkedList<Vect>> zeroLine;
    
    public static void init(){
    	phi = new double[Env.p_l][Env.p_h];
    	for (int i = 0; i < Env.p_l; i++) {
			for (int j = 0; j < Env.p_h; j++) {
				phi[i][j] = 1;
			}
		}
    	
    }

    //region Gaussion blur
    public static int[][] KERNEL = new int[][]{{1, 4, 7, 4, 1}, {4, 16, 26, 16, 4}, {7, 26, 41, 26, 7}, {4, 16, 26, 16, 4}, {1, 4, 7, 4, 1}};
    public static double DIVISOR = 273.0;
    public static int RADIUS = 3;
    public static void Blur()
    {
        double[][] blur = new double[Env.p_l][Env.p_h];

        for (int i = RADIUS - 1; i < Env.p_l - RADIUS; i++){
            for (int j = RADIUS - 1; j < Env.p_h - RADIUS; j++) {
                for (int k = -RADIUS + 1; k < RADIUS; k++) {
                    for (int l = -RADIUS + 1; l < RADIUS; l++) {
                        blur[i][j] += phi[i + k][j + l] * KERNEL[RADIUS - k - 1][RADIUS - l - 1];
                    }
                }
                blur[i][j] /= DIVISOR;
            }
        }
        phi = blur;
    }
    //endregion Gaussian blur
    
    @SuppressWarnings("static-access")
    /**
     * Phi(x) = min distance(x, pi � myKD) - threshold
     * @param myKD
     * @param threshold
     */
	public static void calcPhi(MyKDTree myKD, double threshold, LinkedList<Voxel> narrowBand){
    	int r = Env.p_sub_res;
    	int i;
    	int j;
    	for(Voxel v : narrowBand){
    		for(int ki = 0; ki<r; ki++){
				for(int kj = 0; kj<r; kj++){
					i = Math.max(0, Math.min(v.x*r + ki, Env.p_l-1));
					j = Math.max(0, Math.min(v.y*r + kj, Env.p_l-1));
					phi[i][j] = myKD.phi(new double[] {(double) (i), (double) (j)}) - threshold;
				}
			}
    	}
        Blur();
		///// DEBUG !!!!!
		modifyBorders();
		calcZeroLine();
    }
    
    private static void rec_aux(LinkedList<Vect> lv){
    	if(lv.isEmpty()){
    		return;
    	}
    	Vect v = lv.poll();
    	rec_aux(lv);
    	Voxel voxel1 = new Voxel((int) v.x, (int) v.y);
		voxel1 = voxel1.smallGridCoordToBigGridCoord();
		if (!(voxel1.x < 0 || voxel1.x >= Env.width() ||  voxel1.y < 0 || voxel1.y >= Env.width() || ! NarrowBand.narrowband[voxel1.x][voxel1.y])){
			lv.add(v);
		}
    	
    }
    
    public static void calcZeroLine(){
    	MarchingSquareSuperLisse m = new MarchingSquareSuperLisse(phi, 0);
		zeroLine = m.doMarch();
		for(LinkedList<Vect> l : zeroLine){
			rec_aux(l);
		}
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
