package chess;

import java.util.ArrayList;
import java.util.List;

public class Knight extends Piece {
    
    public Knight(boolean isWhite, Position position) {
        super(isWhite, position);
    }
    
    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int currentRank = position.getRank();
        int currentFile = position.getFile();
        
        // Knight moves in L-shape
        int[][] knightMoves = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
        
        for (int[] move : knightMoves) {
            Position newPos = new Position(currentFile + move[0], currentRank + move[1]);
            if (newPos.isValid()) {
                Piece target = board.getPiece(newPos);
                if (target == null || target.isWhite() != isWhite) {
                    moves.add(newPos);
                }
            }
        }
        
        return moves;
    }
    
    @Override
    public ReturnPiece.PieceType getPieceType() {
        return isWhite ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN;
    }
    
    @Override
    public char getSymbol() {
        return 'N';
    }
}