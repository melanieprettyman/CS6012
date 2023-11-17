package assignment05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.NoSuchElementException;

class WebBrowserTest {
    WebBrowser chrome;
    WebBrowser safari;


    SinglyLinkedList<URL> chromeHistory;
    URL URL1, URL2, URL3, URL4;

    {
        try {
            URL1 = new URL("https://1");
            URL2 = new URL("https://2");
            URL3 = new URL("https://3");
            URL4 = new URL("https://4");


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() throws MalformedURLException {
        // TEST Constructor 1 for creating a new web browser with no history
        chrome = new WebBrowser();
        chrome.visit(URL1);
        chrome.visit(URL2);
        chrome.visit(URL3);
    }

    @Test
    void visit() {
        //visit URL4
        chrome.visit(URL4);
        //Get history
        chromeHistory = chrome.history();
        //Chrome should contain 4 URLS
        assertEquals(4,chromeHistory.size());
    }

    @Test
    void back() {
        //Go back one URl, current url should now be URL2
        assertEquals(URL2.toString(), chrome.back().toString());
        //Go back one URl, current url should now be URL1
        assertEquals(URL1.toString(), chrome.back().toString());
        //chrome history is empty, should throw an error
        assertThrows(NoSuchElementException.class, () -> {
            chrome.back();
        });


    }

    @Test
    void forward() {
        //visit URL4
        chrome.visit(URL4);
        chrome.back(); //current url is URL3
        assertEquals(URL4.toString(), chrome.forward().toString()); //assert the forward URL is URL4
        //chrome is on current URL, should throw an error if you try to go forward
        assertThrows(NoSuchElementException.class, () -> {
            chrome.forward();
        });

    }

    @Test
    void history() {
        //Get history
        chromeHistory = chrome.history();
        historyHelper(chromeHistory);

        //TEST Constructor 2 for creating a new web browser with a preloaded history
        safari = new WebBrowser(chromeHistory);
        SinglyLinkedList<URL> safariHistory = safari.history();
        historyHelper(safariHistory);

    }

    public void historyHelper(SinglyLinkedList<URL> history){
        var iterator = history.iterator();
        var id = 3;
        while(iterator.hasNext()){
            var x = iterator.next();
            assertEquals("https://"+id--, x.toString());
        }
    }
}