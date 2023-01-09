package boardgame;

public class Piece {
	
	/*O protected faz com que apenas classes e subclasses do mesmo pacote tenham acesso
	 * Toda peça tem uma posição*/
	protected Position position;
	
	/*Faz uma associação entre a peça e o tabuleiro*/
	private Board board;
	
	/*Só declara o tabuleiro, pois o valor inicial da peça é null, então não tem necessidade
	 * de declarar*/
	
	public Piece(Board board) {
		this.board = board;
	}

	/*Por se tratar de um mecanismo interno, não é interessante deixar que qualquer classe
	 * tenha acesso, logo usa o protected para filtrar e permitir apenas classes e subclasses
	 * do mesmo pacote.*/
	protected Board getBoard() {
		return board;
	}
}
