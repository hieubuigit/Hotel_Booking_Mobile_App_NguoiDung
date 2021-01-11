package com.chuyende.hotelbookingappofuser.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ScrollView;
import android.widget.Spinner;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;

import com.chuyende.hotelbookingappofuser.R;
import com.chuyende.hotelbookingappofuser.adapters.AdapterExpandableHeightGridView;
import com.chuyende.hotelbookingappofuser.adapters.AdapterGridViewPhong;
import com.chuyende.hotelbookingappofuser.data_models.LoaiPhong;
import com.chuyende.hotelbookingappofuser.data_models.Phong;
import com.chuyende.hotelbookingappofuser.data_models.TinhThanhPho;
import com.chuyende.hotelbookingappofuser.firebase_models.FBDaTaTinhThanhPho;
import com.chuyende.hotelbookingappofuser.firebase_models.FBDataLoaiPhong;
import com.chuyende.hotelbookingappofuser.firebase_models.FBDataPhong;
import com.chuyende.hotelbookingappofuser.interfaces.ListLoaiPhong;
import com.chuyende.hotelbookingappofuser.interfaces.ListPhong;
import com.chuyende.hotelbookingappofuser.interfaces.ListTinhThanhPho;

import java.util.ArrayList;
import java.util.List;

public class FragmentManHinhNha extends Fragment {
    AdapterExpandableHeightGridView adapterExpandableHeightGridView;
    ScrollView scrollView;
    Spinner sp_TinhThanh, sp_TieuChi, sp_LoaiPhong;
    SearchView searchView;
    FBDaTaTinhThanhPho fbDaTaTinhThanhPho = new FBDaTaTinhThanhPho();
    FBDataLoaiPhong fbDataLoaiPhong = new FBDataLoaiPhong();
    FBDataPhong fbDataPhong = new FBDataPhong();

    public AdapterGridViewPhong adapterGridViewPhong;
    public static String ALL = "Tất cả";

    @Override
    public void onStart() {
        super.onStart();

//        [START du lieu spinner Tinh thanh pho]
        fbDaTaTinhThanhPho.layDuLieuTinhThanhPho(new ListTinhThanhPho() {
            @Override
            public void interfaceListTinhThanhPho(List<TinhThanhPho> listTinhThanhPho) {
                ArrayList<String> arrlstTenTinhThanhPho = new ArrayList<String>();
                arrlstTenTinhThanhPho.add(0, ALL);
                for (TinhThanhPho robot : listTinhThanhPho) {
                    arrlstTenTinhThanhPho.add(robot.getTinhThanhPho());
                }

                ArrayAdapter<String> arrAdtSpinner_TinhThanhPho =
                        new ArrayAdapter((Activity) getContext(), android.R.layout.simple_spinner_item, arrlstTenTinhThanhPho);
                arrAdtSpinner_TinhThanhPho.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_TinhThanh.setAdapter(arrAdtSpinner_TinhThanhPho);
                arrAdtSpinner_TinhThanhPho.notifyDataSetChanged();
            }
        });
//        [END du lieu spinner Tinh thanh pho]

//        [START du lieu spinner loai phong]
        fbDataLoaiPhong.layDuLieuLoaiPhong(new ListLoaiPhong() {
            @Override
            public void interfaceListLoaiPhong(List<LoaiPhong> listLoaiPhong) {
                ArrayList<String> arrlstLoaiPhong = new ArrayList<String>();
                arrlstLoaiPhong.add(0, ALL);
                for (LoaiPhong robot : listLoaiPhong) {
                    arrlstLoaiPhong.add(robot.getLoaiphong());
                }

                ArrayAdapter arrAdtSpinner_LoaiPhong = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, arrlstLoaiPhong);
                arrAdtSpinner_LoaiPhong.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                sp_LoaiPhong.setAdapter(arrAdtSpinner_LoaiPhong);
                arrAdtSpinner_LoaiPhong.notifyDataSetChanged();
            }
        });
//        [END du lieu spinner loai phong]

//        [START lọc theo tiêu chí]
        sp_TieuChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                [START lọc theo rating]
                if (sp_TieuChi.getSelectedItem().equals("Rating")) {
                    fbDataPhong.layDuLieuPhongRating(new ListPhong() {
                        @Override
                        public void interfaceListPhong(List<Phong> listPhong) {
//                [START hiện dữ liệu phòng]
                            adapterGridViewPhong = new AdapterGridViewPhong(getContext(), R.layout.listview_item, (ArrayList) listPhong);
                            adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
                            adapterExpandableHeightGridView.setExpanded(true);
                            adapterGridViewPhong.notifyDataSetChanged();
//                [END hiện dữ liệu phòng]

                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    adapterGridViewPhong.timKiemVaLoc(query, sp_TinhThanh.getSelectedItem().toString(), sp_LoaiPhong.getSelectedItem().toString(),
                                            sp_TieuChi.getSelectedItem().toString());
                                    return true;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    adapterGridViewPhong.timKiemVaLoc(newText, sp_TinhThanh.getSelectedItem().toString(), sp_LoaiPhong.getSelectedItem().toString(),
                                            sp_TieuChi.getSelectedItem().toString());
                                    return true;
                                }
                            });
                            sp_TinhThanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    adapterGridViewPhong.timKiemVaLoc(searchView.getQuery().toString(), sp_TinhThanh.getSelectedItem().toString()
                                            , sp_LoaiPhong.getSelectedItem().toString(),
                                            sp_TieuChi.getSelectedItem().toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                            sp_LoaiPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    adapterGridViewPhong.timKiemVaLoc(searchView.getQuery().toString(), sp_TinhThanh.getSelectedItem().toString(), sp_LoaiPhong.getSelectedItem().toString(),
                                            sp_TieuChi.getSelectedItem().toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    });

                }
//                [END lọc theo rating]

//                [START lọc theo giá tăng dần]
                if (sp_TieuChi.getSelectedItem().equals("Giá tăng dần")) {
                    fbDataPhong.layDuLieuPhongCostASC(new ListPhong() {
                        @Override
                        public void interfaceListPhong(List<Phong> listPhong) {
//                [START hiện dữ liệu phòng]
                            adapterGridViewPhong = new AdapterGridViewPhong(getContext(), R.layout.listview_item, (ArrayList) listPhong);
                            adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
                            adapterExpandableHeightGridView.setExpanded(true);
                            adapterGridViewPhong.notifyDataSetChanged();
//                [END hiện dữ liệu phòng]
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    adapterGridViewPhong.timKiemVaLoc(query, sp_TinhThanh.getSelectedItem().toString(), sp_LoaiPhong.getSelectedItem().toString(),
                                            sp_TieuChi.getSelectedItem().toString());
                                    return true;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    adapterGridViewPhong.timKiemVaLoc(newText, sp_TinhThanh.getSelectedItem().toString(), sp_LoaiPhong.getSelectedItem().toString(),
                                            sp_TieuChi.getSelectedItem().toString());
                                    return true;
                                }
                            });
                            sp_TinhThanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    adapterGridViewPhong.timKiemVaLoc(searchView.getQuery().toString(), sp_TinhThanh.getSelectedItem().toString()
                                            , sp_LoaiPhong.getSelectedItem().toString(),
                                            sp_TieuChi.getSelectedItem().toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                            sp_LoaiPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    adapterGridViewPhong.timKiemVaLoc(searchView.getQuery().toString(), sp_TinhThanh.getSelectedItem().toString(), sp_LoaiPhong.getSelectedItem().toString(),
                                            sp_TieuChi.getSelectedItem().toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                        }
                    });

                }
