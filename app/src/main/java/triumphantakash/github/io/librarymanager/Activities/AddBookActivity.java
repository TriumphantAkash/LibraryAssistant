package triumphantakash.github.io.librarymanager.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import triumphantakash.github.io.librarymanager.R;
import triumphantakash.github.io.librarymanager.models.Book;

public class AddBookActivity extends AppCompatActivity {

    EditText bookTitle, bookAuthor, bookPublisher, bookCatagories;
    Button submitButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

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
                book.setBookAuthor(bookAuthor.getTransitionName().toString());
                /*
                ..
                ..
                ..
                */
                //call web service POST method here and feed this book object to it



            }
        });

    }
}
