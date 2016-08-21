package triumphantakash.github.io.librarymanager.models;

import java.io.Serializable;

/**
 * Created by Akash on 8/20/2016.
 */
public class Book implements Serializable{
    String bookTitle;
    String bookAuthor;

    //
    public Book(String title, String author){
        this.bookTitle = title;
        this.bookAuthor = author;
    }

    //
    public Book(){

    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }
}
