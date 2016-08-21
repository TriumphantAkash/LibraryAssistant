package triumphantakash.github.io.librarymanager.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

                if(isInputValid(book)){
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
                            Log.i("HAHA", "error in adding book" + error);
                        }
                    });

                    //go back to the previous Activity
                    finish();
                }else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(AddBookActivity.this);
                    alert.setTitle("Incomplete Data");
                    alert.setIcon(R.drawable.warning_icon);
                    alert.setMessage("Can not add book with incomplete data. One or more fields are empty.");
                    alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            //Toast.makeText(AddBookActivity.this, "Book Deleted", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alert.show();
                }
            }
        });

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public boolean isInputValid(Book book){
        if((book.getBookTitle().trim().length()>0) && (book.getBookAuthor().trim().length() > 0) && (book.getBookCatagories().trim().length() > 0)&& (book.getBookPublisher().trim().length()>0)){
            return true;
        }else{
            return false;
        }
    }
}
