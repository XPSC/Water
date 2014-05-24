import java.util.*;

//MARCHING SQUARE
//A utiliser sur un double[][], trace un isocontour.


public class MarchingSquareSuperLisse {
	//grille sur laquelle est donnée les valeurs de phi
	public double[][] grid;
	
	//Largeur de la grille
	public int n;
	
	//largeur de la grille au carré
	public int n2;
	
	//Valeur de l'isocontour
	public double seuil = 0;
	
	//tête de lecture sur la grille (quatre cases sont lues
	private boolean[] evalSquare;
	
	//liste des lignes d'isocontour
	public LinkedList<LinkedList<Vect>> contours;
	
	//mémoire de la derniere direction prise
	private int previousStep; //Dir
	
	//prochaine direction à prendre
	private int nextStep; //Dir
	
	//mémoire des profils de cases rencontrées lors de la pré-lécture
	public int[][] cas;
	
	//associe une case non traitée à son état.
	public LinkedHashMap<Integer, Integer> temp;
	
	//Constructeur
	MarchingSquareSuperLisse(double[][] grid, double seuil){
		this.grid = grid;
		this.n = grid.length;
		this.n2 = n*n;
		this.seuil = seuil;
		this.evalSquare = new boolean[4];
		this.contours = new LinkedList<LinkedList<Vect>>();
		this.cas = new int[n][n];
		this.temp = new LinkedHashMap<Integer, Integer>(); 
		
	}
	
	//HELPERS
	
	//Conversion des coordonnées d'une case-----------
	
	//[1..n2] -> [1..n][1..n]
	private Vect ln_squ(int i){
		return new Vect(i/n, i%n);
	}
	
	//[1..n][1..n] -> [1..n2]
	private int squ_ln(Vect v){
		return (((int) v.x)*n + (int) v.y);
	}
	
	//choisit un point non traité.
	public int startPoint(){
		for(int i = 0; i < n2; i++){
			if(temp.containsKey(i)) return i;
		}
		return n2;
	}
	
	//vérifie si la case est sous l'isocontour.
	private boolean isVoxelFilled(int x, int y){
		if(x < 0 || y < 0 || x >= n || y >= n) return false;
		if(grid[x][y] < 0) return true;
		return false;
	}
	
	//censure x
	private double format(double x){
		return Math.min(Math.max(x, 0), n-1);
	}
	
	//permet de traiter les valeur hors-grille comme étant sous l'isocontour
	private double eval_grid(int x, int y){
		if(x>=0 && x<n && y<n && y>=0) return grid[x][y];
		else return -9;
	}
	//--------------------------------------------------
	//FIN HELPERS
	
	
	//retourne les isocontours, fonction à appeler.
	public LinkedList<LinkedList<Vect>> doMarch(){
		walkGrid(); //repérage des cas sur chaque case. prélecture.
		drawContours();
		return contours;
	}
	
	//Main While Loop
	private void walkGrid(){
		for(int x = 0; x < n; x++){
			for(int y = 0; y < n; y++){ //parcours chaque case du tableau.
				Step(x, y);
			}
		}
	}
	
	private void drawContours(){
		Integer i;
		LinkedList<Vect> contour = new LinkedList<Vect>();
		while(!temp.isEmpty()){
			//Prends un point de temp, qui est non traité
			i = startPoint();
			
			//Parcourt grid[][] à partir de ce point jusqu'a fermer un contour, toutes les cases rencontrées sont considérées traitées
			contour = rentrerContour(i);
			
			//retourne le contour trouvé.
			contours.add(contour);
		}
		return;
	}
	
