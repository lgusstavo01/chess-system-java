package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
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
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
			/*Converte as 2 posições para posições de matrizes*/
			Position source = sourcePosition.toPosition();
			Position target = targetPosition.toPosition();
			/*Operação para validar, se de fato existia uma peça na posição de origem*/
			validateSourcePosition(source);
			/*Será responsável por informar a peça que foi capturada*/
			Piece capturedPiece = makeMove(source, target);
			/*Tem que fazer um downcast, pois a peça é do tipo Piece*/
			return (ChessPiece)capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		/*Salva a peça da origem*/
		Piece p = board.removePiece(source);
		/*Captura uma possível peça que possa existir no destino*/
		Piece pieceCaptured = board.removePiece(target);
		/*Aloca a peça da posição de origem na posição de destino*/
		board.placePiece(p, target);
		/*Retorna a peça capturada*/
		return pieceCaptured;
	}
	
	private void validateSourcePosition (Position position) {
		if (!board.thereIsAPiece(position))
			throw new ChessException("Não existe peça na posição de origem.");
	}
	
	
	private void placeNewPiece (char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}
	
	/*Responsável por iniciar uma partida de xadrez*/
	private void initialSetup () {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));

	}
}
