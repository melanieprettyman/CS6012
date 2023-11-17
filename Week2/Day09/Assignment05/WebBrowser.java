package assignment05;

import java.net.URL;
import java.util.NoSuchElementException;

public class WebBrowser {

    public WebBrowser(){}
    //-- This constructor creates a new web browser with no previously-visited webpages and no webpages to visit next.

    public WebBrowser(SinglyLinkedList<URL> history){}
    //-- This constructor creates a new web browser with a preloaded history of visited webpages, given as a list of URLLinks to an external site. objects. The first webpage in the list is the "current" webpage visited, and the remaining webpages are ordered from most recently visited to least recently visited.


    public void visit(URL webpage){}
    //-- This method simulates visiting a webpage, given as a URL. Note that calling this method should clear the forward button stack, since there is no URL to visit next.

    public URL back()throws NoSuchElementException {}
    //-- This method simulates using the back button, returning the URL visited. NoSuchElementExceptionLinks to an external site. is thrown if there is no previously-visited URL.

    public URL forward() throws NoSuchElementException {}
   // -- This method simulates using the forward button, returning the URL visited. NoSuchElementException is thrown if there is no URL to visit next.


    public SinglyLinkedList<URL> history() {}
    //-- This method generates a history of URLs visited, as a list of URL objects ordered from most recently visited to least recently visited (including the "current" webpage visited), without altering subsequent behavior of this web browser. "Forward" URLs are not included. The behavior of the method must be O(N), where N is the number of URLs.



}

