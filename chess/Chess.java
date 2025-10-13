//Shashank Vemparala (sv763) & Aiman Koli (mk2177) 
package chess;

import java.util.ArrayList;

public class Chess {

    enum Player { white, black }
    
    private static Game game = new Game();
    
    /**
     * Plays the next move for whichever player has the turn.
     * 
     * @param move String for next move, e.g. "a2 a3"
     * 
     * @return A ReturnPlay instance that contains the result of the move.
     */
    public static ReturnPlay play(String move) {
        return game.playMove(move.trim());
    }
    
    /**
     * This method should reset the game, and start from scratch.
     */
    public static void start() {
        game = new Game();
    }
}