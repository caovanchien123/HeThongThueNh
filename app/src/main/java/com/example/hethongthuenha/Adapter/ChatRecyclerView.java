package com.example.hethongthuenha.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hethongthuenha.API.PersonAPI;
import com.example.hethongthuenha.Model.Chat;
import com.example.hethongthuenha.Model.Room;
import com.example.hethongthuenha.R;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;


public class ChatRecyclerView extends RecyclerView.Adapter<ChatRecyclerView.MyViewHolder> {

    private Context context;
    private List<Chat> chats;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    NumberFormat formatter = NumberFormat.getCurrencyInstance();
    public ChatRecyclerView(Context context, List<Chat> chats) {
        this.context = context;
        this.chats = chats;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvChat, tvAddress, tvPrice, tvTypeRoom;
        private LinearLayout linearChat;
        private ImageView imgChat, imgAgree, imgRefuse;
        private CardView cvChat;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvChat = itemView.findViewById(R.id.tv_custom_chat);
            linearChat = itemView.findViewById(R.id.linear_custom_chat);
            imgChat = itemView.findViewById(R.id.img_custom_chat);
            imgAgree = itemView.findViewById(R.id.img_agree_chat);
            imgRefuse = itemView.findViewById(R.id.img_refuse_chat);
            tvAddress = itemView.findViewById(R.id.tv_address_chat);
            tvPrice = itemView.findViewById(R.id.tv_price_chat);
            tvTypeRoom = itemView.findViewById(R.id.tv_type_room_chat);
            cvChat = itemView.findViewById(R.id.cv_custom_chat);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_chat, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Chat chat = chats.get(position);
        holder.tvChat.setText(chat.getText());

        String fromUser = PersonAPI.getInstance().getEmail();
        String fromUserChat = chat.getFrom_email_person();


        if (!fromUser.equals(fromUserChat)) {
            holder.linearChat.setGravity(Gravity.LEFT);
            holder.tvChat.setBackgroundResource(R.drawable.border_chat_left);
        } else {
            holder.linearChat.setGravity(Gravity.RIGHT);
            holder.tvChat.setBackgroundResource(R.drawable.border_chat_right);
        }

        if (chat.getUrl().equals("")) {
            holder.imgChat.setVisibility(View.GONE);
            holder.imgAgree.setVisibility(View.GONE);
            holder.imgRefuse.setVisibility(View.GONE);
            holder.tvAddress.setVisibility(View.GONE);
            holder.tvPrice.setVisibility(View.GONE);
            holder.tvTypeRoom.setVisibility(View.GONE);
            holder.cvChat.setVisibility(View.GONE);
        } else {
            holder.imgChat.setVisibility(View.VISIBLE);
            holder.imgAgree.setVisibility(View.VISIBLE);
            holder.imgRefuse.setVisibility(View.VISIBLE);
            holder.tvAddress.setVisibility(View.VISIBLE);
            holder.tvPrice.setVisibility(View.VISIBLE);
            holder.tvTypeRoom.setVisibility(View.VISIBLE);
            holder.cvChat.setVisibility(View.VISIBLE);
            Picasso.with(context).load(chat.getUrl()).placeholder(R.drawable.home)
                    .into(holder.imgChat);

            db.collection("Room").get()
                    .addOnCompleteListener(v -> {
                        if (v.isSuccessful()) {
                            for (QueryDocumentSnapshot value : v.getResult()) {
                                Room room = value.toObject(Room.class);
                                for (String s : room.getStage3().getImagesURL())
                                    if (s.equals(chat.getUrl())) {
                                        holder.tvAddress.setText("Địa chỉ:"+room.getStage1().getAddress());
                                        holder.tvPrice.setText("Giá:" + formatter.format(room.getStage1().getPrice()));
                                        holder.tvTypeRoom.setText(room.getStage1().getType_room());
                                    }
                            }
                        }
                    });

            holder.imgAgree.setOnClickListener(v -> {
                Toast.makeText(context, "I'm agree", Toast.LENGTH_SHORT).show();
            });

            holder.imgRefuse.setOnClickListener(v -> {
                Toast.makeText(context, "I'm refuse", Toast.LENGTH_SHORT).show();
            });
        }


    }

    @Override
    public int getItemCount() {
        return chats.size();
    }
}
