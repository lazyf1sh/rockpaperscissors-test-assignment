package games.banzai.rps.server.other;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This thread is run again and again until players enter commands (Rock, Paper, Scissors) to start round.
 */
public final class GameSessionWorker implements Runnable
{
    public static final String                   WOAH_YOU_VE_BEATEN_ANOTHER_PLAYER = "Woah, you've beaten another player!";
    public static final String                   TRY_YOUR_LUCK_NEXT_TIME           = "Try your luck next time.";
    private final       GameSession              gameSession;
    private final       ScheduledExecutorService scheduledExecutorService;

    public GameSessionWorker(GameSession gameSession, ScheduledExecutorService scheduledExecutorService)
    {
        this.gameSession = gameSession;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Override
    public void run()
    {
        AtomicReference<RockPaperScissors> player1move = gameSession.getPlayer1move();
        AtomicReference<RockPaperScissors> player2move = gameSession.getPlayer2move();
        PlayerInteractionJob player1 = gameSession.getPlayer1();
        PlayerInteractionJob player2 = gameSession.getPlayer2();

        if (player1.isReadyToPlay() && player2.isReadyToPlay())
        {
            if (player1move.get() == null || player2move.get() == null)
            {
                return;
            }

            if (GameResult.DRAW == RockPaperScissorsDecisionMaker.decide(player1move.get(), player2move.get()))
            {
                player1move.set(null);
                player2move.set(null);

                player1.sendMessage(Constants.DRAW_PLEASE_TRY_AGAIN);
                player2.sendMessage(Constants.DRAW_PLEASE_TRY_AGAIN);

                player1.sendMessage(Constants.PLEASE_SELECT_MOVE);
                player2.sendMessage(Constants.PLEASE_SELECT_MOVE);
                return;
            }

            GameResult gameResult = RockPaperScissorsDecisionMaker.decide(player1move.get(), player2move.get());
            if (gameResult == GameResult.PLAYER1_WIN)
            {
                player1.sendMessage(WOAH_YOU_VE_BEATEN_ANOTHER_PLAYER);
                player2.sendMessage(TRY_YOUR_LUCK_NEXT_TIME);
            }
            else if (gameResult == GameResult.PLAYER2_WIN)
            {
                player1.sendMessage(TRY_YOUR_LUCK_NEXT_TIME);
                player2.sendMessage(WOAH_YOU_VE_BEATEN_ANOTHER_PLAYER);
            }

            player1.sendMessage(Constants.GAME_FINISHED_DISCONNECT);
            player2.sendMessage(Constants.GAME_FINISHED_DISCONNECT);

            player1.disconnect();
            player2.disconnect();

            scheduledExecutorService.shutdownNow();
        }


    }
}
