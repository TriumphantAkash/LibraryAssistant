package triumphantakash.github.io.librarymanager.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import rx.Scheduler;
import triumphantakash.github.io.librarymanager.R;
import triumphantakash.github.io.librarymanager.models.Book;
import triumphantakash.github.io.librarymanager.services.LibraryService;

public class BookDetailsActivity extends AppCompatActivity {

    private ShareActionProvider mShareActionProvider;
    @InjectView(R.id.authorName)TextView authorName;
    @InjectView(R.id.publisher)TextView publisher;
    @InjectView(R.id.bookName)TextView title;
    @InjectView(R.id.tags)TextView tags;

    @InjectView(R.id.checkoutDetails)TextView checkoutDetails;
    @InjectView(R.id.checkoutButton) Button checkoutButton;
    @InjectView(R.id.deleteButton) Button deleteBookButton;
    @InjectView(R.id.modifyButton) Button modifyButton;

    Book receivedBook;
    RestAdapter restAdapter;
    LibraryService libraryService;
    String endPoint = "https://interview-api-staging.bytemark.co";
    String enteredName;

    @OnClick(R.id.checkoutButton)
    void onCheckoutClick(){
        AlertDialog.Builder alert = new AlertDialog.Builder(BookDetailsActivity.this);
        final EditText edittext = new EditText(getApplicationContext());
        alert.setIcon(R.mipmap.ic_launcher);
        edittext.setHint("Name..");
        edittext.setHintTextColor(Color.GRAY);
        alert.setTitle("Book Checkout");
        edittext.setTextColor(Color.BLACK);

        alert.setView(edittext);

        alert.setPositiveButton("Checkout", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                enteredName = edittext.getText().toString();
                if(enteredName.trim().length() == 0){
                    Toast.makeText(BookDetailsActivity.this, "Invalid name input\nCould not checkout", Toast.LENGTH_SHORT).show();
                }else{
                    //call correesponding web service for checkout
                    String currentDateandTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                    String timeZone = Calendar.getInstance().getTimeZone().getDisplayName(false, TimeZone.SHORT);
                    receivedBook.setLastCheckedOut(currentDateandTime+" "+timeZone);
                    receivedBook.setLastCheckedOutBy(enteredName);
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
                                    feedData(receivedBook);
                                    //finish();
                                }
                            });
                    Toast.makeText(BookDetailsActivity.this, "Book checked out by: " + enteredName + " @ " + currentDateandTime, Toast.LENGTH_SHORT).show();
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                Toast.makeText(BookDetailsActivity.this, "Checkout operation cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        alert.show();
    }

    @OnClick(R.id.deleteButton)
    void onDeleteButtonClick(){
        AlertDialog.Builder alert = new AlertDialog.Builder(BookDetailsActivity.this);
        alert.setTitle("Book Delete Confirmation");
        alert.setIcon(R.drawable.warning1_icon);
        alert.setMessage("Are you sure want to delete book '" + receivedBook.getBookTitle() + "'?");
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String[] urlsParts = receivedBook.getBookURL().split("/");
                libraryService.deleteBook(urlsParts[2])
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
                                Toast.makeText(BookDetailsActivity.this, "Book deleted", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                finish();   //back to home Activity
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                Toast.makeText(BookDetailsActivity.this, "Delete Operation Cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        alert.show();
    }

    @OnClick(R.id.modifyButton)
    void onModifyClick(){
        toAddBookActivity();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        receivedBook = (Book)getIntent().getSerializableExtra("bookObject");
        ButterKnife.inject(this);

        Log.i("HAHA", Calendar.getInstance().getTimeZone().getDisplayName(false, TimeZone.SHORT));

        //feed data to text fields
        feedData(receivedBook);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        restAdapter =  new RestAdapter.Builder().setEndpoint(endPoint).build();
        libraryService = restAdapter.create(LibraryService.class);

       }

    @Override
    protected void onResume() {
        super.onResume();
        refetchData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /** Inflating the current activity's menu with res/menu/items.xml */
        getMenuInflater().inflate(R.menu.menu_book_details_activity, menu);

        /** Getting the actionprovider associated with the menu item whose id is share */
        MenuItem item = menu.findItem(R.id.menu_item_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);

        /** Setting a share intent */
        mShareActionProvider.setShareIntent(getDefaultShareIntent());

        return super.onCreateOptionsMenu(menu);

    }

    /** Returns a share intent */
    private Intent getDefaultShareIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "ByteMark Library Book: ");
        intent.putExtra(Intent.EXTRA_TEXT, receivedBook.getBookTitle() + " by " + receivedBook.getBookAuthor());
        return intent;
    }

    public void feedData(Book book){
        String str = book.getBookAuthor();
        if(str == null) {
            authorName.setText("info not available");
        }else{
            authorName.setText(str);
        }
        str = book.getBookTitle();
        if(str == null) {
            title.setText("info not available");
        }else{
            title.setText(str);
        }
        str = book.getBookPublisher();
        if(str == null) {
            publisher.setText("info not available");
        }else{
            publisher.setText(str);
        }

        str = book.getBookCatagories();
        if(str == null) {
            tags.setText("info not available");
        }else{
            tags.setText(str);
        }

        str = book.getLastCheckedOutBy();
        if(str == null) {
            checkoutDetails.setText("Not yet checked out");
        }else {
            checkoutDetails.setText(str + " @ "+receivedBook.getLastCheckedOut());
        }
    }

    public void toAddBookActivity(){
        Intent intent = new Intent(this, AddBookActivity.class).putExtra("operation", "modify");
        intent.putExtra("bookObject", receivedBook);
        startActivity(intent);
    }

    public void refetchData(){  //will be called from onResume
        String[] urlsParts = receivedBook.getBookURL().split("/");
        libraryService.getBook(urlsParts[2])
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
                        feedData(book);
                    }
                });
    }
}
