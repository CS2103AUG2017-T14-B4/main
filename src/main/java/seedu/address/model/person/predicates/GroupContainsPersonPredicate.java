//@@author hthjthtrh
package seedu.address.model.person.predicates;

import java.util.function.Predicate;

import seedu.address.model.group.Group;
import seedu.address.model.person.ReadOnlyPerson;

public class GroupContainsPersonPredicate implements Predicate<ReadOnlyPerson> {

    Group group;

    public GroupContainsPersonPredicate(Group grp) {
        this.group = grp;
    }

    @Override
    public boolean test(ReadOnlyPerson readOnlyPerson) {
        return group.contains(readOnlyPerson);
    }
}
//@@author