//                [END lọc theo giá tăng dần]

//                [START lọc theo giá giảm dần]
                if (sp_TieuChi.getSelectedItem().equals("Giá giảm dần")) {
                    fbDataPhong.layDuLieuPhongCostDES(new ListPhong() {
                        @Override
                        public void interfaceListPhong(List<Phong> listPhong) {
//                [START hiện dữ liệu phòng]
                            adapterGridViewPhong = new AdapterGridViewPhong(getContext(), R.layout.listview_item, (ArrayList) listPhong);
                            adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
                            adapterExpandableHeightGridView.setExpanded(true);
                            adapterGridViewPhong.notifyDataSetChanged();
//                [END hiện dữ liệu phòng]
                            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                                @Override
                                public boolean onQueryTextSubmit(String query) {
                                    adapterGridViewPhong.timKiemVaLoc(query, sp_TinhThanh.getSelectedItem().toString(), sp_LoaiPhong.getSelectedItem().toString(),
                                            sp_TieuChi.getSelectedItem().toString());
                                    return true;
                                }

                                @Override
                                public boolean onQueryTextChange(String newText) {
                                    adapterGridViewPhong.timKiemVaLoc(newText, sp_TinhThanh.getSelectedItem().toString(), sp_LoaiPhong.getSelectedItem().toString(),
                                            sp_TieuChi.getSelectedItem().toString());
                                    return true;
                                }
                            });
                            sp_TinhThanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    adapterGridViewPhong.timKiemVaLoc(searchView.getQuery().toString(), sp_TinhThanh.getSelectedItem().toString()
                                            , sp_LoaiPhong.getSelectedItem().toString(),
                                            sp_TieuChi.getSelectedItem().toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                            sp_LoaiPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    adapterGridViewPhong.timKiemVaLoc(searchView.getQuery().toString(), sp_TinhThanh.getSelectedItem().toString(), sp_LoaiPhong.getSelectedItem().toString(),
                                            sp_TieuChi.getSelectedItem().toString());
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {
                                }
                            });
                        }
                    });

                }
//                [END lọc theo giá giảm dần]
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
//        [END lọc theo tiêu chí]
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_man_hinh_nha, container, false);

//      [START Ánh xạ]
        // gridview full height
        adapterExpandableHeightGridView = view.findViewById(R.id.gridViewMain);
        // seachview
        searchView = view.findViewById(R.id.searchView_ManHinhNha);
        // scrollview
        scrollView = view.findViewById(R.id.scrollView);
        // spinner
        sp_LoaiPhong = view.findViewById(R.id.spinner_LoaiPhong_Man_Hinh_Nha);
        sp_TieuChi = view.findViewById(R.id.spinner_TieuChi_Man_Hinh_Nha);
        sp_TinhThanh = view.findViewById(R.id.spinner_TinhThanhPho_Man_Hinh_Nha);
//        [END Ánh xạ]
        return view;
    }
}