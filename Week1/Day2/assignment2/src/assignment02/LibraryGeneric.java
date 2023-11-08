package assignment02;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Scanner;

/**
 * Class representation of a library (a collection of library books).
 * 
 */
public class LibraryGeneric<T> {

  private ArrayList<LibraryBookGeneric<T>> library;

  public LibraryGeneric() {

    library = new ArrayList<LibraryBookGeneric<T>>();
  }

  /**
   * Add the specified book to the library, assume no duplicates.
   * 
   * @param isbn
   *          -- ISBN of the book to be added
   * @param author
   *          -- author of the book to be added
   * @param title
   *          -- title of the book to be added
   */
  public void add(long isbn, String author, String title) {

    library.add(new LibraryBookGeneric(isbn, author, title));
  }

  /**
   * Add the list of library books to the library, assume no duplicates.
   * 
   * @param list
   *          -- list of library books to be added
   */
  public void addAll(ArrayList<LibraryBookGeneric<T>> list) {
    library.addAll(list);
  }

  /**
   * Add books specified by the input file. One book per line with ISBN, author,
   * and title separated by tabs.
   * 
   * If file does not exist or format is violated, do nothing.
   * 
   * @param filename
   */
  public void addAll(String filename) {
    ArrayList<LibraryBookGeneric<T>> toBeAdded = new ArrayList<LibraryBookGeneric<T>>();

    try (Scanner fileIn = new Scanner(new File(filename))) {

      int lineNum = 1;

      while (fileIn.hasNextLine()) {
        String line = fileIn.nextLine();

        try (Scanner lineIn = new Scanner(line)) {
          lineIn.useDelimiter("\\t");

          if (!lineIn.hasNextLong()) {
            throw new ParseException("ISBN", lineNum);
          }
          long isbn = lineIn.nextLong();

          if (!lineIn.hasNext()) {
            throw new ParseException("Author", lineNum);
          }
          String author = lineIn.next();

          if (!lineIn.hasNext()) {
            throw new ParseException("Title", lineNum);
          }
          String title = lineIn.next();
          toBeAdded.add(new LibraryBookGeneric(isbn, author, title));
        }
        lineNum++;
      }
    } catch (FileNotFoundException e) {
      System.err.println(e.getMessage() + " Nothing added to the library.");
      return;
    } catch (ParseException e) {
      System.err.println(e.getLocalizedMessage() + " formatted incorrectly at line " + e.getErrorOffset()
          + ". Nothing added to the library.");
      return;
    }

    library.addAll(toBeAdded);
  }

  /**
   * Returns the holder of the library book with the specified ISBN.
   * 
   * If no book with the specified ISBN is in the library, returns null.
   * 
   * @param isbn
   *          -- ISBN of the book to be looked up
   */
  public <T> T lookup(long isbn) {
    //Loop over each library-book in the library-book arraylist
    for(LibraryBookGeneric book: library){
      //if the library-books ISBN matches the ISBN in question
      if(book.getIsbn() == isbn){
        //return the holder of the library book
        return (T) book.getHolder();
      }
    }
    //otherwise return null
    return null;
  }

  /**
   * Returns the list of library books checked out to the specified holder.
   * 
   * If the specified holder has no books checked out, returns an empty list.
   * 
   * @param holder
   *          -- holder whose checked out books are returned
   */
  public ArrayList<LibraryBookGeneric> lookup(T holder) {
    //Create an arraylist of Library-books, this will contain a list of the holders-books
    ArrayList<LibraryBookGeneric> holdersBooks = new ArrayList<>();

    //Loop over each library-book in the library-book arraylist
    for(LibraryBookGeneric book: library){
      //if the library-books holder matches the holder in question
      if (book.getHolder() != null && book.getHolder().equals(holder) ){
        //add the book to holdersBooks
        holdersBooks.add(book);
      }
    }
    //return arraylist of holders books
    return holdersBooks;

  }

  /**
   * Sets the holder and due date of the library book with the specified ISBN.
   * 
   * If no book with the specified ISBN is in the library, returns false.
   * 
   * If the book with the specified ISBN is already checked out, returns false.
   * 
   * Otherwise, returns true.
   * 
   * @param isbn
   *          -- ISBN of the library book to be checked out
   * @param holder
   *          -- new holder of the library book
   * @param month
   *          -- month of the new due date of the library book
   * @param day
   *          -- day of the new due date of the library book
   * @param year
   *          -- year of the new due date of the library book
   * 
   */
  //if someone want to check out a book (and it's not checkout, return true)
  public boolean checkout(long isbn, T holder, int month, int day, int year) {
    // Loop over each library-book in the library-book arraylist
    for (LibraryBookGeneric book : library) {
      // Check for ISBN
      if (book.getIsbn() == isbn) {
        // If the book is already checked out, return false
        if (book.getHolder() != null) {
          return false;
        } else {
          // Set the holder and due date fields and return true
          book.setHolder(holder);
          book.setDueDate(new GregorianCalendar(year, month, day));
          return true;
        }
      }
    }
    // If no book with the specified ISBN is found in the library, return false
    return false;
  }

  /**
   * Unsets the holder and due date of the library book.
   * 
   * If no book with the specified ISBN is in the library, returns false.
   * 
   * If the book with the specified ISBN is already checked in, returns false.
   * 
   * Otherwise, returns true.
   * 
   * @param isbn
   *          -- ISBN of the library book to be checked in
   */
  public boolean checkin(long isbn) {
    //Loop over each library-book in the library-book arraylist
    for (LibraryBookGeneric book : library) {
      //check for IBSN matching ISBN in question
      if (book.getHolder() != null && book.getIsbn() == isbn) {
        book.holder = null;
        book.dueDate = null;
        return true;
      }
    }
    // If no book with the specified ISBN is found in the library, return false
    return false;
  }

  /**
   * Unsets the holder and due date for all library books checked out be the
   * specified holder.
   * 
   * If no books with the specified holder are in the library, returns false;
   * 
   * Otherwise, returns true.
   * 
   * @param holder
   *          -- holder of the library books to be checked in
   */
  public boolean checkin(T holder) {
    //Loop over each library-book in the library-book arraylist
    for (LibraryBookGeneric book : library) {
      //if the book holder is not null (it's not already checked in)
      // and if the books holder is the holder in question
      if (book.getHolder() != null && book.getHolder().equals(holder)) {
        //check in book
        book.holder = null;
        book.dueDate = null;
        return true;
      }
    }
    return false;
  }
}
