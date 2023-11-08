package assignment02;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
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

  /**
   * Returns the list of library books, sorted by ISBN (smallest ISBN
   first).
   */
  public ArrayList<LibraryBookGeneric<T>> getInventoryList() {
    ArrayList<LibraryBookGeneric<T>> libraryCopy = new ArrayList<LibraryBookGeneric<T>>();
    libraryCopy.addAll(library);
    OrderByIsbn comparator = new OrderByIsbn();
    sort(libraryCopy, comparator);
    return libraryCopy;
  }


  public ArrayList<LibraryBookGeneric<T>> getOrderedByAuthor() {
    ArrayList<LibraryBookGeneric<T>> libraryCopy = new ArrayList<LibraryBookGeneric<T>>();
    libraryCopy.addAll(library);
    OrderByAuthor comparator = new OrderByAuthor();
    sort(libraryCopy, comparator);
    return libraryCopy;
  }



  public ArrayList<LibraryBookGeneric<T>> getOverdueList(int month, int day, int year) {
    GregorianCalendar dueDate = new GregorianCalendar(year, month, day);
    ArrayList<LibraryBookGeneric<T>> overDueList = new ArrayList<LibraryBookGeneric<T>>();
    //Loop over each library-book in the library-book arraylist
    for (LibraryBookGeneric book : library) {
      int res = book.getDueDate().compareTo(dueDate);
      if(res<0){
        overDueList.add(book);
      }
    }
    OrderByDueDate comparator = new OrderByDueDate();
    sort(overDueList, comparator);
    return overDueList;
  }

  /**
   * Performs a SELECTION SORT on the input ArrayList.
   * 1. Find the smallest item in the list.
   * 2. Swap the smallest item with the first item in the list.
   * 3. Now let the list be the remaining unsorted portion
   * (second item to Nth item) and repeat steps 1, 2, and 3.
   */
  private static <ListType> void sort(ArrayList<ListType> list, Comparator<ListType> c) {
    for (int i = 0; i < list.size() - 1; i++) {
      int j, minIndex;
      for (j = i + 1, minIndex = i; j < list.size(); j++)
        if (c.compare(list.get(j), list.get(minIndex)) < 0)
          minIndex = j;
      ListType temp = list.get(i);
      list.set(i, list.get(minIndex));
      list.set(minIndex, temp);
    }
  }

  /**
   * Comparator that defines an ordering among library books using the
   ISBN.
   */
  protected class OrderByIsbn implements Comparator<LibraryBookGeneric<T>> {
    /**
     * Returns a negative value if lhs is smaller than rhs. Returns a positive
     * value if lhs is larger than rhs. Returns 0 if lhs 	and rhs are equal.
     */
    public int compare(LibraryBookGeneric<T> lhs, LibraryBookGeneric<T> rhs) {
      return (int) (lhs.getIsbn() - rhs.getIsbn());
    }
  }


  protected class OrderByAuthor implements Comparator<LibraryBookGeneric<T>> {
    @Override
    public int compare(LibraryBookGeneric<T> o1, LibraryBookGeneric<T> o2) {
        int author = ( o1.getAuthor().compareTo(o2.getAuthor()));
        if(author == 0){
         int title = (o1.getTitle().compareTo(o2.getTitle()));
         return title;
        }
      return author;
      }
  }


  protected class OrderByDueDate implements Comparator<LibraryBookGeneric<T>> {
    @Override
    public int compare(LibraryBookGeneric<T> o1, LibraryBookGeneric<T> o2) {
      GregorianCalendar dueDate1 = o1.getDueDate();
      GregorianCalendar dueDate2 = o2.getDueDate();

      return (int) (dueDate1.compareTo(dueDate2));
    }
  }


}
