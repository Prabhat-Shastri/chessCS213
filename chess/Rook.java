package chess;
import java.util.ArrayList;
import java.util.List;
public class Rook extends Piece {
    public Rook(boolean isWhite, Position position) {
        super(isWhite, position);
    }
    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int currentRank = position.getRank();
        int currentFile = position.getFile();
        int[][] directions = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        //it only moves horizontally or vertically so these are the 4 options for its movement 
        for (int[] dir : directions) {
            for (int i = 1; i < 8; i++) {
                int nextFile = currentFile + i * dir[0];
                int nextRank = currentRank + i * dir[1];
                Position newPos = new Position(nextFile,nextRank);
                if (!newPos.isValid()) {
                    break;
                }
                Piece target = board.getPiece(newPos);
                if (target == null) {
                    moves.add(newPos); //if empty, valid
                } else {
                    if (target.isWhite() != isWhite) {
                        moves.add(newPos);
                        //if enemy piece then we can kill and all to moves
                    }
                    break;
                }
            }
        }
        return moves;
    }
    @Override
    public ReturnPiece.PieceType getPieceType() {
        if(isWhite)
        {
            return ReturnPiece.PieceType.WR;
        }
        else{
            return ReturnPiece.PieceType.BR;
        }
    }
    @Override
    public char getSymbol() {
        return 'R';
    }
}