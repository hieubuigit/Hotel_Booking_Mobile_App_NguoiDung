package com.chuyende.hotelbookingappofuser.firebase_models;

import android.util.Log;

import androidx.annotation.Nullable;

import com.chuyende.hotelbookingappofuser.data_models.TinhThanhPho;
import com.chuyende.hotelbookingappofuser.interfaces.ListTinhThanhPho;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FBDaTaTinhThanhPho {
    FirebaseFirestore db;

    public static final String COLLECTION_TINHTHANHPHO = "TinhThanhPho";
    public static final String FIELD_MATINHTHANHPHO = "maTinhThanhPho";
    public static final String FIELD_TENTINHTHANHPHO = "tinhThanhPho";

    public FBDaTaTinhThanhPho() {
        db = FirebaseFirestore.getInstance();
    }

    public void layDuLieuTinhThanhPho(ListTinhThanhPho listTinhThanhPho) {

        db.collection(COLLECTION_TINHTHANHPHO).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.d("Error-DataTinhThanhPho", " => " + error.getMessage());
                }
                if (value != null) {
                    ArrayList<TinhThanhPho> arrlstTinhThanhPho = new ArrayList<TinhThanhPho>();
                    TinhThanhPho tinhThanhPho;
                    for (QueryDocumentSnapshot document : value) {
                        tinhThanhPho = new TinhThanhPho(document.getString(FIELD_MATINHTHANHPHO), document.getString(FIELD_TENTINHTHANHPHO));
                        arrlstTinhThanhPho.add(tinhThanhPho);
                    }
                    listTinhThanhPho.interfaceListTinhThanhPho(arrlstTinhThanhPho);
                }
            }
        });
    }
}
