import net.sf.javaml.core.kdtree.KDTree;

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
	static Voxel[][] grille = new Voxel[h][l];                                                    //le cadrillage representant l'espace, chaque voxel etant une case, possedant des propriétés propres.
	static Vect[][] grille_isosurf = new Vect[l*p_sub_res][h*p_sub_res];                                             //grille sur laquelle sera calculée la surface du fluide ( plus de precision)
	static LinkedList<Particule> liste_particules;                       //liste des particules présentes dans la simulation, utile pour calculer l'évolution de leurs positions.
	static KDTree kdTree;

	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//retourne la hauteur (physique) de l'espace de simulation
	static double hauteur(){
		return res*((double) h);
	}
	//------------------------------------------------------------------------------
	
	
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//retourne la largeur (physique) de l'espace de simulation
	static double largeur(){
		return res*((double) h);
	}
	//------------------------------------------------------------------------------
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//retourne le nombre de cases en largeur de la grille sur laquelle est calculée la surface du fluide.
	static double l_iso(){
		return l*p_sub_res;
	}
	//------------------------------------------------------------------------------
	
	//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	//retourne le nombre de cases en largeur de la grille sur laquelle est calculée la surface du fluide.
	static double h_iso(){
		return h*p_sub_res;
	}
	//------------------------------------------------------------------------------

	public static void ConstructKdTree() { // creates a two-dimensional search tree of the particle list
		kdTree = new KDTree(2);
		for (Particule particule : liste_particules)
			AddParticleToKdTree(particule);
	}
	public static void AddParticleToKdTree(Particule particule) {
		kdTree.insert(particule.position.ToArray(), particule);
	}
	public static void DeleteParticleFromKdTree(Particule particule) {
		kdTree.delete(particule.position.ToArray());
	}
	public static Particule NearestParticle(double[] coordinates) {
		return (Particule) kdTree.nearest(coordinates);
	}
	public static Particule NearestParticle(Particule particule) {
		return (Particule) kdTree.nearest(particule.position.ToArray());
	}
	public static Particule NearestParticle(Vect vect) {
		return (Particule) kdTree.nearest(vect.ToArray());
	}
	public static double phi(double[] coordinates) {
		Particule nearestParticle = NearestParticle(coordinates);
		double[] nearestCoordinates = nearestParticle.position.ToArray();
		return Math.sqrt((nearestCoordinates[0] - coordinates[0]) * (nearestCoordinates[0] - coordinates[0]) + (nearestCoordinates[1] - coordinates[1]) * (nearestCoordinates[1] - coordinates[1]));
	}
	public static double phi(Particule particule) {
		Particule nearestParticle = NearestParticle(particule);
		return nearestParticle.position.distance(particule.position);
	}
	public static double phi(Vect vect) {
		Particule nearestParticle = NearestParticle(vect);
		return nearestParticle.position.distance(vect);
	}
}
