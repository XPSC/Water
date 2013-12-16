import java.util.LinkedList;

/**
 * Created by roma on 16/12/2013.
 */
public class Functions { // a mettre ou tu veux...
	public LinkedList<Vect> VectorGroups (double[][] champ) {
		LinkedList<Vect> result = new LinkedList<Vect>();

		if ((champ == null) || (champ.length == 0))
			return result;

		int height = champ.length;
		int width = champ[0].length;

		boolean[][] visited = new boolean[height][width];
		for (int i = 0; i < height; ++i)
			for (int j = 0; j < width; ++j)
				if ((!visited[i][j]) && (champ[i][j] < 0)) {
					result.add(new Vect(i, j));
					Visit(champ, visited, i, j, height, width);
				}

		return result;
	}

	public void Visit(double[][] champ, boolean[][] visited, int i, int j, int height, int width) {
		visited[i][j] = true;
		if (i > 0) {
			if ((!visited[i - 1][j]) && (champ[i - 1][j] < 0))                              Visit(champ, visited, i - 1, j, height, width);
			if ((j > 0) && (!visited[i - 1][j - 1]) && (champ[i - 1][j - 1] < 0))           Visit(champ, visited, i - 1, j - 1, height, width);
			if ((j < width - 1) && (!visited[i - 1][j + 1]) && (champ[i - 1][j + 1] < 0))  Visit(champ, visited, i - 1, j + 1, height, width);
		}

		if (i < height - 1) {
			if ((!visited[i + 1][j]) && (champ[i + 1][j] < 0))                              Visit(champ, visited, i + 1, j, height, width);
			if ((j > 0) && (!visited[i + 1][j - 1]) && (champ[i + 1][j - 1] < 0))           Visit(champ, visited, i + 1, j - 1, height, width);
			if ((j < width - 1) && (!visited[i + 1][j + 1]) && (champ[i + 1][j + 1] < 0))  Visit(champ, visited, i + 1, j + 1, height, width);
		}

		if ((j > 0) && (!visited[i][j - 1]) && (champ[i][j - 1] < 0))                       Visit(champ, visited, i, j - 1, height, width);
		if ((j < width - 1) && (!visited[i][j + 1]) && (champ[i][j + 1] < 0))               Visit(champ, visited, i, j + 1, height, width);
	}
}
