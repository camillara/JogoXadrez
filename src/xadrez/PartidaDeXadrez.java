package xadrez;

import boardgame.Peca;
import boardgame.Posicao;
import boardgame.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez { // ChessMatch

	private int turn;
	private Cores jogadorAtivo;
	private Tabuleiro tabuleiro;

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
		nextTurn();
		return (PecaDeXadrez) pecaCapturada;
		
	}
	
	private Peca makeMove(Posicao origem, Posicao destino) {
		Peca p = tabuleiro.removerPeca(origem);
		Peca pecaCapturada = tabuleiro.removerPeca(destino);
		tabuleiro.posicaoPeca(p, destino);
		return pecaCapturada;
	}
	
	private void validadePosicaoOrigem (Posicao posicao) {
		if (!tabuleiro.temPeca(posicao)) {
			throw new XadrezExceptions("Não existe peça na posição de origem.");
		}
		if(jogadorAtivo!=((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new XadrezExceptions("Não é possível mover peças do adversário.");
		}
		if(!tabuleiro.peca(posicao).existeMovimentoPossivel()) {
			throw new XadrezExceptions("Não existe movimentos possíveis para a peça de origem.");
		}
	}
	
	private void validadePosicaoDestino(Posicao origem, Posicao destino) {
		if(!tabuleiro.peca(origem).movimentosPosiveis(destino)) {
			throw new XadrezExceptions("A peça escolhida não pode se mover para a posição de destino.");
		}
	}
	
	private void nextTurn() {
		turn++;
		jogadorAtivo = (jogadorAtivo == Cores.BRANCO) ? Cores.PRETO :  Cores.BRANCO;
	}
	
	private void novaPosicaoPeca(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.posicaoPeca(peca, new PosicaoXadrez(coluna, linha).toPosicao());
	}
	
	private void initialSetup() { // método responsável por iniciar a partida de Xadrez, colocando as peças no tabuleiro.
		novaPosicaoPeca('c', 1, new Torre(tabuleiro, Cores.BRANCO));
		novaPosicaoPeca('c', 2, new Torre(tabuleiro, Cores.BRANCO));
		novaPosicaoPeca('d', 2, new Torre(tabuleiro, Cores.BRANCO));
		novaPosicaoPeca('e', 2, new Torre(tabuleiro, Cores.BRANCO));
		novaPosicaoPeca('e', 1, new Torre(tabuleiro, Cores.BRANCO));
		novaPosicaoPeca('d', 1, new Rei(tabuleiro, Cores.BRANCO));

		novaPosicaoPeca('c', 7, new Torre(tabuleiro, Cores.PRETO));
		novaPosicaoPeca('c', 8, new Torre(tabuleiro, Cores.PRETO));
		novaPosicaoPeca('d', 7, new Torre(tabuleiro, Cores.PRETO));
		novaPosicaoPeca('e', 7, new Torre(tabuleiro, Cores.PRETO));
		novaPosicaoPeca('e', 8, new Torre(tabuleiro, Cores.PRETO));
		novaPosicaoPeca('d', 8, new Rei(tabuleiro, Cores.PRETO));
	}

}
