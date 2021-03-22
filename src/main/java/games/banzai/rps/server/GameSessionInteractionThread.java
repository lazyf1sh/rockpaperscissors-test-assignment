package games.banzai.rps.server;

import java.util.concurrent.atomic.AtomicReference;

public class GameSessionInteractionThread extends Thread
{
    private GameSession  gameSession;


    public GameSessionInteractionThread(GameSession gameSession)
    {
        super("GameSessionInteractionThread: Player " + gameSession.getPlayer1().getNickname() + " player 2: " + gameSession.getPlayer2().getNickname() + " -");
        this.gameSession = gameSession;
    }

    @Override
    public void run()
    {
        PlayerInteractionThread player1 = gameSession.getPlayer1();
        PlayerInteractionThread player2 = gameSession.getPlayer2();

        player1.sendMessage("Game starts.");
        player2.sendMessage("Game starts.");

        player1.sendMessage("Please enter 1,2,3.");
        player2.sendMessage("Please enter 1,2,3.");

        AtomicReference<GameElements> player1move = new AtomicReference<>();
        AtomicReference<GameElements> player2move = new AtomicReference<>();

        PlayerMessageCallBack player1MessageCallBack = msg -> player1move.set(GameElements.valueOf(msg));
        player1.addCallBack(player1MessageCallBack);
        PlayerMessageCallBack player2MessageCallBack = msg -> player2move.set(GameElements.valueOf(msg));
        player2.addCallBack(player2MessageCallBack);

        while (player1move.get() == null || player2move.get() == null)
        {
        }

        GameResult run = RockPaperScissorsDecisionMaker.run(player1move.get(), player2move.get());
        player1.sendMessage(run.toString());
        player2.sendMessage(run.toString());

        player1.removeCallBack(player1MessageCallBack);
        player2.removeCallBack(player2MessageCallBack);

        player1.sendMessage("Game finished. You now will be disconnected.");
        player2.sendMessage("Game finished. You now will be disconnected.");

        player1.disconnect();
        player2.disconnect();
    }
}
