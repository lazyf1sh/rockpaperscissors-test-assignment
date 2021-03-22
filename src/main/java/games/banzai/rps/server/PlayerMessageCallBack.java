package games.banzai.rps.server;

@FunctionalInterface
public interface PlayerMessageCallBack extends CommonCallback
{
    void onPlayerMessage(String msg);
}
