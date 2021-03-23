package games.banzai.rps.server.other;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Runs a game between two players.
 *
 * @author Ivan Kopylov
 */
public class GameSessionStarterThread extends Thread
{
    private final GameSession gameSession;

    public GameSessionStarterThread(GameSession gameSession)
    {
        super("GameSessionInteractionThread. Player1 " + gameSession.getPlayer1().getNickname() + "; player 2: " + gameSession.getPlayer2().getNickname() + " - ");
        this.gameSession = gameSession;
    }

    @Override
    public void run()
    {
        PlayerInteractionJob player1 = gameSession.getPlayer1();
        PlayerInteractionJob player2 = gameSession.getPlayer2();

        player1.sendMessage(Constants.GAME_STARTS_YOUR_OPPONENT_IS + player2.getNickname());
        player2.sendMessage(Constants.GAME_STARTS_YOUR_OPPONENT_IS + player1.getNickname());

        player1.sendMessage(Constants.PLEASE_SELECT_MOVE);
        player2.sendMessage(Constants.PLEASE_SELECT_MOVE);

        PlayerMessageCallBack player1MessageCallBack = msg -> gameSession.getPlayer1move().set(RockPaperScissorsMoveParse.parse(msg));
        PlayerMessageCallBack player2MessageCallBack = msg -> gameSession.getPlayer2move().set(RockPaperScissorsMoveParse.parse(msg));

        player1.addCallBack(player1MessageCallBack);
        player2.addCallBack(player2MessageCallBack);

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new GameSessionWorker(gameSession, scheduledExecutorService), 0, 500, TimeUnit.MILLISECONDS);
    }
}
