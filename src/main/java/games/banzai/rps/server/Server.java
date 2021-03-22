package games.banzai.rps.server;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class Server
{
    /**
     * Used this que to maintain players' join order.
     */
    private final BlockingQueue<PlayerInteractionThread> playersThreadsQue = new LinkedBlockingDeque<>();
    private final Set<GameSession>                       gameSessions      = Collections.synchronizedSet(new HashSet<>());

    public void runServer()
    {
        new ConnectionListenerThread(playersThreadsQue).start();
        new MatcherThread(playersThreadsQue, gameSessions).start();
        new GameSessionRunnerThread(gameSessions).start();
        new MaintenanceThread(playersThreadsQue).start();

        Runtime.getRuntime().addShutdownHook(new Thread(() ->
                playersThreadsQue
                        .stream()
                        .filter(PlayerInteractionThread::isStale)
                        .peek(thread -> thread.sendMessage("Server is stopping now. You will be disconnected."))
                        .forEach(Thread::interrupt)));
    }
}
