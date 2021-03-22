package games.banzai.rps.server;

public class RockPaperScissorsDecisionMaker
{
    public static GameResult run(GameElements player1, GameElements player2)
    {
        if (player1 == GameElements.ROCK && player2 == GameElements.ROCK)
        {
            return GameResult.DRAW;
        }
        return null;
    }
}
