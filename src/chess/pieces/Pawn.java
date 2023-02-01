package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
	}

	@Override
	public boolean[][] possibleMovies() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0, 0);

		if (getColor() == Color.BRANCA) {
			/* Testa se o peao pode andar uma casa para cima*/
			p.setValues(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) 
				mat[p.getRow()][p.getColumn()] = true;

			/* Testa se o peao pode andar 2 casas para cima */
			p.setValues(position.getRow() - 2, position.getColumn());
			Position p2 = new Position(position.getRow() - 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount() == 0 && !getBoard().thereIsAPiece(p2) && getBoard().positionExists(p2)) 
				mat[p.getRow()][p.getColumn()] = true;

			/* Testa se o peao pode andar uma casa na diagonal a esquerda */
			p.setValues(position.getRow() - 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p))
				mat[p.getRow()][p.getColumn()] = true;
			
			/* Testa se o peao pode andar uma casa na diagonal a direita */
			p.setValues(position.getRow() - 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p))
				mat[p.getRow()][p.getColumn()] = true;
		} else {
			p.setValues(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) 
				mat[p.getRow()][p.getColumn()] = true;

			/* Testa se o peao pode andar 2 casas para cima */
			p.setValues(position.getRow() + 2, position.getColumn());
			Position p2 = new Position(position.getRow() + 1, position.getColumn());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getMoveCount() == 0 && !getBoard().thereIsAPiece(p2) && getBoard().positionExists(p2)) 
				mat[p.getRow()][p.getColumn()] = true;

			/* Testa se o peao pode andar uma casa na diagonal a esquerda */
			p.setValues(position.getRow() + 1, position.getColumn() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p))
				mat[p.getRow()][p.getColumn()] = true;
			
			/* Testa se o peao pode andar uma casa na diagonal a direita */
			p.setValues(position.getRow() + 1, position.getColumn() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p))
				mat[p.getRow()][p.getColumn()] = true;
		}
		return mat;
	}
	
	@Override
	public String toString() {
		return "P";
	}
}
