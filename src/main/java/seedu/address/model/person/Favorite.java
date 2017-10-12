//@@author: Giang
package seedu.address.model.person;

/**
 * Represents whether a Person is favorited in the address book.
 */
public class Favorite {
    private boolean favorite;

    public Favorite() {
        this.favorite = false;
    }


    @Override
    public String toString() {
        return favorite ? "true" : "false";
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Favorite // instanceof handles nulls
                && this.favorite == (((Favorite) other).favorite)); // state check
    }
}
