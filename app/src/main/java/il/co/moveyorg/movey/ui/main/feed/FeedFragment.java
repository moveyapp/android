package il.co.moveyorg.movey.ui.main.feed;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.flipboard.bottomsheet.BottomSheetLayout;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.data.model.Post;
import il.co.moveyorg.movey.ui.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends BaseFragment implements FeedMvpView, View.OnClickListener {

    @Inject
    FeedPresenter feedPresenter;

    @Inject
    FeedAdapter feedAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.fragment_feed_bottomsheet)
    BottomSheetLayout createPostBottomSheet;

    @BindView(R.id.fragment_feed_fab_create_post)
    FloatingActionButton openCreatePostLayoutBtn;
    private Button createPostBtn;
    private EditText editPostContent;


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

        openCreatePostLayoutBtn.setOnClickListener(this);

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

    @Override
    public void dismissCreatePost() {
        createPostBottomSheet.dismissSheet();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_feed_fab_create_post: {
                openCreatePost();
                break;
            }
            case R.id.create_post_submit_btn: {
//                Toast.makeText(getActivity(), "Post clicked!", Toast.LENGTH_SHORT).show();
                if (editPostContent != null) {
                    feedPresenter.createNewPost(editPostContent.getText().toString());
                }
                break;
            }

        }
    }

    private void openCreatePost() {
        createPostBottomSheet.setPeekSheetTranslation(600);

        createPostBottomSheet.showWithSheetView(LayoutInflater.from(getActivity()).inflate(R.layout.layout_create_new_post, createPostBottomSheet, false));

        createPostBtn =
            createPostBottomSheet.findViewById(R.id.create_post_submit_btn);

        editPostContent =
            createPostBottomSheet.findViewById(R.id.create_post_layout_edit_post_textview);

        createPostBtn.setOnClickListener(this);
    }
}
