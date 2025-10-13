package chess;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    
    public King(boolean isWhite, Position position) {
        super(isWhite, position);
    }
    
    @Override
    public List<Position> getPossibleMoves(Board board) {
        return getPossibleMoves(board, true);
    }

    public List<Position> getPossibleMoves(Board board, boolean includeCastling) {
        List<Position> moves = new ArrayList<>();
        int currentRank = position.getRank();
        int currentFile = position.getFile();

        // King moves one square in any direction
        int[][] directions = {
            {0, 1}, {0, -1}, {1, 0}, {-1, 0},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };

        for (int[] dir : directions) {
            Position newPos = new Position(currentFile + dir[0], currentRank + dir[1]);
            if (newPos.isValid()) {
                Piece target = board.getPiece(newPos);
                if (target == null || target.isWhite() != isWhite) {
                    moves.add(newPos);
                }
            }
        }

        // Castling - only include if requested to avoid infinite recursion
        if (includeCastling && !hasMoved) {
            // Kingside castling
            if (canCastle(board, true)) {
                moves.add(new Position(currentFile + 2, currentRank));
            }
            // Queenside castling
            if (canCastle(board, false)) {
                moves.add(new Position(currentFile - 2, currentRank));
            }
        }

        return moves;
    }
    
    private boolean canCastle(Board board, boolean kingside) {
        int currentRank = position.getRank();
        int currentFile = position.getFile();
        int rookFile = kingside ? 7 : 0;
        int direction = kingside ? 1 : -1;
        
        // Check if rook exists and hasn't moved
        Piece rook = board.getPiece(new Position(rookFile, currentRank));
        if (!(rook instanceof Rook) || rook.hasMoved()) {
            return false;
        }
        
        // Check if squares between king and rook are empty
        int startFile = kingside ? currentFile + 1 : rookFile + 1;
        int endFile = kingside ? rookFile - 1 : currentFile - 1;
        
        for (int file = startFile; file <= endFile; file++) {
            if (!board.isEmpty(new Position(file, currentRank))) {
                return false;
            }
        }
        
        // Check if king is in check or would pass through check
        for (int i = 0; i <= 2; i++) {
            Position testPos = new Position(currentFile + i * direction, currentRank);
            if (isSquareUnderAttack(board, testPos)) {
                return false;
            }
        }
        
        return true;
    }
    
    private boolean isSquareUnderAttack(Board board, Position pos) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Piece piece = board.getPiece(new Position(j, i));
                if (piece != null && piece.isWhite() != isWhite) {
                    // For enemy kings, exclude castling moves to avoid infinite recursion
                    if (piece instanceof King) {
                        King enemyKing = (King) piece;
                        List<Position> kingMoves = enemyKing.getPossibleMoves(board, false);
                        if (kingMoves.contains(pos)) {
                            return true;
                        }
                    } else {
                        if (piece.canMoveTo(pos, board)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public ReturnPiece.PieceType getPieceType() {
        return isWhite ? ReturnPiece.PieceType.WK : ReturnPiece.PieceType.BK;
    }
    
    @Override
    public char getSymbol() {
        return 'K';
    }
}