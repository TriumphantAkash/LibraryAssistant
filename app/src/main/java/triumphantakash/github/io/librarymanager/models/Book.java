package triumphantakash.github.io.librarymanager.models;

import java.io.Serializable;

/**
 * Created by Akash on 8/20/2016.
 */
public class Book implements Serializable{
    String bookTitle;
    String bookAuthor;
    String bookPublisher;
    String bookCatagories;
    String bookURL;
    String lastCheckedOut;
    String lastCheckedOutBy;

    public void setBookPublisher(String bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public void setBookCatagories(String bookCatagories) {
        this.bookCatagories = bookCatagories;
    }

    public void setBookURL(String bookURL) {
        this.bookURL = bookURL;
    }

    public void setLastCheckedOut(String lastCheckedOut) {
        this.lastCheckedOut = lastCheckedOut;
    }

    public void setLastCheckedOutBy(String lastCheckedOutBy) {
        this.lastCheckedOutBy = lastCheckedOutBy;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public String getBookCatagories() {
        return bookCatagories;
    }

    public String getBookURL() {
        return bookURL;
    }

    public String getLastCheckedOut() {
        return lastCheckedOut;
    }

    public String getLastCheckedOutBy() {
        return lastCheckedOutBy;
    }

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
