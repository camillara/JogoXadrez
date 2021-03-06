package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez { // ChessMatch

	private int turn;
	private Cores jogadorAtivo;
	private Tabuleiro tabuleiro;
	private boolean check;
	private boolean checkMate;
	private PecaDeXadrez enPassante;
	private PecaDeXadrez promocao;
	
	private List<Peca> pecasTabuleiro = new ArrayList<>();
	private List<Peca> pecasCapturadas = new ArrayList<>();


	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		turn = 1;
		jogadorAtivo = Cores.BRANCO;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Cores getJogadorAtivo() {
		return jogadorAtivo;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}

	public PecaDeXadrez getenPassante() {
		return enPassante;
	}
	
	public PecaDeXadrez getPromocao() {
		return promocao;
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
	
	public boolean [][] movimentosPossiveis(PosicaoXadrez posicaoOrigem){
		Posicao posicao = posicaoOrigem.toPosicao();
		validadePosicaoOrigem(posicao);
		return tabuleiro.peca(posicao).movimentosPossiveis();		
	}	
	
	public PecaDeXadrez movimentoPeca(PosicaoXadrez  posicaoOrigem, PosicaoXadrez posicaoDestino) {
		Posicao origem = posicaoOrigem.toPosicao();
		Posicao destino = posicaoDestino.toPosicao();
		validadePosicaoOrigem(origem);
		validadePosicaoDestino(origem, destino);
		Peca pecaCapturada = makeMove(origem, destino);
		
		if(testaCheck(jogadorAtivo)) {
			undoMove(origem, destino, pecaCapturada);
			throw new XadrezExceptions("Voc?? n??o pode se colocar em xeque!");
		}
		
		PecaDeXadrez pecaMovida = (PecaDeXadrez)tabuleiro.peca(destino);
		
		//# movimento especial: PROMOCAO
		promocao=null;
		if(pecaMovida instanceof Peao) {
			if(pecaMovida.getCor()==Cores.BRANCO && destino.getLinha()==0 || pecaMovida.getCor()==Cores.PRETO && destino.getLinha()== 7) {
				promocao = (PecaDeXadrez) tabuleiro.peca(destino);
				promocao = replacePromocaoPeca("Q");
			}
		}
		
		
		check = (testaCheck(oponente(jogadorAtivo))) ? true : false; // vai verificar se a ap??s a jogada o oponente est?? em check.

		if(testaCheckMate(oponente(jogadorAtivo))) {
			checkMate = true;
		}
		else {			
			nextTurn();
		}
		
		//#movimento especial enPassante
		if(pecaMovida instanceof Peao && (destino.getLinha()== origem.getLinha()-2) || (destino.getLinha()== origem.getLinha()+2)) {
			enPassante = pecaMovida;			
		}
		else {
			enPassante = null;
		}		
		return (PecaDeXadrez) pecaCapturada;
		
	}
	
	public PecaDeXadrez replacePromocaoPeca (String tipo) {
		if(promocao == null) {
			throw new IllegalStateException("N??o ?? poss??vel fazer a promo????o. ");
		}
		if(!tipo.equals("B") && !tipo.equals("N") && !tipo.equals("R") && !tipo.equals("Q")) {
			return promocao;
		}
		Posicao pos = promocao.getPosicaoXadrez().toPosicao();
		Peca p = tabuleiro.removerPeca(pos);
		pecasTabuleiro.remove(p);		
		PecaDeXadrez novaPeca = novaPeca(tipo, promocao.getCor());
		tabuleiro.posicaoPeca(novaPeca, pos);
		pecasTabuleiro.add(novaPeca);
		return novaPeca;
		
		
	}
	
	private PecaDeXadrez novaPeca(String tipo, Cores cor) {
		if(tipo.equals("B")) return new Bispo(tabuleiro, cor);
		if(tipo.equals("N")) return new Cavalo(tabuleiro, cor);
		if(tipo.equals("Q")) return new Rainha(tabuleiro, cor);
		return new Torre(tabuleiro, cor);
		
	}
	
	private Peca makeMove(Posicao origem, Posicao destino) {
		PecaDeXadrez p = (PecaDeXadrez)tabuleiro.removerPeca(origem);
		p.incrementarMovimentos();
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.posicaoPeca(p, destino);
		if(pecaCapturada!=null) {
			pecasTabuleiro.remove(pecaCapturada);
			pecasCapturadas.add(pecaCapturada);			
		}
		
		//#movimento especial: roque do lado do rei
		if(p instanceof Rei && destino.getColuna() == origem.getColuna()+2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna()+3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna()+1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(origemTorre);
			tabuleiro.posicaoPeca(torre, destinoTorre);
			torre.incrementarMovimentos();
		}
		
		//#movimento especial: roque do lado da rainha
		if(p instanceof Rei && destino.getColuna() == origem.getColuna()-2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna()-4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna()-1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(origemTorre);
			tabuleiro.posicaoPeca(torre, destinoTorre);
			torre.incrementarMovimentos();
		}
		
		//#movimento especial enPassante
		if(p instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && pecaCapturada==null) {
				Posicao peaoPosicao;
				if(p.getCor()==Cores.BRANCO) {
					peaoPosicao = new Posicao(destino.getLinha()+1, destino.getColuna());
				}
				else {
					peaoPosicao = new Posicao(destino.getLinha()-1, destino.getColuna());
				}
				pecaCapturada=tabuleiro.removerPeca(peaoPosicao);
				pecasCapturadas.add(pecaCapturada);
				pecasTabuleiro.remove(pecaCapturada);
			}
		}
		
		return pecaCapturada;
	}
	
	private void undoMove(Posicao origem, Posicao destino, Peca pecaCapturada) { // desfazer o movimento caso o ??ltimo movimento deixe o rei em Xeque Mate.
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removerPeca(destino);
		p.decrementarMovimentos();
		tabuleiro.posicaoPeca(p, origem);
		
		if(pecaCapturada!=null) {
			tabuleiro.posicaoPeca(pecaCapturada, destino);
			pecasCapturadas.remove(pecaCapturada);
			pecasTabuleiro.add(pecaCapturada);	
		}
		
		//#movimento especial: roque do lado do rei
		if(p instanceof Rei && destino.getColuna() == origem.getColuna()+2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna()+3);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna()+1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(destinoTorre);
			tabuleiro.posicaoPeca(torre, origemTorre);
			torre.decrementarMovimentos();
		}
		
		//#movimento especial: roque do lado da rainha
		if(p instanceof Rei && destino.getColuna() == origem.getColuna()-2) {
			Posicao origemTorre = new Posicao(origem.getLinha(), origem.getColuna()-4);
			Posicao destinoTorre = new Posicao(origem.getLinha(), origem.getColuna()-1);
			PecaDeXadrez torre = (PecaDeXadrez) tabuleiro.removerPeca(destinoTorre);
			tabuleiro.posicaoPeca(torre, origemTorre);
			torre.decrementarMovimentos();
		}
		
		//#movimento especial enPassante
		if(p instanceof Peao) {
			if(origem.getColuna() != destino.getColuna() && pecaCapturada==enPassante) {
				PecaDeXadrez peao = (PecaDeXadrez)tabuleiro.removerPeca(destino);
				Posicao peaoPosicao;
				if(p.getCor()==Cores.BRANCO) {
					peaoPosicao = new Posicao(3, destino.getColuna());
				}
				else {
					peaoPosicao = new Posicao(4, destino.getColuna());
				}
				tabuleiro.posicaoPeca(peao, peaoPosicao);
			}
		}
	}
	
	private void validadePosicaoOrigem (Posicao posicao) {
		if (!tabuleiro.temPeca(posicao)) {
			throw new XadrezExceptions("N??o existe pe??a na posi????o de origem.");
		}
		if(jogadorAtivo!=((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExceptions("N??o ?? poss??vel mover pe??as do advers??rio.");
		}
		if(!tabuleiro.peca(posicao).existeMovimentoPossivel()) {
			throw new XadrezExceptions("N??o existe movimentos poss??veis para a pe??a de origem.");
		}
	}
	
	private void validadePosicaoDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).movimentosPosiveis(destino)) {
			throw new XadrezExceptions("A pe??a escolhida n??o pode se mover para a posi????o de destino.");
		}
	}
	
	private void nextTurn() {
		turn++;
		jogadorAtivo = (jogadorAtivo == Cores.BRANCO) ? Cores.PRETO :  Cores.BRANCO;
	}
	
	private Cores oponente(Cores cor) {
		return (cor == Cores.BRANCO) ? Cores.PRETO : Cores.BRANCO; //muda a cor para ser a vez do oponente.
	}
	
	private PecaDeXadrez rei (Cores cor) { // ver a cor da pe??a do REI
		List <Peca> lista = pecasTabuleiro.stream().filter(x->((PecaDeXadrez)x).getCor()==cor).collect(Collectors.toList());
		for(Peca p : lista) {
			if(p instanceof Rei) {
				return (PecaDeXadrez) p;
			}
		}
		throw new IllegalStateException("N??o existe o rei da cor " + cor + "no tabuleiro.");
	}
	
	private boolean testaCheck(Cores cor) {
		Posicao posicaoRei = rei(cor).getPosicaoXadrez().toPosicao();
		//pe??as do tabuleiro filtrada pelas cores do concorrente.
		List <Peca> pecasOponente = pecasTabuleiro.stream().filter(x->((PecaDeXadrez)x).getCor()==oponente(cor)).collect(Collectors.toList());
		for (Peca p : pecasOponente) { //ver se o movimento de alguma pe??a da lista de oponentes coloca o rei em check.
			boolean [][] mat = p.movimentosPossiveis();
			if(mat[posicaoRei.getLinha()][posicaoRei.getColuna()]) {
				return true; // retorna verdadeiro se o o Rei estiver em xeque.
			}
		}
		return false; // retorna falso, significa que o Rei n??o est?? em xeque.
	}
	
	private boolean testaCheckMate(Cores cor) {
		if(!testaCheck(cor)) {
			return false;
		}
		//lista de pe??as do tabuleiro filtradas pela cor do argumento.
		List <Peca> lista = pecasTabuleiro.stream().filter(x->((PecaDeXadrez)x).getCor()==cor).collect(Collectors.toList());
		for (Peca p : lista) {
			boolean [][] mat = p.movimentosPossiveis();
			for(int i=0; i<tabuleiro.getLinhas(); i++) {
				for(int j=0; j<tabuleiro.getColunas(); j++) {
					if(mat[i][j]) {
						Posicao origem = ((PecaDeXadrez)p).getPosicaoXadrez().toPosicao();
						Posicao destino = new Posicao(i,j);
						Peca pecaCapturada = makeMove(origem, destino);
						boolean testCheck = testaCheck(cor);
						undoMove(origem, destino, pecaCapturada);
						if(!testCheck) {
							return false; //Rei n??o est?? em check mate
						}
					}
				}
			}
		}
		return true;
	}
	
	private void novaPosicaoPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.posicaoPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
		pecasTabuleiro.add(peca);
	}
	
	private void initialSetup() { // m??todo respons??vel por iniciar a partida de Xadrez, colocando as pe??as no tabuleiro.
		novaPosicaoPeca('a', 1, new Torre(tabuleiro, Cores.BRANCO));
		novaPosicaoPeca('b', 1, new Cavalo(tabuleiro, Cores.BRANCO));
		novaPosicaoPeca('c', 1, new Bispo(tabuleiro, Cores.BRANCO));
		novaPosicaoPeca('d', 1, new Rainha(tabuleiro, Cores.BRANCO));
		novaPosicaoPeca('e', 1, new Rei(tabuleiro, Cores.BRANCO, this));
		novaPosicaoPeca('f', 1, new Bispo(tabuleiro, Cores.BRANCO));
		novaPosicaoPeca('g', 1, new Cavalo(tabuleiro, Cores.BRANCO));
		novaPosicaoPeca('h', 1, new Torre(tabuleiro, Cores.BRANCO));		
		novaPosicaoPeca('a', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		novaPosicaoPeca('b', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		novaPosicaoPeca('c', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		novaPosicaoPeca('d', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		novaPosicaoPeca('e', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		novaPosicaoPeca('f', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		novaPosicaoPeca('g', 2, new Peao(tabuleiro, Cores.BRANCO, this));
		novaPosicaoPeca('h', 2, new Peao(tabuleiro, Cores.BRANCO, this));

		novaPosicaoPeca('a', 8, new Torre(tabuleiro, Cores.PRETO));
		novaPosicaoPeca('b', 8, new Cavalo(tabuleiro, Cores.PRETO));
		novaPosicaoPeca('c', 8, new Bispo(tabuleiro, Cores.PRETO));
		novaPosicaoPeca('d', 8, new Rainha(tabuleiro, Cores.PRETO));
		novaPosicaoPeca('e', 8, new Rei(tabuleiro, Cores.PRETO, this));
		novaPosicaoPeca('g', 8, new Cavalo(tabuleiro, Cores.PRETO));
		novaPosicaoPeca('h', 8, new Torre(tabuleiro, Cores.PRETO));
		novaPosicaoPeca('f', 8, new Bispo(tabuleiro, Cores.PRETO));
		novaPosicaoPeca('a', 7, new Peao(tabuleiro, Cores.PRETO, this));
		novaPosicaoPeca('b', 7, new Peao(tabuleiro, Cores.PRETO, this));
		novaPosicaoPeca('c', 7, new Peao(tabuleiro, Cores.PRETO, this));
		novaPosicaoPeca('d', 7, new Peao(tabuleiro, Cores.PRETO, this));
		novaPosicaoPeca('e', 7, new Peao(tabuleiro, Cores.PRETO, this));
		novaPosicaoPeca('f', 7, new Peao(tabuleiro, Cores.PRETO, this));
		novaPosicaoPeca('g', 7, new Peao(tabuleiro, Cores.PRETO, this));
		novaPosicaoPeca('h', 7, new Peao(tabuleiro, Cores.PRETO, this));
	}

}
