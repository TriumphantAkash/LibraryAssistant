package triumphantakash.github.io.librarymanager.services;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import triumphantakash.github.io.librarymanager.models.Book;

/**
 * Created by Akash on 8/20/2016.
 */
public interface LibraryService {
    @GET("/books")      //here is the other url part.best way is to start using /
    public void getBooks(Callback<Book[]> response);     //string user is for passing values from edittext for eg: user=basil2style,google
    //response is the response from the server which is now in the POJO

}
