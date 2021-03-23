package games.banzai.rps.server.other;

import java.util.concurrent.BlockingQueue;

/**
 * Removes died threads.
 */
public class MaintenanceJob implements Runnable
{
    private final BlockingQueue<PlayerInteractionJob> playersThreads;

    public MaintenanceJob(BlockingQueue<PlayerInteractionJob> playersThreads)
    {
        this.playersThreads = playersThreads;
    }

    private static boolean threadDied(PlayerInteractionJob thread)
    {
        if (thread.isStale())
        {
            System.out.println("Thread of player " + thread.getNickname() + " died.");
            return true;
        }
        return false;
    }

    @Override
    public void run()
    {
        playersThreads.removeIf(MaintenanceJob::threadDied);
    }
}
