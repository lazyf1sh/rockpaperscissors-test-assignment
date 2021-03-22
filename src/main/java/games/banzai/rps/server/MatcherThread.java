package games.banzai.rps.server;

import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

public class MatcherThread extends Thread
{
    private final Set<PlayerInteractionThread> playersThreads;
    private final Set<GameSession>             gameSessions;

    public MatcherThread(Set<PlayerInteractionThread> playersThreads, Set<GameSession> gameSessions)
    {
        super("MatcherThread");
        this.playersThreads = playersThreads;
        this.gameSessions = gameSessions;
    }

    @Override
    public void run()
    {
        while (true)
        {
            if (playersThreads.size() > 0 && playersThreads.stream().filter(thread -> thread.isActive() && thread.isReadyToPlay()).count() > 1)
            {
                Iterator<PlayerInteractionThread> iterator = playersThreads.iterator();

                PlayerInteractionThread playerOne = iterator.next();
                iterator.remove();

                PlayerInteractionThread playerTwo = iterator.next();
                iterator.remove();

                gameSessions.add(new GameSession(UUID.randomUUID().toString(), playerOne, playerTwo, true));
            }
        }
    }
}
