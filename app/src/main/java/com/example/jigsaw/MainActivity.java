package com.example.jigsaw;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static final int MSG_TIME_COUNTER = 1;

    private TextView mTimeTV;

    private ImageButton mFragmentIB00;
    private ImageButton mFragmentIB01;
    private ImageButton mFragmentIB02;
    private ImageButton mFragmentIB10;
    private ImageButton mFragmentIB11;
    private ImageButton mFragmentIB12;
    private ImageButton mFragmentIB20;
    private ImageButton mFragmentIB21;
    private ImageButton mFragmentIB22;

    private Button mRestartBTN;

    private int mTime;

    private int[] mFragmentResId = {R.mipmap.img_xiaoxiong_00x00, R.mipmap.img_xiaoxiong_00x01, R.mipmap.img_xiaoxiong_00x02,
            R.mipmap.img_xiaoxiong_01x00, R.mipmap.img_xiaoxiong_01x01, R.mipmap.img_xiaoxiong_01x02,
            R.mipmap.img_xiaoxiong_02x00, R.mipmap.img_xiaoxiong_02x01, R.mipmap.img_xiaoxiong_02x02};
    private int[] mFragmentIndex = new int[mFragmentResId.length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        shuffle();

        mHandler.sendEmptyMessageDelayed(MSG_TIME_COUNTER, 1000);
    }

    private void shuffle() {
        for (int i = 0; i < mFragmentResId.length; i++) {
            mFragmentIndex[i] = i;
        }

        int r1, r2;
        for (int i = 0; i < 20; i++) {
            r1 = (int) (Math.random() * (mFragmentIndex.length - 1));
            do {
                r2 = (int) (Math.random() * (mFragmentIndex.length - 1));
            } while (r1 == r2);
            swap(r1, r2);
        }

        mFragmentIB00.setImageResource(mFragmentResId[mFragmentIndex[0]]);
        mFragmentIB01.setImageResource(mFragmentResId[mFragmentIndex[1]]);
        mFragmentIB02.setImageResource(mFragmentResId[mFragmentIndex[2]]);
        mFragmentIB10.setImageResource(mFragmentResId[mFragmentIndex[3]]);
        mFragmentIB11.setImageResource(mFragmentResId[mFragmentIndex[4]]);
        mFragmentIB12.setImageResource(mFragmentResId[mFragmentIndex[5]]);
        mFragmentIB20.setImageResource(mFragmentResId[mFragmentIndex[6]]);
        mFragmentIB21.setImageResource(mFragmentResId[mFragmentIndex[7]]);
        mFragmentIB22.setImageResource(mFragmentResId[mFragmentIndex[8]]);
    }

    private void swap(int r1, int r2) {
        int tmp = mFragmentIndex[r1];
        mFragmentIndex[r1] = mFragmentIndex[r2];
        mFragmentIndex[r2] = tmp;
    }

    private void initView() {
        mTimeTV = findViewById(R.id.main_time_tv);
        mFragmentIB00 = findViewById(R.id.main_fragment_00x00_ib);
        mFragmentIB01 = findViewById(R.id.main_fragment_00x01_ib);
        mFragmentIB02 = findViewById(R.id.main_fragment_00x02_ib);
        mFragmentIB10 = findViewById(R.id.main_fragment_01x00_ib);
        mFragmentIB11 = findViewById(R.id.main_fragment_01x01_ib);
        mFragmentIB12 = findViewById(R.id.main_fragment_01x02_ib);
        mFragmentIB20 = findViewById(R.id.main_fragment_02x00_ib);
        mFragmentIB21 = findViewById(R.id.main_fragment_02x01_ib);
        mFragmentIB22 = findViewById(R.id.main_fragment_02x02_ib);
        mRestartBTN = findViewById(R.id.main_restart_btn);
    }

    public void onClick(View view) {

    }

    public void restart(View view) {
        shuffle();

        mHandler.removeMessages(MSG_TIME_COUNTER);
        mTime = 0;
        mTimeTV.setText("时间: " + mTime + " 秒");
        mHandler.sendEmptyMessageDelayed(MSG_TIME_COUNTER, 1000);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == MSG_TIME_COUNTER) {
                mTime += 1;
                mTimeTV.setText("时间: " + mTime + " 秒");
                mHandler.sendEmptyMessageDelayed(MSG_TIME_COUNTER, 1000);
            }
        }
    };
}