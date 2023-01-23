package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}

	/* Verificar se existe alguma peca adversaria no caminho */
	protected boolean isThereOpponentPiece(Position position) {
		/* Pega a peca que foi passada como argumento */
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		/*
		 * Verifica se existe alguma peca no caminho, caso tenha, verifica se eh da
		 * mesma cor
		 */
		return p != null && p.getColor() != color;
	}

}
