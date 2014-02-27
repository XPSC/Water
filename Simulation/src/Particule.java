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
    	position = new Vect((double)x, (double)y);
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
     * @return velocity (vx) of the particle
     */
    public float getVitesseU(){
    	return Env.getVelocityValueU(bigX(), bigY());
    }
    
    /**
     * 
     * @return velocity (vy) of the particle
     */
    public float getVitesseV(){
    	return Env.getVelocityValueV(bigX(), bigY());
    }
    
    // Problem? double ?
    
    /**
     * calculates the new position with the velocity value given by Diewald lib
     * @return 
     */
    public double semiReflexion(double a){
    	if (a<0) return (-a);
      return a;
    }
    public void update(){
    	//float deltaT = Env.timeStep(); 
    	float a =  Env.timeStep()*((float) Env.width()*Env.p_sub_res);
        position.x += (double)(getVitesseU()*a);
        position.y += (double)(getVitesseV()*a);
        
        // if (x, y) outside, we do a reflexion
        position.x = semiReflexion(position.x);
        position.y = semiReflexion(position.y);
        
        position.x = Env.width()*Env.p_sub_res - 1 - position.x;
        position.y = Env.height()*Env.p_sub_res - 1 - position.y;
        
        position.x = semiReflexion(position.x);
        position.y = semiReflexion(position.y);
        
        position.x = Env.width()*Env.p_sub_res - 1 - position.x;
        position.y = Env.height()*Env.p_sub_res - 1 - position.y;
        
        x = (int) Math.floor(position.x);
        y = (int) Math.floor(position.y);
        
        
    }
    
}
