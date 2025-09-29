package chess;

import java.util.List;

public abstract class Piece {
    protected boolean isWhite;
    protected Position position;
    protected boolean hasMoved;
    
    public Piece(boolean isWhite, Position position) {
        this.isWhite = isWhite;
        this.position = position;
        this.hasMoved = false;
    }
    
    public boolean isWhite() { return isWhite; }
    public Position getPosition() { return position; }
    public boolean hasMoved() { return hasMoved; }
    
    public void setPosition(Position position) {
        this.position = position;
        this.hasMoved = true;
    }
    
    public abstract List<Position> getPossibleMoves(Board board);
    public abstract ReturnPiece.PieceType getPieceType();
    public abstract char getSymbol();
    
    public boolean canMoveTo(Position to, Board board) {
        List<Position> possibleMoves = getPossibleMoves(board);
        return possibleMoves.contains(to);
    }
}