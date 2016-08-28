package triumphantakash.github.io.librarymanager.Activities;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Akash on 8/28/2016.
 */
public interface AddBookView extends MvpView{
    void showBookAddSuccess(String successText);
    void showBookAddError(String errorTitle, String errorMsg);
    void showUnsavedDataWarning(String warningTitle, String warningMsg);
}