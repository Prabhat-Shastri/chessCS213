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
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
        //northeast southeast, northwest, and southwest respectively 
        for (int[] temp : directions) {
            for (int i = 1; i < 8; i++) {
                int nextFile = currentFile + i * temp[0];
                int nextRank = currentRank + i * temp[1];
                Position newPos = new Position(nextFile, nextRank);
                if (!newPos.isValid()){
                    break;
                }
                Piece target = board.getPiece(newPos);
                if (target == null) {
                    //If there is no piece at target, we just consider the move because we already checked it is a legal move
                    moves.add(newPos);
                } else {
                    if (target.isWhite() != isWhite) {
                        //Only if it is a different color, we can authorize the move
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
        if (isWhite){
            return ReturnPiece.PieceType.WB;
        }
        else
        {
            return ReturnPiece.PieceType.BB;
        }
    }
    
    @Override
    public char getSymbol() {
        return 'B';
    }
}