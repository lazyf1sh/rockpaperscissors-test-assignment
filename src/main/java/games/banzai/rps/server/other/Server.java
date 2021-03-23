package games.banzai.rps.server.other;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class Server
{
    private static final int                                 NUMBER_OF_JOBS    = 3;
    /**
     * Used this que to maintain players' join order.
     */
    private final        BlockingQueue<PlayerInteractionJob> playersThreadsQue = new LinkedBlockingDeque<>();
    private final        int                                 port;
    private              ServerSocket                        serverSocket;

    public Server(int port)
    {
        this.port = port;
    }

    public void runServer()
    {
        try
        {
            serverSocket = new ServerSocket(port);
            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(NUMBER_OF_JOBS);

            scheduledExecutorService.scheduleAtFixedRate(new ConnectionListenerJob(playersThreadsQue, serverSocket), 0, 100, TimeUnit.MILLISECONDS);
            scheduledExecutorService.scheduleAtFixedRate(new PlayersMatcherJob(playersThreadsQue), 0, 100, TimeUnit.MILLISECONDS);
            scheduledExecutorService.scheduleAtFixedRate(new MaintenanceJob(playersThreadsQue), Constants.MAINTENANCE_INTERVAL, Constants.MAINTENANCE_INTERVAL, TimeUnit.MILLISECONDS);

            Runtime.getRuntime().addShutdownHook(buildStopThread());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private Thread buildStopThread()
    {
        return new Thread(() ->
                playersThreadsQue
                        .stream()
                        .filter(PlayerInteractionJob::isStale)
                        .peek(thread -> thread.sendMessage("Server is stopping now. You will be disconnected."))
                        .forEach(PlayerInteractionJob::disconnect));
    }

    public void stopServer()
    {
        try
        {
            serverSocket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
