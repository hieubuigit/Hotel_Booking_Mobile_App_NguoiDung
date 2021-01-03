package com.chuyende.hotelbookingappofuser.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.chuyende.hotelbookingappofuser.R;

public class FragmentManHinhTaiKhoan extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_man__hinh__tai__khoan,container,false);
        return view;
    }
}
