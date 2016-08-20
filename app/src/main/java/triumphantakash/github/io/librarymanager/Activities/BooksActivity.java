package triumphantakash.github.io.librarymanager.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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


        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Book temp = (Book) parent.getAdapter().getItem(position);
                toBookDetailsActivity(temp);
            }
        });
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

    public void toBookDetailsActivity(Book book){
        Intent intent = new Intent(this, BookDetailsActivity.class);
        intent.putExtra("bookObject", book);
        startActivity(intent);
    }
}
