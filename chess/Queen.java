package chess;

import java.util.ArrayList;
import java.util.List;

public class Queen extends Piece {
    
    public Queen(boolean isWhite, Position position) {
        super(isWhite, position);
    }
    
    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int currentRank = position.getRank();
        int currentFile = position.getFile();
        
        // Queen moves like both rook and bishop
        int[][] directions = {
            {0, 1}, {0, -1}, {1, 0}, {-1, 0},  // Rook moves
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1} // Bishop moves
        };
        
        for (int[] dir : directions) {
            for (int i = 1; i < 8; i++) {
                Position newPos = new Position(currentFile + i * dir[0], currentRank + i * dir[1]);
                if (!newPos.isValid()) break;
                
                Piece target = board.getPiece(newPos);
                if (target == null) {
                    moves.add(newPos);
                } else {
                    if (target.isWhite() != isWhite) {
                        moves.add(newPos);
                    }
                    break;
                }
            }
        }
        
        return moves;
    }
    
    @Override
    public ReturnPiece.PieceType getPieceType() {
        return isWhite ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ;
    }
    
    @Override
    public char getSymbol() {
        return 'Q';
    }
}