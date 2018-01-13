package il.co.moveyorg.movey.ui.main.feed;

import java.util.List;

import il.co.moveyorg.movey.data.model.Post;
import il.co.moveyorg.movey.ui.base.MvpView;

/**
 * Created by eladk on 11/30/17.
 */

public interface FeedMvpView extends MvpView {
  void showPosts(List<Post> posts);
  void addPost(Post post);
}
