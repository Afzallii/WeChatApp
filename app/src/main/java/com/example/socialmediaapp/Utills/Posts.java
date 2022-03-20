package com.example.socialmediaapp.Utills;

public class Posts {
    String id,PostDate,PostDescription,UserprofileImageUri,postImageUrl,username;

    public Posts() {

    }

    public Posts(String id, String postDate, String postDescription, String userprofileImageUri, String postImageUrl, String username) {
        this.id = id;
        this.PostDate = postDate;
        this.PostDescription = postDescription;
        this.UserprofileImageUri = userprofileImageUri;
        this.postImageUrl = postImageUrl;
        this.username = username;
    }

    public String getPostDate() {
        return PostDate;
    }

    public void setPostDate(String postDate) {
        PostDate = postDate;
    }

    public String getPostDescription() {
        return PostDescription;
    }

    public void setPostDescription(String postDescription) {
        PostDescription = postDescription;
    }

    public String getUserprofileImageUri() {
        return UserprofileImageUri;
    }

    public void setUserprofileImageUri(String userprofileImageUri) {
        UserprofileImageUri = userprofileImageUri;
    }

    public String getPostImageUrl() {
        return postImageUrl;
    }

    public void setPostImageUrl(String postImageUrl) {
        this.postImageUrl = postImageUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
