package xadrez;

import boardgame.Peca;
import boardgame.Posicao;
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
	
	public PosicaoXadrez getPosicaoXadrez() {
		return PosicaoXadrez.fromPosicao(posicao);
	}
	
	protected boolean temPecasOponetes(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return p !=null && p.getCor()!=cor;
	}

}
