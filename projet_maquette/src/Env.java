import java.util.LinkedList;

//++  Cette classe représente l'environnement dans lequel évolue le fluide. ++//
//++  Elle contient donc les constantes physiques (g, viscosité etc...), les ++//
//++  constantes liées à la simulation (resolution du quadrillage de        ++//
//++  l'espace, pas temporel de la simulation etc...)                       ++//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

public class Env {
	static double t =  0.3;                                                 //echantillonage temporelle de la simulation.
	static double res = 0.6;                                                   //resolution de la discretisation spatiale de l'espace.
	static int p_sub_res = 6;                                                //ratio donnant la resolution de l'isocontour (surface du fluide), qui est plus précise.
	static int l = 150;                                                        //largeur de l'espace de simulation
	static int h = 300;                                                        //hauteur de l'espace de simulation
	Voxel[][] grille;                                                    //le cadrillage representant l'espace, chaque voxel etant une case, possedant des propriétés propres.
	Vect[][] grille_isosurf;                                             //grille sur laquelle sera calculée la surface du fluide ( plus de precision)
	static LinkedList<Particule> liste_particules;                       //liste des particules présentes dans la simulation, utile pour calculer l'évolution de leurs positions.
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// Constructeur
	Env(double t, double res, int p_sub_res, int l, int h){
		
		this.grille = new Voxel[h][l];
		this.grille_isosurf = new Vect[l*p_sub_res][h*p_sub_res];
	}
	//------------------------------------------------------------------------------
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//retourne la hauteur (physique) de l'espace de simulation
	double hauteur(){
		return res*((double) h);
	}
	//------------------------------------------------------------------------------
	
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//retourne la largeur (physique) de l'espace de simulation
	double largeur(){  
		return res*((double) h);
	}
	//------------------------------------------------------------------------------
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//retourne le nombre de cases en largeur de la grille sur laquelle est calculée la surface du fluide.
	double l_iso(){
		return l*p_sub_res;
	}
	//------------------------------------------------------------------------------
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//retourne le nombre de cases en largeur de la grille sur laquelle est calculée la surface du fluide.
	double h_iso(){
		return h*p_sub_res;
	}
	//------------------------------------------------------------------------------
	
}
