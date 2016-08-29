package triumphantakash.github.io.librarymanager.Activities.book_details;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import triumphantakash.github.io.librarymanager.dagger.DaggerMyAppComponent;
import triumphantakash.github.io.librarymanager.dagger.MyAppComponent;
import triumphantakash.github.io.librarymanager.models.Book;
import triumphantakash.github.io.librarymanager.services.BookServicesModule;

/**
 * Created by Akash on 8/28/2016.
 */
public class BookDetailsPresenter extends MvpBasePresenter<BookDetailsView>{

    MyAppComponent component = DaggerMyAppComponent.builder()
            .bookServicesModule(new BookServicesModule()).build();

    public void checkoutBook(Book book){
        BookServicesModule abs = new BookServicesModule();
        abs.modifyBook(book);
        if(isViewAttached()){
            getView().feedData(book);
            getView().toastMsg("Book checked out by: " + book.getLastCheckedOutBy() + " @ " + book.getLastCheckedOut());
        }
    }

    public void deleteBook(String book_no){
        BookServicesModule abs = new BookServicesModule();
        abs.deleteBook(book_no);
        if(isViewAttached()){
            getView().toastMsg("Book deleted");
        }
    }

}
