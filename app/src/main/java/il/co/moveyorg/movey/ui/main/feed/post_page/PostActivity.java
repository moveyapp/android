package il.co.moveyorg.movey.ui.main.feed.post_page;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import il.co.moveyorg.movey.R;
import il.co.moveyorg.movey.data.model.Post;
import il.co.moveyorg.movey.ui.base.BaseActivity;

public class PostActivity extends BaseActivity {

  private Post post;

  @BindView(R.id.activity_post_username_textview)
  TextView postUserNameTextView;

  @BindView(R.id.activity_post_content_textview)
  TextView postContentTextView;

  @BindView(R.id.activity_post_comments_recyclerview)
  RecyclerView commentsRecyclerView;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_post);
    activityComponent().inject(this);
    ButterKnife.bind(this);
  }

  @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
  public void onPostRecieved(Post post) {
//    Toast.makeText(this, "Post:" + post.getContent(), Toast.LENGTH_SHORT).show();
    this.post = post;
    postUserNameTextView.setText(this.post.getUserName());
    postContentTextView.setText(this.post.getContent());
  }

  @Override
  public void onStart() {
    super.onStart();
    EventBus.getDefault().register(this);
  }

  @Override
  public void onStop() {
    super.onStop();
    EventBus.getDefault().unregister(this);
  }

}
