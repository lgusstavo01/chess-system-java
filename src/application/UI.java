package application;

import java.util.Arrays;
import java.util.Currency;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	/* Codigo para limpar a tela */
	public static void clearScreen() {
		System.out.println("\033[H\033[2J");
		System.out.flush();
	}

	/* Lendo uma peça de xadrez */
	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			/* Exemplo de entrada: a1 */
			/* Leio a primeira letra do string. */
			String s = sc.nextLine();
			char column = s.charAt(0);
			/* Leio a segunda letra da string e a converto para inteiro */
			int row = Integer.parseInt(s.substring(1));
			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro na leitura da posicao. Os valores validos sao de a1 ate h8");
		}
	}
	
	/* Metodo responsavel por imprimir a partida */
	public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
		printBoard(chessMatch.getPieces());
		System.out.println();
		printCapturedPieces(captured);
		System.out.println();
		System.out.println("Turno: " + chessMatch.getTurn());
		
		/* Verifica se a partida nao se encontra em checkMate */
		
		if(!chessMatch.getCheckMate()) {
			System.out.println("Esperando jogador: " + chessMatch.getCurrentPlayer());
			System.out.println();
				if(chessMatch.getCheck()) 
					System.out.println("CHECK!");
		} else {
			System.out.println("CHECKMATE !!");
			System.out.println("Vencedor: " + chessMatch.getCurrentPlayer());
		}
	}

	/* Printo o tabuleiro na tela */
	public static void printBoard(ChessPiece[][] piece) {
		for (int i = 0; i < piece.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < piece.length; j++) {
				printPiece(piece[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	/*Printo o tabuleiro com as posicoes que as pecas podem se mover*/
	public static void printBoard(ChessPiece[][] piece, boolean[][] possibleMoves) {
		for (int i = 0; i < piece.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < piece.length; j++) {
				printPiece(piece[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	/* Printo a peça na tela */
	private static void printPiece(ChessPiece piece, boolean background) {
		if (background)
			System.out.print(ANSI_BLUE_BACKGROUND);
		if (piece == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			if (piece.getColor() == Color.BRANCA) {
				System.out.print(ANSI_WHITE + piece + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}
	
	private static void printCapturedPieces(List<ChessPiece> captured) {
		/* Filtrando as pecas capturadas de cor branca */
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor() == Color.BRANCA).collect(Collectors.toList());
		/* Filtrando as pecas capturadas de cor preta */
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor() == Color.PRETA).collect(Collectors.toList());
	
		System.out.println("Pecas capturadas: ");
		System.out.println();
		System.out.print("Brancas: ");
		/* Garantir que as pecas impressas terao a cor branca */
		System.out.print(ANSI_WHITE);
		/* Macete para imprimir um array no Java */
		System.out.print(Arrays.toString(white.toArray()));
		/* Reseta para a cor padrao */
		System.out.print(ANSI_RESET);
		System.out.println();
		System.out.println();
		/* Garantir que as pecas impressas terao a cor amarela */
		System.out.print(ANSI_YELLOW);
		System.out.print("Pretas: ");
		System.out.print(Arrays.toString(black.toArray()));
		/* Reseta para a cor padrao */
		System.out.print(ANSI_RESET);
		System.out.println();
	}
}















