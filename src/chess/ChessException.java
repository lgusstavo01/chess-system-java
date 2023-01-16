package chess;

import boardgame.BoardException;

/*Podemos considerar que toda excessão do jogo de xadrêz, 
 * pode ser uma excessão de tabuleiro
 * para facilitar para o programa, 
 * nós declaramos que ChessException extends BoardException*/
public class ChessException extends BoardException {

	private static final long serialVersionUID = 1L;

	public ChessException(String msg) {
		super(msg);
	}
}