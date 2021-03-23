package games.banzai.rps.server.other;

/**
 * Converts player input to inner structure - {@link RockPaperScissors}.
 *
 * @author Ivan Kopylov
 */
public class RockPaperScissorsMoveParse
{
    /**
     * Parses user input.
     *
     * @param userInput user input.
     * @return null when input is unexpected.
     */
    public static RockPaperScissors parse(String userInput)
    {
        RockPaperScissors result = null;
        if (!userInput.isBlank())
        {
            if (userInput.equals("1") || userInput.toLowerCase().equals("rock") || userInput.toLowerCase().equals("r"))
            {
                result = RockPaperScissors.ROCK;
            }
            else if (userInput.equals("2") || userInput.toLowerCase().equals("paper") || userInput.toLowerCase().equals("p"))
            {
                result = RockPaperScissors.PAPER;
            }
            else if (userInput.equals("3") || userInput.toLowerCase().equals("scissors") || userInput.toLowerCase().equals("s"))
            {
                result = RockPaperScissors.SCISSORS;
            }
        }
        System.out.println("Parsed result: " + result);
        return result;//invalid input indicator
    }
}
