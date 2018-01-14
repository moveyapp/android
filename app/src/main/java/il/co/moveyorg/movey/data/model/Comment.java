package il.co.moveyorg.movey.data.model;

import java.util.List;

/**
 * Created by eladk on 1/14/18.
 */

public class Comment {

    private String id;
    private String postId;
    private String content;
    private String createdAt;
    private String updatedAt;
    private String userId;
    private String userName;

  public String getPostId() {
    return postId;
  }

  public void setPostId(String postId) {
    this.postId = postId;
  }

  public String getCreatedAt() {
      return createdAt;
    }

    public void setCreatedAt(String createdAt) {
      this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
      return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
      this.updatedAt = updatedAt;
    }

    public String getUserId() {
      return userId;
    }

    public void setUserId(String userId) {
      this.userId = userId;
    }

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getContent() {
      return content;
    }

    public void setContent(String content) {
      this.content = content;
    }

    public String getUserName() {
      return userName;
    }

    public void setUserName(String userName) {
      this.userName = userName;
    }
  }


