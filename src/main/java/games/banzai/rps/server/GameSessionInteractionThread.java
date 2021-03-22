package games.banzai.rps.server;

public class GameSessionInteractionThread extends Thread
{
    private GameSession  gameSession;
    private GameElements player1move;
    private GameElements player2move;

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

        PlayerMessageCallBack player1MessageCallBack = msg -> player1move = GameElements.valueOf(msg);
        player1.addCallBack(player1MessageCallBack);
        PlayerMessageCallBack player2MessageCallBack = msg -> player2move = GameElements.valueOf(msg);
        player2.addCallBack(player2MessageCallBack);

        while (player1move == null || player2move == null)
        {
        }

        GameResult run = RockPaperScissorsDecisionMaker.run(player1move, player2move);
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
