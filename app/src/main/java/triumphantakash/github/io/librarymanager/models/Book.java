package triumphantakash.github.io.librarymanager.models;

import java.io.Serializable;

/**
 * Created by Akash on 8/20/2016.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Book implements Serializable{

    @SerializedName("title")
    @Expose
    String bookTitle;

    @SerializedName("author")
    @Expose
    String bookAuthor;

    @SerializedName("publisher")
    @Expose
    String bookPublisher;

    @SerializedName("categories")
    @Expose
    String bookCatagories;

    @SerializedName("url")
    @Expose
    String bookURL;

    @SerializedName("lastCheckedOut")
    @Expose
    String lastCheckedOut;

    @SerializedName("lastCheckedOutBy")
    @Expose
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
