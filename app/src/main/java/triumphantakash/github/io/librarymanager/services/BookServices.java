package triumphantakash.github.io.librarymanager.services;

import android.widget.Toast;

import retrofit.RestAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import triumphantakash.github.io.librarymanager.Activities.book_list.BookListView;
import triumphantakash.github.io.librarymanager.models.Book;

/**
 * Created by Akash on 8/28/2016.
 */
public class BookServices {
    String endPoint = "https://interview-api-staging.bytemark.co";
    RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(endPoint).build();
    LibraryService libraryService = restAdapter.create(LibraryService.class);

    public void postBook(Book receivedBook){
        boolean ret = false;
        libraryService.addBook(receivedBook)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Book>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(Book book) {
                        //the added book object is returned here
                    }
                });
    }

    public void modifyBook(Book receivedBook){
        String[] urlsParts = receivedBook.getBookURL().split("/");
        libraryService.updateBook(urlsParts[2], receivedBook)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Book>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Book book) {

                    }
                });

    }

    public void deleteBooks(){
        libraryService.deleteAllBooks()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Book>() {
                               @Override
                               public void onCompleted() {

                               }

                               @Override
                               public void onError(Throwable e) {

                               }

                               @Override
                               public void onNext(Book book) {

                               }
                           }

                );
    }

    public void deleteBook(String book_no){
        libraryService.deleteBook(book_no)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Book>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Book book) {

                    }
                });
    }

}

