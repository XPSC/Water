// Cette classe représente une particule et les propriétés qui lui sont associées
// Par exemple la distance à cette particule, sa vitesse, sa position, ses voisines?, sa distance à la surface du fluide?
// L'ensemble des particules est référencé dans la liste Env.liste_particules
//-------------------------------------------------------------------------------

public class Particule {
	Vect position;
	Vect vitesse;
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Construit un champ donnant la distance à la particule.
	double[][] champ_dist(){
		double[][] champ = new double[Env.p_sub_res*Env.h][Env.p_sub_res*Env.l];
		for(int i = 0; i < Env.p_sub_res*Env.h; i++){
			for(int j = 0; j < Env.p_sub_res*Env.l; j++){
				champ[i][j] = (new Vect(i,j).distance(new Vect(this.position.x*Env.p_sub_res, this.position.y*Env.p_sub_res)));
			}
		}
		return champ;
	}
	//-------------------------------------------------------------------------------
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//Donne la distance a la surface de la particule.  (A FAIRE)
	double dist_surface(){
		return 0.0;
	}
	//--------------------------------------------------------------------------------
}
