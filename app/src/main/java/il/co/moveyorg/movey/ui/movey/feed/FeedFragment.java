package il.co.moveyorg.movey.ui.movey.feed;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.data.model.Ribot;
import il.co.moveyorg.movey.ui.base.BaseFragment;
import il.co.moveyorg.movey.ui.main.MainMvpView;
import il.co.moveyorg.movey.ui.main.MainPresenter;
import il.co.moveyorg.movey.ui.main.RibotsAdapter;
import il.co.moveyorg.movey.util.DialogFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends BaseFragment implements MainMvpView {

    @Inject
    MainPresenter mainPresenter;

    @Inject
    RibotsAdapter mRibotsAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    public FeedFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentComponent().inject(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_feed, container, false);

        ButterKnife.bind(this,view);

        mRecyclerView.setAdapter(mRibotsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mainPresenter.attachView(this);
        mainPresenter.loadRibots();

        return view;
    }

    /***** MVP View methods implementation *****/
    @Override
    public void showRibots(List<Ribot> ribots) {
        mRibotsAdapter.setRibots(ribots);
        mRibotsAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {
        DialogFactory.createGenericErrorDialog(getContext(), getString(R.string.error_loading_ribots))
                .show();
    }

    @Override
    public void showRibotsEmpty() {
//        mRibotsAdapter.setRibots(Collections.<Ribot>emptyList());
//        mRibotsAdapter.notifyDataSetChanged();
//        Toast.makeText(getContext(), R.string.empty_ribots, Toast.LENGTH_LONG).show();
    }

}
