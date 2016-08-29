package triumphantakash.github.io.librarymanager.dagger;

import dagger.Component;
import triumphantakash.github.io.librarymanager.Activities.add_book.AddBookPresenter;
import triumphantakash.github.io.librarymanager.Activities.book_details.BookDetailsActivity;
import triumphantakash.github.io.librarymanager.Activities.book_details.BookDetailsPresenter;
import triumphantakash.github.io.librarymanager.Activities.book_list.BookListActivity;
import triumphantakash.github.io.librarymanager.Activities.book_list.BookListPresenter;
import triumphantakash.github.io.librarymanager.services.BookServicesModule;

/**
 * Created by Akash on 8/29/2016.
 */
//list all the places where I want to inject the object
//later when I want to inject that object, I will call the corresponding method for that particular activity / class
@Component(
        modules = {BookServicesModule.class}
)
public interface MyAppComponent {
    void inject(AddBookPresenter addBookPresenter);
    void inject(BookDetailsActivity bookDetailsActivity);
    void inject(BookDetailsPresenter BookDetailsPresenter);
    void inject(BookListActivity bookListActivity);
    void inject(BookListPresenter bookListPresenter);
}
