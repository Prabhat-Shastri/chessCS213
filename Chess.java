package chess;

import java.util.ArrayList;

public class Chess {

        enum Player { white, black }
    
	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for details of
	 *         the contents of the returned ReturnPlay instance.
	 */
    public static String getPlayer(){
        return Player;
    }
    public static String[] getMoves(String move){
        m = move.split(" ");
        if (m.length>0){
            return m;
        }else{
            // Raise error?
            String [] e = String [0];
            return e;
        }

    }
	public static ReturnPlay play(String move) {

		/* FILL IN THIS METHOD */
        String [] temp = getMoves(move);
        if (temp.length==1){
            //resign?
            return null;
        }
        if (temp.length==2){
            //Classic move
            //Check valid move
            //check if puts us under check
            //other normal play instructions
            return null;
        }
        if (temp.length==3){
            //asking for a draw
            return null;
        }

		
		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
		return null;
	}
	
	
	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		/* FILL IN THIS METHOD */
	}
}