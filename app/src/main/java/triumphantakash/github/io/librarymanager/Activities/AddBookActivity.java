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
    String flag = "add";
    Book receivedBook;
    Bundle passedData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        passedData = getIntent().getExtras();


        restAdapter = new RestAdapter.Builder().setEndpoint(endPoint).build();
        bookTitle = (EditText)findViewById(R.id.bookTitle);
        bookAuthor = (EditText)findViewById(R.id.bookAuthor);
        bookPublisher = (EditText)findViewById(R.id.bookPublisher);
        bookCatagories = (EditText)findViewById(R.id.bookCatagories);

        if(passedData.getString("operation").equals("modify")){
            modifyBook();
        }
        submitButton = (Button)findViewById(R.id.addBookButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                receivedBook.setBookTitle(bookTitle.getText().toString());
                receivedBook.setBookAuthor(bookAuthor.getText().toString());
                receivedBook.setBookCatagories(bookCatagories.getText().toString());
                receivedBook.setBookPublisher(bookPublisher.getText().toString());

                if(isInputValid(receivedBook)){
                    /*
                    ..
                    ..
                    ..
                    */
                    //call web service POST method here and feed this book object to it
                    LibraryService libraryService = restAdapter.create(LibraryService.class);
                    if(flag.equals("add")) {
                        libraryService.addBook(receivedBook, new Callback<Object>() {
                            @Override
                            public void success(Object o, Response response) {
                                Log.i("HAHA", response.toString());
                                finish();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.i("HAHA", "error in adding book" + error);
                            }
                        });
                    }else{  //flag == "modify"
                        String[] urlsParts = receivedBook.getBookURL().split("/");
                        libraryService.updateBook(urlsParts[2], receivedBook, new Callback<Object>() {
                            @Override
                            public void success(Object o, Response response) {
                                Log.i("HAHA", "success" + response.toString());
                                Toast.makeText(AddBookActivity.this, "Book modified", Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.i("HAHA", "error in checkout book" + error);
                            }
                        });

                    }

                    //go back to the previous Activity
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

    public void modifyBook(){
        //Modify Operation
        flag = "modify";
        getSupportActionBar().setTitle("Modify Book");
        receivedBook = (Book)getIntent().getSerializableExtra("bookObject");
        bookTitle.setText(receivedBook.getBookTitle());
        bookAuthor.setText(receivedBook.getBookAuthor());
        bookCatagories.setText(receivedBook.getBookCatagories());
        bookPublisher.setText(receivedBook.getBookPublisher());
    }
}
