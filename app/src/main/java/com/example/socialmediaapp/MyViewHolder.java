package com.example.socialmediaapp;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import de.hdodenhof.circleimageview.CircleImageView;


public class MyViewHolder extends RecyclerView.ViewHolder {

    CircleImageView profileImagePost;
    TextView profileUsername,timeAgo,postDescription;
    TextView commentCounter,likeCounter;
    ImageView likeImage,postImage,commentImage;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        profileImagePost=itemView.findViewById(R.id.profileImagePost);
        profileUsername=itemView.findViewById(R.id.profileUsername);
        timeAgo=itemView.findViewById(R.id.timeAgo);
        postDescription=itemView.findViewById((R.id.postDescription));
        commentCounter=itemView.findViewById(R.id.commentCounter);
        likeCounter=itemView.findViewById(R.id.likeCounter);
        likeImage=itemView.findViewById(R.id.likeImage);
        postImage=itemView.findViewById(R.id.postImage);
        commentImage=itemView.findViewById(R.id.commentImage);
    }
}
