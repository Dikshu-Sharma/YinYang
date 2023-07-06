package com.soulharmony;


import static com.soulharmony.model.Constants.ID_SEPARATOR;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.soulharmony.adapter.MessageAdapter;
import com.soulharmony.model.Constants;
import com.soulharmony.model.Message;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatScreen extends AppCompatActivity {
    String receiverImage;
    String receiverUserId;
    String receiverUserName;
    CircleImageView profile;
    FirebaseDatabase database;
    CardView sendButton;
    EditText textMessage;
    String logInUserChatRoom, receiverChatRoom;
    RecyclerView recyclerView;
    ArrayList<Message> messagesArrayList;
    MessageAdapter messageAdapter;
    DatabaseReference chatReference;
    String loginUserId;

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(this, MatchActivity.class);
//        intent.putExtra("loginUserId", loginUserId);
//        startActivity(intent);
//        finish();
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_screen);
        database = FirebaseDatabase.getInstance();
        getSupportActionBar().hide();
        Intent intent = getIntent();
        loginUserId = intent.getStringExtra("loginUserId");
        receiverUserName = getIntent().getStringExtra("receiverUserName");
        receiverImage = getIntent().getStringExtra("receiverImage");
        receiverUserId = getIntent().getStringExtra("receiverUserId");
        messagesArrayList = new ArrayList<>();
        sendButton = findViewById(R.id.sendButtonId1);
        textMessage = findViewById(R.id.textMessage1);
        profile = findViewById(R.id.senderProfilePictureId1);
        recyclerView = findViewById(R.id.recyclerView1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        messageAdapter = new MessageAdapter(ChatScreen.this, messagesArrayList, loginUserId);
        recyclerView.setAdapter(messageAdapter);
        Picasso.get().load(receiverImage).into(profile);
        TextView receiverUserNameEditText = findViewById(R.id.receiverUserNameId1);
        receiverUserNameEditText.setText(receiverUserName);
        logInUserChatRoom = loginUserId + ID_SEPARATOR + receiverUserId;
        receiverChatRoom = receiverUserId + ID_SEPARATOR + loginUserId;
        chatReference = database.getReference().child("chats").child(logInUserChatRoom).child("messages");
        chatReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Message messages = dataSnapshot.getValue(Message.class);
                    messagesArrayList.add(messages);
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sendButton.setOnClickListener(view -> {
            String message = textMessage.getText().toString();
            if (message.isEmpty()) {
                Toast.makeText(ChatScreen.this, "Please Type Message", Toast.LENGTH_SHORT).show();
                return;
            }
            textMessage.setText("");
            Date date = new Date();
            Message messageNew = new Message(message, loginUserId, date.getTime());
            database = FirebaseDatabase.getInstance();
            database.getReference().child("chats")
                    .child(logInUserChatRoom)
                    .child("messages")
                    .push().setValue(messageNew).addOnCompleteListener(task -> database.getReference().child("chats")
                            .child(receiverChatRoom)
                            .child("messages")
                            .push().setValue(messageNew).addOnCompleteListener(task1 -> {
                                Toast.makeText(ChatScreen.this, "Message sent successfully", Toast.LENGTH_SHORT).show();
                            }));
        });

    }
}