import diewald_fluid.Fluid2D;
import diewald_fluid.Fluid2D_CPU;
import processing.core.PApplet;

import java.util.LinkedList;

public class DEBUG_PhiLine extends PApplet {
	// created from fluid2d_basic_cpu

	int fluid_size_x = Env.width();
	int fluid_size_y = Env.height();

	int cell_size = Env.cellSize();
	int window_size_x = fluid_size_x * cell_size + (cell_size * 2);
	int window_size_y = fluid_size_y * cell_size + (cell_size * 2);

	Fluid2D fluid;

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void setup() {


		size(window_size_x, window_size_y, JAVA2D); // P2D is slower, test it

		fluid = createFluidSolver();
		frameRate(60);
		Env.init(fluid);	
		
		
		for (int i = 0; i < fluid_size_x; i = i + 10) {
			for (int j = 0; j < fluid_size_y; j = j + 10) {
				//new Particule(i * Env.p_sub_res, j * Env.p_sub_res);
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	public void draw() {
		background(255);
		Env.calcDensityField();

		float[] d = Env.getDensityField();
		for (int i = 0; i < fluid_size_x; i++) {
			for (int j = 0; j < fluid_size_y; j++) {
				if (d[fluid.IDX(i, j)] > 1) {
					fluid.addVelocity(i, j, 0, d[fluid.IDX(i, j)] * 0.01F);
				}
			}
		}
		if (mousePressed) {
			// println(d);
			fluidInfluence(fluid);
		}
		fluid.update();
		image(fluid.getDensityMap(), 0, 0, width, height);
		// println(frameRate);
		Env.calcVelocityField();
        Particles.calcPhi(Env.narrowband);
		for (Particule particule : Particles.particles) {
			particule.update();
			//drawParticle(particule);
		}
		drawNarrowBand();
		drawLine(Phi.getZeroLine());
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// createFluidSolver();
	//
	Fluid2D createFluidSolver() {

		Fluid2D fluid_tmp = new Fluid2D_CPU(this, fluid_size_x, fluid_size_y); // initialize
																				// de
																				// solver

		fluid_tmp.setParam_Timestep(Env.timeStep());
		fluid_tmp.setParam_Iterations(16);
		fluid_tmp.setParam_IterationsDiffuse(1);
		fluid_tmp.setParam_Viscosity(0.000001f);
		fluid_tmp.setParam_Diffusion(0.00000001f);
		fluid_tmp.setParam_Vorticity(1.0f);
		fluid_tmp.processDensityMap(true);
		fluid_tmp.processDiffusion(true);
		fluid_tmp.processViscosity(true);
		fluid_tmp.processVorticity(true);
		fluid_tmp.processDensityMap(true);
		fluid_tmp.setObjectsColor(1, 1, 1, 1);
		return fluid_tmp;
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// fluidInfluence();
	//
	public void fluidInfluence(Fluid2D fluid2d) {
		if (mouseButton == LEFT) {
			setDens(fluid2d, mouseX, mouseY, 5, 5, 2, 2, 2);
		}

		if (mouseButton == RIGHT) {
			float vel_fac = 0.13f;
			setVel(fluid2d, mouseX, mouseY, 2, 2, (mouseX - pmouseX) * vel_fac,
					(mouseY - pmouseY) * vel_fac);
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// setDens();
	//
	void setDens(Fluid2D fluid2d, int x, int y, int sizex, int sizey, float r,
			float g, float b) {
		for (int y1 = 0; y1 < sizey; y1++) {
			for (int x1 = 0; x1 < sizex; x1++) {
				int xpos = (int) (x / (float) cell_size) + x1 - sizex / 2;
				int ypos = (int) (y / (float) cell_size) + y1 - sizey / 2;
				fluid2d.addDensity(0, xpos, ypos, r);
				fluid2d.addDensity(1, xpos, ypos, g);
				fluid2d.addDensity(2, xpos, ypos, b);
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// setVel();
	//
	void setVel(Fluid2D fluid2d, int x, int y, int sizex, int sizey,
			float velx, float vely) {
		for (int y1 = 0; y1 < sizey; y1++) {
			for (int x1 = 0; x1 < sizex; x1++) {
				int xpos = (int) ((x / (float) cell_size)) + x1 - sizex / 2;
				int ypos = (int) ((y / (float) cell_size)) + y1 - sizey / 2;
				fluid2d.addVelocity(xpos, ypos, velx, vely);
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////////////////////////////////
	// addObject();
	//
	public void addObject(Fluid2D fluid2d, int posx, int posy, int sizex,
			int sizey, int mode) {
		int offset = 0;
		int xlow = posx;
		int xhig = posx + sizex;
		int ylow = posy;
		int yhig = posy + sizey;

		for (int x = xlow - offset; x < xhig + offset; x++) {
			for (int y = ylow - offset; y < yhig + offset; y++) {
				if (x < 0 || x >= fluid2d.getSizeXTotal() || y < 0
						|| y >= fluid2d.getSizeYTotal())
					continue;
				if (mode == 0)
					fluid2d.addObject(x, y);
				if (mode == 1)
					fluid2d.removeObject(x, y);
			}
		}
	}

	// /////// DEBUG

////Affiche croix
public void drawCross(int a, int b){
	stroke(255, 0, 0);
	int x = a*Env.p_cellsize+Env.cellSize();
	int y = b*Env.p_cellsize+Env.cellSize();
	line(x-3, y, x+3, y);
	line(x, y-3, x, y+3);
}

////Affiche particule
public void drawParticle(Particule particule){
stroke(255, 0, 0);
drawCross(particule.x, particule.y);
}

public void drawLine(LinkedList<LinkedList<Vect>> listVect){
	int a = Env.p_cellsize;
	int x1 = -5;
	int x2;
	int y1 = 0;
	int y2;
	for (LinkedList<Vect> liste : listVect){
		x1=-5;
		for (Vect vect : liste){
			x2 = (int) vect.x*a;
			y2 = (int) vect.y*a;
			// if x1==-5, x1 y1 has not been defined yet
			stroke(255, 255, 255);
			if(x1 != -5)
				line(x1, y1, x2, y2);
			x1 = x2;
			y1 = y2;
		}
	}
}

////Affiche un �l�ment de la grille (grande)
public void drawCell(int x, int y, int param){
switch(param){
case 1:stroke(255, 255, 255); break;
case 2: stroke(255, 0, 0); break;
case 3: stroke(0, 255, 0); break;
case 4 : stroke(0, 0, 255); break;
}

int a = Math.round(Env.cellSize());
int b = Math.round(Env.cellSize()/4);
fill(0, 0, 0, 0);
//rect(a*(x+1), a*(y+1), a-1, a-1);
//point(x*a+1, y*a+1);

//opt : print out phi
//fill(200, 200, 255, 150);
//textSize(16);
//text((int) Math.round(Phi.phi[x*Env.p_sub_res][y*Env.p_sub_res]/10), a*(x+1)+b, a*(y+1)+3*b);
}

public void drawNarrowBand(){


for(int i = 0; i<Env.width(); i++){
	for(int j = 0; j<Env.height(); j++){
		drawCell(i, j, NarrowBand.cellState[i][j]);
	}
	}
}

}
