package triumphantakash.github.io.librarymanager.Activities.book_list;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.RestAdapter;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import triumphantakash.github.io.librarymanager.Activities.add_book.AddBookActivity;
import triumphantakash.github.io.librarymanager.Activities.book_details.BookDetailsActivity;
import triumphantakash.github.io.librarymanager.R;
import triumphantakash.github.io.librarymanager.adapters.BookListAdapter;
import triumphantakash.github.io.librarymanager.dagger.DaggerMyAppComponent;
import triumphantakash.github.io.librarymanager.dagger.MyAppComponent;
import triumphantakash.github.io.librarymanager.models.Book;
import triumphantakash.github.io.librarymanager.services.BookServicesModule;
import triumphantakash.github.io.librarymanager.services.LibraryService;

public class BookListActivity extends MvpActivity<BookListView, BookListPresenter> implements BookListView {

    @InjectView(R.id.bookList) ListView bookList;
    @InjectView (R.id.buttonAddBook) Button addBookButton ;
    BookListAdapter bookListAdapter;
    String endPoint = "https://interview-api-staging.bytemark.co";
    RestAdapter restAdapter;
    ArrayList<Book> bookListFromServer, newList;
    LibraryService libraryService;

    MyAppComponent component = DaggerMyAppComponent.builder()
            .bookServicesModule(new BookServicesModule()).build();

    @OnClick(R.id.buttonAddBook)
    void onAddBookClick()
    {
        toAddBookActivity();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books);

        ButterKnife.inject(this);
            bookListFromServer=new ArrayList<>();

            bookListAdapter=new BookListAdapter(getApplicationContext(),bookListFromServer);
            bookList.setAdapter(bookListAdapter);

            restAdapter=new RestAdapter.Builder().setEndpoint(endPoint).build();

            libraryService=restAdapter.create(LibraryService.class);

            bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                                                @Override
                                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                    Book temp = (Book) parent.getAdapter().getItem(position);
                                                    toBookDetailsActivity(temp);
                                                }
                                            }

            );

        }

        @Override
    protected void onResume(){
        super.onResume();
            fetchBooks();
    }

    @NonNull
    @Override
    public BookListPresenter createPresenter() {
        return new BookListPresenter();
    }

    public void toBookDetailsActivity(Book book){
        Intent intent = new Intent(this, BookDetailsActivity.class);
        intent.putExtra("bookObject", book);
        startActivity(intent);
    }

    public void toAddBookActivity(){
        Intent intent = new Intent(this, AddBookActivity.class).putExtra("operation", "add");
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
        AlertDialog.Builder alert = new AlertDialog.Builder(BookListActivity.this);
        alert.setIcon(R.drawable.warning2_icon);
        alert.setTitle("Deleting All Books");
        alert.setMessage("Are you sure you want to delete all books!?");

        alert.setPositiveButton("DELETE ANYWAY", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        presenter.deleteAllBooks();
                    }
                }

        );
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener()

                    {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            // what ever you want to do with No option.
                            //Toast.makeText(BookDetailsActivity.this, "Checkout operation cancelled", Toast.LENGTH_SHORT).show();
                        }
                    }

            );

            alert.show();
        }

    @Override
    public void showAllBooksDeleted(String message) {
        Toast.makeText(BookListActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    //couldn't figure out a way to return book list from async callback so fetching book list here
    @Override
    public void fetchBooks(){
        libraryService.getBooks()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Book[]>(){
                    @Override
                    public final void onCompleted() {
                        // do nothing
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                bookListAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    @Override
                    public final void onError(Throwable e) {
                        Log.e("HAHA", "error in fetching books");
                    }

                    @Override
                    public final void onNext(Book[] books) {
                        //mCardAdapter.addData(response);
                        bookListFromServer.clear();
                        for (int i = 0; i < books.length; i++) {
                            bookListFromServer.add(books[i]);
                        }

                    }
                });
    }

}
