package com.example.hethongthuenha.MainActivity.Fragment.Main_Room;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hethongthuenha.Adapter.RoomRecyclerView;
import com.example.hethongthuenha.CreateRoom.CreateRoomActivity;
import com.example.hethongthuenha.Model.Room;
import com.example.hethongthuenha.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;


public class fragment_main_room extends Fragment  implements MainRoomContract.View{

    private FloatingActionButton floatingActionButton;
    private RoomRecyclerView adapter;
    private RecyclerView recyclerView;
    private MainRoomPresenter presenter;


    public fragment_main_room() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main_room, container, false);
        presenter=new MainRoomPresenter(this);
        Init(view);
        Event();
        return view;
    }

    private void Init(View view){
        presenter.InitRoom();
        recyclerView=view.findViewById(R.id.roomRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        floatingActionButton=view.findViewById(R.id.floatingActionButton);
    }

    private void Event(){
        floatingActionButton.setOnClickListener(v ->
                startActivity(new Intent(getActivity(), CreateRoomActivity.class)));
    }

    @Override
    public void InitAdapter(List<Room> rooms) {
        adapter=new RoomRecyclerView(getActivity(),rooms);
        recyclerView.setAdapter(adapter);
    }
}