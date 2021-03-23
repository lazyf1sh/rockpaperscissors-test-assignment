package games.banzai.rps.server.other;

import java.util.concurrent.atomic.AtomicReference;

public final class GameSession
{
    private final PlayerInteractionJob player1;
    private final PlayerInteractionJob player2;


    public GameSession(PlayerInteractionJob player1, PlayerInteractionJob player2)
    {
        this.player1 = player1;
        this.player2 = player2;
    }


    private final AtomicReference<RockPaperScissors> player1move = new AtomicReference<>();
    private final AtomicReference<RockPaperScissors> player2move = new AtomicReference<>();

    public AtomicReference<RockPaperScissors> getPlayer1move()
    {
        return player1move;
    }

    public AtomicReference<RockPaperScissors> getPlayer2move()
    {
        return player2move;
    }

    public PlayerInteractionJob getPlayer1()
    {
        return player1;
    }

    public PlayerInteractionJob getPlayer2()
    {
        return player2;
    }
}
