package games.banzai.rps.server;

import javax.swing.*;

public enum GameElements
{
    ROCK("1"),
    PAPER("2"),
    SCISSORS("3");

    private final String value;

    GameElements(String value)
    {
        this.value = value;
    }
}
