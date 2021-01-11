package com.chuyende.hotelbookingappofuser.activities;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.data_models.ThanhToan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ManHinhThanhToan extends AppCompatActivity {

    EditText edtDateNDen, edtDatenDi;
    ImageButton imgDatenDen, ingDateNDi;
    Calendar calendarone, calendartwo;
    SimpleDateFormat simpleDateFormat;
    private TextView txtTenphong, txtDiachi, txtSonguoi, txtGiaphong, txtTamtinh, txtTongTien, image1;
    EditText edtGiamgia;
    private ImageView imgHinhanh;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_thanh_toan);
        //ngayden-ngaydi
        //onvaluetextchange thuc hien
        //try catch
        //tinh tong ngay new date()

        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        // lấy ảnh đại diện
        loadAnh();

//        TongTien();

        //tinh Tổng Tiền

        //firebase
        getdataTT();

        imgDatenDen = (ImageButton) findViewById(R.id.img_Ngayden);
        ingDateNDi = (ImageButton) findViewById(R.id.img_Ngaydi);

        //get datePickerDialog
        simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy");
        imgDatenDen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chongay1();
            }
        });

        ingDateNDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chongay2();
            }
        });


        setControl();
    }

    public void setControl() {
        edtDateNDen = findViewById(R.id.edit_Ngayden);
        edtDatenDi = findViewById(R.id.edit_Ngaydi);
        txtTenphong = findViewById(R.id.edit_tenphongtt);
        txtDiachi = findViewById(R.id.edit_diachitt);
        txtSonguoi = findViewById(R.id.txtsonguoi);
        txtGiaphong = findViewById(R.id.txtGia);
        txtTamtinh = findViewById(R.id.txtGiatamtinh);
        edtGiamgia = findViewById(R.id.txtmagiamgia);
        txtTongTien = findViewById(R.id.txttongtien);
        imgHinhanh = findViewById(R.id.img_hinhanh);
        image1 = findViewById(R.id.img1);
    }

    public void chongay1() {
        calendarone = Calendar.getInstance();
        int ngay = calendarone.get(Calendar.DATE);
        int thang = calendarone.get(Calendar.MONTH);
        int nam = calendarone.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendarone.set(i, i1, i2);
                edtDateNDen.setText(simpleDateFormat.format(calendarone.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    public void chongay2() {
        calendartwo = Calendar.getInstance();
        int ngay = calendartwo.get(Calendar.DATE);
        int thang = calendartwo.get(Calendar.MONTH);
        int nam = calendartwo.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendartwo.set(i, i1, i2);
                edtDatenDi.setText(simpleDateFormat.format(calendartwo.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    public void getdataTT() {
        db.collection("Phong").document("KS010WBuLfIBmX55ssYoGq3U").get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        ThanhToan thanhToan = task.getResult().toObject(ThanhToan.class);
                        Log.d("test", "onComplete: " + thanhToan.toString());

                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();

                            txtTenphong.setText(thanhToan.getTenPhong());
                            txtDiachi.setText(thanhToan.getDiaChiPhong());
                            txtSonguoi.setText("" + thanhToan.getSoKhach());
                            txtGiaphong.setText(thanhToan.getGiaThue().toString());
                            txtTamtinh.setText(thanhToan.getGiaThue().toString());
                            edtGiamgia.setText("" + thanhToan.getPhanTramGiamGia());


                        }

                    }
                });
    }


    public void loadAnh() {
        StorageReference imageRefl = storageReference.child("/media/phong/KS010WBuLfIBmX55ssYoGq3U/anhDaiDien/1ad8aa1d-8b8f-4a3e-a066-be4b58d86b2a.png");

        long MAXBYTES = 1024 * 1024;


        imageRefl.getBytes(MAXBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {

                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgHinhanh.setImageBitmap(bitmap);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

//    public void TongTien() {
//        if (edtDateNDen == null && edtDatenDi == null) {
//            Context context = getApplicationContext();
//            CharSequence text = "vui long chon ngay den va ngay di";
//            Toast toast = Toast.makeText(context, text, Toast.LENGTH_LONG);
//            toast.show();
//        } else {
//            int nTongngay = (int) ((calendartwo.getTimeInMillis() - calendarone.getTimeInMillis()) / (1000 * 60 * 60 * 24));
//            String stien = txtTamtinh.getText().toString();
//            int a = Integer.parseInt(stien);
//            int nTong = nTongngay * a;
//            txtTongTien.setText(String.valueOf(nTong));
//        }
//
//    }

}