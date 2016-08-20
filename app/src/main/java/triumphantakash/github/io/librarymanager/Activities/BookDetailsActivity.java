package triumphantakash.github.io.librarymanager.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import triumphantakash.github.io.librarymanager.R;
import triumphantakash.github.io.librarymanager.models.Book;

public class BookDetailsActivity extends AppCompatActivity {

    TextView authorName, publisher, title, tags, checkoutDetails;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Book book = (Book)getIntent().getSerializableExtra("bookObject");

        authorName = (TextView)findViewById(R.id.authorName);
        publisher = (TextView)findViewById(R.id.publisher);
        title = (TextView)findViewById(R.id.bookName);
        tags = (TextView)findViewById(R.id.tags);
        checkoutDetails = (TextView)findViewById(R.id.checkoutDetails);


        //feed data to text fields
        authorName.setText(book.getBookAuthor());
        title.setText(book.getBookTitle());

    }
}
