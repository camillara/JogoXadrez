package xadrez.pecas;

import boardgame.Posicao;
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

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean [][] mat = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		//verificar acima da peça
		p.setValores(posicao.getLinha()-1, posicao.getColuna()); // posicao é a posicao da própria peça.
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha()-1);
		}
		if(getTabuleiro().posicaoExiste(p) && temPecasOponetes(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//verificar a esquerda da peça
		p.setValores(posicao.getLinha(), posicao.getColuna()-1); // posicao é a posicao da própria peça.
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna()-1);
		}
		if(getTabuleiro().posicaoExiste(p) && temPecasOponetes(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//verificar a direita da peça
		p.setValores(posicao.getLinha(), posicao.getColuna()+1); // posicao é a posicao da própria peça.
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna()+1);
		}
		if(getTabuleiro().posicaoExiste(p) && temPecasOponetes(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//verificar abaixo da peça
		p.setValores(posicao.getLinha()+1, posicao.getColuna()); // posicao é a posicao da própria peça.
		while(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha()+1);
		}
		if(getTabuleiro().posicaoExiste(p) && temPecasOponetes(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	}
}
