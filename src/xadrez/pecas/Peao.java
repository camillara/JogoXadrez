package xadrez.pecas;

import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.Cores;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez{
	
	private PartidaDeXadrez partida;

	public Peao(Tabuleiro tabuleiro, Cores cor, PartidaDeXadrez partida) {
		super(tabuleiro, cor);
		this.partida=partida;
	}
	
	@Override
	public String toString() {
		return "P"; //PEAO = PAWN
	}

	@Override
	public boolean[][] movimentosPossiveis() {
		boolean [][] mat = new boolean [getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0,0);
		
		if(getCor() == Cores.BRANCO) {
			p.setValores(posicao.getLinha()-1, posicao.getColuna()); //uma posicao na linha acima, na mesma coluna
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) { // verificar se existe a linha acima e se não tem peça.
				mat [p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha()-2, posicao.getColuna()); //uma posicao na linha acima, na mesma coluna
			Posicao p2 = new Posicao (posicao.getLinha()-1, posicao.getColuna());
			
			// MOVER 2 CASAS NA PRIMEIRA JOGADA
			//verificar se existe a posicao p e p2 na linha acima, se não tem peça na posicao de p e p2, e se é o primeiro movimento da peca.
			if(getTabuleiro().posicaoExiste(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temPeca(p) && !getTabuleiro().temPeca(p2) && getContarMovimentos() ==0) { 
				mat [p.getLinha()][p.getColuna()] = true;
			}
			
			//CAPTURAR PEÇA DO OPONENTE À ESQUERDA
			p.setValores(posicao.getLinha()-1, posicao.getColuna()-1); //uma posicao na linha acima, na mesma coluna
			if(getTabuleiro().posicaoExiste(p) && temPecasOponetes(p)) { // verificar se existe a linha acima/esquerda e se tem peça de do oponente.
				mat [p.getLinha()][p.getColuna()] = true;
			}
			
			//CAPTURAR PEÇA DO OPONENTE À DIREITA
			p.setValores(posicao.getLinha()-1, posicao.getColuna()+1); //uma posicao na linha acima, na mesma coluna
			if(getTabuleiro().posicaoExiste(p) && temPecasOponetes(p)) { // verificar se existe a linha acima/direita e se tem peça de do oponente.
				mat [p.getLinha()][p.getColuna()] = true;
			}
			
			//#movimento especial enPassante BRANCAS
			if(posicao.getLinha()==3) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna()-1);
				if(getTabuleiro().posicaoExiste(esquerda) && temPecasOponetes(esquerda)&& getTabuleiro().peca(esquerda)==partida.getenPassante()) {
					mat [esquerda.getLinha()-1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna()+1);
				if(getTabuleiro().posicaoExiste(direita) && temPecasOponetes(direita)&& getTabuleiro().peca(direita)==partida.getenPassante()) {
					mat [direita.getLinha()-1][direita.getColuna()] = true;
				}
			}

		}
		else {
			p.setValores(posicao.getLinha()+1, posicao.getColuna()); //uma posicao na linha acima, na mesma coluna
			if(getTabuleiro().posicaoExiste(p) && !getTabuleiro().temPeca(p)) { // verificar se existe a linha acima e se não tem peça.
				mat [p.getLinha()][p.getColuna()] = true;
			}
			p.setValores(posicao.getLinha()+2, posicao.getColuna()); //uma posicao na linha acima, na mesma coluna
			Posicao p2 = new Posicao (posicao.getLinha()+1, posicao.getColuna());
			
			// MOVER 2 CASAS NA PRIMEIRA JOGADA
			//verificar se existe a posicao p e p2 na linha acima, se não tem peça na posicao de p e p2, e se é o primeiro movimento da peca.
			if(getTabuleiro().posicaoExiste(p) && getTabuleiro().posicaoExiste(p2) && !getTabuleiro().temPeca(p) && !getTabuleiro().temPeca(p2) && getContarMovimentos() ==0) { 
				mat [p.getLinha()][p.getColuna()] = true;
			}
			
			//CAPTURAR PEÇA DO OPONENTE À ESQUERDA
			p.setValores(posicao.getLinha()+1, posicao.getColuna()-1); //uma posicao na linha acima, na mesma coluna
			if(getTabuleiro().posicaoExiste(p) && temPecasOponetes(p)) { // verificar se existe a linha acima/esquerda e se tem peça de do oponente.
				mat [p.getLinha()][p.getColuna()] = true;
			}
			
			//CAPTURAR PEÇA DO OPONENTE À DIREITA
			p.setValores(posicao.getLinha()+1, posicao.getColuna()+1); //uma posicao na linha acima, na mesma coluna
			if(getTabuleiro().posicaoExiste(p) && temPecasOponetes(p)) { // verificar se existe a linha acima/direita e se tem peça de do oponente.
				mat [p.getLinha()][p.getColuna()] = true;
			}
			
			//#movimento especial enPassante PRETAS
			if(posicao.getLinha()==4) {
				Posicao esquerda = new Posicao(posicao.getLinha(), posicao.getColuna()-1);
				if(getTabuleiro().posicaoExiste(esquerda) && temPecasOponetes(esquerda)&& getTabuleiro().peca(esquerda)==partida.getenPassante()) {
					mat [esquerda.getLinha()+1][esquerda.getColuna()] = true;
				}
				Posicao direita = new Posicao(posicao.getLinha(), posicao.getColuna()+1);
				if(getTabuleiro().posicaoExiste(direita) && temPecasOponetes(direita)&& getTabuleiro().peca(direita)==partida.getenPassante()) {
					mat [direita.getLinha()+1][direita.getColuna()] = true;
				}
			}
		}
		
		return mat;
	}
}
