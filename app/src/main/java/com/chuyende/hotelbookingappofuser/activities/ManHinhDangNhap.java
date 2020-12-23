package com.chuyende.hotelbookingappofuser.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.data_models.TaiKhoanNguoiDung;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class ManHinhDangNhap extends AppCompatActivity {
    TextView tv_NextTo_Dang_Ky, tv_NextTo_QTK;
    Button btn_Dang_Nhap, btn_Dang_Nhap_Google, btn_Dang_Nhap_Facebook, btn_Dang_Nhap_Twitter;
    EditText txt_Ten_TK_DN, txt_Mat_Khau_DN;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public final ArrayList<TaiKhoanNguoiDung> arrayListTKNguoiDung = new ArrayList<TaiKhoanNguoiDung>();
    private FirebaseAuth mAuth;
    static final int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;
//    private CallbackManager mCallbackManager;
    @Override
    public void onStart() {
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
        GoogleSignInAccount currentUser = GoogleSignIn.getLastSignedInAccount(this);
        if (currentUser != null) {
            Intent intent = new Intent(ManHinhDangNhap.this, ManHinhNha.class);
            startActivity(intent);
        }
        super.onStart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man__hinh__dang__nhap);
        setControl();
        setEvent();
    }

    // viết sự kiện ở setEvent()
    private void setEvent() {
//        [START lấy dữ liệu từ firebase thêm vào arraylist]
        db.collection("TaiKhoanNguoiDung").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                // nếu lấy thành công
                if (task.isSuccessful()) {
                    // document duyệt dữ liệu trong task
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        TaiKhoanNguoiDung taiKhoanNguoiDung = new TaiKhoanNguoiDung();
                        // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
                        taiKhoanNguoiDung.setTen_TKNguoiDung(document.get("tenTaiKhoan").toString());
                        taiKhoanNguoiDung.setMatKhau_TKNguoiDung(document.get("matKhau").toString());
                        taiKhoanNguoiDung.setEmail_TKNguoiDung(document.get("email").toString());
                        taiKhoanNguoiDung.setSdt_TKNguoiDung(document.get("soDienThoai").toString());
                        taiKhoanNguoiDung.setMa_TKNguoiDung(document.getId());
                        // thêm vào arraylist
                        arrayListTKNguoiDung.add(taiKhoanNguoiDung);
                        // kiểm tra
                        Log.d("TAG-tentaikhoan", "tentk : " + taiKhoanNguoiDung.getTen_TKNguoiDung());
                        Log.d("TAG-matkhau", "matkhau : " + taiKhoanNguoiDung.getMatKhau_TKNguoiDung());
                        Log.d("TAG-ArrayList", "arrlist : " + arrayListTKNguoiDung.toString());
                    }

                    Log.d("TAG-ArrayList2", "arrlist : " + arrayListTKNguoiDung.toString());
                } else {
                    Log.d("TAG", "Error getting documents: ", task.getException());
                }
            }
        });
//        [END lấy dữ liệu từ firebase thêm vào arraylist]

//         [START click button chuyển sang màn hình đăng ký]
        tv_NextTo_Dang_Ky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManHinhDangNhap.this, ManHinhDangKy.class);
                startActivity(intent);
            }
        });
//         [END click button chuyển sang màn hình đăng ký]

//         [START click button chuyển sang màn hình quên tài khoản]
        tv_NextTo_QTK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManHinhDangNhap.this, ManHinhQuenTaiKhoan.class);
                startActivity(intent);
            }
        });
//        [END click button chuyển sang màn hình quên tài khoản]

//         [START click button đăng nhập bằng tài khoản đăng ký]
        // chưa hoàn thành
        btn_Dang_Nhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_Ten_TK_DN.getText().toString().trim().length() == 0) {
                    txt_Ten_TK_DN.setError("Không được để trống");
                }
                if (txt_Mat_Khau_DN.getText().toString().trim().length() == 0) {
                    txt_Mat_Khau_DN.setError("Không được để trống");
                }
                for (TaiKhoanNguoiDung taiKhoanNguoiDung2 : arrayListTKNguoiDung) {
                    if (taiKhoanNguoiDung2.getTen_TKNguoiDung().equals(txt_Ten_TK_DN.getText().toString().trim()) == true
                            && taiKhoanNguoiDung2.getMatKhau_TKNguoiDung().equals(txt_Mat_Khau_DN.getText().toString().trim()) == true) {
                        Toast.makeText(getApplicationContext(),"Đăng nhập thành công",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(ManHinhDangNhap.this, ManHinhNha.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("key_id_ten_tai_khoan",taiKhoanNguoiDung2.getMa_TKNguoiDung());
                        intent.putExtras(bundle);
                        startActivity(intent);
                    } else {
                        Log.d("TAG4", "Dang Nhap that bai");
                    }
                }
                Log.d("TAG-ArrayList_after_click", "Gia tri arrlist => " + arrayListTKNguoiDung.toString());
            }
        });

//        [END click button đăng nhập bằng tài khoản đăng ký]
//        [START đăng nhập bằng tk google]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance();
        btn_Dang_Nhap_Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
//        [END đăng nhập bằng tk google]

//        [START đăng nhập bằng tk facebook ]
//        // Initialize Facebook Login button
//        mCallbackManager = CallbackManager.Factory.create();
//        LoginButton loginButton = mBinding.buttonFacebookLogin;
//        loginButton.setReadPermissions("email", "public_profile");
//        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                handleFacebookAccessToken(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d(TAG, "facebook:onCancel");
//                // ...
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d(TAG, "facebook:onError", error);
//                // ...
//            }
//        });
//// ...
//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//
//            // Pass the activity result back to the Facebook SDK
//            mCallbackManager.onActivityResult(requestCode, resultCode, data);
//        }
//        [END đăng nhập bằng tk facebook ]
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account);
//                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                //Calling a new function to handle signin
                handleSignInResult(task);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            startActivity(new Intent(ManHinhDangNhap.this, ManHinhNha.class));
            Log.d("TAG-Successssssssssssss", "Login ok");
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("TAG", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(ManHinhDangNhap.this, ManHinhNha.class);
                            startActivity(intent);
                            Log.d("TAG-Successssssssssssss222222222", "Login ok");
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                        }

                        // ...
                    }
                });
    }

    // ánh xạ
    private void setControl() {
        //TextView
        tv_NextTo_Dang_Ky = findViewById(R.id.textView_MH_Dang_Nhap_Press_Dang_Ky);
        tv_NextTo_QTK = findViewById(R.id.textView_MH_Dang_Nhap_Press_QTK);
        //EditText
        txt_Ten_TK_DN = findViewById(R.id.editTextTenTK_DangNhap);
        txt_Mat_Khau_DN = findViewById(R.id.editTextMatKhau_DangNhap);
        //Button
        btn_Dang_Nhap = findViewById(R.id.button_DangNhap);
        btn_Dang_Nhap_Google = findViewById(R.id.button_DangNhap_Google);
        btn_Dang_Nhap_Facebook = findViewById(R.id.button_DangNhap_Facebook);
        btn_Dang_Nhap_Twitter = findViewById(R.id.button_DangNhap_Twitter);
        // sign in
    }
}
