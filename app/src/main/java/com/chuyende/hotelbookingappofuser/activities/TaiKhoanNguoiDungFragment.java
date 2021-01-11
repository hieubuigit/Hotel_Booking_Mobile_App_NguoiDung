package com.chuyende.hotelbookingappofuser.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.dialogs.ChonNgaySinhDialog;

public class TaiKhoanNguoiDungFragment extends Fragment {
    TextView tvTieuDe;
    EditText edtTenDayDu, edtQuocTich, edtEmailNguoiDung, edtSoDienThoai, edtNgaySinh;
    Button btnCapNhat, btnDangXuat;
    ImageView imvAvatarNguoiDung, imvIconChonNgaySinh;
    RecyclerView rcvCacPhongDaDat;
    RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = null;
        v = inflater.inflate(R.layout.fragment_tai_khoan_nguoi_dung, container, false);

        // Get all views form UI
        tvTieuDe = v.findViewById(R.id.tvTieuDe);
        edtTenDayDu = v.findViewById(R.id.edtTenDayDu);
        edtQuocTich = v.findViewById(R.id.edtQuocTich);
        edtEmailNguoiDung = v.findViewById(R.id.edtEmail);
        edtSoDienThoai = v.findViewById(R.id.edtSoDienThoai);
        edtNgaySinh = v.findViewById(R.id.edtChonNgaySinh);
        btnCapNhat = v.findViewById(R.id.btnCapNhat);
        btnDangXuat = v.findViewById(R.id.btnDangXuat);
        imvAvatarNguoiDung = v.findViewById(R.id.imvAvatarNguoiDung);
        imvIconChonNgaySinh = v.findViewById(R.id.imvIconChonNgaySinh);
        rcvCacPhongDaDat = v.findViewById(R.id.rcvCacPhongDaDat);

        // Set title for tai khoan nguoi dung screen
        tvTieuDe.setText(R.string.title_tai_khoan_nguoi_dung);

        imvIconChonNgaySinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChonNgaySinhDialog();
            }
        });

        return v;
    }

    // Show dialog chon ngay sinh
    public void showChonNgaySinhDialog() {
        DialogFragment fragment = new ChonNgaySinhDialog();
        fragment.show(getChildFragmentManager(), "CHON_NGAY_SINH");
    }
}