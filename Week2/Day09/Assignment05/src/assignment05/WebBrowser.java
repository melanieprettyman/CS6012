package assignment05;

import java.net.URL;
import java.util.NoSuchElementException;

public class WebBrowser {
    private static SinglyLinkedList<URL> history;
    private LinkedListStack<URL> forwardStack;
    //private LinkedListStack<URL> backStack;


    URL currentWebpage_;

    /**
     * Constructor 1: Creates a new web browser with no history
     */
    public WebBrowser() {
        history = new SinglyLinkedList<>();
        forwardStack = new LinkedListStack<>();
    }

    /**
     * Constructor 2: Creates a new web browser with a preloaded history
     * @param history
     */
    public WebBrowser(SinglyLinkedList<URL> history) {
        this.history = history;
        forwardStack = new LinkedListStack<>();
    }

    /**
     * This method simulates visiting a webpage, given as a URL.
     * Note that calling this method should clear the forward button stack, since there is no URL to visit next.
     * @param webpage
     */
    public void visit(URL webpage){
        //most recent webpage is always at the beginning of the list
        history.insertFirst(webpage);
        //clear forward stack because there are no webpages after this
        forwardStack.clear();
    }

    /**
     * This method simulates using the back button, returning the URL visited.
     * NoSuchElementExceptionLinks to an external site. is thrown if there is no previously-visited URL.
     * @return
     * @throws NoSuchElementException
     */
    public URL back()throws NoSuchElementException {
        if (history.isEmpty()) {
            throw new NoSuchElementException("No webpage history.");
        }
        //Remove current web-url from history
        currentWebpage_ = history.deleteFirst();
        //Push the current webpage to forward stack
        forwardStack.push(currentWebpage_);
        //return previous webpage
        return history.getFirst();

    }

    /**
     * This method simulates using the forward button, returning the URL visited.
     * @return
     * @throws NoSuchElementException
     */
    public URL forward() throws NoSuchElementException {
        // NoSuchElementException is thrown if there is no URL to visit next.
        if (history.isEmpty()) {
            throw new NoSuchElementException("No webpage history.");
        }
        //Pop a webpage from forward-stack
        URL forwardURL = (URL) forwardStack.pop();
        //Add webpage to history
        history.insertFirst(forwardURL);
        //return the forwardURL page
        return forwardURL; }

    /**
     * This method generates a history of URLs visited, as a list of URL objects ordered from most recently visited to least recently visited
     * @return
     */
    public SinglyLinkedList<URL> history() {
        return history; }



}

