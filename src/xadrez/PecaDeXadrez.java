package xadrez;

import boardgame.Peca;
import boardgame.Tabuleiro;

public abstract class PecaDeXadrez extends Peca{ //ChessPiece
	
	private Cores cor;

	public PecaDeXadrez(Tabuleiro tabuleiro, Cores cor) {
		super(tabuleiro);
		this.cor = cor;
	}

	public Cores getCor() {
		return cor;
	}	

}
