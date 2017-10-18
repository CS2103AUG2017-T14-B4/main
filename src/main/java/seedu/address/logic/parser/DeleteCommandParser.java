package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    private int STI(String a) {
        a = a.trim();
        return Integer.parseInt(a);
    }

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        args = args.trim();
        List<String> indexStrs = Arrays.asList(args.split(" "));
        ArrayList<Index> indexes = new ArrayList<>();

        for (int i=0; i<indexStrs.size(); i++) {
            indexes.add( Index.fromOneBased(STI(indexStrs.get(i))-i));
        }

        /*
        for (String indexStr : indexStrs) {
            try {
                indexes.add(ParserUtil.parseIndex(indexStr));
            } catch (IllegalValueException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        }
        */
        /*
        ArrayList<Index> indexFinal = new ArrayList<>();

        for (int i=0; i<indexes.size(); i++) {
            try {
                indexFinal.add(ParserUtil.parseIndex(String.valueOf(indexes.get(i).getOneBased())));
            } catch (IllegalValueException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        }
        */

        return new DeleteCommand(indexes);

        /*
        try {
            Index index = ParserUtil.parseIndex(args);
            return new DeleteCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        */
    }

}
