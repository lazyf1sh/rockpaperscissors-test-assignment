package games.banzai.rps.server.other;

/**
 * Decides how wins.
 *
 * @author Ivan Kopylov
 */
public class RockPaperScissorsDecisionMaker
{
    public static GameResult decide(RockPaperScissors player1turn, RockPaperScissors player2turn)
    {
        if (player1turn == player2turn)
        {
            return GameResult.DRAW;
        }

        if (player1turn == RockPaperScissors.ROCK && player2turn == RockPaperScissors.SCISSORS)
        {
            return GameResult.PLAYER1_WIN;
        }
        if (player1turn == RockPaperScissors.PAPER && player2turn == RockPaperScissors.ROCK)
        {
            return GameResult.PLAYER1_WIN;
        }
        if (player1turn == RockPaperScissors.SCISSORS && player2turn == RockPaperScissors.PAPER)
        {
            return GameResult.PLAYER1_WIN;
        }

        return GameResult.PLAYER2_WIN;
    }

}
