package application;

import xadrez.PartidaDeXadrez;

public class Programa {

	public static void main(String[] args) {		
		
		PartidaDeXadrez partida = new PartidaDeXadrez();
		UI.imprimirTabuleiro(partida.getPecas());
	

	}

}
