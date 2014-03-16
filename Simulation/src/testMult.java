import java.util.LinkedList;

public class testMult {
		
	testMult(){
	}
		
		static double[][] initialize_test(){
			double[][] grid = new double[25][25];
			for(int i = 0; i < 25; i++){
				for(int j = 0 ; j < 25; j++){
					if (i > 10 && i < 17 && j<6) grid[i][j] = -5;
					else grid[i][j] = 5;
				}
			}
			
			for(int i = 0; i < 25; i++){
				for(int j = 0 ; j < 25; j++){
					if (i > 18 && i < 23 && j > 12 && j < 18) grid[i][j] = -5;
				}
			}

			return grid;
		}
		
		public static void main(String[] args) {
			double[][] grid = initialize_test();
			MarchingSquareMult m = new MarchingSquareMult(grid, 25, 0);
			/*for(int i = 0; i < 25; i++){
				for(int j = 0; j< 25; j++){
					System.out.print(m.a_lire[i][j] + " ");
				}
				System.out.println(" ");
			}*/
			LinkedList<LinkedList<Vect>> contours = m.doMarch();
			for(LinkedList<Vect> c : contours)
				System.out.println(c);
		}
	}
