package com.chuyende.hotelbookingappofuser.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.data_models.TaiKhoanNguoiDung;
import com.chuyende.hotelbookingappofuser.firebase_models.FBDataTaiKhoanNguoiDung;
import com.chuyende.hotelbookingappofuser.interfaces.ListTaiKhoanNguoiDung;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.List;

public class ManHinhDangNhap extends AppCompatActivity {
    Button btn_Dang_Nhap, btn_Dang_Nhap_Google, btn_Dang_Nhap_Facebook, btn_Dang_Nhap_Twitter;
    EditText txt_Ten_TK_DN, txt_Mat_Khau_DN;
    TextView tv_NextTo_Dang_Ky, tv_NextTo_QTK;

    //firebase
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth;

    // google
    static final int RC_SIGN_IN = 123;
    private GoogleSignInClient mGoogleSignInClient;

    //facebook
    private CallbackManager mCallbackManager;

    FBDataTaiKhoanNguoiDung fbDataTaiKhoanNguoiDung = new FBDataTaiKhoanNguoiDung();


    @Override
    public void onStart() {
        super.onStart();

//         Check if user is signed in (non-null) and update UI accordingly.
//        GoogleSignInAccount checkAccount = GoogleSignIn.getLastSignedInAccount(this);
//        if (checkAccount != null) {
//            Intent intent = new Intent(ManHinhDangNhap.this, FragmentMain.class);
//            startActivity(intent);
//        }
        mAuth = FirebaseAuth.getInstance();
//        updateWithToken(AccessToken.getCurrentAccessToken());
//        LoginManager.getInstance().retrieveLoginStatus(this, new LoginStatusCallback() {
//            @Override
//            public void onCompleted(AccessToken accessToken) {
//                if (accessToken != null)
//                {
//                    Intent intent = new Intent(ManHinhDangNhap.this, FragmentMain.class);
//                    startActivity(intent);
//                }
//            }
//
//            @Override
//            public void onFailure() {
//                Log.d("ErrorAutoLoginFacebook", "Login Fail");
//            }
//
//            @Override
//            public void onError(Exception exception) {
//                Log.w("ErrorAutoLoginFacebook", exception.getMessage());
//            }
//        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_dang_nhap);
        setControl();
        setEvent();
    }

    // viết sự kiện ở setEvent()
    private void setEvent() {
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
        btn_Dang_Nhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txt_Ten_TK_DN.getText().toString().trim().length() == 0) {
                    txt_Ten_TK_DN.setError("Không được để trống");
                }
                if (txt_Mat_Khau_DN.getText().toString().trim().length() == 0) {
                    txt_Mat_Khau_DN.setError("Không được để trống");
                }
                fbDataTaiKhoanNguoiDung.layDuLieuTaiKhoanNguoiDung(new ListTaiKhoanNguoiDung() {
                    @Override
                    public void interfaceListTaiKhoanNguoiDung(List<TaiKhoanNguoiDung> listTaiKhoanNguoiDung) {
                        for (TaiKhoanNguoiDung robot : listTaiKhoanNguoiDung) {
                            if (robot.getTen_TKNguoiDung().equals(txt_Ten_TK_DN.getText().toString().trim())
                                    && robot.getMatKhau_TKNguoiDung().equals(txt_Mat_Khau_DN.getText().toString().trim())) {
                                Intent intent = new Intent(ManHinhDangNhap.this, FragmentMain.class);
                                startActivity(intent);
                                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                                break;
                            } else {
                                Toast.makeText(getApplicationContext(), "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

            }
        });
//        [END click button đăng nhập bằng tài khoản đăng ký]

//        [START đăng nhập bằng tk google]
        // Configure Google Sign In
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        btn_Dang_Nhap_Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
//        [END đăng nhập bằng tk google]

//        [START đăng nhập bằng tk facebook ]
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        btn_Dang_Nhap_Facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginManager.getInstance().logInWithReadPermissions(ManHinhDangNhap.this,
                        Arrays.asList("email", "public_profile"));

                LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        handleFacebookAccessToken(loginResult.getAccessToken());
                    }

                    @Override
                    public void onCancel() {
                        Intent intent = new Intent(getApplicationContext(), ManHinhDangNhap.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("TAG", "facebook:onError", error);
                        // ...
                    }
                });
                ;
            }
        });
//        [END đăng nhập bằng tk facebook ]
        btn_Dang_Nhap_Twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });
    }

    //    //    [START Google]
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

                //Calling a new function to handle signin
                handleSignInResult(task);
            } catch (ApiException e) {
                Log.w("TAG", "Google sign in failed", e);
            }
        }
        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Log.d("TAG-Successssssssssssss", "Login ok");
        } catch (ApiException e) {
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
//                            FirebaseUser users = mAuth.getCurrentUser();
                            Intent intent = new Intent(ManHinhDangNhap.this, FragmentMain.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                        }

                    }
                });
    }
//    //    [END Google]

    //    [START facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("TAG", "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        Log.d("HandleFbAccessToken", ": " + credential.toString());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
//                            FirebaseUser users = mAuth.getCurrentUser();
                            Intent intent = new Intent(ManHinhDangNhap.this, FragmentMain.class);
                            startActivity(intent);
//                            updateUI(users);
//                            updateWithToken(accessToken);
                        } else {
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            Toast.makeText(ManHinhDangNhap.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
                        }
                    }
                });
    }

    //    [END facebook]
    private void logOut() {
        LoginManager.getInstance().logOut();
        FirebaseAuth.getInstance().signOut();
    }

//    private void updateWithToken(AccessToken currentAccessToken) {
//        if (currentAccessToken != null) {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent i = new Intent(ManHinhDangNhap.this, FragmentMain.class);
//                    startActivity(i);
//                    Toast.makeText(ManHinhDangNhap.this, "Logined Your Facebook Account", Toast.LENGTH_SHORT).show();
//                    finish();
//                }
//            }, 1000);
//        } else {
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    Intent i = new Intent(ManHinhDangNhap.this, ManHinhDangNhap.class);
//                    startActivity(i);
//                    finish();
//                }
//            }, 1000);
//        }
//    }

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
