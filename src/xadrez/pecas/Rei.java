package xadrez.pecas;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.Cores;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Rei extends PecaDeXadrez{
	
	private PartidaDeXadrez partida;

	public Rei(Tabuleiro tabuleiro, Cores cor, PartidaDeXadrez partida) {
		super(tabuleiro, cor);
		this.partida = partida;
	}
	
	@Override
	public String toString() {
		return "K"; //Rei = King
	}
	
	private boolean podeMover(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez) getTabuleiro().peca(posicao);
		return p==null || p.getCor()!=getCor();
	}
	
	private boolean testaRookCastling(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		//retorna verdadeiro se for uma peca do tipo torre && for da mesma cor do rei && a contagem de movimentos da torre for igual a zero. 
		return p != null && p instanceof Torre && p.getCor() == getCor() && p.getContarMovimentos() == 0;
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
		
		//#Movimento especial roque (castling)
		if(getContarMovimentos()==0 && ! partida.getCheck() ) { // verificar se a contagem de movimentos do rei é igual a zero, e se o rei não está em xeque
			//#movimento especial roque pequeno=  roque do lado do rei.
			Posicao posT1 = new Posicao (posicao.getLinha(), posicao.getColuna()+3); //pegou a posição onde deve estar a torre do rei.
			if(testaRookCastling(posT1)) {
				Posicao p1 = new Posicao (posicao.getLinha(), posicao.getColuna()+1);
				Posicao p2 = new Posicao (posicao.getLinha(), posicao.getColuna()+2);
				if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2)==null) {
					mat[posicao.getLinha()][posicao.getColuna()+2]=true;
				}
			}
			//#movimento especial roque grande=  roque do lado da rainha
			Posicao posT2 = new Posicao (posicao.getLinha(), posicao.getColuna()-4); //pegou a posição onde deve estar a torre do rei.
			if(testaRookCastling(posT2)) {
				Posicao p1 = new Posicao (posicao.getLinha(), posicao.getColuna()-1);
				Posicao p2 = new Posicao (posicao.getLinha(), posicao.getColuna()-2);
				Posicao p3 = new Posicao (posicao.getLinha(), posicao.getColuna()-3);
				if(getTabuleiro().peca(p1) == null && getTabuleiro().peca(p2)==null && getTabuleiro().peca(p3)==null) {
					mat[posicao.getLinha()][posicao.getColuna()-2]=true;
				}
			}
		}
		return mat;
	}

}
