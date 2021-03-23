package games.banzai.rps.server.other;

@FunctionalInterface
public interface PlayerMessageCallBack
{
    void onPlayerMessage(String msg);
}
