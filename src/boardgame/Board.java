package boardgame;

public class Board {
	private Integer rows;
	private Integer columns;

	/* Declaro que o tabuleiro vai receber uma matriz de peças */
	private Piece[][] pieces;

	public Board(Integer rows, Integer columns) {
		if (rows < 1 || columns < 1)
			throw new BoardException("Erro ao criar o tabuleiro: E necessario pelo menos 1 linha e 1 coluna");
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}

	public Integer getRows() {
		return rows;
	}

	public Integer getColumns() {
		return columns;
	}

	/*
	 * Aqui eu retorno a peça que está em determinada posição, informando a linha e
	 * a coluna
	 */
	public Piece piece(int row, int column) {
		if (!positionExists(row, column))
			throw new BoardException("Nao existe essa posicao no tabuleiro !");
		return pieces[row][column];
	}

	/*
	 * Aqui eu retorno a peça que está em determinada posicção, informando uma
	 * posição específica
	 */
	public Piece piece(Position position) {
		if (!positionExists(position))
			throw new BoardException("Nao existe essa posicao no tabuleiro !");
		return pieces[position.getRow()][position.getColumn()];
	}
	
	/*Aloca uma peça no tabuleiro*/
	public void placePiece(Piece piece, Position position) {
		/*Verifica se já existe uma peça no tabuleiro*/
		if (thereIsAPiece(position))
			throw new BoardException("Ja existe uma peça nessa posicao " + position);
		/*Passa a posição da coluna e da linha em que a peça vai ficar*/
		pieces[position.getRow()][position.getColumn()] = piece;
		/*Informa que a peça agora tem uma posição*/
		piece.position = position;
	}
	
	public Piece removePiece (Position position) {
		if(!positionExists(position))
			throw new BoardException("Nao existe essa posicao no tabuleiro !");
		
		if (piece(position) == null)
			return null;
		
		/*Armazeno a peça na variável aux*/
		Piece aux = piece(position);
		/*Informo que a posição para a qual aux tá apontando será nulla*/
		aux.position = null;
		/*Declaro que na minha matriz, a posição informada passa a ter o valor nulo*/
		pieces[position.getRow()][position.getColumn()] = null;
		/*Retorno a peça que foi removida*/
		return aux;
	}
	
	/*Verifica se existe uma posição informando a linha e a coluna*/
	private boolean positionExists (int row, int column) {
		return row >= 0 && row < rows && column >= 0 && column < columns;
	}
	
	/*Verficia se existe uma posição informando uma posição*/
	public boolean positionExists (Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	/*Verifica se existe uma peça em uma determinada posição*/
	public boolean thereIsAPiece (Position position) {
		if (!positionExists(position))
			throw new BoardException("Nao existe essa posicao no tabuleiro !");
		return piece(position) != null;
	}
}
