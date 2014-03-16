
public class Vect implements Comparable<Vect>{
	public double x;
	public double y;
	
	Vect(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	double[] ToArray() {
		return new double[]{x, y};
	}
	
	public String toString(){
		return "("+x+"; "+y+")";
	}
	
	// Distance entre deux vecteurs
	double distance(Vect V){
		return Math.sqrt((V.x-this.x)*(V.x-this.x) + (V.y-this.y)*(V.y-this.y));
	}
	
	@Override
	public int compareTo(Vect v) {
		if (this.x > v.x) {
			return 1;
		} else if (this.x < v.x) {
			return -1;
		} else {
			if(this.y > v.y){
				return 1;
			} else if (this.y < v.y){
				return -1;
			} else return 0;
		}
	}
}
