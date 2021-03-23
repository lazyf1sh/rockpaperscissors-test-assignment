package games.banzai.rps.server.other;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Thread that listens to new connections of new players.
 *
 * @author Ivan Kopylov
 */
public class ConnectionListenerJob implements Runnable
{
    private final BlockingQueue<PlayerInteractionJob> playersThreads;
    private final ServerSocket                        serverSocket;

    public ConnectionListenerJob(BlockingQueue<PlayerInteractionJob> playersThreads, ServerSocket serverSocket)
    {
        this.playersThreads = playersThreads;
        this.serverSocket = serverSocket;
    }

    @Override
    public void run()
    {
        try
        {
            Socket socket = serverSocket.accept();
            System.out.println("New connection from " + socket.getInetAddress() + ":" + socket.getPort());

            ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

            PlayerResource playerResource = new PlayerResource(socket, scheduledExecutorService);
            PlayerInteractionJob playerInteractionJob = new PlayerInteractionJob(playerResource);

            scheduledExecutorService.scheduleAtFixedRate(playerInteractionJob, 0, 500, TimeUnit.MILLISECONDS);

            if (!playersThreads.offer(playerInteractionJob))
            {
                playerInteractionJob.sendMessage("No free slots on the server.");
                playerInteractionJob.disconnect();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
