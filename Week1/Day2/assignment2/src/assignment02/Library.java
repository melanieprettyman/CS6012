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
public class Library<T> {

  private ArrayList<LibraryBook<T>> library;

  public Library() {

    library = new ArrayList<LibraryBook<T>>();
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

    library.add(new LibraryBook(isbn, author, title));
  }

  /**
   * Add the list of library books to the library, assume no duplicates.
   * 
   * @param list
   *          -- list of library books to be added
   */
  public void addAll(ArrayList<LibraryBook<T>> list) {
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
    ArrayList<LibraryBook<T>> toBeAdded = new ArrayList<LibraryBook<T>>();

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
          toBeAdded.add(new LibraryBook(isbn, author, title));
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

  //Looks up a book in the library by ISBN.
  public T lookup(long isbn) {
    //Loop over each library-book in the library-book arraylist
    for(LibraryBook<T> book: library){
      //if the library-books ISBN matches the ISBN in question
      if(book.getIsbn() == isbn){
        //return the holder of the library book
        return  book.getHolder();
      }
    }
    //otherwise return null
    return null;
  }

//Looks up books in the library by holder.
  public ArrayList<LibraryBook<T>> lookup(T holder) {
    //Create an arraylist of Library-books, this will contain a list of the holders-books
    ArrayList<LibraryBook<T>> holdersBooks = new ArrayList<>();

    //Loop over each library-book in the library-book arraylist
    for(LibraryBook<T> book: library){
      //if the library-books holder matches the holder in question
      if (book.getHolder() != null && book.getHolder().equals(holder) ){
        //add the book to holdersBooks
        holdersBooks.add(book);
      }
    }
    //return arraylist of holders books
    return holdersBooks;

  }

//Checks out a book from the library by ISBN with the new holder and due date
  public boolean checkout(long isbn, T holder, int month, int day, int year) {
    // Loop over each library-book in the library-book arraylist
    for (LibraryBook<T> book : library) {
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

  //Checks in a book from the library by ISBN
  public boolean checkin(long isbn) {
    //Loop over each library-book in the library-book arraylist
    for (LibraryBook<T> book : library) {
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

  //Checks in a book from the library by holder
  public boolean checkin(T holder) {
    //Loop over each library-book in the library-book arraylist
    for (LibraryBook<T> book : library) {
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
  public ArrayList<LibraryBook<T>> getInventoryList() {
    ArrayList<LibraryBook<T>> libraryCopy = new ArrayList<LibraryBook<T>>();
    libraryCopy.addAll(library);
    OrderByIsbn comparator = new OrderByIsbn();
    sort(libraryCopy, comparator);
    return libraryCopy;
  }

//Gets the list of library books sorted by author
  public ArrayList<LibraryBook<T>> getOrderedByAuthor() {
    // Create a copy of the library to avoid modifying the original
    ArrayList<LibraryBook<T>> libraryCopy = new ArrayList<LibraryBook<T>>();
    libraryCopy.addAll(library);

    // Create a comparator to sort by author and then by title
    OrderByAuthor comparator = new OrderByAuthor();

    // Sort the copied library and return the sorted list
    sort(libraryCopy, comparator);
    return libraryCopy;
  }


 //Gets the list of overdue books based on the specified date.
  public ArrayList<LibraryBook<T>> getOverdueList(int month, int day, int year) {

    // Create a date to check against
    GregorianCalendar dueDate = new GregorianCalendar(year, month, day);

    // Initialize a list to hold overdue books
    ArrayList<LibraryBook<T>> overDueList = new ArrayList<LibraryBook<T>>();

    // Loop through each book in the library to identify overdue books
    for (LibraryBook<T> book : library) {
      if(book.getDueDate()!= null) {
        int res = book.getDueDate().compareTo(dueDate);
        if (res < 0) {
          overDueList.add(book);
        }
      }
    }
    // Create a comparator for sorting by due date
    OrderByDueDate comparator = new OrderByDueDate();
    // Sort the list of overdue books by the due date and return it
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
  protected class OrderByIsbn implements Comparator<LibraryBook<T>> {
    /**
     * Returns a negative value if lhs is smaller than rhs. Returns a positive
     * value if lhs is larger than rhs. Returns 0 if lhs 	and rhs are equal.
     */
    public int compare(LibraryBook<T> lhs, LibraryBook<T> rhs) {
      return (int) (lhs.getIsbn() - rhs.getIsbn());
    }
  }



  protected class OrderByAuthor implements Comparator<LibraryBook<T>> {
    @Override
    public int compare(LibraryBook<T> o1, LibraryBook<T> o2) {
      String author1 = o1.getAuthor();
      String author2 = o2.getAuthor();

      if (author1 == null && author2 == null) {
        return 0; // Both authors are null, consider them equal
      } else if (author1 == null) {
        return -1; // o1's author is null, place it before o2
      } else if (author2 == null) {
        return 1; // o2's author is null, place it before o1
      } else {
        // Both authors are not null, compare them
        int authorComparison = author1.compareTo(author2);
        if (authorComparison == 0) {
          // Authors are the same, compare by title
          return o1.getTitle().compareTo(o2.getTitle());
        } else {
          return authorComparison;
        }
      }
    }
  }

  //Comparator that defines an ordering among library books using the due date.
  protected class OrderByDueDate implements Comparator<LibraryBook<T>> {
    @Override
    public int compare(LibraryBook<T> o1, LibraryBook<T> o2) {
      //Cast the objects due-dates to GregorianCalendar
      GregorianCalendar dueDate1 = o1.getDueDate();
      GregorianCalendar dueDate2 = o2.getDueDate();
      // Comparing by due date
      return (int) (dueDate1.compareTo(dueDate2));
    }
  }


}
