package com.chuyende.hotelbookingappofuser.activities;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.data_models.DaDat;
import com.chuyende.hotelbookingappofuser.data_models.NguoiDung;
import com.chuyende.hotelbookingappofuser.data_models.Phong;
import com.chuyende.hotelbookingappofuser.data_models.Photo;
import com.chuyende.hotelbookingappofuser.data_models.ThanhToan;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;

public class ManHinhThanhToan extends AppCompatActivity {

    EditText edtDateNDen, edtDatenDi;
    ImageView imgDatenDen, ingDateNDi;
    Calendar calendarone, calendartwo;
    SimpleDateFormat simpleDateFormat;

    private TextView txtTenphong, txtDiachi, txtSonguoi, txtGiaphong, txtTamtinh, txtTongTien, image1;
    TextView edtGiamgia;


    private Button btnDongYDatPhong;

    private ImageView imgHinhanh;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseStorage firebaseStorage;
    StorageReference storageReference;

    public static final String COLLECTION_PHONG = "Phong";
    public static final String MA_PHONG = "KS010WBuLfIBmX55ssYoGq3U";
    public static final String DUONG_DAN = "/media/phong/KS010WBuLfIBmX55ssYoGq3U/anhDaiDien/1ad8aa1d-8b8f-4a3e-a066-be4b58d86b2a.png";

