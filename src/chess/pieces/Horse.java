package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Horse extends ChessPiece {

	public Horse(Board board, Color color) {
		super(board, color);
	}

	public boolean canMove(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p == null || p.getColor() != getColor();
	}

	@Override
	public boolean[][] possibleMovies() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);

		// nw - cima
		p.setValues(position.getRow() - 2, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;

		// nw - baixo
		p.setValues(position.getRow() - 1, position.getColumn() - 2);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;

		// ne - cima
		p.setValues(position.getRow() - 2, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;

		// ne - baixo
		p.setValues(position.getRow() - 1, position.getColumn() + 2);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;

		// se - cima
		p.setValues(position.getRow() + 2, position.getColumn() + 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;

		// se - baixo
		p.setValues(position.getRow() + 1, position.getColumn() + 2);
		if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)
				|| getBoard().positionExists(p) && isThereOpponentPiece(p))
			mat[p.getRow()][p.getColumn()] = true;

		// sw - cima
		p.setValues(position.getRow() + 2, position.getColumn() - 1);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;

		// sw - baixo
		p.setValues(position.getRow() + 1, position.getColumn() - 2);
		if (getBoard().positionExists(p) && canMove(p))
			mat[p.getRow()][p.getColumn()] = true;

		return mat;
	}

	@Override
	public String toString() {
		return "H";
	}
}
