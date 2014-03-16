import java.util.*;
public class MarchingSquareMult {
	public double[][] grid;
	public int n;
	public int n2;
	public double seuil = 0;
	private boolean[] evalSquare;
	public LinkedList<LinkedList<Vect>> contours;
	private int previousStep; //Dir
	private int nextStep; //Dir
	public int[][] cas;
	public LinkedHashMap<Integer, Integer> temp;
	
	//Constructeur
	MarchingSquareMult(double[][] grid, int n, double seuil){
		this.grid = grid;
		this.n = n;
		this.n2 = n*n;
		this.seuil = seuil;
		this.evalSquare = new boolean[4];
		this.contours = new LinkedList<LinkedList<Vect>>();
		this.cas = new int[n][n];
		this.temp = new LinkedHashMap<Integer, Integer>();
		
	}
	
	private Vect ln_squ(int i){
		return new Vect(i/n, i%n);
	}
	
	private int squ_ln(Vect v){
		return (((int) v.x)*n + (int) v.y);
	}
	
	//retourne le perimetre de l'objet le plus en haut a gauche.
	public LinkedList<LinkedList<Vect>> doMarch(){
		walkGrid();
		drawContours();
		return contours;
	}
	
	//Main While Loop
	private void walkGrid(){
		for(int x = 0; x < n; x++){
			for(int y = 0; y < n; y++){
				Step(x, y);
			}
		}
	}
	
	private void drawContours(){
		Integer i;
		LinkedList<Vect> contour = new LinkedList<Vect>();
		while(!temp.isEmpty()){
			i = startPoint();
			contour = rentrerContour(i);
			contours.add(contour);
		}
		return;
	}
	
	public LinkedList<Vect> rentrerContour(int i){
		Vect v = ln_squ(i);
		int startX = (int) v.x;
		int startY = (int) v.y;
		if(startX < 0)
			startX = 0;
		if(startX > n)
			startX = n;
		if(startY < 0)
			startY = 0;
		if(startY > n)
			startY = n;
		
		//Creer une liste de points constituant le contour.
		LinkedList<Vect> pointList = new LinkedList<Vect>();
		
		int x = startX;
		int y = startY;
		
		do{
			System.out.println(x + " " + y);
			//set the nextStep
			Step(x, y);
			//add the point
			if(x >= 0 && x < n && y >= 0 && y < n) pointList.add(new Vect(x,y));
			temp.remove(squ_ln(new Vect(x, y)));
			switch (nextStep){
			case 1: x--; break;
			case 2: y++; break;
			case 3: x++; break;
			case 4: y--; break;
			default: break;
			}
			
		} while (x != startX || y != startY);
		return pointList;
	}
	
	public int startPoint(){
		for(int i = 0; i < n2; i++){
			if(temp.containsKey(i)) return i;
		}
		return n2;
	}
	
	//Update le carré. Update le previous et next directions
	private void Step(int x, int y){
		
		
		//Construire le carré d'éval.
		evalSquare[0] = isVoxelFilled(x-1, y-1);
		evalSquare[1] = isVoxelFilled(x-1, y);
		evalSquare[2] = isVoxelFilled(x, y-1);
		evalSquare[3] = isVoxelFilled(x, y);
		
		//mettre en mémoire l'étape précedente.
		previousStep = nextStep;
		
		//determiner l'état du carré
		int state = 0; //tout vide.
		if (evalSquare[0]) state +=1;
		if (evalSquare[1]) state +=2;
		if (evalSquare[2]) state +=4;
		if (evalSquare[3]) state +=8; //il est déjà ici impossible d'avoir 15!
		
		switch(state){
		case 1: nextStep = 1; break; // up
		case 2: nextStep = 2; break; // right
		case 3: nextStep = 2; break; // right
		case 4: nextStep = 4; break; // Left
		case 5: nextStep = 1; break; // Up
		case 6: if(previousStep == 1) nextStep = 4; else nextStep = 2; break; // Ambigü : up -> Left, else right
		case 7: nextStep = 2; break; // right
		case 8: nextStep = 3; break; // down
		case 9: if(previousStep == 2) nextStep = 1; else nextStep = 3; break; // ambigü : right -> up, else down
		case 10: nextStep = 3; break; //down
		case 11: nextStep = 3; break; // down
		case 12: nextStep = 4; break; // left
		case 13: nextStep = 1; break; // up
		case 14: nextStep = 4; break; // left
		case 15: nextStep = 0; break;
		default: 
			nextStep = 0; //none
		}
		
		cas[x][y] = state;
		if (state != 0 && state != 15) temp.put(squ_ln(new Vect(x, y)), state);
		
		return;
	}
	
	//vérifie si la case est dans le contour.
	private boolean isVoxelFilled(int x, int y){
		if(x < 0 || y < 0 || x >= n || y >= n) return false;
		if(grid[x][y] < 0) return true;
		return false;
	}
}
