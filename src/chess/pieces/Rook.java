package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
	}

	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possibleMovies() {
		/* Matriz para auxiliar */
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

		/* Variavel para auxiliar */
		Position p = new Position(0, 0);

		// above - acima

		/*
		 * Declaro que vou comecar a ler a partir da posicao acima daq a peca se
		 * encontra, mas na mesma coluna
		 */
		p.setValues(position.getRow() - 1, position.getColumn());

		/*
		 * Enquanto existir posicao no tabuleiro e nao existir uma peca na posicao
		 * acima, continue
		 */
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			/* Salva em uma matriz todos os locais em que a peca pode se mover */
			mat[p.getRow()][p.getColumn()] = true;
			/*
			 * Olho na linha acima daq eu estava olhando e refaco todo o processo até nao
			 * satisfazer a condicao do while
			 */
			p.setRow(p.getRow() - 1);
		}

		/*
		 * Caso chegue nesse if, ele ira verificar se a peca que se encontra acima e do
		 * oponente, caso seja, ele vai marcar como verdadeiro
		 */
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// left - esquerda

		/*
		 * Declaro que vou comecar a ler a partir da posicao a esquerda daq a peca se
		 * encontra, mas na mesma linha
		 */
		p.setValues(position.getRow(), position.getColumn() - 1);

		/*
		 * Enquanto existir posicao no tabuleiro e nao existir uma peca na posicao a
		 * esquerda, continue
		 */
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			/* Salva em uma matriz todos os locais em que a peca pode se mover */
			mat[p.getRow()][p.getColumn()] = true;
			/*
			 * Olho na coluna a esquerda daq eu estava olhando e refaco todo o processo até
			 * nao satisfazer a condicao do while
			 */
			p.setColumn(p.getColumn() - 1);
		}

		/*
		 * Caso chegue nesse if, ele ira verificar se a peca que se encontra a esquerda
		 * e do oponente, caso seja, ele vai marcar como verdadeiro
		 */
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// right - direita

		/*
		 * Declaro que vou comecar a ler a partir da posicao a direita daq a peca se
		 * encontra, mas na mesma linha
		 */
		p.setValues(position.getRow(), position.getColumn() + 1);

		/*
		 * Enquanto existir posicao no tabuleiro e nao existir uma peca na posicao a
		 * direita, continue
		 */
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			/* Salva em uma matriz todos os locais em que a peca pode se mover */
			mat[p.getRow()][p.getColumn()] = true;
			/*
			 * Olho na coluna a direita daq eu estava olhando e refaco todo o processo até
			 * nao satisfazer a condicao do while
			 */
			p.setColumn(p.getColumn() + 1);
		}

		/*
		 * Caso chegue nesse if, ele ira verificar se a peca que se encontra a direita e
		 * do oponente, caso seja, ele vai marcar como verdadeiro
		 */
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		// below - abaixo

		/*
		 * Declaro que vou comecar a ler a partir da posicao abaixo daq a peca se
		 * encontra, mas na mesma coluna
		 */
		p.setValues(position.getRow() + 1, position.getColumn());

		/*
		 * Enquanto existir posicao no tabuleiro e nao existir uma peca na posicao
		 * abaixo, continue
		 */
		while (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
			/* Salva em uma matriz todos os locais em que a peca pode se mover */
			mat[p.getRow()][p.getColumn()] = true;
			/*
			 * Olho na linha abaixo daq eu estava olhando e refaco todo o processo até nao
			 * satisfazer a condicao do while
			 */
			p.setRow(p.getRow() + 1);
		}

		/*
		 * Caso chegue nesse if, ele ira verificar se a peca que se encontra abaixo e do
		 * oponente, caso seja, ele vai marcar como verdadeiro
		 */
		if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}

		/*
		 * Retorno a matriz com todas as posicoes acima, que sao possiveis de se mover
		 */
		return mat;
	}

}
