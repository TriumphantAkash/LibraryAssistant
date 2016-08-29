package triumphantakash.github.io.librarymanager.Activities.book_details;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import triumphantakash.github.io.librarymanager.models.Book;
import triumphantakash.github.io.librarymanager.services.BookServices;

/**
 * Created by Akash on 8/28/2016.
 */
public class BookDetailsPresenter extends MvpBasePresenter<BookDetailsView>{
    public void checkoutBook(Book book){
        BookServices abs = new BookServices();
        abs.modifyBook(book);
        if(isViewAttached()){
            getView().feedData(book);
            getView().toastMsg("Book checked out by: " + book.getLastCheckedOutBy() + " @ " + book.getLastCheckedOut());
        }
    }

    public void deleteBook(String book_no){
        BookServices abs = new BookServices();
        abs.deleteBook(book_no);
        if(isViewAttached()){
            getView().toastMsg("Book deleted");
        }
    }

}
