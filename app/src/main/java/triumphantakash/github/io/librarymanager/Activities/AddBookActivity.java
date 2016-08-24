package triumphantakash.github.io.librarymanager.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import triumphantakash.github.io.librarymanager.R;
import triumphantakash.github.io.librarymanager.models.Book;
import triumphantakash.github.io.librarymanager.services.LibraryService;

public class AddBookActivity extends AppCompatActivity {
    @InjectView(R.id.bookTitle) EditText bookTitle;
    @InjectView(R.id.bookAuthor) EditText bookAuthor;
    @InjectView(R.id.bookPublisher) EditText bookPublisher;
    @InjectView(R.id.bookCatagories) EditText bookCatagories;

    RestAdapter restAdapter;
    String endPoint = "https://interview-api-staging.bytemark.co";
    String flag = "add";
    Book receivedBook;
    Bundle passedData;


    @OnClick(R.id.addBookButton)
    void onSubmitClick(){
        receivedBook.setBookTitle(bookTitle.getText().toString());
        receivedBook.setBookAuthor(bookAuthor.getText().toString());
        receivedBook.setBookCatagories(bookCatagories.getText().toString());
        receivedBook.setBookPublisher(bookPublisher.getText().toString());

        if (isInputValid(receivedBook)) {
                    /*
                    ..
                    ..
                    ..
                    */
            //call web service POST method here and feed this book object to it
            LibraryService libraryService = restAdapter.create(LibraryService.class);
            if (flag.equals("add")) {
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
                                Toast.makeText(AddBookActivity.this, "Book added", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
            } else {  //flag == "modify"
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
                                //the added book object is returned here
                                Toast.makeText(AddBookActivity.this, "Book updated", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });


            }

            //go back to the previous Activity
        } else {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        passedData = getIntent().getExtras();

        ButterKnife.inject(this);

        restAdapter = new RestAdapter.Builder().setEndpoint(endPoint).build();

        if(passedData.getString("operation").equals("modify")){
            modifyBook();
        } else {
            receivedBook = new Book();
        }

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

    @Override
    public void onBackPressed() {
        validate();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        validate();//there is just one option
        return true;
    }

    void validate(){
        Book book = new Book();
        book.setBookTitle(bookTitle.getText().toString());
        book.setBookAuthor(bookAuthor.getText().toString());
        book.setBookCatagories(bookCatagories.getText().toString());
        book.setBookPublisher(bookPublisher.getText().toString());

        if((book.getBookTitle().trim().length()>0) || (book.getBookAuthor().trim().length() > 0) || (book.getBookCatagories().trim().length() > 0) || (book.getBookPublisher().trim().length()>0)){
            //unsaved data present
            AlertDialog.Builder alert = new AlertDialog.Builder(AddBookActivity.this);
            alert.setIcon(R.drawable.warning_icon);
            alert.setTitle("Unsaved Data");
            alert.setMessage("Unsaved data present\nStill want to leave the screen?");

            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    finish();
                }
            });

            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            alert.show();
        }
        else{
            finish();
        }
        //super.onBackPressed();
    }
}
