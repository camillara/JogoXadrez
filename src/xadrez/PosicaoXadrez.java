package xadrez;

import boardgame.Posicao;

public class PosicaoXadrez {
	
	private char coluna;
	private int linha;
	public PosicaoXadrez(char coluna, int linha) {
		if(coluna < 'a' || coluna > 'h' || linha < 1 || linha > 8) {
			throw new XadrezExceptions("Erro instanciando Posição Xadrez. Valores válidos de a1 até h8.");
		}
		this.coluna = coluna;
		this.linha = linha;
	}
	public char getColuna() {
		return coluna;
	}
	public int getLinha() {
		return linha;
	}
	
	protected Posicao toPosicao() { // converter a posicao do tabuleiro para a posicao da matriz.
		return new Posicao(8-linha, coluna - 'a');
		//Lógica: temos 8 linhas, então, para encontrar a linha da matriz basta subtrair 8 do número da linha
		//Lógica: temos 8 colunas que vão de a até h, então, para encontrar a coluna da matriz basta subtrair o caracter da coluna do caracter de a.
	}
	
	protected static PosicaoXadrez fromPosicao(Posicao posicao) {
		return new PosicaoXadrez((char)('a'- posicao.getColuna()), 8-posicao.getLinha());
	}
	
	@Override
	public String toString() {
		return "" + coluna + linha;
	}
	
	

}
