package application;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.PosicaoXadrez;
import xadrez.XadrezExceptions;

public class Programa {

	public static void main(String[] args) {		
		
		Scanner sc = new Scanner (System.in);
		PartidaDeXadrez partida = new PartidaDeXadrez();
		List <PecaDeXadrez> capturadas = new ArrayList<>();
		
		while(!partida.getCheckMate()) {
			try {
				UI.limparTela();
				UI.imprimirPartida(partida, capturadas);
				System.out.println();
				System.out.print("Origem: ");
				PosicaoXadrez origem = UI.lerPosicaoXadrez(sc);
				
				boolean [][] movimentosPossiveis = partida.movimentosPossiveis(origem);
				UI.limparTela();
				UI.imprimirTabuleiro(partida.getPecas(), movimentosPossiveis);
				
				System.out.println();
				System.out.print("Destino: ");
				PosicaoXadrez destino = UI.lerPosicaoXadrez(sc);
				
				PecaDeXadrez pecaCapturada = partida.movimentoPeca(origem, destino);
				
				if(pecaCapturada!= null) {
					capturadas.add(pecaCapturada);
				}
				if(partida.getPromocao()!=null) {
					System.out.print("Informe a peça para promoção (B/N/R/Q): ");
					String tipo = sc.nextLine();
					partida.replacePromocaoPeca(tipo);
				}
			}
			catch(XadrezExceptions e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			
		}
		UI.limparTela();
		UI.imprimirPartida(partida, capturadas);
		

	}

}
