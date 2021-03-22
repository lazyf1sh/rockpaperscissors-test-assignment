package games.banzai.rps.server;

public final class GameSession
{
    private final String                  id;
    private final PlayerInteractionThread player1;
    private final PlayerInteractionThread player2;
    private       boolean                 isActive;

    public GameSession(String id, PlayerInteractionThread player1, PlayerInteractionThread player2, boolean isActive)
    {
        this.id = id;
        this.player1 = player1;
        this.player2 = player2;
        this.isActive = isActive;
    }

    public String getId()
    {
        return id;
    }

    public PlayerInteractionThread getPlayer1()
    {
        return player1;
    }

    public PlayerInteractionThread getPlayer2()
    {
        return player2;
    }

    public void closeSession()
    {
        isActive = false;
    }
}