	public LinkedList<Vect> rentrerContour(int i){
		
		//v est les coordonnées du point de départ
		Vect v = ln_squ(i);
		
		//On extrait x et y, on censure les valeurs hors grille.
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
		
		//x et y sont les coordonées de la tête de lecture.
		int x = startX;
		int y = startY;
		
		do{
			//set the nextStep, on lit le cas[][] qui est pré-construit.
			int state = cas[x][y];
			
			//On enregistre le dernier déplacement.
			previousStep = nextStep;
			
			switch(state){
			//Les formules compliquées sont des interpolations
			case 1: nextStep = 1; 
			pointList.add(new Vect(format(x+(eval_grid(x-1, y-1)/(eval_grid(x,y-1)-eval_grid(x-1,y-1)))) +5 , format(y-1) +5 )); 
			break;
			
			case 2: nextStep = 2; 
			pointList.add(new Vect(format(x-1)  +5 , format(y-(eval_grid(x-1,y-1)/(eval_grid(x-1,y-1)-eval_grid(x-1,y) )))  +5  ));
			break; 
			// ERROR
			
			case 3: nextStep = 2;
			pointList.add(new Vect(format(x+(eval_grid(x-1, y-1)/(eval_grid(x,y-1)-eval_grid(x-1,y-1))))  +5 ,format(y-1) +5 )); 
			break; // ERROR
			
			case 4: nextStep = 4; 
			pointList.add(new Vect(format(x) +5 ,format(y+(eval_grid(x,y-1)/(eval_grid(x,y)-eval_grid(x,y-1))))  +5 ));
			break; // Left
			
			case 5: nextStep = 1; 
			pointList.add(new Vect(format(x) +5 ,format(y+(eval_grid(x,y-1)/(eval_grid(x,y)-eval_grid(x,y-1)))) +5 )); 
			break; // Up
			
			case 6: 
				if(previousStep == 1){ nextStep = 4; 
				pointList.add(new Vect(format(x) +5 ,format(y+(eval_grid(x,y-1)/(eval_grid(x,y)-eval_grid(x,y-1)))) +5 ));
				} else{ nextStep = 2; 
				pointList.add(new Vect(format(x-1) +5 ,format(y-(eval_grid(x-1,y-1)/(eval_grid(x-1,y-1)-eval_grid(x-1,y)))) +5 ));
				} break;
			
			case 7: nextStep = 2; 
			pointList.add(new Vect(format(x)  +5 , format(y+(eval_grid(x,y-1)/(eval_grid(x,y)-eval_grid(x,y-1)))) +5 ));
			break; // right
			
			case 8: nextStep = 3; 
			pointList.add(new Vect(format(x-(eval_grid(x-1, y)/(eval_grid(x-1,y)-eval_grid(x,y)) ))   +5 , format(y) +5 )); 
			break; // down
			
			case 9:
				if(previousStep == 2){nextStep = 1; 
				pointList.add(new Vect(format(x+(eval_grid(x-1, y-1)/(eval_grid(x,y-1)-eval_grid(x-1,y-1))))  +5 , format(y-1) +5 ));
				} else{ nextStep = 3; 
				pointList.add(new Vect(format(x-(eval_grid(x-1, y)/(eval_grid(x-1,y)-eval_grid(x,y))))  +5 , format(y) +5 ));
				} break;
				
			case 10: nextStep = 3; 
			pointList.add(new Vect(format(x-1) +5 ,format(y-(eval_grid(x-1,y-1)/(eval_grid(x-1,y-1)-eval_grid(x-1,y)))) +5 ));
			break; //down
			
			case 11: nextStep = 3; pointList.add(new Vect(format(x+(eval_grid(x-1, y-1)/(eval_grid(x,y-1)-eval_grid(x-1,y-1))))  +5 , format(y-1) +5 )); 
			break; // down
			
			case 12: nextStep = 4; 
			pointList.add(new Vect(format(x-(eval_grid(x-1, y)/(eval_grid(x-1,y)-eval_grid(x,y))))  +5 , format(y) +5 ));
			break; // left
			
			case 13: nextStep = 1; 
			pointList.add(new Vect(format(x-(eval_grid(x-1, y)/(eval_grid(x-1,y)-eval_grid(x,y))))  +5 , format(y) +5 ));
			break; // up
			
			case 14: nextStep = 4;
			pointList.add(new Vect(format(x-1) +5 , format(y-(eval_grid(x-1,y-1)/(eval_grid(x-1,y-1)-eval_grid(x-1,y)))) +5 ));
			break; // left
			
			case 15: nextStep = 0; break;
			default: 
				nextStep = 0; //none
			}
			
			//On enlève le point qu'on a parcouru des point à traiter.
			temp.remove(squ_ln(new Vect(x, y))); 
			
			//On bouge la tête de lecture selon le contour
			switch (nextStep){
			case 1: x--; break;
			case 2: y++; break;
			case 3: x++; break;
			case 4: y--; break;
			default: break;
			}
			
		} while (x != startX || y != startY); //On s'arrête lorsqu'on boucle
		return pointList;
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
		//On garde le contour à gauche
		case 1: nextStep = 1; break; // up
		case 2: nextStep = 2; break; // right
		case 3: nextStep = 2; break;// right
		case 4: nextStep = 4; break; // Left
		case 5: nextStep = 1; break; // Up
		case 6: if(previousStep == 1) nextStep = 4; else nextStep = 2; break;
		case 7: nextStep = 2; break; // right
		case 8: nextStep = 3; break; // down
		case 9: if(previousStep == 2) nextStep = 1; else nextStep = 3; break;
		case 10: nextStep = 3; break; //down
		case 11: nextStep = 3; break; // down
		case 12: nextStep = 4; break; // left
		case 13: nextStep = 1; break; // up
		case 14: nextStep = 4; break; // left
		case 15: nextStep = 0; break;
		default: // chaque case est vide, RAS
			nextStep = 0; //none
		}
		
		cas[x][y] = state; //On stocke en prélecture les cas rencontrés sur chaque case.
		if (state != 0 && state != 15) temp.put(squ_ln(new Vect(x, y)), state);
		
		return;
	}
	
	
}
