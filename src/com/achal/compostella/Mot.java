package com.achal.compostella;

/**
 * Class qui définit l'objet Mot
 * Cet objet spécifie un mot et ces différentes traductions
 * @author Aurore
 *
 */
public class Mot {
	
	private String motL1, motL2, motL3, motL4, motL5;

	public Mot(String motL1, String motL2, String motL3, String motL4, String motL5) {
		super();
		this.motL1 = motL1;
		this.motL2 = motL2;
		this.motL3 = motL3;
		this.motL4 = motL4;
		this.motL5 = motL5;
	}
	
	public String getMotL4() {
		return motL4;
	}

	public void setMotL4(String motL4) {
		this.motL4 = motL4;
	}

	public String getMotL5() {
		return motL5;
	}

	public void setMotL5(String motL5) {
		this.motL5 = motL5;
	}

	public String getMotL1() {
		return motL1;
	}

	public void setMotL1(String motL1) {
		this.motL1 = motL1;
	}

	public String getMotL2() {
		return motL2;
	}

	public void setMotL2(String motL2) {
		this.motL2 = motL2;
	}

	public String getMotL3() {
		return motL3;
	}

	public void setMotL3(String motL3) {
		this.motL3 = motL3;
	}
	

}
