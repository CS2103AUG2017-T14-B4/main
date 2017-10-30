package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.UndoableCommand.appendPersonList;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Lists all person within the group
 */
public class ViewGroupCommand extends Command {

    public static final String COMMAND_WORD = "viewGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": list all persons in the specified group(by group name or index)\n"
            + "Parameters: GROUP_NAME          OR          INDEX\n"
            + "Example: " + COMMAND_WORD + " SmartOnes";

    public static final String MESSAGE_GROUPING_PERSON_SUCCESS = "List of person in group '%s':\n";

    public static final String MESSAGE_GROUP_NONEXISTENT = "This group does not exist!\n";

    private Index index = null;
    private String grpName = null;

    public ViewGroupCommand(Index idx) {
        this.index = idx;
    }

    public ViewGroupCommand(String grpName) {
        this.grpName = grpName;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<Group> grpList = model.getAddressBook().getGroupList();
        Group grpToView;

        if (this.index != null) {
            try {
                System.out.println(index.getZeroBased() + "\n" + grpList.size());

                grpToView = grpList.get(index.getZeroBased());
                System.out.println(index.getZeroBased() + "\n" + grpList.size());
                return new CommandResult(personListAsString(grpToView));
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
            }
        } else { //either index or grpName should be non-null
            for (int i = 0; i < grpList.size(); i++) {
                if (grpList.get(i).getGrpName().equals(this.grpName)) {
                    return new CommandResult(personListAsString(grpList.get(i)));
                }
            }
        }
        throw new CommandException(MESSAGE_EXECUTION_FAILURE, MESSAGE_GROUP_NONEXISTENT);
    }

    /**
     * creates and returns a string to represent the list of person in the group
     * @param grpToView the target group
     * @return string to represent list of person
     */
    private String personListAsString(Group grpToView) {
        List<ReadOnlyPerson> personList = grpToView.getPersonList();

        StringBuilder sb = new StringBuilder();
        sb.append(String.format(MESSAGE_GROUPING_PERSON_SUCCESS, grpToView.getGrpName()));

        appendPersonList(sb, personList);

        return sb.toString();
    }
}
