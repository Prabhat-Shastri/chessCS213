package chess;
import java.util.ArrayList;
public class Board {
    private Piece[][] board;
    private Position enPassantTarget;
    public Board() {
        //initializing a new board with their starting positions 
        board = new Piece[8][8];
        setupInitialBoard();
    }
    public Board(Board other) {
        board = new Piece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (other.board[i][j] != null) {
                    board[i][j] = copyPiece(other.board[i][j]);
                }
            }
        }
        this.enPassantTarget = other.enPassantTarget;
    }
    private Piece copyPiece(Piece piece) {
        Position pos = new Position(piece.getPosition().getFile(), piece.getPosition().getRank());
        Piece newPiece = null;
        //creates a new piece
        if (piece instanceof Pawn) {
            newPiece = new Pawn(piece.isWhite(), pos);
        } else if (piece instanceof Rook) {
            newPiece = new Rook(piece.isWhite(), pos);
        } else if (piece instanceof Knight) {
            newPiece = new Knight(piece.isWhite(), pos);
        } else if (piece instanceof Bishop) {
            newPiece = new Bishop(piece.isWhite(), pos);
        } else if (piece instanceof Queen) {
            newPiece = new Queen(piece.isWhite(), pos);
        } else if (piece instanceof King) {
            newPiece = new King(piece.isWhite(), pos);
        }
        //if it has moved, it sets hasmoved equal to true
        //helps in cases like castling
        if (piece.hasMoved()) {
            newPiece.hasMoved = true;
        }
        return newPiece;
    }
    private void setupInitialBoard() {
        // Set up pawns
        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn(true, new Position(i, 1));
            board[6][i] = new Pawn(false, new Position(i, 6));
        }
        // Set up other pieces 
        board[0][0] = new Rook(true, new Position(0, 0));
        board[0][1] = new Knight(true, new Position(1, 0));
        board[0][2] = new Bishop(true, new Position(2, 0));
        board[0][3] = new Queen(true, new Position(3, 0));
        board[0][4] = new King(true, new Position(4, 0));
        board[0][5] = new Bishop(true, new Position(5, 0));
        board[0][6] = new Knight(true, new Position(6, 0));
        board[0][7] = new Rook(true, new Position(7, 0));
        board[7][0] = new Rook(false, new Position(0, 7));
        board[7][1] = new Knight(false, new Position(1, 7));
        board[7][2] = new Bishop(false, new Position(2, 7));
        board[7][3] = new Queen(false, new Position(3, 7));
        board[7][4] = new King(false, new Position(4, 7));
        board[7][5] = new Bishop(false, new Position(5, 7));
        board[7][6] = new Knight(false, new Position(6, 7));
        board[7][7] = new Rook(false, new Position(7, 7));
    }
    public Piece getPiece(Position pos) {
        if (!pos.isValid()) {
            return null;
        }
        return board[pos.getRank()][pos.getFile()];
        // at a particular position, it returns the piece there.
        // if nothing present, returns null 
    }
    public void setPiece(Position pos, Piece piece) {
        if (pos.isValid()) {
            board[pos.getRank()][pos.getFile()] = piece;
            if (piece != null) {
                piece.position = pos;
                // puts a piece at the given position
            }
        }
    }
    public void removePiece(Position pos) {
        if (pos.isValid()) {
            board[pos.getRank()][pos.getFile()] = null;
            //removes a piece from the positoon given and sets it to null cause we removed it
        }
    }
    public boolean isEmpty(Position pos) {
        return getPiece(pos) == null;
        //if empty, return null
    }
    public King getKing(boolean isWhite) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece instanceof King && piece.isWhite() == isWhite) {
                    return (King) piece;
                    //return king
                }
            }
        }
        return null;
    }
    public ArrayList<ReturnPiece> getAllPieces() {
        ArrayList<ReturnPiece> pieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board[i][j];
                if (piece != null) {
                    ReturnPiece rp = new ReturnPiece();
                    rp.pieceType = piece.getPieceType();
                    rp.pieceFile = ReturnPiece.PieceFile.values()[piece.getPosition().getFile()];
                    rp.pieceRank = piece.getPosition().getRankInt();
                    pieces.add(rp);
                }
            }
        }
        return pieces;
    }
    public Position getEnPassantTarget() { return enPassantTarget; }
    public void setEnPassantTarget(Position target) { this.enPassantTarget = target; }
}