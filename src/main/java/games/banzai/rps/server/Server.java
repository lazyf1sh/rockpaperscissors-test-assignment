package games.banzai.rps.server;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Server
{
    private final Set<PlayerInteractionThread> playersThreads = Collections.synchronizedSet(new HashSet<>());
    private final Set<GameSession>             gameSessions   = Collections.synchronizedSet(new HashSet<>());

    public void runServer()
    {
        new ConnectionListenerThread(playersThreads).start();
        new MatcherThread(playersThreads, gameSessions).start();
        new GameSessionRunnerThread(gameSessions).start();
        new MaintenanceThread(playersThreads).start();

        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                playersThreads
                        .stream()
                        .filter(PlayerInteractionThread::isActive)
                        .peek(thread -> thread.sendMessage("Server is stopping now. You will be disconnected."))
                        .forEach(Thread::interrupt)));
    }
}
