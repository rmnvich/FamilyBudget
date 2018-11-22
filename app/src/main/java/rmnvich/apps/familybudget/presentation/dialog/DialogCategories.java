package rmnvich.apps.familybudget.presentation.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import java.util.Objects;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import rmnvich.apps.familybudget.R;
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl;
import rmnvich.apps.familybudget.presentation.adapter.categories.CategoriesAdapter;
import rmnvich.apps.familybudget.presentation.custom.ExpandableBottomSheetDialog;

import static rmnvich.apps.familybudget.data.common.Constants.LOAD_DATA_DELAY;

public class DialogCategories extends ExpandableBottomSheetDialog {

    private CompositeDisposable mCompositeDisposable;
    private DatabaseRepositoryImpl mDatabaseRepository;

    private DialogCategoriesCallback mCallback;
    private CategoriesAdapter mAdapter;

    private ProgressBar mProgressBar;

    public DialogCategories(Context context, DatabaseRepositoryImpl databaseRepository) {
        super(context);
        mDatabaseRepository = databaseRepository;
        mCompositeDisposable = new CompositeDisposable();

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        assert inflater != null;
        @SuppressLint("InflateParams")
        View view = inflater.inflate(R.layout.dialog_categories, null);

        mProgressBar = view.findViewById(R.id.progress_bar);
        mAdapter = new CategoriesAdapter();
        mAdapter.setListener(categoryId -> {
            mCallback.onClickCategory(categoryId);
            this.dismiss();
        });

        RecyclerView recyclerView = view.findViewById(R.id.bottom_sheet_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);

        this.setContentView(view);
        Objects.requireNonNull(this.getWindow())
                .setSoftInputMode(WindowManager
                        .LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    private Disposable getAllCategories() {
        return mDatabaseRepository.getAllCategories()
                .subscribe(categories -> {
                            mAdapter.setData(categories);
                            mProgressBar.setVisibility(View.INVISIBLE);
                        }, throwable -> mProgressBar.setVisibility(View.INVISIBLE),
                        () -> mProgressBar.setVisibility(View.INVISIBLE));
    }

    public void show(DialogCategoriesCallback callback) {
        if (mCallback == null)
            mCallback = callback;
        this.show();

        new Handler().postDelayed(
                () -> mCompositeDisposable.add(getAllCategories()),
                LOAD_DATA_DELAY);
    }

    public interface DialogCategoriesCallback {
        void onClickCategory(int categoryId);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mCompositeDisposable.dispose();
    }
}
