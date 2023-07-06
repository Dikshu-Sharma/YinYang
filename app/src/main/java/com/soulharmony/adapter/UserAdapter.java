package com.soulharmony.adapter;

import static com.soulharmony.model.Constants.DEFAULT_IMAGE;
import static com.soulharmony.model.Constants.PROFILE_PICTURE_INDEX;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.soulharmony.ChatScreen;
import com.soulharmony.MatchActivity;
import com.soulharmony.R;
import com.soulharmony.model.Constants;
import com.soulharmony.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    MatchActivity matchActivity;
    List<User> usersArrayList;
    String loginUserId;
    public UserAdapter(MatchActivity matchActivity, List<User> usersArrayList, String loginUserId) {
        this.matchActivity = matchActivity;
        this.usersArrayList = usersArrayList;
        this.loginUserId = loginUserId;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.match_user_item, parent, false);
        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User receiverUser = usersArrayList.get(position);
        holder.textViewName.setText(receiverUser.getName());
        Picasso.get()
                .load(receiverUser.getImagesUrlWithIndex().getOrDefault(Constants.PROFILE_PICTURE_INDEX, DEFAULT_IMAGE))
                .into(holder.imageViewUser);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(matchActivity, ChatScreen.class);
            intent.putExtra("receiverUserId",receiverUser.get_id());
            intent.putExtra("receiverImage",receiverUser.getImagesUrlWithIndex().getOrDefault(PROFILE_PICTURE_INDEX, DEFAULT_IMAGE));
            intent.putExtra("receiverUserName", receiverUser.getName());
            intent.putExtra("loginUserId", loginUserId);
            matchActivity.startActivity(intent);
//            matchActivity.finish(); // TODO : I DOUBT IS IT REQUIRED
        });
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public ImageView imageViewUser;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.userMatchesId1);
            imageViewUser = itemView.findViewById(R.id.userImageId1);
        }
    }
}
