package games.banzai.rps.server.other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Some player's resources.
 *
 * @author Ivan Kopylov
 */
public final class PlayerResource
{
    private final Socket                     socket;
    private final AtomicReference<String>    nickname               = new AtomicReference<>();
    private       PrintWriter                writer;
    private       BufferedReader             reader;
    private final AtomicReference<Boolean>   active                 = new AtomicReference<>();
    private final Set<PlayerMessageCallBack> playerMessageCallbacks = new HashSet<>();

    /**
     * Job that launched {@link PlayerInteractionJob}. Bad decision to store it here, don't have better solution. I need somehow stop a thread from inside the thread.
     */
    private final ScheduledExecutorService scheduledExecutorService;

    public PlayerResource(Socket socket, ScheduledExecutorService scheduledExecutorService)
    {
        this.socket = socket;
        this.scheduledExecutorService = scheduledExecutorService;
        this.active.set(true);

        try
        {
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new PrintWriter(socket.getOutputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public ScheduledExecutorService getScheduledExecutorService()
    {
        return scheduledExecutorService;
    }

    public BufferedReader getReader()
    {
        return reader;
    }

    public AtomicReference<Boolean> getActive()
    {
        return active;
    }

    public Socket getSocket()
    {
        return socket;
    }

    public AtomicReference<String> getNickname()
    {
        return nickname;
    }


    public PrintWriter getWriter()
    {
        return writer;
    }



    public Set<PlayerMessageCallBack> getPlayerMessageCallbacks()
    {
        return playerMessageCallbacks;
    }
}
