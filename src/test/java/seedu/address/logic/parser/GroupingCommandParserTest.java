//@@author hthjthtrh
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.GroupingCommandParser.MESSAGE_INCORRECT_GROUPNAME_FORMAT;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.GroupingCommand;
import seedu.address.testutil.TypicalIndexes;

public class GroupingCommandParserTest {
    private GroupingCommandParser parser = new GroupingCommandParser();
    private List<Index> testIndexes = new ArrayList<>();

    @Test
    public void parse_allFieldsPresent_success() {
        testIndexes.add(TypicalIndexes.INDEX_FIRST_PERSON);
        String testGrpName = "test";
        assertParseSuccess(parser, "test 1", new GroupingCommand(testGrpName, testIndexes));

        //duplicate indexes
        assertParseSuccess(parser, "test 1 1 1", new GroupingCommand(testGrpName, testIndexes));

        //multiple indexes
        testIndexes.add(TypicalIndexes.INDEX_THIRD_PERSON);
        testIndexes.add(TypicalIndexes.INDEX_THIRDTEENTH_PERSON);
        assertParseSuccess(parser, "test 1 3 13", new GroupingCommand(testGrpName, testIndexes));
    }

    @Test
    public void parse_indexFieldsMissing_failure() {
        String expectedMessage = MESSAGE_INVALID_COMMAND_FORMAT + GroupingCommand.MESSAGE_USAGE;

        assertParseFailure(parser, "test", expectedMessage);

        //trailing white spaces
        assertParseFailure(parser, "   test    ", expectedMessage);
    }

    @Test
    public void parse_emptyArgument_failure() {
        String expectedMessage = MESSAGE_INVALID_COMMAND_FORMAT + GroupingCommand.MESSAGE_USAGE;

        assertParseFailure(parser, "",  expectedMessage);
    }

    @Test
    public void parse_invalidGroupName_failure() {
        String expectedMessage = MESSAGE_INCORRECT_GROUPNAME_FORMAT + GroupingCommand.MESSAGE_USAGE;

        assertParseFailure(parser, "1234321 1 3 5 ", expectedMessage);
    }
}
//@@author
