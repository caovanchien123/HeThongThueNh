package com.example.hethongthuenha.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hethongthuenha.ActivityRoomDetail;
import com.example.hethongthuenha.Model.Description_Room;
import com.example.hethongthuenha.Model.Image_Room;
import com.example.hethongthuenha.Model.Room;
import com.example.hethongthuenha.R;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;

public class RoomRecyclerView extends RecyclerView.Adapter<RoomRecyclerView.MyViewHolder> {


    private List<Room> rooms;
    private Context context;

    public RoomRecyclerView(Context context,List<Room> rooms) {
        this.rooms = rooms;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgRoom;
        private TextView tvTitle,tvAddress,tvPrice,tvTypeRoom,tvTimeAdded;
        private CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView =itemView.findViewById(R.id.cvRoomMain);
            imgRoom=itemView.findViewById(R.id.custom_img_main);
            tvTitle=itemView.findViewById(R.id.tv_title_main);
            tvAddress=itemView.findViewById(R.id.tv_address_main);
            tvPrice=itemView.findViewById(R.id.tv_price_main);
            tvTypeRoom=itemView.findViewById(R.id.tv_type_room_main);
            tvTimeAdded=itemView.findViewById(R.id.tv_time_added_room);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.custom_room_main,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Room room=rooms.get(position);
        Description_Room stage1=room.getStage1();
        Image_Room stage3=room.getStage3();

        NumberFormat formatter = NumberFormat.getCurrencyInstance();

        String title=stage1.getTitle();
        String address= stage1.getAddress();
        String price=formatter.format(stage1.getPrice());
        String type_room=stage1.getType_room();
        String type_date=stage1.getType_date();
        String urlImg=stage3.getImagesURL().get(0);

        holder.tvTitle.setText(title);
        holder.tvAddress.setText(address);
        holder.tvPrice.setText(price+"/"+type_date);
        holder.tvTypeRoom.setText(type_room);

        String timeAgo=(String) DateUtils.getRelativeTimeSpanString(room.getTimeAdded()
        .getSeconds()*1000);

        holder.tvTimeAdded.setText(timeAgo);

        Picasso.with(context).load(urlImg).error(R.drawable.ic_baseline_error_24)
                .placeholder(R.drawable.loading).into(holder.imgRoom);

        holder.cardView.setOnClickListener(v->{
            Intent intent=new Intent(context, ActivityRoomDetail.class);
            intent.putExtra("room",room);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }
}
