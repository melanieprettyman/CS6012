package assignment02;

import java.util.GregorianCalendar;

public class LibraryBook extends Book{
    String holder;
    // Create a GregorianCalendar instance
    GregorianCalendar dueDate = new GregorianCalendar();

    // Set the due date values
    int year = 2022;
    int month = 11; // Note: months are zero-based (0 - 11)
    int day = 30;
    int hour = 23;
    int minute = 59;
    int second = 59;


    public LibraryBook(long isbn, String author, String title) {
        super(isbn, author, title);
    }

    //CHECK BOOK IN
    public void checkBookIn(){
        this.holder = null;
        this.dueDate = null;
    }


    //CHECK BOOK OUT
    public void checkOut(String holder, int year, int month, int day) {
        this.holder = holder;
        this.dueDate = new GregorianCalendar(year, month -1, day);
    }

    //HELPER FUNCTIONS
    public String getHolder(){
        return holder;
    }
    public GregorianCalendar getDueDate(){
        return dueDate;
    }
    public void setHolder(String holder) {
        this.holder = holder;
    }

    public void setDueDate(GregorianCalendar gregorianCalendar) {
        this.dueDate = gregorianCalendar;
    }
}
