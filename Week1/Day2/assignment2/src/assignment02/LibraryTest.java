package assignment02;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
        ArrayList<LibraryBook> booksCheckedOut = lib.lookup("Jane Doe");

//        var booksCheckedOut = lib.lookup("Jane Doe");
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

        //CASE; WRONG HOLDER NAME, RETURN EMPTY ARRAY-LIST (LOOK-UP)
        var mismatchHolder = lib.lookup("Harry Doe");
        assertEquals(mismatchHolder.size(), 0);

    }
    @Test
    public void stringLibraryTest() {
        // test a library that uses names (String) to id patrons
        Library<String> lib = new Library<>();
        lib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
        lib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
        lib.add(9780446580342L, "David Baldacci", "Simple Genius");

        String patron1 = "Jane Doe";

        assertTrue(lib.checkout(9780330351690L, patron1, 1, 1, 2008));

        assertTrue(lib.checkout(9780374292799L, patron1, 1, 1, 2008));

        var booksCheckedOut1 = lib.lookup(patron1);
        assertEquals(booksCheckedOut1.size(), 2);
        assertTrue(booksCheckedOut1.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild")));
        assertTrue(booksCheckedOut1.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat")));
        assertEquals(booksCheckedOut1.get(0).getHolder(), patron1);
        assertEquals(booksCheckedOut1.get(0).getDueDate(), new GregorianCalendar(2008, 1, 1));
        assertEquals(booksCheckedOut1.get(1).getHolder(),patron1);
        assertEquals(booksCheckedOut1.get(1).getDueDate(),new GregorianCalendar(2008, 1, 1));

        assertTrue(lib.checkin(patron1));

    }

    @Test
    public void phoneNumberTest(){
        // test a library that uses phone numbers (PhoneNumber) to id patrons
        var lib = new Library<PhoneNumber>();
        lib.add(9780374292799L, "Thomas L. Friedman", "The World is Flat");
        lib.add(9780330351690L, "Jon Krakauer", "Into the Wild");
        lib.add(9780446580342L, "David Baldacci", "Simple Genius");

        PhoneNumber patron2 = new PhoneNumber("801.555.1234");

        assertTrue(lib.checkout(9780330351690L, patron2, 1, 1, 2008));
        assertTrue(lib.checkout(9780374292799L, patron2, 1, 1, 2008));

        ArrayList<LibraryBook<PhoneNumber>> booksCheckedOut2 = lib.lookup(patron2);

        assertEquals(booksCheckedOut2.size(), 2);
        assertTrue(booksCheckedOut2.contains(new Book(9780330351690L, "Jon Krakauer", "Into the Wild")));
        assertTrue(booksCheckedOut2.contains(new Book(9780374292799L, "Thomas L. Friedman", "The World is Flat")));
        assertEquals(booksCheckedOut2.get(0).getHolder(),patron2);
        assertEquals(booksCheckedOut2.get(0).getDueDate(),new GregorianCalendar(2008, 1, 1));
        assertEquals(booksCheckedOut2.get(1).getHolder(), patron2);
        assertEquals(booksCheckedOut2.get(1).getDueDate(), new GregorianCalendar(2008, 1, 1));

        assertTrue(lib.checkin(patron2));


    }

    @Test
    public void testOverDueBookList() {
        // test a medium library
        var lib = new Library();
        lib.addAll("Mushroom_Publishing.txt");

        //Have someone check out a books
        var checkout1 = lib.checkout(9781843190042L, "Jane Doe", 1, 1, 2008);
        var checkout2 = lib.checkout(9781843190073L, "Jane Doe", 1, 1, 2008);
        var checkout3 = lib.checkout(9781843190110L, "Jane Doe", 1, 1, 2008);
        var checkout4 = lib.checkout(9781843190349L, "Jane Doe", 1, 1, 2024);
        var checkout5 = lib.checkout(9781843190363L, "Jane Doe", 1, 1, 2024);

        //create overdue list
        ArrayList<LibraryBook> overdueList = lib.getOverdueList(11,1,2022);
        //There are 3 overdue books, assert that the size of the list is 3
        assertEquals(overdueList.size(), 3);

    }

    @Test
    public void testInventory() {
        // make a library
        var lib = new Library();
        lib.add(1000, "Thomas L. Friedman", "The World is Flat");
        lib.add(1001, "Jon Krakauer", "Into the Wild");
        lib.add(1002, "David Baldacci", "Simple Genius");
        //create sorted ISBN list
        ArrayList<LibraryBook> sortByISBN = lib.getInventoryList();
        //Assert the first ISBN in the list is 1000
        assertEquals(sortByISBN.get(0).getIsbn(), 1000);


    }

    @Test
    public void testOrderByAuthor() {
        // make a library
        var lib = new Library();
        lib.add(1000, "Thomas L. Friedman", "The World is Flat");
        lib.add(1001, "Jon Krakauer", "Into the Wild");
        lib.add(1002, "David Baldacci", "Simple Genius");
        //create sorted author list
        ArrayList<LibraryBook> sortByAuthor = lib.getOrderedByAuthor();
        //First author is David, assert the first author in the list is David
        assertEquals(sortByAuthor.get(0).getAuthor(), "David Baldacci");

        //CASE; SAME AUTHOR, DIFF BOOKS
        //Add book with the same author but Title is alphabetically first
        lib.add(1003, "David Baldacci", "Evil Genius");
        ArrayList<LibraryBook> sortByAuthor2 = lib.getOrderedByAuthor();
        //First book in the list should be "Evil Genius" assert this is true
         assertEquals(sortByAuthor2.get(0).getTitle(), "Evil Genius");

         //CASE; DUPLICATE BOOKS
        //Add a repeat book to library
        lib.add(1004, "David Baldacci", "Evil Genius");
        ArrayList<LibraryBook> sortByAuthor3 = lib.getOrderedByAuthor();
        //list should be "Evil Genius", "Evil Genius", "Simple Genius". Assert this is true
        assertEquals(sortByAuthor3.get(0).getTitle(), "Evil Genius");
        assertEquals(sortByAuthor3.get(1).getTitle(), "Evil Genius");
        assertEquals(sortByAuthor3.get(2).getTitle(), "Simple Genius");
        assertEquals(sortByAuthor3.get(3).getAuthor(), "Jon Krakauer");



    }
    @Test
    public void testOrderByAuthorComparatorHandlesNulls() {
        Library<String> library = new Library<>();

        // Create LibraryBookGeneric instances with null authors
        LibraryBook<String> book1 = new LibraryBook<>(123456789L, null, "Book Title 1");
        LibraryBook<String> book2 = new LibraryBook<>(987654321L, "John Doe", "Book Title 2");

        ArrayList<LibraryBook<String>> bookList = new ArrayList<>();
        bookList.add(book1);
        bookList.add(book2);

        library.addAll(bookList);

        // Test the comparator with a null author book
        // This should not throw any exceptions
        library.getOrderedByAuthor();
    }
}