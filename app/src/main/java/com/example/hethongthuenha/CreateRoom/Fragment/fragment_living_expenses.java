package com.example.hethongthuenha.CreateRoom.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.hethongthuenha.Model.LivingExpenses_Room;
import com.example.hethongthuenha.R;

import java.util.ArrayList;


public class fragment_living_expenses extends Fragment {

    private EditText etWater, etElectricity, etInternet, etTV, etParkingSpace;
    private IDataCommunication dataCommunication;

    public fragment_living_expenses() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            dataCommunication = (IDataCommunication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DataCommunication");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_living_expenses, container, false);

        etWater = view.findViewById(R.id.etWater);
        etElectricity = view.findViewById(R.id.etElectricity);
        etInternet = view.findViewById(R.id.etInternet);
        etParkingSpace = view.findViewById(R.id.etParkingSpace);
        etTV = view.findViewById(R.id.etTV);

        Button btFinishStage2 = view.findViewById(R.id.btnFinishStage2);

        btFinishStage2.setOnClickListener(v -> {
            if (isValid()) {
                double priceWater = Double.parseDouble(etWater.getText().toString());
                double priceElectricity = Double.parseDouble(etElectricity.getText().toString());
                double priceInternet = Double.parseDouble(etInternet.getText().toString());
                double priceTV = Double.parseDouble(etTV.getText().toString());
                double priceParkingSpace = Double.parseDouble(etParkingSpace.getText().toString());

                LivingExpenses_Room stage2 = new LivingExpenses_Room
                        (priceWater, priceElectricity, priceInternet, priceTV, priceParkingSpace);

                dataCommunication.LivingExpenses(stage2);

                fragment_image fragment = new fragment_image();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameContainer, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return view;
    }

    private boolean isValid() {
        boolean valid = true;
        if (TextUtils.isEmpty(etElectricity.getText())) {
            valid = false;
            etElectricity.setError("Làm ơn không để trống");
        }
        if (TextUtils.isEmpty(etTV.getText())) {
            valid = false;
            etTV.setError("Làm ơn không để trống");
        }
        if (TextUtils.isEmpty(etInternet.getText())) {
            valid = false;
            etInternet.setError("Làm ơn không để trống");
        }
        if (TextUtils.isEmpty(etWater.getText())) {
            valid = false;
            etWater.setError("Làm ơn không để trống");
        }
        if (TextUtils.isEmpty(etParkingSpace.getText())) {
            valid = false;
            etParkingSpace.setError("Làm ơn không để trống");
        }

        return valid;
    }

}