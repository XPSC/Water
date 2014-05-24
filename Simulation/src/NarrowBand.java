import java.util.LinkedList;
import java.util.Queue;

/** 
 * CellState divides the simulation (big) grid in 3 categories of cells :
 *  1: air cells
 *  2 : surface (considered as liquid cells, but with boundaries conditions)
 *  3 : liquid cells
 *   * (old : 4 cat.)
 * 
 * NarrowBand is the band around the surface where the level set method (phi calculation, runge kutta, particles) is applied
 * It is represented by a boolean array and a list.
 * To construct the narrow band with Manhattan distance, we use a queue from the phi = 0 line (right ?)
 * 
 * NarrowBand divides the simulation in 2 categories
 * true : narrowband
 * false : not in narrowband
 */
public class NarrowBand {
	static int[][] cellState;
	static boolean[][] narrowband;
	static private int _bandThickness; // >= 1
	
	// static LinkedList<Voxel> listvoxels;
	// static Queue<Voxel> queue;

	/**
	 * Initialization after Init is initialized (the surface at t = 0 must be
	 * defined)
	 */
	public static LinkedList<Voxel> init(int bandThickness) {
		LinkedList<Voxel> rlist = new LinkedList<Voxel>();
		cellState = new int[Env.width()][Env.height()];
		narrowband = new boolean[Env.width()][Env.height()];
		_bandThickness = bandThickness;		
		
		for (int i = 0; i < Env.width(); i++) {
			for (int j = 0; j < Env.height(); j++) {
				cellState[i][j] = 1;
			}
		}

		// liquid is defined initially by all cells below the initial surface,
		// so that the initial configuration must not be too much specific

		for (Voxel voxel : Init.getInitSurface()) {
			Voxel voxel2 = voxel.smallGridCoordToBigGridCoord();
			cellState[voxel2.x][voxel2.y] = 2;
		}
		for (int i = 0; i < Env.width(); i++) {
			for (int j = 0; j < Env.height(); j++) {
				narrowband[i][j] = false;
			}
		}
		for (int i = 0; i < Env.width(); i++) {
			for (int j = 0; j < Env.height(); j++) {
				if (cellState[i][j] == 2) {
					for (int k = -_bandThickness + 1; k < _bandThickness; k++){
						for (int l = -_bandThickness + 1; l < _bandThickness; l++) {
							int x = i + k;
							int y = j + l;

							if ((x >= 0) && (y >= 0) && (x < Env.height()) && (y < Env.height())){
								narrowband[x][y] = true;
								rlist.add(new Voxel(x, y));
							}
						}
					}
					if (Env.isInside(i, j + 1)) {
						cellState[i][j + 1] = 3;
						
					}
					for (int k = j + 2; k < Env.height(); k++) {
						cellState[i][k] = 3;
					}
				}
			}
		}
		return rlist;
	}
	
	////////////

	private static void addVoxelAndNeighborsToThickBand(Vect V) {
		Voxel voxel1 = new Voxel((int) V.x, (int) V.y);
		Voxel voxel2 = voxel1.smallGridCoordToBigGridCoord();
		
		for (int k = -_bandThickness + 1; k < _bandThickness; k++){
			for (int l = -_bandThickness + 1; l < _bandThickness; l++) {
				int x = voxel2.x + k;
				int y = voxel2.y + l;

				if ((x >= 0) && (y >= 0) && (x < Env.height()) && (y < Env.height())){
					narrowband[x][y] = true;
				}
			}
		}
	}


	public static void update(LinkedList<LinkedList<Vect>> zeroline){
		for(int i = 0; i< Env.height(); i++){
			for(int j= 0; j< Env.height(); j++){
				narrowband[i][j] = false;
			}
		}
		
		for(LinkedList<Vect> l : zeroline){
			for(Vect v : l){
				addVoxelAndNeighborsToThickBand(v);
			}
		}
	}
	/////////////
	
	//XXX narrowband; renvoie les cases de la grande grille dans la narrowband
	public static LinkedList<Voxel> narrowBandToList(LinkedList<LinkedList<Vect>> zeroline){
		NarrowBand.update(zeroline);
		LinkedList<Voxel> listvoxels = new LinkedList<Voxel>();
		for (int i = 0; i < Env.width(); i++) {
			for (int j = 0; j < Env.height(); j++) {
				if (narrowband[i][j]) {
					listvoxels.add(new Voxel(i,j));
				}
			}
		}
		return listvoxels;
	}
	
}
