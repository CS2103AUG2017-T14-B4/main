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
public class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the list of person identified by the index numbers used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 4 2";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Person:\n";

    private ArrayList<Index> targetIdxs;

    public DeleteCommand(ArrayList<Index> targetIndex) {
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

        StringBuilder sb = new StringBuilder();
        sb.append(MESSAGE_DELETE_PERSON_SUCCESS);

        for (int i = 0; i < toDeletePerson.size(); i++) {
            sb.append(i + 1);
            sb.append(". ");
            sb.append(toDeletePerson.get(i));
            sb.append("\n");
        }

        return new CommandResult(sb.toString());
    /*
        ArrayList<ReadOnlyPerson> peopleToDelete = new ArrayList<>();
        for (int idx : executableIdx) {
            peopleToDelete.add(lastShownList.get(idx));
        }

        for (ReadOnlyPerson person : peopleToDelete) {
            try {
                model.deletePerson(person);
            } catch (PersonNotFoundException e) {
                assert false : "The target person cannot be missing";
            }
        }
        */

    /*
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deletePerson(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete));
        */
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIdxs.equals(((DeleteCommand) other).targetIdxs)); // state check
    }
}
