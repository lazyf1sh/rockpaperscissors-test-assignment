package games.banzai.rps.server.entry.point;

import games.banzai.rps.server.other.Constants;
import games.banzai.rps.server.other.Server;

/**
 * App entry point.
 */
public class ServerRunner
{
    public static void main(String[] args)
    {
        new Server(Constants.DEFAULT_PORT).runServer();
        System.out.println("Server is run on port " + Constants.DEFAULT_PORT);
    }
}
