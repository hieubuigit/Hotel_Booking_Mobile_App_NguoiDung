package com.chuyende.hotelbookingappofuser.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
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
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class FragmentManHinhNha extends Fragment {
    AutoCompleteTextView autoCompleteTextView;
    AdapterExpandableHeightGridView adapterExpandableHeightGridView;
    public AdapterGridViewPhong adapterGridViewPhong;
    ArrayAdapter arrAdapterAutoComplete;

    ImageView imgClearSearch;
    ScrollView scrollView;
    Spinner sp_TinhThanh, sp_TieuChi, sp_LoaiPhong;
    SearchView searchView;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FBDaTaTinhThanhPho fbDaTaTinhThanhPho = new FBDaTaTinhThanhPho();
    FBDataLoaiPhong fbDataLoaiPhong = new FBDataLoaiPhong();
    FBDataPhong fbDataPhong = new FBDataPhong();

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
        view = inflater.inflate(R.layout.fragment_man__hinh__nha, container, false);

        //        [START sự kiện clear text autocompletextview]
//                imgClearSearch.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // làm trống text
//                        autoCompleteTextView.getText().clear();
//                        // sau khi làm trống text thì quay lại dữ liệu ban đầu
////                        adapterGridViewPhong = new AdapterGridViewPhong(getContext(), R.layout.listview_item, (ArrayList) listPhong);
////                        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
////                        adapterExpandableHeightGridView.setExpanded(true);
////                        adapterGridViewPhong.notifyDataSetChanged();
//                    }
//                });
//        [END sự kiện clear text autocompletetextview]
//        [START Ánh xạ]
        // gridview full height
        adapterExpandableHeightGridView = view.findViewById(R.id.gridViewMain);
//        // autocomplete
//        autoCompleteTextView = view.findViewById(R.id.multiauto_Search);
        // seachview
        searchView = view.findViewById(R.id.searchView_ManHinhNha);
        // scrollview
        scrollView = view.findViewById(R.id.scrollView);
        // spinner
        sp_LoaiPhong = view.findViewById(R.id.spinner_LoaiPhong_Man_Hinh_Nha);
        sp_TieuChi = view.findViewById(R.id.spinner_TieuChi_Man_Hinh_Nha);
        sp_TinhThanh = view.findViewById(R.id.spinner_TinhThanhPho_Man_Hinh_Nha);
//        // imageview
//        imgClearSearch = view.findViewById(R.id.imageview_ClearText);
//        [END Ánh xạ]


//         [START multiautpcomplete search]
        // add ten phong vao danh sach tim kiem
//        db.collection(COLLECTION_PHONG).addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//                if (error != null) {
//                    Log.d("Error-Data-Phong-TenPhong", " => " + error.getMessage());
//                }
//                if (value != null) {
//                    for (QueryDocumentSnapshot document : value) {
//                        arrlistAutoComplete.add(document.getString(TENPHONG));
//                    }
//                }
//            }
//        });
//        arrAdapterAutoComplete = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, arrlistAutoComplete);
//        autoCompleteTextView.setAdapter(arrAdapterAutoComplete);
//                db.collection("Phong")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        // nếu lấy thành công
//                        if (task.isSuccessful()) {
//                            // document duyệt dữ liệu trong task
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                arrAdtMultiAutoComplete.add(document.get("tenPhong").toString());
//                            }
//                        }
//                    }
//                });
//        // khi click vào dữ liệu được show ra
//        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                // bien item lay du lieu khi autocompletextview show ra ket qua
//                String item = adapterView.getItemAtPosition(i).toString();
//                // lấy dữ liệu có điều kiện, whereEqualTo("Tên field", giá trị trừu tượng: tùy vào kiểu dữ liệu của field (true, false, String, int...)
//
////                db.collection(COLLECTION_PHONG)
////                        .whereEqualTo(TENPHONG, item)
////                        .get()
////                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
////                            @Override
////                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
////                                if (task.isSuccessful()) {
////                                    // mỗi lần click vào 1 item thì tạo danh sách mới, danh sách này chỉ hiện đúng thông tin của item
////                                    // nếu không tạo danh sách mới thì mỗi lần click vào item khác sẽ hiện thông tin cũ
////                                    ArrayList<Phong> arrlistKetQuaTimKiem = new ArrayList<Phong>();
////                                    for (QueryDocumentSnapshot document : task.getResult()) {
////                                        Phong clsphong = new Phong();
////                                        // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
////                                        clsphong.setAnhDaiDien(document.get(ANHDAIDIEN).toString());
////                                        clsphong.setTenPhong(document.get(TENPHONG).toString());
////                                        clsphong.setRatingPhong(document.getDouble(RATINGPHONG));
////                                        clsphong.setGiaThue(document.getDouble(GIATHUE));
////                                        // thêm vào arraylist
////                                        arrlistKetQuaTimKiem.add(clsphong);
////                                        Log.d("Tag search", arrlistKetQuaTimKiem.toString());
////                                    }
////                                    adapterGridViewPhong = new AdapterGridViewPhong(getContext(), R.layout.listview_item, arrlistKetQuaTimKiem);
////                                    adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
////                                    adapterExpandableHeightGridView.setExpanded(true);
////                                } else {
////                                    Log.d("TAG11111111111111", "Error getting documents: ", task.getException());
////                                }
////                            }
////                        });
////                Toast.makeText(ManHinhNha.this, "Selected Item is: \t" + item, Toast.LENGTH_LONG).show();
//            }
//        });
////        [END multiautpcomplete search]

        // lọc phòng theo tỉnh thành
//        sp_TinhThanh.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
////                [ đã chọn tỉnh thành và loại phòng]
//                if (sp_TinhThanh.getSelectedItem() != TAT_CA_TINH_THANH) {
//                    db.collection(COLLECTION_PHONG)
//                            .whereEqualTo(MATINHTHANHPHO, sp_TinhThanh.getSelectedItem().toString())
//                            .whereEqualTo(MALOAIPHONG, sp_LoaiPhong.getSelectedItem().toString())
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    // nếu lấy thành công
//                                    if (task.isSuccessful()) {
//                                        ArrayList<Phong> arrlistLoc = new ArrayList<Phong>();
//                                        // document duyệt dữ liệu trong task
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            Phong clsphong = new Phong();
//                                            // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
//                                            clsphong.setAnhDaiDien(document.get(ANHDAIDIEN).toString());
//                                            clsphong.setTenPhong(document.get(TENPHONG).toString());
//                                            clsphong.setRatingPhong(document.getDouble(RATINGPHONG));
//                                            clsphong.setGiaThue(document.getDouble(GIATHUE));
//                                            // thêm vào arraylist
//                                            arrlistLoc.add(clsphong);
//                                        }
//                                        adapterGridViewPhong = new AdapterGridViewPhong(getContext(),
//                                                R.layout.listview_item, arrlistLoc);
//                                        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
//                                        adapterExpandableHeightGridView.setExpanded(true);
//
//                                    } else {
//                                        Log.d("TAG", "Error getting documents: ", task.getException());
//                                    }
//                                }
//                            });
//                }
////                [chọn tỉnh thành mà chưa chọn loại phòng]
//                if (sp_TinhThanh.getSelectedItem() != TAT_CA_TINH_THANH && sp_LoaiPhong.getSelectedItem() == TAT_CA_LOAI_PHONG) {
//                    db.collection(COLLECTION_PHONG)
//                            .whereEqualTo(MATINHTHANHPHO, sp_TinhThanh.getSelectedItem().toString())
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    // nếu lấy thành công
//                                    if (task.isSuccessful()) {
//                                        ArrayList<Phong> arrListLoc = new ArrayList<Phong>();
//                                        // document duyệt dữ liệu trong task
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            Phong clsphong = new Phong();
//                                            // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
//                                            clsphong.setAnhDaiDien(document.get(ANHDAIDIEN).toString());
//                                            clsphong.setTenPhong(document.get(TENPHONG).toString());
//                                            clsphong.setRatingPhong(document.getDouble(RATINGPHONG));
//                                            clsphong.setGiaThue(document.getDouble(GIATHUE));
//                                            // thêm vào arraylist
//                                            arrListLoc.add(clsphong);
//                                        }
//                                        adapterGridViewPhong = new AdapterGridViewPhong(getContext(),
//                                                R.layout.listview_item, arrListLoc);
//                                        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
//                                        adapterExpandableHeightGridView.setExpanded(true);
//
//                                    } else {
//                                        Log.d("TAG", "Error getting documents: ", task.getException());
//                                    }
//                                }
//                            });
//                }
////                Toast.makeText(getApplicationContext(),sp_TinhThanh.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        [END du lieu spinner Tinh thanh pho]


        // loc phòng theo loai phong
//        sp_LoaiPhong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
////                [đã chọn loại phòng và tỉnh thành]
//                if (sp_LoaiPhong.getSelectedItem() != TAT_CA_LOAI_PHONG) {
//                    db.collection(COLLECTION_PHONG)
//                            .whereEqualTo(MATINHTHANHPHO, sp_TinhThanh.getSelectedItem().toString())
//                            .whereEqualTo(MALOAIPHONG, sp_LoaiPhong.getSelectedItem().toString())
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    // nếu lấy thành công
//                                    if (task.isSuccessful()) {
//                                        ArrayList<Phong> arrListLoc = new ArrayList<Phong>();
//                                        // document duyệt dữ liệu trong task
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            Phong clsphong = new Phong();
//                                            // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
//                                            clsphong.setAnhDaiDien(document.get(ANHDAIDIEN).toString());
//                                            clsphong.setTenPhong(document.get(TENPHONG).toString());
//                                            clsphong.setRatingPhong(document.getDouble(RATINGPHONG));
//                                            clsphong.setGiaThue(document.getDouble(GIATHUE));
//                                            // thêm vào arraylist
//                                            arrListLoc.add(clsphong);
//                                        }
//                                        adapterGridViewPhong = new AdapterGridViewPhong(getContext(),
//                                                R.layout.listview_item, arrListLoc);
//                                        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
//                                        adapterExpandableHeightGridView.setExpanded(true);
//                                    }
//                                }
//                            });
//                }
//                // [chọn loại phòng mà chưa chọn tỉnh thành]
//                if (sp_LoaiPhong.getSelectedItem() != TAT_CA_LOAI_PHONG && sp_TinhThanh.getSelectedItem() == TAT_CA_TINH_THANH) {
//                    db.collection(COLLECTION_PHONG)
//                            .whereEqualTo(MALOAIPHONG, sp_LoaiPhong.getSelectedItem().toString())
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    // nếu lấy thành công
//                                    if (task.isSuccessful()) {
//                                        ArrayList<Phong> arrListLoc = new ArrayList<Phong>();
//                                        // document duyệt dữ liệu trong task
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            Phong clsphong = new Phong();
//                                            // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
//                                            clsphong.setAnhDaiDien(document.get(ANHDAIDIEN).toString());
//                                            clsphong.setTenPhong(document.get(TENPHONG).toString());
//                                            clsphong.setRatingPhong(document.getDouble(RATINGPHONG));
//                                            clsphong.setGiaThue(document.getDouble(GIATHUE));
//                                            // thêm vào arraylist
//                                            arrListLoc.add(clsphong);
//                                        }
//                                        adapterGridViewPhong = new AdapterGridViewPhong(getContext(),
//                                                R.layout.listview_item, arrListLoc);
//                                        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
//                                        adapterExpandableHeightGridView.setExpanded(true);
//                                    }
//                                }
//                            });
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
//        [END du lieu spinner LoaiPhong]

//        // lọc phòng theo tiêu chí
//        sp_TieuChi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                String item = adapterView.getItemAtPosition(i).toString();
//                if (item.equals("Rating") == true) {
//                    db.collection(COLLECTION_PHONG)
//                            .orderBy(RATINGPHONG, Query.Direction.DESCENDING)
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    // nếu lấy thành công
//                                    if (task.isSuccessful()) {
//                                        ArrayList<Phong> arrlistSortRating = new ArrayList<Phong>();
//                                        // document duyệt dữ liệu trong task
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            Phong clsphong = new Phong();
//                                            // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
//                                            clsphong.setAnhDaiDien(document.get(ANHDAIDIEN).toString());
//                                            clsphong.setTenPhong(document.get(TENPHONG).toString());
//                                            clsphong.setRatingPhong(document.getDouble(RATINGPHONG));
//                                            clsphong.setGiaThue(document.getDouble(GIATHUE));
//                                            // thêm vào arraylist
//                                            arrlistSortRating.add(clsphong);
//                                        }
//                                        adapterGridViewPhong = new AdapterGridViewPhong(getContext(), R.layout.listview_item, arrlistSortRating);
//                                        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
//                                        adapterExpandableHeightGridView.setExpanded(true);
//                                    }
//                                }
//                            });
//                }
//                if (item.equals("Giá tăng dần") == true) {
//                    db.collection(COLLECTION_PHONG)
//                            .orderBy(GIATHUE, Query.Direction.ASCENDING)
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    // nếu lấy thành công
//                                    if (task.isSuccessful()) {
//                                        ArrayList<Phong> arrlistSortAscending = new ArrayList<Phong>();
//                                        // document duyệt dữ liệu trong task
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            Phong clsphong = new Phong();
//                                            // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
//                                            clsphong.setAnhDaiDien(document.get(ANHDAIDIEN).toString());
//                                            clsphong.setTenPhong(document.get(TENPHONG).toString());
//                                            clsphong.setRatingPhong(document.getDouble(RATINGPHONG));
//                                            clsphong.setGiaThue(document.getDouble(GIATHUE));
//                                            // thêm vào arraylist
//                                            arrlistSortAscending.add(clsphong);
//                                        }
//                                        adapterGridViewPhong = new AdapterGridViewPhong(getContext(), R.layout.listview_item, arrlistSortAscending);
//                                        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
//                                        adapterExpandableHeightGridView.setExpanded(true);
//                                    }
//                                }
//                            });
//
//                }
//                if (item.equals("Giá giảm dần") == true) {
//                    db.collection(COLLECTION_PHONG)
//                            .orderBy(GIATHUE, Query.Direction.DESCENDING)
//                            .get()
//                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                @Override
//                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                    // nếu lấy thành công
//                                    if (task.isSuccessful()) {
//                                        ArrayList<Phong> arrlistSortDescending = new ArrayList<Phong>();
//                                        // document duyệt dữ liệu trong task
//                                        for (QueryDocumentSnapshot document : task.getResult()) {
//                                            Phong clsphong = new Phong();
//                                            // gán giá trị của field tenTaiKhoan trên firebase : document.get("my-field").toString(); vào các trường của class
//                                            clsphong.setAnhDaiDien(document.get(ANHDAIDIEN).toString());
//                                            clsphong.setTenPhong(document.get(TENPHONG).toString());
//                                            clsphong.setRatingPhong(document.getDouble(RATINGPHONG));
//                                            clsphong.setGiaThue(document.getDouble(GIATHUE));
//                                            // thêm vào arraylist
//                                            arrlistSortDescending.add(clsphong);
//                                        }
//                                        adapterGridViewPhong = new AdapterGridViewPhong(getContext(), R.layout.listview_item, arrlistSortDescending);
//                                        adapterExpandableHeightGridView.setAdapter(adapterGridViewPhong);
//                                        adapterExpandableHeightGridView.setExpanded(true);
//                                    }
//                                }
//                            });
//                }
////                Toast.makeText(getApplicationContext(), adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });
        return view;
    }
}