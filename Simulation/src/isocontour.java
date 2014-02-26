
public class isocontour {

	
	int[][] implicite(double r){       // methode des marching squares pour determiner la surface du plan d'eau, prend en parametre
		                               // le rayon des particules et retourne un tableau avec des "0" sur la frontière
		                               // des "1" en dehors du fluide et de "-1" dans le fluide.
		
		
		//Sur ce bloc on calcule pour chaque case la distance à la particule la plus proche.
		//L'algorithme est dégeulasse, on peut surement faire plus efficace.
		double[][] champ = new double[Env.p_sub_res*Env.h][Env.p_sub_res*Env.l];
		double mem;
		for(int i = 0; i < Env.p_sub_res*Env.h; i++){
			for(int j = 0; j < Env.p_sub_res*Env.l; j++){
				Vect point = new Vect(i*Env.res*Env.p_sub_res, j*Env.res*Env.p_sub_res);
				mem = Env.liste_particules.peek().position.distance(point);
				for(Particule k : Env.liste_particules){
					double d = Math.min(mem, k.position.distance(point));
					champ[i][j] = d < r? 0.0 : d;
					
				}
			}
		} // à ce stade le tableau champ est le phi(x) de l'article, ce champ vaut 0 "dans" les particules, donc sur la frontière, et vaut la distance à la particule la plus 
		  // proche en dehors.
		
		//Ici il faut un bloc qui lisse phi(x) à proximité de la surface et le rend "signé" grace aux fonctions données dans l'artice de Foster: practical fluid animation
		//ATTENTION, les formules de lissage font intervenir du gradient, il y a surement besoin de la bibli d'algèbre linéaire.
			//
			//
			//(BLOC A CONSTRUIRE)
			//(Il faut juste utiliser la bible d'algèbre pour appliquer
			//au champ phi les équations déjà données).
			//
			//
		
		//Il faut maintenant utiliser le marching squares algorithme qui va permettre d'obtenir la courbe ou phi est minimum
		//
		
		boolean[] square = new boolean[4];      //Je pré-supose que je commence a un endroit ou il n'y a pas de fluide.
		square[0] = champ[Env.p_sub_res*Env.h - 1][0];
		square[1] = champ[Env.p_sub_res*Env.h - 1][1];
		square[2] = champ[Env.p_sub_res*Env.h - 2][0];
		square[3] = champ[Env.p_sub_res*Env.h - 2][1];
		
		while
		
	}
}
