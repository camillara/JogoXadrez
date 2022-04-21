package xadrez.pecas;

import boardgame.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaDeXadrez;

public class Torre extends PecaDeXadrez {

	public Torre(Tabuleiro tabuleiro, Cores cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "R"; //Torre  = Rook
	}

}