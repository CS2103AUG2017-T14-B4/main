package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebView;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonFacebookOpenEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * The Browser Panel of the App.
 */
public class BrowserPanel extends UiPart<Region> {


    public static final String GOOGLE_SEARCH_URL_SUFFIX = "&cad=h&dg=dbrw&newdg=1";
    public static final String GOOGLE_MAP_URL_PREFIX = "https://www.google.com/maps/search/?api=1&query=";
    public static final String FACEBOOK_PREFIX = "https://www.facebook.com/";
    public static final String DEFAULT_PAGE = "http://www.nus.edu.sg/";
    private static final String FXML = "BrowserPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(this.getClass());

    @FXML
    private WebView browser;

    public BrowserPanel() {
        super(FXML);

        // To prevent triggering events for typing inside the loaded Web page.
        getRoot().setOnKeyPressed(Event::consume);

        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    private void loadPersonPage(ReadOnlyPerson person) {
        loadPage(GOOGLE_MAP_URL_PREFIX
                + StringUtil.partiallyEncode(person.getAddress().value)
                    + GOOGLE_SEARCH_URL_SUFFIX);
    }

    private void loadFacebookPage(ReadOnlyPerson person) {
        loadPage(FACEBOOK_PREFIX + person.getFacebook().value);
    }

    public void loadPage(String url) {
        Platform.runLater(() -> browser.getEngine().load(url));
    }

    /**
     * Loads a default HTML file with a background that matches the general theme.
     */
    private void loadDefaultPage() {
        loadPage(DEFAULT_PAGE);
    }

    /**
     * Frees resources allocated to the browser.
     */
    public void freeResources() {
        browser = null;
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonPage(event.getNewSelection().person);
    }

    @Subscribe
    private void handlePersonFacebookOpenEvent(PersonFacebookOpenEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadFacebookPage(event.getNewSelection());
    }

}
