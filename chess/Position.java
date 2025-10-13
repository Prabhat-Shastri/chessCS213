package chess;
public class Position {
    private int file; // 0-7 (a-h)
    private int rank; // 0-7 (1-8)
    public Position(int file, int rank) {
        this.file = file;
        this.rank = rank;
    }
    public Position(String pos) {
        this.file = pos.charAt(0) - 'a';
        this.rank = pos.charAt(1) - '1';
    }
    public int getFile() { return file; }
    public int getRank() { return rank; }
    public char getFileChar() { return (char)('a' + file); }
    public int getRankInt() { return rank + 1; }
    public boolean isValid() {
        return file >= 0 && file < 8 && rank >= 0 && rank < 8;
    }
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Position position = (Position) obj;
        return file == position.file && rank == position.rank;
    }
    public String toString() {
        return "" + getFileChar() + getRankInt();
    }
}