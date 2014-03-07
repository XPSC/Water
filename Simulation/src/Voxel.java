//++  Cette classe décrit un carré élémentaire de l'espace de simulation  ++//
//++  Elle contient donc les variables caractérisant localement le fluide ++//
//++  (pression, vitesse etc...).                                         ++//
//++  Une case doit également pouvoir pointer vers les cases adjacentes   ++//
//++  et connaître sa position.                                           ++//
//++                                                                      ++//
//++                                                                      ++//
//++                                                                      ++//
//++                                                                      ++//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//
//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++//


public class Voxel {
      // � revoir
	 int x;
	 int y;
	 
	 public Voxel(int x, int y){
		 this.x = x;
		 this.y = y;
	 }
	 
	 public Voxel smallGridCoordToBigGridCoord(){
		 return new Voxel(x/Env.p_sub_res, y/Env.p_sub_res);
	 }
}
