package chess;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece {
    
    public Pawn(boolean isWhite, Position position) {
        super(isWhite, position);
    }
    
    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int direction = isWhite ? 1 : -1;
        int currentRank = position.getRank();
        int currentFile = position.getFile();
        
        // Forward move
        Position oneForward = new Position(currentFile, currentRank + direction);
        if (oneForward.isValid() && board.isEmpty(oneForward)) {
            moves.add(oneForward);
            
            // Double forward move from starting position
            if (!hasMoved) {
                Position twoForward = new Position(currentFile, currentRank + 2 * direction);
                if (twoForward.isValid() && board.isEmpty(twoForward)) {
                    moves.add(twoForward);
                }
            }
        }
        
        // Diagonal captures
        Position leftCapture = new Position(currentFile - 1, currentRank + direction);
        if (leftCapture.isValid()) {
            Piece target = board.getPiece(leftCapture);
            if (target != null && target.isWhite() != isWhite) {
                moves.add(leftCapture);
            }
            // En passant
            if (leftCapture.equals(board.getEnPassantTarget())) {
                moves.add(leftCapture);
            }
        }
        
        Position rightCapture = new Position(currentFile + 1, currentRank + direction);
        if (rightCapture.isValid()) {
            Piece target = board.getPiece(rightCapture);
            if (target != null && target.isWhite() != isWhite) {
                moves.add(rightCapture);
            }
            // En passant
            if (rightCapture.equals(board.getEnPassantTarget())) {
                moves.add(rightCapture);
            }
        }
        
        return moves;
    }
    
    @Override
    public ReturnPiece.PieceType getPieceType() {
        return isWhite ? ReturnPiece.PieceType.WP : ReturnPiece.PieceType.BP;
    }
    
    @Override
    public char getSymbol() {
        return 'P';
    }
}