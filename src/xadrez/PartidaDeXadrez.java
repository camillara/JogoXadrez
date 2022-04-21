package xadrez;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez { // ChessMatch

	private Tabuleiro tabuleiro;

	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		initialSetup();
	}

	public PecaDeXadrez[][] getPecas() {
		PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j]= (PecaDeXadrez)tabuleiro.peca(i,j);
			}
		}
		return mat;
	}
	
	private void initialSetup() { // método responsável por iniciar a partida de Xadrez, colocando as peças no tabuleiro.
		tabuleiro.posicaoPeca(new Torre(tabuleiro, Cores.BRANCO), new Posicao(2,1));
		tabuleiro.posicaoPeca(new Rei(tabuleiro, Cores.PRETO), new Posicao(0,4));
		tabuleiro.posicaoPeca(new Rei(tabuleiro, Cores.BRANCO), new Posicao(7,4));
	}

}
