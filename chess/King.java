package chess;
import java.util.ArrayList;
import java.util.List;
public class King extends Piece {
    public King(boolean isWhite, Position position) {
        super(isWhite, position);
    }
    @Override
    public List<Position> getPossibleMoves(Board board) {
        List<Position> moves = new ArrayList<>();
        int currentRank = position.getRank();
        int currentFile = position.getFile();
        int[][] directions = {
            {0, 1}, {0, -1}, {1, 0}, {-1, 0},
            {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };
        //The king moves only one tile in any direction and these are all the cases
        for (int[] dir : directions) {
            int nextFile = currentFile + dir[0];
            int nextRank = currentRank + dir[1];
            Position newPos = new Position(nextFile, nextRank);
            if (newPos.isValid()) {
                Piece target = board.getPiece(newPos);
                if (target == null || target.isWhite() != isWhite) {
                    //adding to the moves only if square is empty or has an enemy piece
                    moves.add(newPos);
                }
            }
        }
        // Castling
        if (!hasMoved) {
            // Kingside castling
            if (canCastle(board, true)) {
                Position kingCastle = new Position (currentFile + 2, currentRank);
                moves.add(kingCastle);
            }
            // Queenside castling
            if (canCastle(board, false)) {
                Position queenCastle = new Position (currentFile - 2, currentRank);
                moves.add(queenCastle);
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
                return false; //because the path is blocked
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
                //checking if the enemy piece is capable of attacking this square position
                if (piece != null && piece.isWhite() != isWhite) {
                    if (piece.canMoveTo(pos, board)) {
                        return true;
                    }
                }
            }
        }
        //only if no enemy piece can attack this square
        return false;
    }
    
    @Override
    public ReturnPiece.PieceType getPieceType() {
        if(isWhite)
        {
            return ReturnPiece.PieceType.WK;
        }
        else
        {
            return ReturnPiece.PieceType.BK;
        }
    }
    
    @Override
    public char getSymbol() {
        return 'K';
    }
}