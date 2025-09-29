package chess;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends Piece {
    
    public Bishop(boolean isWhite, Position position) {
        super(isWhite, position);
    }
    
    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int currentRank = position.getRank();
        int currentFile = position.getFile();
        
        // Diagonal directions
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        
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
        return isWhite ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB;
    }
    
    @Override
    public char getSymbol() {
        return 'B';
    }
}