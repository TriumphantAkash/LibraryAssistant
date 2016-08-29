package triumphantakash.github.io.librarymanager.Activities.book_details;

import com.hannesdorfmann.mosby.mvp.MvpView;

import triumphantakash.github.io.librarymanager.models.Book;

/**
 * Created by Akash on 8/28/2016.
 */
public interface BookDetailsView extends MvpView {
    void toastMsg(String str);
    void feedData(Book book);
}
