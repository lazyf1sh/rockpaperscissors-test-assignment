package games.banzai.rps.server.other;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;

/**
 * Waits for at least two players to connect them between each other.
 *
 * @author Ivan Kopylov
 */
public class PlayersMatcherJob implements Runnable
{
    private final BlockingQueue<PlayerInteractionJob> playersThreads;

    public PlayersMatcherJob(BlockingQueue<PlayerInteractionJob> playersThreads)
    {
        this.playersThreads = playersThreads;
    }

    @Override
    public void run()
    {
        if (atLeastTwoReadyPlayers())
        {
            try
            {
                PlayerInteractionJob playerOne = playersThreads.take();
                PlayerInteractionJob playerTwo = playersThreads.take();

                GameSession gameSession = new GameSession(playerOne, playerTwo);
                new GameSessionStarterThread(gameSession).start();
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }

    private boolean atLeastTwoReadyPlayers()
    {
        return playersThreads.size() > 1 &&
                playersThreads
                        .stream()
                        .filter(PlayerInteractionJob::isReadyToPlay)
                        .count() > 1;
    }
}
