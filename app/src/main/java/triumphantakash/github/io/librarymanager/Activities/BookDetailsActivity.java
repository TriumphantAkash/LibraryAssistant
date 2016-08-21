package triumphantakash.github.io.librarymanager.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import triumphantakash.github.io.librarymanager.R;
import triumphantakash.github.io.librarymanager.models.Book;

public class BookDetailsActivity extends AppCompatActivity {

    private ShareActionProvider mShareActionProvider;
    TextView authorName, publisher, title, tags, checkoutDetails;
    Button checkoutButton;
    Book receivedBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        receivedBook = (Book)getIntent().getSerializableExtra("bookObject");

        authorName = (TextView)findViewById(R.id.authorName);
        publisher = (TextView)findViewById(R.id.publisher);
        title = (TextView)findViewById(R.id.bookName);
        tags = (TextView)findViewById(R.id.tags);
        checkoutDetails = (TextView)findViewById(R.id.checkoutDetails);
        checkoutButton = (Button)findViewById(R.id.checkoutButton);


        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(BookDetailsActivity.this);
                final EditText edittext = new EditText(getApplicationContext());
                edittext.setHint("Enter Your name here...");
                edittext.setHintTextColor(Color.GRAY);
                //edittext.setPadding(10,0,10,0);
                alert.setTitle("Book Checkout");
                edittext.setTextColor(Color.BLACK);

                alert.setView(edittext);

                alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String YouEditTextValue = edittext.getText().toString();
                        Toast.makeText(BookDetailsActivity.this, YouEditTextValue, Toast.LENGTH_SHORT).show();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        Toast.makeText(BookDetailsActivity.this, "Book was not checked out", Toast.LENGTH_SHORT).show();
                    }
                });

                alert.show();
            }
        });

        //feed data to text fields
        authorName.setText(receivedBook.getBookAuthor());
        title.setText(receivedBook.getBookTitle());

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        intent.putExtra(Intent.EXTRA_SUBJECT, "BOOK");
        intent.putExtra(Intent.EXTRA_TEXT, receivedBook.getBookTitle()+" by "+receivedBook.getBookAuthor());
        return intent;
    }
}
