package com.achal.compostella;

import java.util.Comparator;

/**
 * Class qui permet de comparer deux mots d'une même langue pour les classer
 * Class utilisée dans la vue Dictionnaire pour classer les mots dans l'ordre de la langue de départ
 * @author Aurore
 *
 */
public class MotComparator implements Comparator<Mot>{


	private int idLangue;

	public MotComparator(int idLangue) {
		super();
		this.idLangue = idLangue;
	}

	public int compare(Mot mot1, Mot mot2) {

		Mot m1 = mot1;
		Mot m2 = mot2;
		int result =0;
		
		switch (idLangue) {
		case 0: result = m1.getMotL1().compareToIgnoreCase(m2.getMotL1());
			break;
		case 1: result = m1.getMotL2().compareToIgnoreCase(m2.getMotL2());
			break;
		case 2: result = m1.getMotL3().compareToIgnoreCase(m2.getMotL3());
			break;
		case 3: result = m1.getMotL4().compareToIgnoreCase(m2.getMotL4());
			break;
		case 4: result = m1.getMotL5().compareToIgnoreCase(m2.getMotL5());
			break;
		default: result = m1.getMotL1().compareToIgnoreCase(m2.getMotL1());
			break;
		}
        return result;
	}

}
