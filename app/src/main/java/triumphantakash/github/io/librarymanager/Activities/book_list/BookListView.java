package triumphantakash.github.io.librarymanager.Activities.book_list;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Akash on 8/28/2016.
 */
public interface BookListView extends MvpView{
    void showAllBooksDeleted(String message);

    void fetchBooks();
}
