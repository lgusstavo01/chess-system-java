package boardgame;

public class Board {
	private Integer rows;
	private Integer columns;

	/* Declaro que o tabuleiro vai receber uma matriz de peças */
	private Piece[][] pieces;

	public Board(Integer rows, Integer columns) {
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public Integer getColumns() {
		return columns;
	}

	public void setColumns(Integer columns) {
		this.columns = columns;
	}

	/*
	 * Aqui eu retorno a peça que está em determinada posição, informando a linha e
	 * a coluna
	 */
	public Piece piece(int row, int column) {
		return pieces[row][column];
	}

	/*
	 * Aqui eu retorno a peça que está em determinada posicção, informando uma
	 * posição específica
	 */
	public Piece piece(Position position) {
		return pieces[position.getRow()][position.getColumn()];
	}
	
	/*Aloca uma peça no tabuleiro*/
	public void placePiece(Piece piece, Position position) {
		/*Passa a posição da coluna e da linha em que a peça vai ficar*/
		pieces[position.getRow()][position.getColumn()] = piece;
		/*Informa que a peça agora tem uma posição*/
		piece.position = position;
	} 
}
