package games.banzai.rps.server.other;

public enum RockPaperScissors
{
    ROCK("1"),
    PAPER("2"),
    SCISSORS("3");

    private final String value;

    RockPaperScissors(String value)
    {
        this.value = value;
    }
}
