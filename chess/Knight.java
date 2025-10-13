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
        int[][] knightMoves = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };
        //The horse moves in an L shape manner thats why its +-2 +-1 or +-1 +-2
        for (int[] move : knightMoves) {
            int nextFile = currentFile + move[0];
            int nextRank = currentRank + move[1];
            Position newPos = new Position(nextFile,nextRank);
            if (newPos.isValid()) {
                Piece target = board.getPiece(newPos);
                if (target == null || target.isWhite() != isWhite) {
                    //again, here it is the same as other pieces, either empty or has enemy piece 
                    moves.add(newPos);
                    //only then we are able to add the move to our list of moves
                }
            }
        }
        return moves;
    }
    @Override
    public ReturnPiece.PieceType getPieceType() {
        if(isWhite)
        {
            return ReturnPiece.PieceType.WN;
        }
        else{
            return ReturnPiece.PieceType.BN;
        }
    }
    @Override
    public char getSymbol() {
        return 'N';
    }
}