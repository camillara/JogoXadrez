package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import xadrez.Cores;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;

public class UI {
	
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

		//cores do texto
		public static final String ANSI_RESET = "\u001B[0m";
		public static final String ANSI_BLACK = "\u001B[30m";
		public static final String ANSI_RED = "\u001B[31m";
		public static final String ANSI_GREEN = "\u001B[32m";
		public static final String ANSI_YELLOW = "\u001B[33m";
		public static final String ANSI_BLUE = "\u001B[34m";
		public static final String ANSI_PURPLE = "\u001B[35m";
		public static final String ANSI_CYAN = "\u001B[36m";
		public static final String ANSI_WHITE = "\u001B[37m";

		//cores do fundo
		public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
		public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
		public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
		public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
		public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
		public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
		public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
		public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
	
		
	// limpar a tela
	public static void limparTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
	
	public static PosicaoXadrez lerPosicaoXadrez(Scanner sc) {
		try {
			String s = sc.nextLine().toLowerCase();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new PosicaoXadrez(coluna, linha);
		}
		catch(RuntimeException e) {
			throw new InputMismatchException("Erro lendo posição do Xadrez. Valores válidos são de a1 até h8.");
		}
	}
	
	public static void imprimirPartida(PartidaDeXadrez partida, List<PecaDeXadrez> capturada) {
		imprimirTabuleiro(partida.getPecas());
		System.out.println();
		imprimirPecasCapturadas(capturada);
		System.out.println();
		System.out.println("Turno: " + partida.getTurn());
		if(!partida.getCheckMate()) {			
			System.out.println("Esperando jogador: " + partida.getJogadorAtivo());
			if(partida.getCheck()) {
				System.out.println("CHECK!!!");
			}
		}
		else {
			System.out.println("CHECKMATE!!!");
			System.out.println("Vencedor: " + partida.getJogadorAtivo());
		}
		
	}
	
	public static void imprimirTabuleiro(PecaDeXadrez[][] pecas) {
		for(int i=0; i<pecas.length; i++) {
			System.out.print((8-i) + " "); //imprimir o número da linha.
			for(int j=0; j<pecas.length; j++) {
				imprimePeca(pecas[i][j], false); //imprimir cada peça na posição [i][j]
			}
			System.out.println(); //pular a linha para inciar a próxima linha.
		}
		System.out.println("  a b c d e f g h");
		
	}

	public static void imprimirTabuleiro(PecaDeXadrez[][] pecas, boolean[][] movimentosPossiveis) {
		for(int i=0; i<pecas.length; i++) {
			System.out.print((8-i) + " "); //imprimir o número da linha.
			for(int j=0; j<pecas.length; j++) {
				imprimePeca(pecas[i][j], movimentosPossiveis[i][j]); //imprimir cada peça na posição [i][j]
			}
			System.out.println(); //pular a linha para inciar a próxima linha.
		}
		System.out.println("  a b c d e f g h");
		
	}
	
	private static void imprimePeca(PecaDeXadrez peca, boolean background) { // método para imprimir cada posicao do tabuleiro. Se houver peça, imprime a peça. Se estier nulo, imprime -
		if(background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if(peca == null ) {
			System.out.print("-" + ANSI_RESET);
		}
		else {
			if(peca.getCor() == Cores.BRANCO) {
				System.out.print(ANSI_WHITE + peca + ANSI_RESET);
			}
			else {
				System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}
	
	private static void imprimirPecasCapturadas(List <PecaDeXadrez> capturadas) { // criar lista de peças capturadas
		List<PecaDeXadrez> brancas = capturadas.stream().filter(x -> x.getCor() == Cores.BRANCO).collect(Collectors.toList()); // filtrar lista de peças capturadas brancas
		List<PecaDeXadrez> pretas = capturadas.stream().filter(x -> x.getCor() == Cores.PRETO).collect(Collectors.toList()); // filtrar lista de peças capturadas pretas
		System.out.println("PEÇAS CAPUTRADAS: ");
		System.out.print("Brancas:");
		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(brancas.toArray()));
		
		System.out.print(ANSI_RESET);
		
		System.out.print("Pretas:\t");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(pretas.toArray()));
		System.out.print(ANSI_RESET);
	}
	
}
