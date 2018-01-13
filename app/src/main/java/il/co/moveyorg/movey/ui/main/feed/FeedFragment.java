package il.co.moveyorg.movey.ui.main.feed;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.data.model.Post;
import il.co.moveyorg.movey.data.model.Ribot;
import il.co.moveyorg.movey.ui.base.BaseFragment;
import il.co.moveyorg.movey.ui.ribot.MainMvpView;
import il.co.moveyorg.movey.ui.ribot.MainPresenter;
import il.co.moveyorg.movey.ui.ribot.RibotsAdapter;
import il.co.moveyorg.movey.util.DialogFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends BaseFragment implements FeedMvpView {

    @Inject
    FeedPresenter feedPresenter;

    @Inject
    FeedAdapter feedAdapter;

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

        mRecyclerView.setAdapter(feedAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        feedPresenter.attachView(this);
        feedPresenter.loadFeed();

        return view;
    }

    @Override
    public void showPosts(List<Post> posts) {
        feedAdapter.setPosts(posts);
        feedAdapter.notifyDataSetChanged();

    }

    @Override
    public void addPost(Post post) {
        feedAdapter.addPost(post);
        feedAdapter.notifyDataSetChanged();
    }


}
