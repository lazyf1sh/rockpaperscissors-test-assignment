package games.banzai.rps.server.other;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicReference;

public final class PlayerInteractionJob implements Runnable
{
    private final PlayerResource playerResource;

    public PlayerInteractionJob(PlayerResource playerResource)
    {
        this.playerResource = playerResource;
    }

    @Override
    public void run()
    {
        try
        {
            if (!isStale())
            {
                PrintWriter writer = playerResource.getWriter();
                AtomicReference<String> nickname = playerResource.getNickname();
                BufferedReader reader = playerResource.getReader();

                if (nickname.get() == null)
                {
                    writer.println("You've successfully connected.");
                    writer.println("Please enter your nickname: ");
                    writer.flush();

                    playerResource.getNickname().set(reader.readLine());

                    System.out.println("Played entered nickname: " + nickname.get());
                }

                String line = reader.readLine();
                if (line == null)
                {
                    System.out.println("null received from: " + nickname.get() + " - end of stream. disconnecting...");
                    this.disconnect();
                    return;
                }
                System.out.println("Message from player " + nickname + ": " + line);

                for (PlayerMessageCallBack playerMessageCallback : playerResource.getPlayerMessageCallbacks())
                {
                    playerMessageCallback.onPlayerMessage(line);
                }
            }
        }
        catch (IOException ex)
        {
            //log message with LOG4J or another suitable logger
            this.disconnect();
            ex.printStackTrace();
        }
    }

    public void sendMessage(String msg)
    {
        if (playerResource.getWriter() != null && !isStale())
        {
            playerResource.getWriter().println(msg);
            playerResource.getWriter().flush();
            System.out.println("Send msg: " + msg + " to player " + playerResource.getNickname());
        }
        else
        {
            //log message with LOG4J or another suitable logger
            throw new RuntimeException("Waiting for this exception during debugging e806ed75c61cb695a384");
        }
    }

    public void addCallBack(PlayerMessageCallBack playerMessageCallback)
    {
        playerResource.getPlayerMessageCallbacks().add(playerMessageCallback);
    }

    /**
     * Active and entered a nickname.
     *
     * @return if players is ready to play.
     */
    public boolean isReadyToPlay()
    {
        return isActive() && playerResource.getNickname().get() != null & !isStale();
    }

    public boolean isActive()
    {
        return playerResource.getActive().get();
    }

    /**
     * Ready to be removed. No longer required from technical and business point of view.
     *
     * @return if object is no more needed.
     */
    public boolean isStale()
    {
        return !playerResource.getActive().get() && !playerResource.getSocket().isClosed();
    }

    public String getNickname()
    {
        return playerResource.getNickname().get();
    }

    /**
     * Closed infrastructure and marks thread to be deleted.
     */
    public void disconnect()
    {
        try
        {
            //log disconnect action with LOG4J or another suitable logger
            this.clearAllCallBacks();
            playerResource.getSocket().close();
            playerResource.getActive().set(false);
            playerResource.getScheduledExecutorService().shutdownNow();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void removeCallBack(PlayerMessageCallBack playerMessageCallback)
    {
        playerResource.getPlayerMessageCallbacks().remove(playerMessageCallback);
    }

    public void clearAllCallBacks()
    {
        playerResource.getPlayerMessageCallbacks().clear();
    }
}
