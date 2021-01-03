package com.chuyende.hotelbookingappofuser.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.chuyende.hotelbookingappofuser.R;

public class FragmentManHinhYeuThich extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_man__hinh__yeu__thich, container, false);
        return view;
    }
}
