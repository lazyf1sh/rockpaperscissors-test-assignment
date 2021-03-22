package games.banzai.rps.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public final class PlayerInteractionThread extends Thread
{
    private final    Socket                     socket;
    private          String                     nickname;
    private          PrintWriter                writer;
    /**
     * Technical field.
     */
    private volatile boolean                    active;
    private final    Set<PlayerMessageCallBack> playerMessageCallbacks = new HashSet<>();

    public PlayerInteractionThread(Socket socket)
    {
        super("PlayerInteractionThread");
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try
        {
            active = true;

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            writer.println("You've successfully connected.");
            writer.println("Please enter your nickname: ");
            writer.flush();

            nickname = reader.readLine();
            System.out.println("Played entered nickname: " + nickname);

            while (true)
            {
                String line = reader.readLine();
                if (line == null)
                {
                    System.out.println("null received from: " + nickname + " - end of stream. disconnecting...");
                    this.disconnect();
                    return;
                }
                System.out.println("Message from player: " + line);

                for (PlayerMessageCallBack playerMessageCallback : playerMessageCallbacks)
                {
                    playerMessageCallback.onPlayerMessage(line);
                }
            }
        }
        catch (IOException e)
        {
            active = false;
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg)
    {
        if (writer != null && isActive())
        {
            writer.println(msg);
            writer.flush();
            System.out.println("Send msg: " + msg + " to player " + nickname);
        }
        else
        {
            throw new RuntimeException("Waiting for this exception during debugging e806ed75c61cb695a384");
        }
    }

    public void addCallBack(PlayerMessageCallBack playerMessageCallback)
    {
        playerMessageCallbacks.add(playerMessageCallback);
    }

    /**
     * Active and entered a nickname.
     *
     * @return if players is ready to play.
     */
    public boolean isReadyToPlay()
    {
        return isActive() && nickname != null;
    }

    public boolean isActive()
    {
        return active && !socket.isClosed();
    }

    public String getNickname()
    {
        return nickname;
    }

    /**
     *
     * Closed infrastructure and marks thread to be deleted.
     */
    public void disconnect()
    {
        try
        {
            active = false;
            socket.close();
            Thread.currentThread().interrupt();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void removeCallBack(PlayerMessageCallBack playerMessageCallback)
    {
        playerMessageCallbacks.remove(playerMessageCallback);
    }
}
