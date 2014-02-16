// Cette classe définit ce qu'est un vecteur, et définit les propriétés associées ( distance entre deux vecteurs )
// Elle servira a décrire la vitesse des particules ou du fluide par exemple.
// Pas grand chose a dire de plus.
//--------------------------------------------------------------------------------------------------

public class Vect {
	double x;
	double y;
	
	//+++++++++++++++++++++++++++++++++++++++
	//Constructeur
	Vect(double x, double y){
		this.x = x;
		this.y = y;
	}
	//---------------------------------------
	
	
	//++++++++++++++++++++++++++++++++++++++++
	// Distance entre deux vecteurs
	double distance(Vect V){
		return Math.sqrt((V.x-this.x)*(V.x-this.x) + (V.y-this.y)*(V.y-this.y));
	}
	//----------------------------------------
	double[] ToArray() {
		return new double[]{x, y};
	}
}
