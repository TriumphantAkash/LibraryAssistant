package triumphantakash.github.io.librarymanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import triumphantakash.github.io.librarymanager.R;
import triumphantakash.github.io.librarymanager.models.Book;

/**
 * Created by Akash on 8/20/2016.
 */
public class BookListAdapter extends ArrayAdapter {

    private final Context context;
    private final ArrayList<Book> BookArrayList;
    TextView bookTitle, bookAuthor;

    public BookListAdapter(Context context, ArrayList<Book> bookList) {
        super(context, R.layout.book_elements, bookList);
        this.context = context;
        this.BookArrayList = bookList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.book_elements, parent, false);
        }

        bookTitle = (TextView)convertView.findViewById(R.id.bookTitle);
        bookTitle.setText(BookArrayList.get(position).getBookTitle());
        bookAuthor = (TextView)convertView.findViewById(R.id.bookAuthor);
        bookAuthor.setText(BookArrayList.get(position).getBookAuthor());
        return convertView;
    }
}
