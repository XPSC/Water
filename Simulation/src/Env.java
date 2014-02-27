import net.sf.javaml.core.kdtree.KDTree;

import java.util.LinkedList;

import processing.core.PApplet;

import diewald_fluid.Fluid2D;
import diewald_fluid.Fluid2D_CPU;

//++  Cette classe représente l'environnement dans lequel évolue le fluide. ++//
//++  Elle contient donc les constantes physiques (g, viscosité etc...), les ++//
//++  constantes liées à la simulation (resolution du quadrillage de        ++//
//++  l'espace, pas temporel de la simulation etc...)                       ++//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//

public class Env {
	private static Fluid2D fluid;
	private static float t =  0.10f;                                                 //echantillonage temporelle de la simulation.
	private static int cellsize = 6;                                                   //resolution de la discretisation spatiale de l'espace.
	static int p_sub_res = 3;                                                //ratio donnant la resolution de l'isocontour (surface du fluide), qui est plus précise.
	private static int l = 100;                                                        //largeur de l'espace de simulation
	private static int h = 100;                                                        //hauteur de l'espace de simulation
	static Voxel[][] grille = new Voxel[h][l];                                                    //le cadrillage representant l'espace, chaque voxel etant une case, possedant des propriétés propres.
	static Vect[][] grille_isosurf = new Vect[l*p_sub_res][h*p_sub_res];                                             //grille sur laquelle sera calculée la surface du fluide ( plus de precision)
	static LinkedList<Particule> liste_particules;                       //liste des particules présentes dans la simulation, utile pour calculer l'évolution de leurs positions.
	static KDTree kdTree;
	static float[] dens_field;
	
	
	// TO DO
	//   
	//  Faire distinction entre particules � la surface et eloign�s de la surface (2 bandes ?)
	// KDTree en double, mieux vaut en int
	// attention d�calages entre grille particule et grille diewald (interpolation bilin�aire ?)
 	
	public static void init(Fluid2D fluidref){
		fluid = fluidref; 
	}
	
	public static float timeStep(){
		return t;
	}
	
	public static int width(){
       return l;
	}
	
	public static int height(){
	       return h;
		}
	
	public static int cellSize(){
	       return cellsize;
		}
		
	public static void calcDensityField(){
		dens_field = fluid.getBufferDensity_byRef(0);
	}
	
	public static float[] getDensityField() {
		return dens_field;
	}
	
	
	public static float getVelocityValue(int x, int y){
		return vel_field[fluid.IDX(x, y)];
	}
	
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
