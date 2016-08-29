package triumphantakash.github.io.librarymanager.Activities.add_book;
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import triumphantakash.github.io.librarymanager.services.BookServices;
import triumphantakash.github.io.librarymanager.models.Book;

/**
 * Created by Akash on 8/28/2016.
 */
public class AddBookPresenter extends MvpBasePresenter<AddBookView> {

    //presenter will first validate this data and then if it's valid
    //pass it to business logic classs and based on the results call corresponding View method to
    //show anything on the UI
    public void addBook(Book book){
        if(isInputValid(book)){
            //call the businesslogic thing
            BookServices abs = new BookServices();
            abs.postBook(book);
            if (isViewAttached())
                getView().showBookAddSuccess("Book Added successfully");
        }else{
            String errorTitle = "Incomplete Data";
            String errorMsg = "Can not add book with incomplete data. One or more fields are empty.";
            if (isViewAttached())
                getView().showBookAddError(errorTitle, errorMsg);
        }
    }

    public void modifyBook(Book book){
        if(isInputValid(book)){
            //call the businesslogic thing
            BookServices abs = new BookServices();
            abs.modifyBook(book);
            if (isViewAttached())
                getView().showBookAddSuccess("Book Modified successfully");
        }else{
            String errorTitle = "Incomplete Data";
            String errorMsg = "Can not add book with incomplete data. One or more fields are empty.";
            if (isViewAttached())
                getView().showBookAddError(errorTitle, errorMsg);
        }
    }





    public boolean isInputValid(Book book){
        if((book.getBookTitle().trim().length()>0) && (book.getBookAuthor().trim().length() > 0) && (book.getBookCatagories().trim().length() > 0)&& (book.getBookPublisher().trim().length()>0)){
            return true;
        }else{
            return false;
        }
    }

    public void unsavedChangeValidation(Book book){
        String warningTitle, warningMsg;
        if((book.getBookTitle().trim().length()>0) || (book.getBookAuthor().trim().length() > 0) || (book.getBookCatagories().trim().length() > 0) || (book.getBookPublisher().trim().length()>0)) {
            warningTitle = "Unsaved Data";
            warningMsg = "Unsaved data present\n" +
                    "Still want to leave the screen?";
        }else{
            warningMsg = null;
            warningTitle = null;
        }
        if (isViewAttached())
            getView().showUnsavedDataWarning(warningTitle, warningMsg);
    }
}
