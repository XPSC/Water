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
	Vitesse vhg, vhd, vvb, vvh;                           //abr. pour vitesse horizontale gauche etc...       Voxel.vhd = Voxel.droit.vhg
	double pression;
	Voxel gauche, droit, bas, haut;
	boolean empty;                                        // si empty = true on peut mettre du liquide dans la case, sinon c'est un "mur".
}
