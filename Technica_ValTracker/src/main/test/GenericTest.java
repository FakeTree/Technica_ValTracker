import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Mostly for brainstorming test cases. Need to organise into
 * appropriate test files if any are written here.
 */
public class GenericTest {

    /*
    * validateRiotId(String gameName, String tagLine)
    * returns boolean : true if valid, false if invalid
    * */
    // username requirements -
    // 3-16 characters long
    // Does not start or end with whitespace
    @Test
    public void validateRiotId_whenUsernameLengthOutOfBounds_shouldReturnFalse() {
        // Create string < 3 or > 16
        // Call function
        // Check if false
    }

    @Test
    public void validateRiotId_whenUsernameLengthWithinBounds_shouldReturnTrue() {
        // Create string >= 3 or <= 16
        // Call function
        // Check if true
    }

    @Test
    public void validateRiotId_whenTaglineLengthOutOfBounds_shouldReturnFalse() {}

    @Test
    public void validateRiotId_whenTaglineLengthWithinBounds_shouldReturnTrue() {}

    @Test
    public void validateRiotId_whenUsernameHasLeadingOrEndingWhitespace_shouldReturnFalse() {}

    @Test
    public void validateRiotId_whenTaglineHasLeadingOrEndingWhitespace_shouldReturnFalse() {}
}
