package games.banzai.rps.server.entry.point;

import games.banzai.rps.server.other.Server;

/**
 * App entry point.
 */
public class ServerRunner
{
    public static void main(String[] args)
    {
        new Server(27015).runServer();
    }
}
