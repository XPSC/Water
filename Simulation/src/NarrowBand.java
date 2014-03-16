import java.util.LinkedList;
import java.util.Queue;

/** 
 * Narrowband divides the simulation (big) grid in 4 categories of cells :
 *  1: air cells
 *  2 : surface (considered as liquid cells, but with boundaries conditions)
 *  3 : narrow band, where particles are located (thus they are also liquid cells)
 *  4 : liquid cells
 * old :
 * 
 * NarrowBand is the band around the surface where the level set method (phi calculation, runge kutta, particles) is applied
 * It is represented by a boolean array and a list.
 * To construct the narrow band with Manhattan distance, we use a queue from the phi = 0 line (right ?)
 */
public class NarrowBand {
	static int[][] narrowband;

	// static LinkedList<Voxel> listvoxels;
	// static Queue<Voxel> queue;

	/**
	 * Initialization after Init is initialized (the surface at t = 0 must be
	 * defined)
	 */
	public static void init() {
		narrowband = new int[Env.width()][Env.height()];
		for (int i = 0; i < Env.width(); i++) {
			for (int j = 0; j < Env.height(); j++) {
				narrowband[i][j] = 1;
			}
		}

		// liquid is defined initially by all cells below the initial surface,
		// so that the initial configuration must not be too much specific

		for (Voxel voxel : Init.getInitSurface()) {
			Voxel voxel2 = voxel.smallGridCoordToBigGridCoord();
			narrowband[voxel2.x][voxel2.y] = 2;
		}

		for (int i = 0; i < Env.width(); i++) {
			for (int j = 0; j < Env.height(); j++) {
				if (narrowband[i][j] == 2) {
					if (Env.isInside(i, j + 1)) {
						narrowband[i][j + 1] = 3;
					}
					for (int k = j + 2; k < Env.height(); k++) {
						narrowband[i][k] = 4;
					}
				}
			}
		}

	}
	
	public static LinkedList<Voxel> narrowBandToList(){
		LinkedList<Voxel> listvoxels = new LinkedList<Voxel>();
		int res = Env.p_sub_res;
		for (int i = 0; i < Env.width(); i++) {
			for (int j = 0; j < Env.height(); j++) {
				if (narrowband[i][j] == 2 || narrowband[i][j] == 3) {
					for(int ki = 0; ki<res; ki++){
						for(int kj = 0; kj<res; kj++){
							listvoxels.add(new Voxel(i*res+ki, j*res+kj));
						}
					}
				}
			}
		}
		return listvoxels;
	}
}
