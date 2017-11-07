//@@author hthjthtrh
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.commands.UndoableCommand.appendPersonList;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.predicates.GroupContainsPersonPredicate;

/**
 * Lists all person within the group
 */
public class ViewGroupCommand extends Command {

    public static final String COMMAND_WORD = "viewGroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": list all persons in the specified group(by group name or index)\n"
            + "Parameters: GROUP_NAME          OR          INDEX\n"
            + "Example: " + COMMAND_WORD + " SmartOnes";

    public static final String MESSAGE_GROUPING_PERSON_SUCCESS = "Listing %d person(s) in the group '%s'";

    public static final String MESSAGE_GROUP_NONEXISTENT = "This group does not exist!\n";

    private Index index = null;
    private String grpName = null;
    private Predicate predicate;

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
                grpToView = grpList.get(index.getZeroBased());

                predicate = new GroupContainsPersonPredicate(grpToView);
                model.updateFilteredPersonList(predicate);

                EventsCenter.getInstance().post(new JumpToListRequestEvent(this.index, true));
                return new CommandResult(String.format(MESSAGE_GROUPING_PERSON_SUCCESS,
                        grpToView.getPersonList().size(), grpToView.getGrpName()));
            } catch (IndexOutOfBoundsException e) {
                throw new CommandException(MESSAGE_EXECUTION_FAILURE, Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
            }
        } else { //either index or grpName should be non-null
            for (int i = 0; i < grpList.size(); i++) {
                if (grpList.get(i).getGrpName().equals(this.grpName)) {
                    grpToView = grpList.get(i);
                    predicate = new GroupContainsPersonPredicate(grpToView);
                    model.updateFilteredPersonList(predicate);

                    EventsCenter.getInstance().post(new JumpToListRequestEvent(Index.fromZeroBased(i), true));
                    return new CommandResult(String.format(MESSAGE_GROUPING_PERSON_SUCCESS,
                            grpToView.getPersonList().size(), grpToView.getGrpName()));
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

    @Override
    public boolean equals(Object other) {
        if (other instanceof ViewGroupCommand) {
            if (this.index == null) {
                return this.grpName.equals(((ViewGroupCommand) other).grpName);
            } else {
                return this.index.equals(((ViewGroupCommand) other).index);
            }
        }
        return false;
    }
}
//@@author
