package il.co.moveyorg.movey.ui.main.feed.post_page;

import il.co.moveyorg.movey.data.model.Comment;
import il.co.moveyorg.movey.ui.base.MvpView;

/**
 * Created by eladk on 1/14/18.
 */

public interface PostMvpView extends MvpView {
  void addComment(Comment comment);
  void dismissCreateComment();
}
