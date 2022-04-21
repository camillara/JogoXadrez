package application;

import xadrez.PecaDeXadrez;

public class UI {

	public static void imprimirTabuleiro(PecaDeXadrez[][] pecas) {
		for(int i=0; i<pecas.length; i++) {
			System.out.print((8-i) + " "); //imprimir o número da linha.
			for(int j=0; j<pecas.length; j++) {
				imprimePeca(pecas[i][j]); //imprimir cada peça na posição [i][j]
			}
			System.out.println(); //pular a linha para inciar a próxima linha.
		}
		System.out.println("  a b c d e f g h");
		
	}
	
	private static void imprimePeca(PecaDeXadrez peca) { // método para imprimir cada posicao do tabuleiro. Se houver peça, imprime a peça. Se estier nulo, imprime -
		if(peca == null ) {
			System.out.print("-");
		}
		else {
			System.out.print(peca);
		}
		System.out.print(" ");
	}
	
}
