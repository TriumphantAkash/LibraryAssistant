package triumphantakash.github.io.librarymanager.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        bookList = (ListView)findViewById(R.id.bookList);


        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book temp = (Book) parent.getAdapter().getItem(position);
                toBookDetailsActivity(temp);
            }
        });

        restAdapter = new RestAdapter.Builder()
                .setEndpoint(endPoint).build();
    }

    @Override
    protected void onResume(){
        super.onResume();

        bookListFromServer = new ArrayList<>();
       // Book book1 = new Book("Ramayana", "Tulsidas");
       // Book book2 = new Book("Ramcharitmanas", "Kaalidas");

        //bookListFromServer.add(book1);
        //bookListFromServer.add(book2);

        bookListAdapter = new BookListAdapter(getApplicationContext(), bookListFromServer);
        bookList.setAdapter(bookListAdapter);

        LibraryService libraryService = restAdapter.create(LibraryService.class);
        libraryService.getBooks(new Callback<Book[]>() {
            @Override
            public void success(Book[] books, Response response) {
                for (int i = 0; i < books.length; i++) {
                    bookListFromServer.add(books[i]);

                }
                bookListAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("HAHA", "failure");
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
        if (id == R.id.action_addBook) {
            toAddBookActivity();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
