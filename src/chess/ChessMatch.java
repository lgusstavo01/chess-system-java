package chess;

import java.util.ArrayList;
import java.util.List;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {

	/* Uma partida de xadrez, tem que ter um tabuleiro */
	private Board board;
	/* Variavel para informar o turno em que se encontra a partida*/
	private int turn;
	/* Variavel para informar qual o jogador vai jogar */
	private Color currentPlayer;
	
	/* Lista das pecas que ainda estao no tabuleiro*/
	List<Piece> piecesOnTheBoard = new ArrayList<>();
	/* Lista das pecas que ja foram capturadas*/
	List<Piece> capturedPieces = new ArrayList<>();
	
	/* Declaro o tamanho do meu tabuleiro 
	 * e declaro o tabuleiro inicial*/
	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.BRANCA;
		initialSetup();
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Color getCurrentPlayer() {
		return currentPlayer;
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
	
	/*Responsavel por printar na tela os movimentos possiveis de uma peca.*/
	public boolean[][] possibleMoves (ChessPosition sourcePosition){
		/*Converte a posicao de xadrez, para uma posicao de matriz normal*/
		Position position = sourcePosition.toPosition();
		/*Valida se existe uma peca na posicao de origem*/
		validateSourcePosition(position);
		/*Retorna uma matriz com os possiveis movimentos da peca*/
		return board.piece(position).possibleMovies();
	}
	
	/* Metodo responsavel por fazer uma jogada */
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
			/*Converte as 2 posições para posições de matrizes*/
			Position source = sourcePosition.toPosition();
			Position target = targetPosition.toPosition();
			/*Operação para validar, se de fato existia uma peça na posição de origem*/
			validateSourcePosition(source);
			/*Operacao para validar se a posicao de destino eh valida*/
			validateTargetPosition(source, target);
			/*Será responsável por informar a peça que foi capturada*/
			Piece capturedPiece = makeMove(source, target);
			/*Declara que trocou de turno*/
			nextTurn();
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
		/* Verifica se alguma peca foi capturada e realiza a condicao */
		if (pieceCaptured != null) {
			capturedPieces.add(pieceCaptured);
			piecesOnTheBoard.remove(pieceCaptured);
		}
		/*Retorna a peça capturada*/
		return pieceCaptured;
	}
	
	private void validateSourcePosition (Position position) {
		if (!board.thereIsAPiece(position))
			throw new ChessException("Nao existe peca na posicao de origem.");
		
		if (!board.piece(position).isThereAnyPossibleMove())
			throw new ChessException("A peca esta presa. Nao existe movimento possivel");
		
		if (currentPlayer != ((ChessPiece)board.piece(position)).getColor())
			throw new ChessException("Existe uma peca na posicao, mas ela eh do adversario.");
	}
	
	private void validateTargetPosition (Position source, Position target) {
		if(!board.piece(source).possibleMove(target))
			throw new ChessException("A peca escolhida nao pode se mover para a posicao de destino.");
	}
	
	/* Metodo responsavel por determinar a troca de turno e definir qual o jogador devera jogar */
	private void nextTurn() {
		/* Incrementa o numero de turno em 1 */
		turn++;
		/* Operacao ternaria, responsavel por alternar o jogador */
		currentPlayer = currentPlayer == Color.BRANCA ? Color.PRETA : Color.BRANCA;
	}
	
	private void placeNewPiece (char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		/* Adiciona uma peca, na minha lista de pecas que estao no tabuleiro */
		piecesOnTheBoard.add(piece);
	}
	
	/*Responsável por iniciar uma partida de xadrez*/
	private void initialSetup () {
		placeNewPiece('c', 1, new Rook(board, Color.BRANCA));
        placeNewPiece('c', 2, new Rook(board, Color.BRANCA));
        placeNewPiece('d', 2, new Rook(board, Color.BRANCA));
        placeNewPiece('e', 2, new Rook(board, Color.BRANCA));
        placeNewPiece('e', 1, new Rook(board, Color.BRANCA));
        placeNewPiece('d', 1, new King(board, Color.BRANCA));

        placeNewPiece('c', 7, new Rook(board, Color.PRETA));
        placeNewPiece('c', 8, new Rook(board, Color.PRETA));
        placeNewPiece('d', 7, new Rook(board, Color.PRETA));
        placeNewPiece('e', 7, new Rook(board, Color.PRETA));
        placeNewPiece('e', 8, new Rook(board, Color.PRETA));
        placeNewPiece('d', 8, new King(board, Color.PRETA));

	}
}
