package triumphantakash.github.io.librarymanager.Activities.add_book;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.hannesdorfmann.mosby.mvp.MvpActivity;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import triumphantakash.github.io.librarymanager.R;
import triumphantakash.github.io.librarymanager.models.Book;

public class AddBookActivity extends MvpActivity<AddBookView, AddBookPresenter> implements AddBookView{
    @InjectView(R.id.bookTitle) EditText bookTitle;
    @InjectView(R.id.bookAuthor) EditText bookAuthor;
    @InjectView(R.id.bookPublisher) EditText bookPublisher;
    @InjectView(R.id.bookCatagories) EditText bookCatagories;
    String flag = "add";
    Book receivedBook;
    Bundle passedData;


    @OnClick(R.id.addBookButton)
    void onSubmitClick(){
        receivedBook.setBookTitle(bookTitle.getText().toString());
        receivedBook.setBookAuthor(bookAuthor.getText().toString());
        receivedBook.setBookCatagories(bookCatagories.getText().toString());
        receivedBook.setBookPublisher(bookPublisher.getText().toString());
        if(flag.equals("add"))
            presenter.addBook(receivedBook);
        else
            presenter.modifyBook(receivedBook);
    }

    @NonNull
    @Override
    public AddBookPresenter createPresenter() {
        return new AddBookPresenter();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        passedData = getIntent().getExtras();
        ButterKnife.inject(this);

        if(passedData.getString("operation").equals("modify")){
            feedData();
        } else {
            receivedBook = new Book();
        }

    }



    public void feedData(){
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
        Book book = createBookObject();
        presenter.unsavedChangeValidation(book);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Book book = createBookObject();
        presenter.unsavedChangeValidation(book);//there is just one option
        return true;
    }


    @Override
    public void showBookAddSuccess(String successText) {
        Toast.makeText(AddBookActivity.this, successText, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showBookAddError(String errorTitle, String errorMessage) {
        AlertDialog.Builder alert = new AlertDialog.Builder(AddBookActivity.this);
        alert.setTitle(errorTitle);
        alert.setIcon(R.drawable.warning_icon);
        alert.setMessage(errorMessage);
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //Toast.makeText(AddBookActivity.this, "Book Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        alert.show();
    }

    @Override
    public void showUnsavedDataWarning(String warningTitle, String warningMsg) {
        if(warningTitle == null)
            finish();
        else{
            //unsaved data present
            final AlertDialog.Builder alert = new AlertDialog.Builder(AddBookActivity.this);
            alert.setIcon(R.drawable.warning_icon);
            alert.setTitle(warningTitle);
            alert.setMessage(warningMsg);

            alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                    finish();
                }
            });

            alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            alert.show();
        }
    }

    Book createBookObject(){
        Book book = new Book();
        book.setBookTitle(bookTitle.getText().toString());
        book.setBookAuthor(bookAuthor.getText().toString());
        book.setBookCatagories(bookCatagories.getText().toString());
        book.setBookPublisher(bookPublisher.getText().toString());
        return book;
    }
}
