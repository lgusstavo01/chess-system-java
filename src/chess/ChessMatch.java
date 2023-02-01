package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Pawn;
import chess.pieces.Rook;

public class ChessMatch {

	/* Uma partida de xadrez, tem que ter um tabuleiro */
	private Board board;
	/* Variavel para informar o turno em que se encontra a partida */
	private int turn;
	/* Variavel para informar qual o jogador vai jogar */
	private Color currentPlayer;
	/* Verifica se a partida se encontra em check */
	private boolean check;
	/* Verifica se a partida se encontra em checkMate */
	private boolean checkMate;

	/* Lista das pecas que ainda estao no tabuleiro */
	List<Piece> piecesOnTheBoard = new ArrayList<>();
	/* Lista das pecas que ja foram capturadas */
	List<Piece> capturedPieces = new ArrayList<>();

	/*
	 * Declaro o tamanho do meu tabuleiro e declaro o tabuleiro inicial
	 */
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

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	/*
	 * Para retornarmos uma peça específica, não queremos que o programa tenha visão
	 * da peça em si, apenas da peça que está no tabuleiro. Pra isso, nós
	 * percorremos todo o tabuleiro e retornamos a peça, fazendo um casting para
	 * CHESSPIECE
	 */
	public ChessPiece[][] getPieces() {
		/* Declara uma matriz temporária */
		ChessPiece[][] temp = new ChessPiece[board.getRows()][board.getColumns()];

		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getColumns(); j++) {
				temp[i][j] = (ChessPiece) board.piece(i, j);
			}
		}
		return temp;
	}

	/* Responsavel por printar na tela os movimentos possiveis de uma peca. */
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		/* Converte a posicao de xadrez, para uma posicao de matriz normal */
		Position position = sourcePosition.toPosition();
		/* Valida se existe uma peca na posicao de origem */
		validateSourcePosition(position);
		/* Retorna uma matriz com os possiveis movimentos da peca */
		return board.piece(position).possibleMovies();
	}

	/* Metodo responsavel por fazer uma jogada */
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		/* Converte as 2 posições para posições de matrizes */
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		/* Operação para validar, se de fato existia uma peça na posição de origem */
		validateSourcePosition(source);
		/* Operacao para validar se a posicao de destino eh valida */
		validateTargetPosition(source, target);
		/* Será responsável por informar a peça que foi capturada */
		Piece capturedPiece = makeMove(source, target);

		/* Se o jogador ficar em check apos a jogada, entra nessa condicao */
		if (testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("Voce nao pode se colocar em check.");
		}

		/* Verifica se o opoente ficou em check */
		check = testCheck(opponent(currentPlayer)) ? true : false;

		/* Verifica se o movimento colocou o oponente em checkmate */
		if (testCheckMate(opponent(currentPlayer)))
			checkMate = true;
		else
			/* Declara que trocou de turno */
			nextTurn();

		/* Tem que fazer um downcast, pois a peça é do tipo Piece */
		return (ChessPiece) capturedPiece;
	}

	/* Metodo responsavel por realizar o movimento */
	private Piece makeMove(Position source, Position target) {
		/* Salva a peça da origem */
		ChessPiece p = (ChessPiece) board.removePiece(source);
		/* Adiciona um movimento a peca */
		p.increaseMoveCount();
		/* Captura uma possível peça que possa existir no destino */
		Piece pieceCaptured = board.removePiece(target);
		/* Aloca a peça da posição de origem na posição de destino */
		board.placePiece(p, target);
		/* Verifica se alguma peca foi capturada e realiza a condicao */
		if (pieceCaptured != null) {
			capturedPieces.add(pieceCaptured);
			piecesOnTheBoard.remove(pieceCaptured);
		}
		/* Retorna a peça capturada */
		return pieceCaptured;
	}

	/* Metodo responsavel por desfazer o movimento */
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece) board.removePiece(target);
		/* Retira um movimento da peca */
		p.decreaseMoveCount();

		board.placePiece(p, source);
		/* Caso alguma peca tenha sido capturada */
		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position))
			throw new ChessException("Nao existe peca na posicao de origem.");

		if (!board.piece(position).isThereAnyPossibleMove())
			throw new ChessException("A peca esta presa. Nao existe movimento possivel");

		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor())
			throw new ChessException("Existe uma peca na posicao, mas ela eh do adversario.");
	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target))
			throw new ChessException("A peca escolhida nao pode se mover para a posicao de destino.");
	}

	/*
	 * Metodo responsavel por determinar a troca de turno e definir qual o jogador
	 * devera jogar
	 */
	private void nextTurn() {
		/* Incrementa o numero de turno em 1 */
		turn++;
		/* Operacao ternaria, responsavel por alternar o jogador */
		currentPlayer = currentPlayer == Color.BRANCA ? Color.PRETA : Color.BRANCA;
	}

	/* Metodo responsavel por informar o oponente */
	private Color opponent(Color color) {
		return color == Color.BRANCA ? Color.PRETA : Color.BRANCA;
	}

	/* Metodo responsavel por identificar o rei de determinada cor */
	private ChessPiece king(Color color) {
		/* Forma padrao de se filtrar uma lista */
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		/* Se chegar nessa excecao, siginifica que o programa esta com algum erro */
		throw new IllegalStateException("Nao existe rei da cor " + color + " no tabuleiro.");
	}

	/* Metodo responsavel por determinar se o rei encontra-se em check */
	private boolean testCheck(Color color) {
		/* Retorna a posicao do rei em formato de matriz */
		Position kingPosition = king(color).getChessPosition().toPosition();
		/* Lista das pecas do oponente da cor do rei */
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());

		for (Piece p : opponentPieces) {
			/* Retorna uma matriz com os movimentos possiveis de uma peca da minha lista */
			boolean[][] mat = p.possibleMovies();

			/*
			 * Se nessa matriz, na linha e coluna em que o rei se encontra, existeir um
			 * movimento verdadeiro, significa que o rei está em check
			 */
			if (mat[kingPosition.getRow()][kingPosition.getColumn()])
				return true;

		}
		return false;
	}

	/* Metodo responsavel por verificar se a partida se encontra em checkMate */
	private boolean testCheckMate(Color color) {
		if (!testCheck(color))
			return false;

		/*
		 * Comeca o teste para verificar se todas as pecas dotabuleiro dessa determinada
		 * cor, tem algum movimento que tire a partida do check
		 */

		/* Pega uma lista de pecas da cor do meu rei */
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());

		/* Percorre todas as pecas */
		for (Piece p : list) {
			/* Declara uma matriz com os movimentos possiveis da peca */
			boolean[][] mat = p.possibleMovies();

			/*
			 * Percorre as linhas e colunas da minha matriz de possiveis movimentos de uma
			 * peca
			 */

			/*
			 * Esse processo vai se repetir para cada peca de determinada cor, que se
			 * encontre no tabuleiro. Caso nao exista nenhum movimento que seja capaz de
			 * tirar a partida de checkMate, o jogo acabou.
			 */

			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getColumns(); j++) {
					/* Verifica se a posicao de I e J eh um movimento possivel para a peca */
					if (mat[i][j]) {
						/* Pega a posicao de origem da peca */
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						/* Define a posicao de destino da peca de origem */
						Position target = new Position(i, j);
						/* Realiza o movimento da peca */
						Piece capturedPiece = makeMove(source, target);
						/* Verifica se a partida permaneceu em checkMate */
						boolean testCheck = testCheck(color);
						/* Desfaz o movimento */
						undoMove(source, target, capturedPiece);

						/*
						 * Faz o teste para ver se o movimento que a peca fez, conseguiu tirar a partida
						 * de checkMate
						 */
						if (!testCheck)
							return false;
					}
				}
			}
		}
		return true;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		/* Adiciona uma peca, na minha lista de pecas que estao no tabuleiro */
		piecesOnTheBoard.add(piece);
	}

	/* Responsável por iniciar uma partida de xadrez */
	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.BRANCA));
		placeNewPiece('e', 1, new King(board, Color.BRANCA));
		placeNewPiece('h', 1, new Rook(board, Color.BRANCA));
		placeNewPiece('a', 2, new Pawn(board, Color.BRANCA));
		placeNewPiece('b', 2, new Pawn(board, Color.BRANCA));
		placeNewPiece('c', 2, new Pawn(board, Color.BRANCA));
		placeNewPiece('d', 2, new Pawn(board, Color.BRANCA));
		placeNewPiece('e', 2, new Pawn(board, Color.BRANCA));
		placeNewPiece('f', 2, new Pawn(board, Color.BRANCA));
		placeNewPiece('g', 2, new Pawn(board, Color.BRANCA));
		placeNewPiece('h', 2, new Pawn(board, Color.BRANCA));

		placeNewPiece('a', 8, new Rook(board, Color.PRETA));
		placeNewPiece('e', 8, new King(board, Color.PRETA));
		placeNewPiece('h', 8, new Rook(board, Color.PRETA));
		placeNewPiece('a', 7, new Pawn(board, Color.PRETA));
		placeNewPiece('b', 7, new Pawn(board, Color.PRETA));
		placeNewPiece('c', 7, new Pawn(board, Color.PRETA));
		placeNewPiece('d', 7, new Pawn(board, Color.PRETA));
		placeNewPiece('e', 7, new Pawn(board, Color.PRETA));
		placeNewPiece('f', 7, new Pawn(board, Color.PRETA));
		placeNewPiece('g', 7, new Pawn(board, Color.PRETA));
		placeNewPiece('h', 7, new Pawn(board, Color.PRETA));

	}
}
