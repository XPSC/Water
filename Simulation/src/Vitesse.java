//Cette classe sert à définir la vitesse associée à un case de fluide.
//Cette vitesse est définie à gauche, droite, en bas, en haut; doit vérifier l'incompressibilité; les conditions aux limites; d'où l'interet de faire une classe à part.
//On utilisera les correspondances suivantes: (gauche: W, droite: E, haut: N, bas: S).
//-------------------------------------------------------------------------------------


public class Vitesse {
	double value;
	Vitesse next;
	Vitesse pred;
}
