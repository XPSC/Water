// Cette classe représente une particule et les propriétés qui lui sont associées
// Par exemple la distance à cette particule, sa vitesse, sa position, ses voisines?, sa distance à la surface du fluide?
// L'ensemble des particules est référencé dans la liste Env.liste_particules
//-------------------------------------------------------------------------------

public class Particule {
	// double --> int ??
	Vect position;
	Vect vitesse;
	public int x;
    public int y;
    
    // Free
    // True if the particule is outside the liquid surface
    // By default, false
    private boolean free = false;    
    
    
    /**
     * Coordinates in smaller grid
     * @param x
     * @param y
     */
    public Particule(int x, int y){
    	this.x=x;
    	this.y = y;
    }
    
    public boolean isFree(){
    	return free;
    }
    
    public void free(){
    	free = true;
    }
    
    public void unFree(){
    	free = false;
    }
    
       
    ///////
    // getVitesse
    // gives Vitesse from velocityfield from a bilinear interpolation
    
    // pour l'instant non, au plus simple 
    
    /**
     * 
     * @return coordinates in the bigger grid
     */
    public int bigX(){
       return x/Env.p_sub_res;
    }
    
    /**
     * 
     * @return coordinates in the bigger grid
     */
    public int bigY(){
        return y/Env.p_sub_res;
     }
    
    /**
     * 
     * @return velocity of the particle
     */
    public float getVitesse(){
    	return Env.getVelocityValue(bigX(), bigY());
    }
    
    /**
     * calculates the new position with the velocity value given by Diewald lib
     * @return 
     */
    public void update(){
        
    }
    
}
