//@@author hthjthtrh
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.group.Group;
import seedu.address.model.person.exceptions.DuplicatePersonException;

public class TypicalGroups {

    public Group testGroup3;

    public Group testGroup4;

    public TypicalGroups() {
        testGroup3 = new Group("TestGrp3");
        try {
            testGroup3.add(TypicalPersons.FIONA);
            testGroup3.add(TypicalPersons.BOB);
            testGroup3.add(TypicalPersons.HOON);
        } catch (DuplicatePersonException e) {
            assert false : "not possible";
        }

        try {
            testGroup4 = new Group(testGroup3);
            testGroup4.setGrpName("TestGrp4");
            testGroup4.add(TypicalPersons.CARL);
            testGroup4.add(TypicalPersons.GEORGE);
        } catch (DuplicatePersonException e) {
            assert false : "not possible";
        }
    }

    public List<Group> getTypicalGroup() {
        return new ArrayList<>(Arrays.asList(testGroup3, testGroup4));
    }
}
//@@author
