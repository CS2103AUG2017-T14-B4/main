package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns an DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        args = args.trim();
        List<String> indexStrs = Arrays.asList(args.split(" "));
        //eliminate duplicates
        HashSet<Integer> indexIntsSet = new HashSet<>();

        for (String indexStr : indexStrs) {
            try {
                indexIntsSet.add(ParserUtil.parseInt(indexStr));
            } catch (IllegalValueException e) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
        }

        List<Integer> indexInts = new ArrayList<>(indexIntsSet);

        Collections.sort(indexInts);


        ArrayList<Index> indexes = new ArrayList<>();

        for (int i = 0; i < indexInts.size(); i++) {
            indexes.add(Index.fromOneBased(indexInts.get(i)));
        }

        for (Index inx : indexes) {
            System.out.println(inx.getOneBased());
        }

        return new DeleteCommand(indexes);
    }

}
