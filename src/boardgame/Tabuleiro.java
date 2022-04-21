package boardgame;

public class Tabuleiro { //Board
	
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	public Tabuleiro(int linhas, int colunas) {
		if(linhas<1 && colunas<1) {
			throw new TabuleiroException("Erro ao criar tabuleiro! É necessário que pelo menos uma linha e uma coluna");
		}
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Peca peca(int linhas, int colunas) {
		if(!posicaoExiste(linhas, colunas)){
			throw new TabuleiroException("Posição inexistente.");
		}
		return pecas[linhas][colunas];
	}
	
	public Peca peca (Posicao posicao) {
		if(!posicaoExiste(posicao)){
			throw new TabuleiroException("Posição inexistente.");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()];
	}
	
	public void posicaoPeca(Peca peca, Posicao posicao) {
		if(temPeca(posicao)) {
			throw new TabuleiroException("Já existe peça nessa posição: " + posicao);
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao=posicao;
	}
	
	private boolean posicaoExiste(int linha, int coluna) { // testa valores válidos para linhas e colunas
		return linha >=0 && linha< linhas && coluna>=0 && coluna<colunas;
	}
	
	public boolean posicaoExiste(Posicao posicao) { // verificar se uma posição existe no tabuleiro.
		return posicaoExiste(posicao.getLinha(), posicao.getColuna()); //chamou a função da linha 44 e passou o argumento.
	}
	
	public boolean temPeca(Posicao posicao) { // testar se já existe peça na posição informada.
		if(!posicaoExiste(posicao)){ // primeiro vai testar se a posição informada existe ou não.
			throw new TabuleiroException("Posição inexistente.");
		}
		return peca(posicao)!=null;		
	}

}