    public static String MAPHONG = "KS010WBuLfIBmX55ssYoGq3U";
    public static String MATAIKHOANNGUOIDUNG = "TKND03";
    public static String DADAT = "DaDat";
    public static String TAIKHOANNGUOIDUNG = "TaiKhoanNguoiDung";
    public static String TENTAIKHOAN = "tenTaiKhoan";
    public static String MANGUOIDUNG = "maNguoiDung";
    public static String NGUOIDUNG = "NguoiDung";
    public static String PHONG = "Phong";
    public static String DD = "DD";
    public static String TAG = "ManHinhThanhToan";
    public static String DONVITINH = " VND/Đêm";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_thanh_toan);




        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        setControl();
        setEvent();
    }

    private void setEvent() {
        // lấy ảnh đại diện
        loadAnh();




        //firebase
        getdataTT();
        imgDatenDen = (ImageView) findViewById(R.id.img_Ngayden);
        ingDateNDi = (ImageView) findViewById(R.id.img_Ngaydi);

        //Thiet lap thong tin tong tien
        if (edtDateNDen.getText().toString().equals("") && edtDatenDi.getText().toString().equals("")){
            txtTamtinh.setText("0" + DONVITINH);
            txtTongTien.setText("0" + DONVITINH);
        }

        //get datePickerDialog
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        imgDatenDen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chongay1();
                content();
            }
        });

        ingDateNDi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chongay2();
                content();
            }
        });

        //Xu ly su kien cho nut dong y dat phong
        try {
            btnDongYDatPhong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String ngayDen = edtDateNDen.getText().toString();
                    String ngayDi = edtDatenDi.getText().toString();
                    if (ngayDen.equals("") && ngayDi.equals("")) {
                        setToastMessageFailure("Vui lòng chọn ngày đến và ngày đi");
                    } else if (ngayDen.equals("") || ngayDi.equals("")) {
                        if (ngayDen.equals(""))
                            setToastMessageFailure("Vui lòng chọn ngày đến");
                        else if (ngayDi.equals(""))
                            setToastMessageFailure("Vui lòng chọn ngày đi");
                    } else {
                        if (kiemTraNgayDi(edtDateNDen.getText().toString(), edtDatenDi.getText().toString()) == false) {
                            setToastMessageFailure("Vui lòng chọn lại ngày đi. Ngày đến phải nhỏ hơn ngày đi");
                        } else {
                            db.collection(TAIKHOANNGUOIDUNG).document(MATAIKHOANNGUOIDUNG)
                                    .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                        @Override
                                        public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                            if (error != null) {
                                                Log.w(TAG, error);
                                                return;
                                            }
                                            if (value != null) {
                                                String tenTaiKhoan = value.getString(TENTAIKHOAN);

                                                //Lay ma nguoi dung tu ten tai khoan
                                                db.collection(NGUOIDUNG).whereEqualTo(TENTAIKHOAN, tenTaiKhoan).addSnapshotListener(new EventListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                                                        if (error != null) {
                                                            Log.w(TAG, error);
                                                            return;
                                                        }
                                                        if (value != null) {
                                                            ArrayList<String> maNguoiDungList = new ArrayList<>();
                                                            for (DocumentSnapshot doc : value) {
                                                                maNguoiDungList.add(doc.getString(MANGUOIDUNG));
                                                            }

                                                            //Lay ma khach san tu ma phong
                                                            db.collection(PHONG).document(MAPHONG).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                                                @Override
                                                                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                                                                    if (error != null) {
                                                                        Log.w(TAG, error);
                                                                        return;
                                                                    }
                                                                    if (value != null) {
                                                                        Phong phong = value.toObject(Phong.class);
                                                                        DaDat daDat = new DaDat();
                                                                        daDat.setMaDat(DD + createRandomAString());
                                                                        daDat.setMaKhachSan(phong.getMaKhachSan());
                                                                        daDat.setMaNguoiDung(maNguoiDungList.get(0));
                                                                        daDat.setMaPhong(MAPHONG);
                                                                        daDat.setNgayDatPhong(getDateTime());
                                                                        daDat.setNgayDen(edtDateNDen.getText().toString());
                                                                        daDat.setNgayDi(edtDatenDi.getText().toString());
                                                                        daDat.setTongThanhToan(Integer.parseInt(txtTongTien.getText().toString().substring(0, txtTongTien.length() - 8)));
                                                                        //Them thong tin dat vao bang da dat
                                                                        db.collection(DADAT).document(daDat.getMaDat()).set(daDat).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void aVoid) {
                                                                                Log.d(TAG, "Them thanh cong");
                                                                                setToastMessageSuccess("Đặt phòng thành công");
                                                                            }
                                                                        }).addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                Log.d(TAG, "Them that bai " + e);
                                                                            }
                                                                        });
                                                                    }
                                                                }
                                                            });
                                                        }
                                                    }
                                                });
                                            }
                                        }
                                    });
                        }
                    }
                }
            });
        } catch (Exception e) {
            Log.d(TAG, "Lỗi " + e);
        }
    }

    public boolean kiemTraNgayDi(String ngayDen, String ngayDi) {
        int ngDen, thDen, nDen, ngDi, thDi, nDi;

        ngDen = Integer.parseInt(ngayDen.substring(0, 2));
        thDen = Integer.parseInt(ngayDen.substring(3, 5));
        nDen = Integer.parseInt(ngayDen.substring(6, 10));
        ngDi = Integer.parseInt(ngayDi.substring(0, 2));
        thDi = Integer.parseInt(ngayDi.substring(3, 5));
        nDi = Integer.parseInt(ngayDi.substring(6, 10));

        if (nDen > nDi) {
            return false;
        } else if (nDen <= nDi){
            if (thDen > thDi) {
                return false;
            } else if (thDen <= thDi) {
                if (ngDen > ngDi) {
                    return false;
                }
            }
        }
        return true;
    }

    public void content() {
        setTamTinhVaTongTien();
        refresh(1000);
    }


    public void refresh(int i) {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                content();
            }
        };
        handler.postDelayed(runnable, i);
    }

    public void setTamTinhVaTongTien() {
        if (!edtDateNDen.getText().toString().equals("") && !edtDatenDi.getText().toString().equals("")
                && kiemTraNgayDi(edtDateNDen.getText().toString(), edtDatenDi.getText().toString()) == true){
            double gia = Double.parseDouble(txtGiaphong.getText().toString().substring(0, txtGiaphong.length() - 7));
            int tongTien = tinhTongPhiThanhToan(edtDateNDen.getText().toString(), edtDatenDi.getText().toString(), gia);
            txtTamtinh.setText(tongTien + DONVITINH);

            if (!txtKhuyenMai.equals("0%")) {
                String khuyenMai = txtKhuyenMai.getText().toString().substring(0, txtKhuyenMai.length() - 1);
                int phanTramKhuyenMai = Integer.parseInt(khuyenMai);
                int tongTienKhuyenMai = tongTien - (tongTien * phanTramKhuyenMai / 100);
                txtTongTien.setText(tongTienKhuyenMai + DONVITINH);
            } else {
                txtTongTien.setText(tongTien + DONVITINH);
            }
        } else {
            txtTamtinh.setText("0" + DONVITINH);
            txtTongTien.setText("0" + DONVITINH);
        }
    }

    //Ham tao chuoi random 20 ki tu
    public String createRandomAString() {
        String candidateChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        Random random = new Random();
        StringBuilder rndString = new StringBuilder();
        while (rndString.length() < 20) {
            int index = (int) (random.nextFloat() * candidateChars.length());
            rndString.append(candidateChars.charAt(index));
        }
        return rndString.toString();
    }


    public void setControl() {
        edtDateNDen = findViewById(R.id.edit_Ngayden);
        edtDatenDi = findViewById(R.id.edit_Ngaydi);
        txtTenphong = findViewById(R.id.edit_tenphongtt);
        txtDiachi = findViewById(R.id.edit_diachitt);
        txtSonguoi = findViewById(R.id.txtsonguoi);
        txtGiaphong = findViewById(R.id.txtGia);
        txtTamtinh = findViewById(R.id.txtGiatamtinh);
        txtKhuyenMai = findViewById(R.id.txtmagiamgia);
        txtTongTien = findViewById(R.id.txttongtien);
        imgHinhanh = findViewById(R.id.img_hinhanh);
        image1 = findViewById(R.id.img1);
        btnDongYDatPhong = findViewById(R.id.btnDongYDatPhong);
    }

    public void chongay1() {
        calendarone = Calendar.getInstance();
        int ngay = calendarone.get(Calendar.DATE);
        int thang = calendarone.get(Calendar.MONTH);
        int nam = calendarone.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth ,  new DatePickerDialog.OnDateSetListener() {
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
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, android.R.style.Theme_Holo_Dialog_MinWidth ,  new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendartwo.set(i, i1, i2);
                edtDatenDi.setText(simpleDateFormat.format(calendartwo.getTime()));
            }
        }, nam, thang, ngay);
        datePickerDialog.show();
    }

    public void getdataTT() {

        db.collection(COLLECTION_PHONG).document(MA_PHONG).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        ThanhToan thanhToan = task.getResult().toObject(ThanhToan.class);
                        Log.d("test", "onComplete: " + thanhToan.toString());

                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();

                            txtTenphong.setText(thanhToan.getTenPhong());
                            txtDiachi.setText(thanhToan.getDiaChiPhong());
                            txtSonguoi.setText("" + thanhToan.getSoKhach() + " người");
                            txtGiaphong.setText(thanhToan.getGiaThue().toString() + " VND/đêm");
                            txtTamtinh.setText(thanhToan.getGiaThue().toString() + " VND");
                            edtGiamgia.setText("" + thanhToan.getPhanTramGiamGia());



        try {
            db.collection(PHONG).document(MAPHONG).get()
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
                                txtGiaphong.setText(thanhToan.getGiaThue().toString() + DONVITINH);
                                txtTamtinh.setText(thanhToan.getGiaThue().toString() + DONVITINH);
                                txtKhuyenMai.setText(thanhToan.getPhanTramGiamGia() + "%");
                            }

                        }
                    });
        } catch (Exception e) {
            Log.d(TAG, "Lỗi " + e);
        }
    }

    public void loadAnh() {
        StorageReference imageRefl = storageReference.child(DUONG_DAN);

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

    //Tinh tong tien ma nguoi dung phai tra theo so ngay dat
    private int tinhTongPhiThanhToan(String ngayDen, String ngayDi, double giathue) {
        int tongPhi = 0;
        int ngDen, thDen, nDen, ngDi, thDi, nDi;

        ngDen = Integer.parseInt(ngayDen.substring(0, 2));
        thDen = Integer.parseInt(ngayDen.substring(3, 5));
        nDen = Integer.parseInt(ngayDen.substring(6, 10));
        ngDi = Integer.parseInt(ngayDi.substring(0, 2));
        thDi = Integer.parseInt(ngayDi.substring(3, 5));
        nDi = Integer.parseInt(ngayDi.substring(6, 10));

        if (nDen == nDi) {
            if (thDen == thDi) {
                tongPhi = (int) giathue * (ngDi - ngDen);
            } else if (thDen < thDi) {
                if (thDen == 1 | thDen == 3 | thDen == 5| thDen == 7| thDen == 8| thDen == 10| thDen == 12) {
                    tongPhi = (int) giathue * (ngDi + (31 - ngDen));
                } else if (thDen == 4 | thDen == 6 | thDen == 9| thDen == 11) {
                    tongPhi = (int) giathue * (ngDi + (30 - ngDen));
                } else if (thDen == 2 && nDen % 4 == 0) {
                    tongPhi = (int) giathue * (ngDi + (29 - ngDen));
                } else if (thDen == 2) {
                    tongPhi = (int) giathue * (ngDi + (28 - ngDen));
                }
            }
        } else if (nDen < nDi) {
            tongPhi = (int) giathue * (ngDi + (31 - ngDen));
        }
        return tongPhi;
    }

    //Lay thoi gian hien tai tu he thong
    private String getDateTime() {
        String dateTime = "";
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss-dd/MM/yyyy");
        dateTime = dateFormat.format(calendar.getTime());
        return dateTime;
    }



    private void setToastMessageSuccess(String text) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast_message_success, (ViewGroup)findViewById(R.id.ToastMessage_layout));
        TextView textView = view.findViewById(R.id.tvTextToast);
        textView.setText(text);

        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }


    private void setToastMessageFailure(String text) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.custom_toast_message_failure, (ViewGroup)findViewById(R.id.ToastMessage_layout));
        TextView textView = view.findViewById(R.id.tvTextToast);
        textView.setText(text);

        final Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }
}