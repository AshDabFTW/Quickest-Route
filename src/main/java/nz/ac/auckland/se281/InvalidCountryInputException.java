package nz.ac.auckland.se281;

/** Custom unchecked runtime exception for when a invalid country was inputted by the user. */
public class InvalidCountryInputException extends RuntimeException {

  public InvalidCountryInputException(String input) {
    super(MessageCli.INVALID_COUNTRY.getMessage(input));
  }
}
