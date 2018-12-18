package com.humine.utils.money;

public class Gemme {

	private int gemme;
	
	public Gemme() {
		this.gemme = 0;
	}
	
	public Gemme(int number) {
		this.gemme = number;
	}
	
	public void addGemme(int gemme) {
		this.gemme += gemme;
	}
	
	public void removeGemme(int gemme) {
		this.gemme = gemme;
	}

	public int getGemme() {
		return gemme;
	}

	public void setGemme(int gemme) {
		this.gemme = gemme;
	}
	
	
}
