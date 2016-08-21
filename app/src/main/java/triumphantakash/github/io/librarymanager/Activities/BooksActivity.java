package triumphantakash.github.io.librarymanager.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import triumphantakash.github.io.librarymanager.R;
import triumphantakash.github.io.librarymanager.adapters.BookListAdapter;
import triumphantakash.github.io.librarymanager.models.Book;
import triumphantakash.github.io.librarymanager.services.LibraryService;

public class BooksActivity extends AppCompatActivity {

    ListView bookList;
    BookListAdapter bookListAdapter;
    String endPoint = "https://interview-api-staging.bytemark.co";
    RestAdapter restAdapter;
    ArrayList<Book> bookListFromServer;
    LibraryService libraryService;
    Button addBookButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        bookList = (ListView)findViewById(R.id.bookList);

        addBookButton = (Button)findViewById(R.id.buttonAddBook);
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toAddBookActivity();
            }
        });
            bookListFromServer=new ArrayList<>();

            bookListAdapter=new BookListAdapter(getApplicationContext(),bookListFromServer);
            bookList.setAdapter(bookListAdapter);

            restAdapter=new RestAdapter.Builder().setEndpoint(endPoint).build();

            libraryService=restAdapter.create(LibraryService.class);

            bookList.setOnItemClickListener(new AdapterView.OnItemClickListener()

            {

                @Override
                public void onItemClick (AdapterView < ? > parent, View view,int position, long id){
                Book temp = (Book) parent.getAdapter().getItem(position);
                toBookDetailsActivity(temp);
            }
            }

            );

        }

        @Override
    protected void onResume(){
        super.onResume();
        bookListFromServer.clear();

        libraryService.getBooks(new Callback<Book[]>() {
            @Override
            public void success(Book[] books, Response response) {
                for (int i = 0; i < books.length; i++) {
                    bookListFromServer.add(books[i]);

                }
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        bookListAdapter.notifyDataSetChanged();
                    }
                });

            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("HAHA", error.toString());
            }
        });
    }

    public void toBookDetailsActivity(Book book){
        Intent intent = new Intent(this, BookDetailsActivity.class);
        intent.putExtra("bookObject", book);
        startActivity(intent);
    }

    public void toAddBookActivity(){
        Intent intent = new Intent(this, AddBookActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_books_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            deleteAllBooks();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void deleteAllBooks(){
        AlertDialog.Builder alert = new AlertDialog.Builder(BooksActivity.this);
        alert.setIcon(R.drawable.warning2_icon);
        alert.setTitle("Deleting All Books");
        alert.setMessage("Are you sure you want to delete all books!?");

        alert.setPositiveButton("DELETE ANYWAY", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                libraryService.deleteAllBooks(new Callback<Object>() {
                    @Override
                    public void success(Object o, Response response) {
                        Log.i("HAHA", response.toString());
                        bookListFromServer.clear();
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                bookListAdapter.notifyDataSetChanged();
                            }
                        });
                        Toast.makeText(BooksActivity.this, "All books deleted", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.i("HAHA", "error in deleting all books" + error);
                    }
                });
            }
        });

        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // what ever you want to do with No option.
                //Toast.makeText(BookDetailsActivity.this, "Checkout operation cancelled", Toast.LENGTH_SHORT).show();
            }
        });

        alert.show();
    }
}
