import diewald_fluid.Fluid2D;
import diewald_fluid.Fluid2D_CPU;
import processing.core.PApplet;

public class main extends PApplet {
  //created from fluid2d_basic_cpu




	int  fluid_size_x = Env.width(); 
	int  fluid_size_y = Env.height();

	int  cell_size    = Env.cellSize();
	int  window_size_x = fluid_size_x  * cell_size + (cell_size * 2);
	int  window_size_y = fluid_size_y  * cell_size + (cell_size * 2);

	Fluid2D fluid;

    
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setup() {
	  size(window_size_x, window_size_y, JAVA2D); // P2D is slower, test it

	  fluid = createFluidSolver();
	  frameRate(60);
	  Env.init(fluid);
	}



	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void draw() {
	  background(255);
      Env.calcDensityField();
      float[] d = Env.getDensityField();
	  for(int i=0; i< fluid_size_x; i++){
		  for(int j=0; j< fluid_size_y; j++){
			 	  if( d[fluid.IDX(i, j)]>1){
			 fluid.addVelocity(i, j, 0, d[fluid.IDX(i, j)]*0.01F);
			 	  }
		 }
	  }
	  if ( mousePressed ) {
      // println(d);
	   fluidInfluence(fluid);
	  }
	  fluid.update();
	  image(fluid.getDensityMap(), 0, 0, width, height);
	  //println(frameRate);
	  DrawCross(20, 20);
	}


	/////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// createFluidSolver();
	//
	Fluid2D createFluidSolver() {

	  Fluid2D fluid_tmp = new Fluid2D_CPU(this, fluid_size_x, fluid_size_y); // initialize de solver

	  fluid_tmp.setParam_Timestep  ( Env.timeStep() );
	  fluid_tmp.setParam_Iterations( 16 );
	  fluid_tmp.setParam_IterationsDiffuse(1);
	  fluid_tmp.setParam_Viscosity ( 0.000001f );
	  fluid_tmp.setParam_Diffusion ( 0.00000001f );
	  fluid_tmp.setParam_Vorticity ( 1.0f );
	  fluid_tmp.processDensityMap  ( true );
	  fluid_tmp.processDiffusion   ( true );
	  fluid_tmp.processViscosity   ( true );
	  fluid_tmp.processVorticity   ( true );
	  fluid_tmp.processDensityMap  ( true );
	  fluid_tmp.setObjectsColor    (1, 1, 1, 1); 
	  return fluid_tmp;
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//fluidInfluence();
//
public void fluidInfluence( Fluid2D fluid2d ) {
if (mouseButton == LEFT ) {
setDens(fluid2d, mouseX, mouseY, 5, 5, 2, 2, 2);
}

if (mouseButton == RIGHT ) {
float vel_fac = 0.13f;
setVel(fluid2d, mouseX, mouseY, 2, 2, (mouseX - pmouseX)*vel_fac, (mouseY - pmouseY)*vel_fac);
}
}    


/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//setDens();
//
void setDens(Fluid2D fluid2d, int x, int y, int sizex, int sizey, float r, float g, float b) {
for (int y1 = 0; y1 < sizey; y1++) {
for (int x1 = 0; x1 < sizex; x1++) {
int xpos = (int)(x/(float)cell_size) + x1 - sizex/2;
int ypos = (int)(y/(float)cell_size) + y1 - sizey/2;
fluid2d.addDensity(0, xpos, ypos, r);
fluid2d.addDensity(1, xpos, ypos, g);
fluid2d.addDensity(2, xpos, ypos, b);
}
}
}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//setVel();
//
void setVel(Fluid2D fluid2d, int x, int y, int sizex, int sizey, float velx, float vely) {
for (int y1 = 0; y1 < sizey; y1++) {
for (int x1 = 0; x1 < sizex; x1++) {
int xpos = (int)((x/(float)cell_size)) + x1 - sizex/2;
int ypos = (int)((y/(float)cell_size)) + y1 - sizey/2;
fluid2d.addVelocity(xpos, ypos, velx, vely);
}
}
}


/////////////////////////////////////////////////////////////////////////////////////////////////////////////
//addObject();
//
public void addObject(Fluid2D fluid2d, int posx, int posy, int sizex, int sizey, int mode) {
int offset = 0;
int xlow = posx;
int xhig = posx + sizex;
int ylow = posy;
int yhig = posy + sizey;

for (int x = xlow-offset ; x < xhig+offset ; x++) {
for (int y = ylow-offset ; y < yhig+offset ; y++) {
if ( x < 0 || x >= fluid2d.getSizeXTotal() || y < 0 || y >= fluid2d.getSizeYTotal() )
continue;
if ( mode == 0) fluid2d.addObject(x, y);
if ( mode == 1) fluid2d.removeObject(x, y); 
}
}
}




///////// DEBUG


//// Affiche croix
public void DrawCross(int a, int b){
	stroke(255, 0, 0);
	int x = a*cell_size;
	int y = b*cell_size;
	line(x-3, y, x+3, y);
	line(x, y-3, x, y+3);
}


}