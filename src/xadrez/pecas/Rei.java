package xadrez.pecas;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez{

	public Rei(Tabuleiro tabuleiro, Cores cor) {
		super(tabuleiro, cor);
	}
	
	@Override
	public String toString() {
		return "K"; //Rei = King
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return p==null || p.getCor()!=getCor();
	}
	

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean [][] mat = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0,0);
		
		//acima
		p.setValores(posicao.getLinha()-1, posicao.getColuna());
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//abaixo
		p.setValores(posicao.getLinha()+1, posicao.getColuna());
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//equerda
		p.setValores(posicao.getLinha(), posicao.getColuna()-1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//direita
		p.setValores(posicao.getLinha(), posicao.getColuna()+1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//noroeste
		p.setValores(posicao.getLinha()-1, posicao.getColuna()-1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//nordeste
		p.setValores(posicao.getLinha()-1, posicao.getColuna()+1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		//sudoeste
		p.setValores(posicao.getLinha()+1, posicao.getColuna()-1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()]=true;
		}
		//sudeste
		p.setValores(posicao.getLinha()+1, posicao.getColuna()+1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		return mat;
	}

}
