package xadrez;

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
	
	private void novaPosicaoPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.posicaoPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}
	
	private void initialSetup() { // método responsável por iniciar a partida de Xadrez, colocando as peças no tabuleiro.
		novaPosicaoPeca('b', 6, new Torre(tabuleiro, Cores.BRANCO));
		novaPosicaoPeca('e', 8, new Rei(tabuleiro, Cores.PRETO));
		novaPosicaoPeca('e', 1, new Rei(tabuleiro, Cores.BRANCO));
	}

}
