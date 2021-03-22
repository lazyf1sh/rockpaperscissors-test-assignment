package games.banzai.rps.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Thread that listens to new connections of new players.
 *
 * @author Ivan Kopylov
 */
public class ConnectionListenerThread extends Thread
{
    private final Set<PlayerInteractionThread> playersThreads;

    public ConnectionListenerThread(Set<PlayerInteractionThread> playersThreads)
    {
        super("ConnectionListenerThread");
        this.playersThreads = playersThreads;
    }

    @Override
    public void run()
    {
        try (ServerSocket serverSocket = new ServerSocket(27015))
        {
            ExecutorService executor = Executors.newFixedThreadPool(Constants.MAX_PLAYERS);

            while (true)
            {
                Socket socket = serverSocket.accept();
                System.out.println("New connection from " + socket.getInetAddress() + ":" + socket.getPort());

                PlayerInteractionThread playerInteractionThread = new PlayerInteractionThread(socket);
                executor.execute(playerInteractionThread);
                playersThreads.add(playerInteractionThread);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}