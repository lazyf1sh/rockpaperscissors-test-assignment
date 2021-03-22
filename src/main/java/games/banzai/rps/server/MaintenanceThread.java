package games.banzai.rps.server;

import java.util.concurrent.BlockingQueue;

/**
 * Removes died threads.
 */
public class MaintenanceThread extends Thread
{
    private final BlockingQueue<PlayerInteractionThread> playersThreads;

    public MaintenanceThread(BlockingQueue<PlayerInteractionThread> playersThreads)
    {
        super("MaintenanceThread");
        this.playersThreads = playersThreads;
    }

    private static boolean threadDied(PlayerInteractionThread thread)
    {
        if (thread.isStale())
        {
            System.out.println("Thread " + thread.getName() + " died.");
            return true;
        }
        return false;
    }

    @Override
    public void run()
    {
        while (true)
        {
            playersThreads.removeIf(MaintenanceThread::threadDied);
        }
    }
}
