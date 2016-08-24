package triumphantakash.github.io.librarymanager.services;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import triumphantakash.github.io.librarymanager.models.Book;
import rx.Observable;

/**
 * Created by Akash on 8/20/2016.
 */
public interface LibraryService {
    @GET("/books")      //here is the other url part.best way is to start using /
    public Observable<Book[]> getBooks();     //string user is for passing values from edittext for eg: user=basil2style,google
    //response is the response from the server which is now in the POJO

    @GET("/books/{book_no}")
    public Observable<Book> getBook(@Path("book_no") String book_no);

    @POST("/books")
    public Observable<Book> addBook(@Body Object object);

    @DELETE("/books/{book_no}")
    public void deleteBook(@Path("book_no") String book_no, Callback<?>callback);

    @DELETE("/clean")
    public void deleteAllBooks(Callback<?>callback);

    @PUT("/books/{book_no}")
    public Observable<Book> updateBook(@Path("book_no") String book_no, @Body Object object);
}
