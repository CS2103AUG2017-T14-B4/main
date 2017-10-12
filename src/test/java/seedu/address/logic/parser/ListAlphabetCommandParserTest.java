//@@author Junting
package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.ListAlphabetCommand;
import seedu.address.model.person.NameStartsWithAlphabetPredicate;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class ListAlphabetCommandParserTest {
    private ListAlphabetCommandParser parser = new ListAlphabetCommandParser();

    @Test
    public void parser_emptyArg_throwsParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListAlphabetCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsListAlphabetCommand() {
        ListAlphabetCommand expectedAlphabetCommand =
                new ListAlphabetCommand(new NameStartsWithAlphabetPredicate(("a")));
                assertParseSuccess(parser, "a", expectedAlphabetCommand);
    }
}
