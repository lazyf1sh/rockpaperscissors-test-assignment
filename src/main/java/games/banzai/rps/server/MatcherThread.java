package games.banzai.rps.server;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

/**
 * Waits for at least two players to connect them between each other.
 *
 * @author Ivan Kopylov
 */
public class MatcherThread extends Thread
{
    private final BlockingQueue<PlayerInteractionThread> playersThreads;
    private final Set<GameSession>                       gameSessions;

    public MatcherThread(BlockingQueue<PlayerInteractionThread> playersThreads, Set<GameSession> gameSessions)
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
            if (atLeastTwoReadyPlayers())
            {
                try
                {
                    PlayerInteractionThread playerOne = playersThreads.take();
                    PlayerInteractionThread playerTwo = playersThreads.take();

                    gameSessions.add(new GameSession(UUID.randomUUID().toString(), playerOne, playerTwo, true));
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

            }
        }
    }

    private boolean atLeastTwoReadyPlayers()
    {
        return playersThreads.size() > 1 &&
                playersThreads
                .stream()
                .filter(PlayerInteractionThread::isReadyToPlay)
                .count() > 1;
    }
}
