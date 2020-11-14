package com.chuyende.hotelbookingappofuser.GiaoDien;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageButton;

import com.chuyende.hotelbookingappofuser.Adapter.DichvuAdapter;
import com.chuyende.hotelbookingappofuser.Adapter.PhotoAdapter;
import com.chuyende.hotelbookingappofuser.Model.Dichvu;
import com.chuyende.hotelbookingappofuser.Model.Photo;
import com.chuyende.hotelbookingappofuser.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class Manhinhchitiet extends AppCompatActivity {

     ViewPager viewPager, viewPagerdv;
     ImageButton ibHeart;
     CircleIndicator circleIndicator, circleIndicatordv;
     PhotoAdapter photoAdapter;
     DichvuAdapter dichvuAdapter;
     List<Dichvu> dvListDichvu;
     List<Photo> mListPhoto;
     Timer mTimer;
     Boolean iconyeuthich = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinhchitiet);


        setControl();

        mListPhoto = getListPhoto();
        photoAdapter = new PhotoAdapter(this, mListPhoto);
        viewPager.setAdapter(photoAdapter);

        circleIndicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(circleIndicator.getDataSetObserver());

        //dich vu:
        dvListDichvu = getDvListDichvu();
        dichvuAdapter = new DichvuAdapter(this, dvListDichvu);
        viewPagerdv.setAdapter(dichvuAdapter);

        circleIndicatordv.setViewPager(viewPagerdv);
        dichvuAdapter.registerDataSetObserver(circleIndicatordv.getDataSetObserver());

        //ham auto hinhanh
        autoSlideImages();

        //phần icon yêu thích
        ibHeart.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
        ibHeart.setOnClickListener(Heart);



    }



    //hàm  click để chọn yêu thích
    View.OnClickListener Heart = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (iconyeuthich){
                view.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
            }
            else {
                view.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
            }

        }
    };

    private void setControl() {
        viewPager = findViewById(R.id.viewpager);
        circleIndicator = findViewById(R.id.circle_indicator);
        ibHeart = findViewById(R.id.ibHeart);
        viewPagerdv = findViewById(R.id.viewpager2);
        circleIndicatordv = findViewById(R.id.circle_indicator2);
    }


    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.hinh1));
        list.add(new Photo(R.drawable.hinh2));
        list.add(new Photo(R.drawable.hinh3));
        list.add(new Photo(R.drawable.hinh4));

        return list;
    }
    private List<Dichvu> getDvListDichvu(){
        List<Dichvu> list = new ArrayList<>();
        list.add(new Dichvu(R.drawable.ic_baseline_live_tv_24));
        list.add(new Dichvu(R.drawable.ic_baseline_local_cafe_24));
        list.add(new Dichvu(R.drawable.ic_baseline_sports_handball_24));
        list.add(new Dichvu(R.drawable.ic_baseline_restaurant_24));
        list.add(new Dichvu(R.drawable.ic_baseline_rowing_24));
        list.add(new Dichvu(R.drawable.ic_baseline_shopping_cart_24));

        return list;
    }

    private void autoSlideImages(){
        if(mListPhoto == null || mListPhoto.isEmpty() || viewPager == null){
            return;
        }
        //Init timer
        if(mTimer==null){
            mTimer = new Timer();
        }
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        int currentItem =  viewPager.getCurrentItem();
                        int totalItem = mListPhoto.size() - 1;
                        if(currentItem<totalItem){
                            currentItem++;
                            viewPager.setCurrentItem(currentItem);
                        } else {
                            viewPager.setCurrentItem(0);
                        }
                    }
                });
            }
        },  500, 5000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTimer !=null){
            mTimer.cancel();
            mTimer = null;
        }
    }
}