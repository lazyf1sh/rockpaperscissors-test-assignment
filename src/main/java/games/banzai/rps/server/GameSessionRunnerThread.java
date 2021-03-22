package games.banzai.rps.server;

import java.util.Iterator;
import java.util.Set;

public class GameSessionRunnerThread extends Thread
{
    private final Set<GameSession> gameSessions;

    public GameSessionRunnerThread(Set<GameSession> gameSessions)
    {
        super("GameSessionThread");
        this.gameSessions = gameSessions;
    }

    @Override
    public void run()
    {
        while (true)
        {
            Iterator<GameSession> iterator = gameSessions.iterator();
            if (iterator.hasNext())
            {
                new GameSessionInteractionThread(iterator.next()).start();
                iterator.remove();
            }
        }
    }
}
