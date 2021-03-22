package games.banzai.rps.server;

import java.util.Set;

public class MaintenanceThread extends Thread
{
    private final Set<PlayerInteractionThread> playersThreads;

    public MaintenanceThread(Set<PlayerInteractionThread> playersThreads)
    {
        super("MaintenanceThread");
        this.playersThreads = playersThreads;
    }

    private static boolean threadDied(PlayerInteractionThread thread)
    {
        if (!thread.isActive())
        {
            System.out.println("Thread " + thread.getName() + " died.");
            return !thread.isActive();
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
