package chess;

import java.util.ArrayList;

public class Game {
    private Board board;
    private boolean isWhiteTurn;
    
    public Game() {
        board = new Board();
        isWhiteTurn = true;
    }
    
    public ReturnPlay playMove(String moveStr) {
        String[] parts = moveStr.split("\\s+");
        
        // Handle resign
        if (parts.length == 1 && parts[0].equals("resign")) {
            ReturnPlay result = new ReturnPlay();
            result.piecesOnBoard = board.getAllPieces();
            result.message = isWhiteTurn ? 
                ReturnPlay.Message.RESIGN_BLACK_WINS : 
                ReturnPlay.Message.RESIGN_WHITE_WINS;
            return result;
        }
        
        // Handle draw request
        boolean drawRequest = false;
        if (parts.length >= 2 && parts[parts.length - 1].equals("draw?")) {
            drawRequest = true;
            // Remove "draw?" from the parts array
            String[] newParts = new String[parts.length - 1];
            System.arraycopy(parts, 0, newParts, 0, parts.length - 1);
            parts = newParts;
        }
        
        if (parts.length < 2) {
            return createIllegalMoveResult();
        }
        
        Position from = new Position(parts[0]);
        Position to = new Position(parts[1]);
        
        if (!from.isValid() || !to.isValid()) {
            return createIllegalMoveResult();
        }
        
        Piece piece = board.getPiece(from);
        if (piece == null || piece.isWhite() != isWhiteTurn) {
            return createIllegalMoveResult();
        }
        
        // Handle promotion
        Piece promotionPiece = null;
        if (parts.length == 3) {
            char promotionChar = parts[2].charAt(0);
            if (piece instanceof Pawn && (to.getRank() == 0 || to.getRank() == 7)) {
                switch (promotionChar) {
                    case 'Q': promotionPiece = new Queen(piece.isWhite(), to); break;
                    case 'R': promotionPiece = new Rook(piece.isWhite(), to); break;
                    case 'B': promotionPiece = new Bishop(piece.isWhite(), to); break;
                    case 'N': promotionPiece = new Knight(piece.isWhite(), to); break;
                    default: promotionPiece = new Queen(piece.isWhite(), to); break;
                }
            }
        } else if (piece instanceof Pawn && (to.getRank() == 0 || to.getRank() == 7)) {
            promotionPiece = new Queen(piece.isWhite(), to);
        }
        
        // Try to make the move
        Board newBoard = new Board(board);
        boolean moveSuccessful = makeMove(newBoard, from, to, promotionPiece);
        
        if (!moveSuccessful) {
            return createIllegalMoveResult();
        }
        
        // Check if this move puts own king in check
        if (isKingInCheck(newBoard, isWhiteTurn)) {
            return createIllegalMoveResult();
        }
        
        // Move is legal, update the board
        board = newBoard;
        isWhiteTurn = !isWhiteTurn;
        
        ReturnPlay result = new ReturnPlay();
        result.piecesOnBoard = board.getAllPieces();
        
        if (drawRequest) {
            result.message = ReturnPlay.Message.DRAW;
            return result;
        }
        
        // Check for check/checkmate
        if (isKingInCheck(board, isWhiteTurn)) {
            if (isCheckmate(board, isWhiteTurn)) {
                result.message = isWhiteTurn ? 
                    ReturnPlay.Message.CHECKMATE_BLACK_WINS : 
                    ReturnPlay.Message.CHECKMATE_WHITE_WINS;
            } else {
                result.message = ReturnPlay.Message.CHECK;
            }
        }
        
        return result;
    }
    
    private boolean makeMove(Board board, Position from, Position to, Piece promotionPiece) {
        Piece piece = board.getPiece(from);
        
        if (!piece.canMoveTo(to, board)) {
            return false;
        }
        
        // Handle special moves
        if (piece instanceof Pawn) {
            Pawn pawn = (Pawn) piece;
            
            // En passant
            if (to.equals(board.getEnPassantTarget())) {
                Position capturedPawnPos = new Position(to.getFile(), from.getRank());
                board.removePiece(capturedPawnPos);
            }
            
            // Set en passant target for double pawn move
            if (Math.abs(to.getRank() - from.getRank()) == 2) {
                board.setEnPassantTarget(new Position(from.getFile(), (from.getRank() + to.getRank()) / 2));
            } else {
                board.setEnPassantTarget(null);
            }
        } else if (piece instanceof King && Math.abs(to.getFile() - from.getFile()) == 2) {
            // Castling
            if (!piece.hasMoved()) {
                int rookFile = to.getFile() > from.getFile() ? 7 : 0;
                int newRookFile = to.getFile() > from.getFile() ? 5 : 3;
                
                Piece rook = board.getPiece(new Position(rookFile, from.getRank()));
                if (rook instanceof Rook && !rook.hasMoved()) {
                    board.setPiece(new Position(newRookFile, from.getRank()), rook);
                    board.removePiece(new Position(rookFile, from.getRank()));
                    rook.setPosition(new Position(newRookFile, from.getRank()));
                }
            }
            board.setEnPassantTarget(null);
        } else {
            board.setEnPassantTarget(null);
        }
        
        // Make the actual move
        board.removePiece(from);
        if (promotionPiece != null) {
            board.setPiece(to, promotionPiece);
        } else {
            board.setPiece(to, piece);
            piece.setPosition(to);
        }
        
        return true;
    }
    
    private boolean isKingInCheck(Board board, boolean isWhiteKing) {
        King king = board.getKing(isWhiteKing);
        if (king == null) return false;
        
        Position kingPos = king.getPosition();
        
        // Check if any opponent piece can attack the king
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(new Position(j, i));
                if (piece != null && piece.isWhite() != isWhiteKing) {
                    if (piece.canMoveTo(kingPos, board)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    private boolean isCheckmate(Board board, boolean isWhitePlayer) {
        // Try all possible moves for the player
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(new Position(j, i));
                if (piece != null && piece.isWhite() == isWhitePlayer) {
                    for (Position move : piece.getPossibleMoves(board)) {
                        Board testBoard = new Board(board);
                        if (makeMove(testBoard, piece.getPosition(), move, null)) {
                            if (!isKingInCheck(testBoard, isWhitePlayer)) {
                                return false; // Found a legal move that gets out of check
                            }
                        }
                    }
                }
            }
        }
        return true; // No legal moves found
    }
    
    private ReturnPlay createIllegalMoveResult() {
        ReturnPlay result = new ReturnPlay();
        result.piecesOnBoard = board.getAllPieces();
        result.message = ReturnPlay.Message.ILLEGAL_MOVE;
        return result;
    }
}