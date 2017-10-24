package seedu.address.logic.commands;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class GroupingCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "CreateGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a group for the list of person based on group name and index numbers provided"
            + "Parameters: GROUP_NAME INDEX [INDEX]...\n"
            + "Example: " + COMMAND_WORD + " SmartOnes 1 4 2";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Created group for people:\n";

    private List<Index> targetIdxs;

    public GroupingCommand(List<Index> targetIndex) {
        this.targetIdxs = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        ArrayList<Integer> executableIdx = new ArrayList<>();
        ArrayList<Integer> unExeIdx = new ArrayList<>();
        Boolean hasExecutableIdx = false;
        for (Index idx : targetIdxs) {
            int intIdx = idx.getZeroBased();
            if (intIdx < lastShownList.size()) {
                executableIdx.add(intIdx);
                hasExecutableIdx = true;
            } else {
                unExeIdx.add(intIdx);
            }
        }
        if (!hasExecutableIdx) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_INDEX_ALL);
        }
        ArrayList<ReadOnlyPerson> toDeletePerson = new ArrayList<>();
        for (int idx : executableIdx) {
            toDeletePerson.add(lastShownList.get(idx));
        }
        for (int i = 0; i < executableIdx.size(); i++) {
            try {
                model.deletePerson(toDeletePerson.get(i));
            } catch (PersonNotFoundException e) {
                assert false : "The target person cannot be missing";
            }
        }

        return new CommandResult(getSb(toDeletePerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GroupingCommand // instanceof handles nulls
                && this.targetIdxs.equals(((GroupingCommand) other).targetIdxs)); // state check
    }

    /**
     * Return a String
     * @param persons to be deleted
     * @return a String with all details listed
     */
    public static String getSb(ArrayList<ReadOnlyPerson> persons) {
        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_DELETE_PERSON_SUCCESS);

        for (int i = 0; i < persons.size(); i++) {
            sb.append(i + 1);
            sb.append(". ");
            sb.append(persons.get(i));
            sb.append("\n");
        }
        return sb.toString();
    }
}
