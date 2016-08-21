package triumphantakash.github.io.librarymanager.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import triumphantakash.github.io.librarymanager.R;
import triumphantakash.github.io.librarymanager.models.Book;
import triumphantakash.github.io.librarymanager.services.LibraryService;

public class AddBookActivity extends AppCompatActivity {

    EditText bookTitle, bookAuthor, bookPublisher, bookCatagories;
    Button submitButton;
    RestAdapter restAdapter;
    String endPoint = "https://interview-api-staging.bytemark.co";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);


        restAdapter = new RestAdapter.Builder().setEndpoint(endPoint).build();
        bookTitle = (EditText)findViewById(R.id.bookTitle);
        bookAuthor = (EditText)findViewById(R.id.bookAuthor);
        bookPublisher = (EditText)findViewById(R.id.bookPublisher);
        bookCatagories = (EditText)findViewById(R.id.bookCatagories);
        submitButton = (Button)findViewById(R.id.addBookButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book = new Book();
                book.setBookTitle(bookTitle.getText().toString());
                book.setBookAuthor(bookAuthor.getText().toString());
                book.setBookCatagories(bookCatagories.getText().toString());
                book.setBookPublisher(bookPublisher.getText().toString());
                /*
                ..
                ..
                ..
                */
                //call web service POST method here and feed this book object to it
                LibraryService libraryService = restAdapter.create(LibraryService.class);
                libraryService.addBook(book, new Callback<Object>() {
                    @Override
                    public void success(Object o, Response response) {
                        Log.i("HAHA", response.toString());
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.i("HAHA", "error in adding book"+error);
                    }
                });

                //go back to the previous Activity
                finish();
            }
        });

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
