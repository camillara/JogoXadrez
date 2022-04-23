package xadrez.pecas;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.Cores;
import xadrez.PecaDeXadrez;

public class Cavalo extends PecaDeXadrez{

	public Cavalo(Tabuleiro tabuleiro, Cores cor) {
		super(tabuleiro, cor);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "N"; //CAVALO = KNIGHT
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return p==null || p.getCor()!=getCor();
	}
	

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean [][] mat = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0,0);
		
		
		p.setValores(posicao.getLinha()-1, posicao.getColuna()-2);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		p.setValores(posicao.getLinha()-2, posicao.getColuna()-1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		p.setValores(posicao.getLinha()-2, posicao.getColuna()+1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		p.setValores(posicao.getLinha()-1, posicao.getColuna()+2);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		p.setValores(posicao.getLinha()+1, posicao.getColuna()+2);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		p.setValores(posicao.getLinha()+2, posicao.getColuna()+1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		
		p.setValores(posicao.getLinha()+2, posicao.getColuna()-1);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		p.setValores(posicao.getLinha()+1, posicao.getColuna()-2);
		if(getTabuleiro().posicaoExiste(p) && podeMover(p)){
			mat[p.getLinha()][p.getColuna()]=true;
		}
		
		return mat;
	}

}
