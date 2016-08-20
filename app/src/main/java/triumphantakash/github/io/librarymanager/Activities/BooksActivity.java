package triumphantakash.github.io.librarymanager.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

import triumphantakash.github.io.librarymanager.R;
import triumphantakash.github.io.librarymanager.adapters.BookListAdapter;
import triumphantakash.github.io.librarymanager.models.Book;

public class BooksActivity extends AppCompatActivity {

    ListView bookList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);
        bookList = (ListView)findViewById(R.id.bookList);
    }

    @Override
    protected void onResume(){
        super.onResume();

        ArrayList<Book> bookListFromServer = new ArrayList<>();
        Book book1 = new Book("Ramayana", "Tulsidas");
        Book book2 = new Book("Ramcharitmanas", "Kaalidas");

        bookListFromServer.add(book1);
        bookListFromServer.add(book2);

        BookListAdapter bookListAdapter = new BookListAdapter(getApplicationContext(), bookListFromServer);
        bookList.setAdapter(bookListAdapter);
    }
}
