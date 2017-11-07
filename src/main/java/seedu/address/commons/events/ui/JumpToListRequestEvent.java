package seedu.address.commons.events.ui;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to jump to the list of persons
 */
public class JumpToListRequestEvent extends BaseEvent {

    public final int targetIndex;
    public boolean isGroupType;

    public JumpToListRequestEvent(Index targetIndex, boolean isGrpType) {
        this.targetIndex = targetIndex.getZeroBased();
        this.isGroupType = isGrpType;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
