package com.example.hethongthuenha;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hethongthuenha.API.PersonAPI;
import com.example.hethongthuenha.Adapter.UtilitieseRecyclerView;
import com.example.hethongthuenha.Model.Description_Room;
import com.example.hethongthuenha.Model.Image_Room;
import com.example.hethongthuenha.Model.LivingExpenses_Room;
import com.example.hethongthuenha.Model.Person;
import com.example.hethongthuenha.Model.Room;
import com.example.hethongthuenha.Model.Utilities_Room;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class
ActivityRoomDetail extends AppCompatActivity {

    //Component
    ImageView imgRoomMain;
    ImageView[] images;
    TextView tvTitle, tvDescription, tvPrice, tvAccommodation,
            tvAmout, tvAddress, tvArea, tvTypeRoom, tvNamePerson, tvContactPerson,
            tvWaterPrice, tvElectricityPrice, tvTvPrice, tvInternetPrice, tvParkingPrice;
    EditText etComment;
    Button btnWatchMore, btnBookRoom;
    LinearLayout[] linearLiving;
    CardView cvPerson;
    //Model
    Room room;
    //Firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    //Other
    DecimalFormat deciFormat;
    NumberFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);

        room = (Room) getIntent().getSerializableExtra("room");
        Init();
    }

    private void Init() {
        images = new ImageView[4];
        linearLiving = new LinearLayout[5];
        Description_Room description_room = room.getStage1();
        LivingExpenses_Room livingExpenses_room = room.getStage2();
        Image_Room image_room = room.getStage3();
        Utilities_Room utilities_room = room.getStage4();

        imgRoomMain = findViewById(R.id.img_room_main_detail);


        //init array image
        for (int i = 0; i < 4; i++) {
            String imgId = "img_room" + (i + 1) + "_detail";
            int resId = getResources().getIdentifier(imgId, "id", getPackageName());
            images[i] = findViewById(resId);
        }
        //init linearlayout
        for (int i = 0; i < 5; i++) {
            String imgId = "layout_living" + (i + 1);
            int resId = getResources().getIdentifier(imgId, "id", getPackageName());
            linearLiving[i] = findViewById(resId);
        }

        tvTitle = findViewById(R.id.tv_title_detail_room);
        tvDescription = findViewById(R.id.tv_description_detail_room);
        tvAccommodation = findViewById(R.id.tv_accommodation_detail_room);
        tvPrice = findViewById(R.id.tv_price_detail_room);
        tvAmout = findViewById(R.id.tv_amout_detail_room);
        tvAddress = findViewById(R.id.tv_address_detail_room);
        tvArea = findViewById(R.id.tv_area_detail_room);
        tvTypeRoom = findViewById(R.id.tv_type_room_detail_room);
        tvNamePerson = findViewById(R.id.name_person_detail);
        tvContactPerson = findViewById(R.id.contact_person_detail);

        btnWatchMore = findViewById(R.id.btn_watchmore_detail);
        btnBookRoom = findViewById(R.id.btnBookRoom);

        tvWaterPrice = findViewById(R.id.custom_water_price);
        tvElectricityPrice = findViewById(R.id.custom_electricity_price);
        tvInternetPrice = findViewById(R.id.custom_internet_price);
        tvTvPrice = findViewById(R.id.custom_tv_price);
        tvParkingPrice = findViewById(R.id.custom_parking_price);

        deciFormat = new DecimalFormat();
        formatter = NumberFormat.getCurrencyInstance();


        etComment = findViewById(R.id.et_comment_detail);
        cvPerson = findViewById(R.id.cv_person_detail);

        LoadUtilities(utilities_room);
        //LoadImage(image_room);
        LoadInformation(description_room);
        LoadInformPerson(room.getPerson_id());
        LoadLivingExpesens(livingExpenses_room);
        NotificationPay();
        CommentRoom();
        GoToPerson();
    }

    private void GoToPerson() {
        cvPerson.setOnClickListener(v -> {
            Intent intent = new Intent(ActivityRoomDetail.this, ActivityPerson.class);
            intent.putExtra("id_person", room.getPerson_id());
            startActivity(intent);
        });
    }

    private void Prepayment() {

        String contact = "0169xxxxxx";
        String id_room = room.getRoom_id();
        String id_person = PersonAPI.getInstance().getUid();
        String text = "Nội dung cú pháp gửi tiền:\n" +
                "\nRoom:" + id_room + "\n" +
                "\nID:" + id_person + "\n" +
                " \n" +
                "-----------------------------------------------\n" +
                "Số tài khoản:" + contact + "\n" +
                " \n" +
                "Tên chủ tài khoản:An a\n" +
                " \n" +
                "*Ít nhất bạn phải đóng ít nhất 10% tiền trọ nếu không sẽ hoàn tiền\n" +
                "gửi lại sau 5-10 ngày\n";

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo trả trước");
        builder.setMessage(text);
        builder.setPositiveButton("Tôi đã hiểu", (dialog, which) -> dialog.dismiss());
        builder.show();
    }

    private void NotificationPay() {

        btnBookRoom.setOnClickListener(v -> {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(ActivityRoomDetail.this);
            builderSingle.setIcon(R.drawable.home);
            builderSingle.setTitle("Chọn hình thức thanh toán");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(ActivityRoomDetail.this, android.R.layout.select_dialog_item);
            arrayAdapter.add("Thanh toán trả trước");
            arrayAdapter.add("Thanh toán trả sau");

            builderSingle.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

            builderSingle.setAdapter(arrayAdapter, (dialog, which) -> {
                //String strName = arrayAdapter.getItem(which);
                switch (which) {
                    case 0:
                        Prepayment();
                        break;
                    case 1:
                        PayLater();
                        break;
                }
            });
            builderSingle.show();
        });

    }

    private void PayLater() {
        db.collection("User").whereEqualTo("uid", room.getPerson_id())
                .get().addOnCompleteListener(value -> {
            if (value.isSuccessful()) {
                for (QueryDocumentSnapshot persons : value.getResult()) {
                    Person person = persons.toObject(Person.class);
                    Intent intent = new Intent(ActivityRoomDetail.this, ActivityChat.class);
                    intent.putExtra("toId",person.getUid());
                    intent.putExtra("toEmail", person.getEmail());
                    intent.putExtra("toName", person.getFullName());
                    intent.putExtra("description_room", "Tôi muốn thuê căn nhà " + room.getStage1().getTitle());
                    intent.putExtra("url", room.getStage3().getImagesURL().get(0));
                    startActivity(intent);
                }
            }
        });
    }

    private void LoadInformPerson(String id_person) {
        db.collection("User").whereEqualTo("uid", id_person)
                .get().addOnSuccessListener(queryDocumentSnapshots -> {
            if (!queryDocumentSnapshots.isEmpty()) {
                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                    Person person = documentSnapshot.toObject(Person.class);
                    tvNamePerson.setText(person.getFullName());
                    tvContactPerson.setText("SĐT:" + person.getContact());
                }
            }
        });
    }

    private void LoadLivingExpesens(LivingExpenses_Room livingExpensesRoom) {
        tvWaterPrice.setText(FormatMoney(livingExpensesRoom.getmWater()));
        tvElectricityPrice.setText(FormatMoney(livingExpensesRoom.getmEletric()));
        tvParkingPrice.setText(FormatMoney(livingExpensesRoom.getmParkingSpace()));
        tvInternetPrice.setText(FormatMoney(livingExpensesRoom.getmInternet()));
        tvTvPrice.setText(FormatMoney(livingExpensesRoom.getmTivi()));
    }

    private String FormatMoney(double d) {
        deciFormat.setMaximumFractionDigits(1);
        return deciFormat.format(d / 1000) + "k";
    }

    private void LoadInformation(Description_Room description_room) {


        tvTitle.setText(description_room.getTitle());
        tvAddress.setText("Địa chỉ:" + description_room.getAddress());
        tvAmout.setText("Sức chứa:" + description_room.getAmout() + " người");
        tvAccommodation.setText("Số lượng:" + description_room.getAccommodation());
        tvPrice.setText("Giá:" + formatter.format(description_room.getPrice()) + "/" + description_room.getType_date());
        tvArea.setText("Diện tích:" + description_room.getArea() + "m2");
        tvTypeRoom.setText("Loại:" + description_room.getType_room());
        tvDescription.setText("Mô tả:" + description_room.getDescription());
    }

    private void LoadUtilities(Utilities_Room utilities_room) {

        List<String> utilityLimit = new ArrayList<>();
        Utilities_Room utilitiesRoom = new Utilities_Room();
        utilitiesRoom.setDescription_utility(utilityLimit);
        for (int i = 0; i < 3; i++)
            utilityLimit.add(utilities_room.getDescription_utility().get(i));

        RecyclerView utilitiesRecyclerView = findViewById(R.id.UtilitiesRecyclerview);
        utilitiesRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        UtilitieseRecyclerView adapter = new UtilitieseRecyclerView(this, utilitiesRoom);
        utilitiesRecyclerView.setAdapter(adapter);


        if (utilities_room.getDescription_utility().size() > 3) {
            btnWatchMore.setOnClickListener(v -> {
                int sizeLimit = utilityLimit.size();
                int sizeAll = utilities_room.getDescription_utility().size();

                if (btnWatchMore.getText().toString().equals("Xem thêm...")) {
                    while (sizeLimit < sizeAll) {
                        utilityLimit.add(utilities_room.getDescription_utility().get(sizeLimit));
                        sizeLimit++;
                    }
                    adapter.notifyDataSetChanged();
                    btnWatchMore.setText("Thu gọn..");
                } else if (btnWatchMore.getText().toString().equals("Thu gọn..")) {
                    while (sizeLimit != 3) {
                        utilityLimit.remove((sizeLimit - 1));
                        sizeLimit--;
                    }
                    adapter.notifyDataSetChanged();
                    btnWatchMore.setText("Xem thêm...");
                }
            });
        } else {
            btnWatchMore.setVisibility(View.GONE);
        }
    }

    private void CommentRoom() {
        etComment.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    event != null &&
                            event.getAction() == KeyEvent.ACTION_DOWN &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                if (event == null || !event.isShiftPressed()) {
                    Toast.makeText(ActivityRoomDetail.this, v.getText().toString(), Toast.LENGTH_SHORT).show();

                    return true; // consume.
                }
            }
            return false; // pass on to other listeners.
        });
    }


    private void LoadImage(Image_Room image_room) {
        for (int i = 0; i < 4; i++) {
            Picasso.with(this).load(image_room.getImagesURL().get(i))
                    .placeholder(R.drawable.home).error(R.drawable.home)
                    .into(images[i]);


            int indexImage = i;
            images[i].setOnClickListener(v ->
                    imgRoomMain.setImageDrawable(images[indexImage].getDrawable()));
        }

        Picasso.with(this).load(image_room.getImagesURL().get(0))
                .placeholder(R.drawable.home).error(R.drawable.home)
                .into(imgRoomMain);
    }


}