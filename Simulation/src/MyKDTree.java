import net.sf.javaml.core.kdtree.KDTree;

public class MyKDTree {
	// Problem? : use of double numbers
	static KDTree kdTree;
		
	/*
	 public static void ConstructKdTree() { // creates a two-dimensional search tree of the particle list
	 	kdTree = new KDTree(2);
		for (Particule particule : liste_particules)
			AddParticleToKdTree(particule);
	}
	*/
	
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
