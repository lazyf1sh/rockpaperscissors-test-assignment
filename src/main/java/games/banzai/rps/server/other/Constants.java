package games.banzai.rps.server.other;

public class Constants
{
    public static final int    MAX_PLAYERS                  = 50;
    public static final String PLEASE_SELECT_MOVE           =
            "Type number and press enter.\n" +
                    "1 - for [r]ock.\n" +
                    "2 - for [p]aper.\n" +
                    "3 - for [s]cissors.\n";
    public static final String GAME_STARTS_YOUR_OPPONENT_IS = "Game starts. Your opponent is ";
    public static final String GAME_FINISHED_DISCONNECT     = "Game finished. You now will be disconnected.";
    public static final String DRAW_PLEASE_TRY_AGAIN        = "Draw. Please try again.";
    /**
     * Is doesn't make sense to run it often.
     */
    public static final int    MAINTENANCE_INTERVAL         = 1000;
    public static final int DEFAULT_PORT = 27015;
}
