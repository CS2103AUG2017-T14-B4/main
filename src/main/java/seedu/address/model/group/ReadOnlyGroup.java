package seedu.address.model.group;

import javafx.beans.property.ObjectProperty;
import seedu.address.model.person.ReadOnlyPerson;

public interface ReadOnlyGroup extends ReadOnlyPerson {

    ObjectProperty<String> grpNameProperty();
    String getGrpName();

}
