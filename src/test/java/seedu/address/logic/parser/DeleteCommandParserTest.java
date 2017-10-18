package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.testutil.TypicalIndexes;
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private ArrayList<Index> testIndexes = new ArrayList<Index>();

    private DeleteCommandParser parser = new DeleteCommandParser();
    private final String DELETE_PARSE_FAIL = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE);

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        testIndexes.add(TypicalIndexes.INDEX_FIRST_PERSON);
        assertParseSuccess(parser, "1", new DeleteCommand(testIndexes));

        testIndexes.clear();
        testIndexes.add(TypicalIndexes.INDEX_FIFTH_PERSON);
        testIndexes.add(TypicalIndexes.INDEX_EIGHTH_PERSON);
        testIndexes.add(TypicalIndexes.INDEX_THIRDTEENTH_PERSON);
        assertParseSuccess(parser, "5 9 15", new DeleteCommand(testIndexes));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ", DELETE_PARSE_FAIL);
        assertParseFailure(parser, "a", DELETE_PARSE_FAIL);
        assertParseFailure(parser, "1 3 a", DELETE_PARSE_FAIL);

    }
}
