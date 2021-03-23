package games.banzai.rps.server;

import games.banzai.rps.server.other.GameResult;
import games.banzai.rps.server.other.RockPaperScissors;
import games.banzai.rps.server.other.RockPaperScissorsDecisionMaker;
import org.junit.Assert;
import org.junit.Test;

public class RockPaperScissorsDecisionMakerTest
{
    @Test
    public void paperRock_Player1Wins()
    {
        GameResult result = RockPaperScissorsDecisionMaker.decide(RockPaperScissors.PAPER, RockPaperScissors.ROCK);
        Assert.assertEquals(GameResult.PLAYER1_WIN, result);
    }

    @Test
    public void paperScissors_Player2Wins()
    {
        GameResult result = RockPaperScissorsDecisionMaker.decide(RockPaperScissors.PAPER, RockPaperScissors.SCISSORS);
        Assert.assertEquals(GameResult.PLAYER2_WIN, result);
    }

    @Test
    public void rockPaper_Player2Wins()
    {
        GameResult result = RockPaperScissorsDecisionMaker.decide(RockPaperScissors.ROCK, RockPaperScissors.PAPER);
        Assert.assertEquals(GameResult.PLAYER2_WIN, result);
    }

    @Test
    public void rockScissors_Player1Wins()
    {
        GameResult result = RockPaperScissorsDecisionMaker.decide(RockPaperScissors.ROCK, RockPaperScissors.SCISSORS);
        Assert.assertEquals(GameResult.PLAYER1_WIN, result);
    }

    @Test
    public void scissorsRock_Player2Wins()
    {
        GameResult result = RockPaperScissorsDecisionMaker.decide(RockPaperScissors.SCISSORS, RockPaperScissors.ROCK);
        Assert.assertEquals(GameResult.PLAYER2_WIN, result);
    }

    @Test
    public void scissorsPaper_Player1Wins()
    {
        GameResult result = RockPaperScissorsDecisionMaker.decide(RockPaperScissors.SCISSORS, RockPaperScissors.PAPER);
        Assert.assertEquals(GameResult.PLAYER1_WIN, result);
    }

    @Test
    public void paperRock_Draw1()
    {
        GameResult result = RockPaperScissorsDecisionMaker.decide(RockPaperScissors.PAPER, RockPaperScissors.PAPER);
        Assert.assertEquals(GameResult.DRAW, result);
    }


    @Test
    public void paperRock_Draw2()
    {
        GameResult result = RockPaperScissorsDecisionMaker.decide(RockPaperScissors.SCISSORS, RockPaperScissors.SCISSORS);
        Assert.assertEquals(GameResult.DRAW, result);
    }

    @Test
    public void paperRock_Draw3()
    {
        GameResult result = RockPaperScissorsDecisionMaker.decide(RockPaperScissors.ROCK, RockPaperScissors.ROCK);
        Assert.assertEquals(GameResult.DRAW, result);
    }
}