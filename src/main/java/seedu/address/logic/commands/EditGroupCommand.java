package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

public class EditGroupCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": edits the group. Supports three kinds of operations: 1. change group name 2. add a person 3. delete" +
            " a person\n"
            + "Parameters: GROUP_NAME grpName NEW_GROUP_NAME\n"
            + "OR: GROUP_NAME add INDEX\n"
            + "OR: GROUP_NAME delete INDEX\n"
            + "Examples: " + COMMAND_WORD + " SmartOnes grpName SuperSmartOnes\n"
            + COMMAND_WORD + " SmartOnes add 3\n"
            + COMMAND_WORD + " SmartOnes delete 4";

    public static final String MESSAGE_ADD_PERSON_SUCCESS = "Added person to group '%s':\n'%s'";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted person from group '%s':\n'%s'";

    public static final String MESSAGE_CHANGE_NAME_SUCCESS = "Name of group '%s' is changed to '%s'";

    public static final String MESSAGE_GROUP_NONEXISTENT = "This group does not exist!\n";

    private String MESSAGE_DUPLICATE_PERSON = "This person is already in the group";

    private String grpName, operation, detail;

    public EditGroupCommand(String grpName, String operation, String detail) {
        this.grpName = grpName;
        this.operation = operation;
        this.detail = detail;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<Group> grpList = model.getAddressBook().getGroupList();

        // find target group
        Group targetGrp = null;
        for (int i = 0; i < grpList.size(); i++) {
            if (grpList.get(i).getGrpName().equals(grpName)) {
                targetGrp = grpList.get(i);
                break;
            }
        }

        if (targetGrp == null) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_GROUP_NONEXISTENT);
        }

        // use 'detail' differently according to operation type
        Index idx = null;
        if (operation.equals("add") || operation.equals("delete")) {
            try {
                idx = ParserUtil.parseIndex(detail);
            } catch (IllegalValueException e) {
                throw new AssertionError("detail should be an integer at this stage");
            }
        }

        // operation is change grpName
        if (idx == null) {
            model.setGrpName(targetGrp, detail);
            return new CommandResult(String.format(MESSAGE_CHANGE_NAME_SUCCESS, grpName, detail));
        } else {
            ReadOnlyPerson targetPerson;
            Person copiedPerson;
            if (operation.equals("add")) { //add operation

                List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
                if (idx.getZeroBased() >= lastShownList.size()) {
                    throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
                targetPerson = lastShownList.get(idx.getZeroBased());
                copiedPerson = new Person(targetPerson);

                try {
                    model.addPersonToGroup(targetGrp, copiedPerson);
                    return new CommandResult(String.format(MESSAGE_ADD_PERSON_SUCCESS, grpName, copiedPerson.toString()));
                } catch (DuplicatePersonException e) {
                    throw new CommandException(MESSAGE_DUPLICATE_PERSON);
                }
            } else { //delete operation

                List<ReadOnlyPerson> grpPersonList = targetGrp.getPersonList();
                if (idx.getZeroBased() >= grpPersonList.size()) {
                    throw new CommandException(MESSAGE_EXECUTION_FAILURE, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
                }
                targetPerson = grpPersonList.get(idx.getZeroBased());
                copiedPerson = new Person(targetPerson);

                try {
                    model.removePersonFromGroup(targetGrp, copiedPerson);
                    return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, grpName, copiedPerson.toString()));
                } catch (PersonNotFoundException e) {
                    assert false : "The target person cannot be missing";
                }
            }
        }
        return null;
    }
}
