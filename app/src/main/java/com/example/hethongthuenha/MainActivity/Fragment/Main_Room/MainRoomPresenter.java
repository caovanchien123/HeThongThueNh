package com.example.hethongthuenha.MainActivity.Fragment.Main_Room;

import android.util.Log;

import com.example.hethongthuenha.Model.Room;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class MainRoomPresenter implements MainRoomContract.Presenter{


    private FirebaseFirestore db=FirebaseFirestore.getInstance();
    private MainRoomContract.View view;

    public MainRoomPresenter(MainRoomContract.View view) {
        this.view = view;
    }

    @Override
    public void InitRoom() {
        List<Room> rooms=new ArrayList<>();
        db.collection("Room").orderBy("timeAdded", Query.Direction.DESCENDING)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            for(DocumentSnapshot documentSnapshot:queryDocumentSnapshots){
                Room room=documentSnapshot.toObject(Room.class);
                rooms.add(room);
            }
            view.InitAdapter(rooms);
        }).addOnFailureListener(e-> Log.d(this.getClass().getName(), "InitRoom: "+e.getMessage()));
    }
}
