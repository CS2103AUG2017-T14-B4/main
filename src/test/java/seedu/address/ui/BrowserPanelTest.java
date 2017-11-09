package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.BrowserPanel.*;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.BrowserPanel.FACEBOOK_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_MAP_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_SUFFIX;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.BrowserPanelLocateEvent;
import seedu.address.commons.events.ui.PersonFacebookOpenEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Facebook;
import seedu.address.model.person.Person;

public class BrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private BrowserPanelLocateEvent locateEventStub;
    private PersonFacebookOpenEvent facebookOpenEventStub;
    private Person dummy;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    private String startAddress = "Clementi";
    private String endAddress = "NUS";

    @Before
    public void setUp() {
        dummy = new Person(ALICE);
        dummy.setFacebook(new Facebook("zuck"));

        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));
        locateEventStub = new BrowserPanelLocateEvent(startAddress, endAddress);
        facebookOpenEventStub = new PersonFacebookOpenEvent(dummy);


        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = new URL(DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_MAP_URL_PREFIX
                + StringUtil.partiallyEncode(ALICE.getAddress().value)
                + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());

        // associated facebook page of a person
        postNow(facebookOpenEventStub);
        expectedPersonUrl = new URL(FACEBOOK_PREFIX + dummy.getFacebook().value);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }

    //@@author majunting
    @Test
    public void displayLocate() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        postNow(locateEventStub);
        URL expectedLocateUrl = new URL(GOOGLE_MAP_DIR_URL_PREFIX
                + StringUtil.partiallyEncode(startAddress) + GOOGLE_MAP_URL_SUFFIX
                + StringUtil.partiallyEncode(endAddress) + GOOGLE_MAP_URL_SUFFIX
                + GOOGLE_MAP_URL_END);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedLocateUrl, browserPanelHandle.getLoadedUrl());
    }
    //@@author
}
