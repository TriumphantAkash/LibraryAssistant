package triumphantakash.github.io.librarymanager.Activities.book_list;

import android.os.Handler;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import triumphantakash.github.io.librarymanager.dagger.DaggerMyAppComponent;
import triumphantakash.github.io.librarymanager.dagger.MyAppComponent;
import triumphantakash.github.io.librarymanager.services.BookServicesModule;

/**
 * Created by Akash on 8/28/2016.
 */
public class BookListPresenter extends MvpBasePresenter<BookListView> {
    MyAppComponent component = DaggerMyAppComponent.builder()
            .bookServicesModule(new BookServicesModule()).build();
    public void deleteAllBooks(){
        BookServicesModule abs = new BookServicesModule();
        abs.deleteBooks();
        //after deleting the books refresh the listView
        //handle that stuff here (maybe write functions for that in View and call it from here)
        if (isViewAttached()) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    getView().fetchBooks();
                }
            }, 100);
            String message = "All books deleted";
            getView().showAllBooksDeleted(message);
        }
    }
}
