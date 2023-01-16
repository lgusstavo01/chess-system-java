package chess;

import boardgame.Board;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	/* Uma partida de xadrez, tem que ter um tabuleiro */
	private Board board;

	/* Declaro o tamanho do meu tabuleiro 
	 * e declaro o tabuleiro inicial*/
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
	}

	/*
	 * Para retornarmos uma peça específica, não queremos que o programa tenha visão
	 * da peça em si, apenas da peça que está no tabuleiro. Pra isso, nós
	 * percorremos todo o tabuleiro e retornamos a peça, fazendo um casting para
	 * CHESSPIECE
	 */
	public ChessPiece[][] getPieces() {
		/*Declara uma matriz temporária*/
		ChessPiece[][] temp = new ChessPiece[board.getRows()][board.getColumns()];

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				temp[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return temp;
	}
	
	private void placeNewPiece (char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	/*Responsável por iniciar uma partida de xadrez*/
	private void initialSetup () {
		placeNewPiece('a', 3, new Rook(board, Color.BLACK));
		placeNewPiece('b', 5, new King(board, Color.BLACK));

	}
}
