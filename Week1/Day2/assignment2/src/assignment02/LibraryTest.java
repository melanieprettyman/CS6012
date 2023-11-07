package assignment02;

import assignment02.Book;
import assignment02.Library;
import assignment02.LibraryBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    @Test
    public void testEmpty() {
        Library lib = new Library();
        assertNull(lib.lookup(978037429279L));

        ArrayList<LibraryBook> booksCheckedOut = lib.lookup("Jane Doe");
        assertEquals(booksCheckedOut.size(), 0);

        assertFalse(lib.checkout(978037429279L, "Jane Doe", 1, 1, 2008));
        assertFalse(lib.checkin(978037429279L));
        assertFalse(lib.checkin("Jane Doe"));
    }

    //testing;
    //add
    //lookup ISBN
    //checkout
    //holder book list
    //checkin ISBN
    //checkin holder
    @Test
    public void testNonEmpty() {

        var lib = new Library();
        // test a small library
        lib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
        lib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
        lib.add(9780446580342L, "David Baldacci", "Simple Genius");

        assertNull(lib.lookup(9780330351690L)); //not checked out
        var res = lib.checkout(9780330351690L, "Jane Doe", 1, 1, 2008);
        assertTrue(res);
        var booksCheckedOut = lib.lookup("Jane Doe");
        assertEquals(booksCheckedOut.size(), 1);
        assertEquals(booksCheckedOut.get(0),new Book(9780330351690L, "Jon Krakauer", "Into the Wild"));
        assertEquals(booksCheckedOut.get(0).getHolder(), "Jane Doe");
        assertEquals(booksCheckedOut.get(0).getDueDate(),new GregorianCalendar(2008, 1, 1));
        res = lib.checkin(9780330351690L);
        assertTrue(res);
        res = lib.checkin("Jane Doe");
        assertFalse(res);
    }

    @Test
    public void testLargeLibrary(){
        // test a medium library
        var lib = new Library();
        lib.addAll("Mushroom_Publishing.txt");

        //CASE; DUPLICATE CHECKOUT
        //Have someone check out a book
        var checkout1 = lib.checkout(9781843190042L, "Jane Doe", 1, 1, 2008);
        //Have someone try and check out the same book
        var checkout2 = lib.checkout(9781843190042L, "Harry Man", 1, 1, 2008);
        //ceckout should return false since this book has already been checkouted
        assertFalse(checkout2);

        //CASE; CHECKING-IN A HOLDER
        var checkin = lib.checkin("Jane Doe");
        assertTrue(checkin);

        //CASE; WRONG ISBN (LOOK-UP)
        assertNull(lib.lookup(1234567891011L)); //not a real ISBN

        //CASE; WRONG HOLDER NAME, RETURN EMPTY ARRAY-LIST
        var mismatchHolder = lib.lookup("Harry Doe");
        assertEquals(mismatchHolder.size(), 0);
        
    }

}