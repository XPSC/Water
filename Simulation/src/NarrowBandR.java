import java.util.LinkedList;

public class NarrowBandR {
	private int _bandWidth;
	private int _bandHeight;

	private int _phiWidth = Env.p_l;
	private int _phiHeight = Env.p_h;

	private int _bandCellResolution = Env.p_sub_res;

	private int _bandThickness; // >= 1

	private Boolean[][] _thinBand; // thinBand is the thickBand of thickness 1 - just the voxels necessary to cover the surface.
	private Boolean[][] _thickBand;

	private LinkedList<Voxel> _thinBandList;
	private LinkedList<Voxel> _thickBandList;

	private CellState[][] _cellState; // Air, Surface of Liquid. See CellState enum.



	public NarrowBandR(int bandThickness) {
		_bandThickness = bandThickness;

		_cellState = new CellState[_phiWidth][_phiHeight];

		_bandWidth = _phiWidth / _bandCellResolution;
		_bandHeight = _phiWidth / _bandCellResolution;
	}



	//region ThickBand

	private void addVoxelToThinBand(Voxel voxel) {
		_thinBand[voxel.x][voxel.y] = true;
		_thinBandList.add(voxel);
	}

	private void addVoxelToThickBand(Voxel voxel) {
		_thickBand[voxel.x][voxel.y] = true;
		_thickBandList.add(voxel);
	}

	private void addVoxelAndNeighborsToThickBand(Voxel voxel) {
		for (int i = -_bandThickness + 1; i < _bandThickness; ++i)
			for (int j = _bandThickness + 1; j < _bandThickness; ++j) {
				int x = voxel.x + i;
				int y = voxel.y + j;

				if ((x >= 0) && (y >= 0) && (x < _bandWidth) && (y < _bandHeight) && (!_thickBand[x][y]))
					addVoxelToThickBand(new Voxel(x, y));
			}
	}

	private void refreshThinBand(double[][] phi) {
		_thinBand = new Boolean[_bandWidth][_bandHeight];
		_thinBandList = new LinkedList<Voxel>();

		for (int i = 0; i < _phiWidth; ++i)
			for (int j = 0; j < _phiHeight; ++j) {
				if (phi[i][j] == 0) {
					int bandX = i / _bandCellResolution;
					int bandY = j / _bandCellResolution;

					if (!_thinBand[bandX][bandY])
						addVoxelToThinBand(new Voxel(bandX, bandY));
				}
			}
	}

	private void refreshThickBand(double[][] phi) {
		refreshThinBand(phi);

		_thickBand = new Boolean[_bandWidth][_bandHeight];
		_thickBandList = new LinkedList<Voxel>();

		for (Voxel voxel : _thinBandList)
			addVoxelAndNeighborsToThickBand(voxel);
	}

	/* Will return the list of voxels that will require recalculating phi for the next few steps based on the current phi values. */
	public LinkedList<Voxel> VoxelsToUpdate(double[][] phi) {
		refreshThickBand(phi);
		LinkedList<Voxel> voxelsToUpdate = new LinkedList<Voxel>();

		for (Voxel voxel : _thickBandList) {
			int x = voxel.x;
			int y = voxel.y;

			for (int i = 0; i < _bandCellResolution; ++i)
				for (int j = 0; j < _bandCellResolution; ++j)
					voxelsToUpdate.add(new Voxel(x + i, y + j));
		}
		return voxelsToUpdate;
	}

	//endregion



	//region CellState

	/* Assuming the top row is air, goes from top to bottom checking for surface cells and switching accordingly from air to liquid. */
	public void UpdateCellStates(double[][] phi) {
		for (int i = 0; i < _phiWidth; ++i) {
			Boolean isAir = true;
			for (int j = 0; j < _phiHeight; ++j) {
				if (phi[i][j] == 0) {
					_cellState[i][j] = CellState.Surface;
					isAir = !isAir;
				} else {
					_cellState[i][j] = isAir ? CellState.Air : CellState.Liquid;
				}
			}
		}
	}

	//endregion

}
