package assignment02;

import java.util.GregorianCalendar;

public class LibraryBookGeneric<T> extends Book{
    T  holder;
    // Create a GregorianCalendar instance
    GregorianCalendar dueDate = new GregorianCalendar();



    public LibraryBookGeneric(long isbn, String author, String title) {
        super(isbn, author, title);
    }

    //CHECK BOOK IN
    public void checkBookIn(){
        this.holder = null;
        this.dueDate = null;
    }


    //CHECK BOOK OUT
    public void checkOut(T holder, int year, int month, int day) {
        this.holder = holder;
        this.dueDate = new GregorianCalendar(year, month -1, day);
    }

    //HELPER FUNCTIONS
    public <T> T getHolder(){
        return (T) holder;
    }
    public GregorianCalendar getDueDate(){
        return dueDate;
    }
    public void setHolder(T holder) {
        this.holder = holder;
    }

    public void setDueDate(GregorianCalendar gregorianCalendar) {
        this.dueDate = gregorianCalendar;
    }
}
