/** @@author Junting **/
package seedu.address.logic.parser;

import seedu.address.logic.commands.DeleteTagCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

public class DeleteTagCommandParser implements Parser<DeleteTagCommand> {

    /**
     * Parses given {@code String} of argument in the context of the DeleteTagCommand
     * and returns a DeleteTagCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteTagCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteTagCommand.MESSAGE_USAGE));
        }

        String keyword = trimmedArgs;

        return new DeleteTagCommand(keyword);
    }

}
