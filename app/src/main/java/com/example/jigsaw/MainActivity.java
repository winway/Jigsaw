package com.example.jigsaw;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

    private int mBlankIndex = 8;
    private int mBlankViewId = R.id.main_fragment_02x02_ib;

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
        switch (view.getId()) {
            case R.id.main_fragment_00x00_ib:
                move(R.id.main_fragment_00x00_ib, 0);
                break;
            case R.id.main_fragment_00x01_ib:
                move(R.id.main_fragment_00x01_ib, 1);
                break;
            case R.id.main_fragment_00x02_ib:
                move(R.id.main_fragment_00x02_ib, 2);
                break;
            case R.id.main_fragment_01x00_ib:
                move(R.id.main_fragment_01x00_ib, 3);
                break;
            case R.id.main_fragment_01x01_ib:
                move(R.id.main_fragment_01x01_ib, 4);
                break;
            case R.id.main_fragment_01x02_ib:
                move(R.id.main_fragment_01x02_ib, 5);
                break;
            case R.id.main_fragment_02x00_ib:
                move(R.id.main_fragment_02x00_ib, 6);
                break;
            case R.id.main_fragment_02x01_ib:
                move(R.id.main_fragment_02x01_ib, 7);
                break;
            case R.id.main_fragment_02x02_ib:
                move(R.id.main_fragment_02x02_ib, 8);
                break;
        }
    }

    private void move(int resId, int index) {
        int indexX = index / 3;
        int indexY = index % 3;
        int blankIndexX = mBlankIndex / 3;
        int blankIndexY = mBlankIndex % 3;

        int diffX = Math.abs(indexX - blankIndexX);
        int diffY = Math.abs(indexY - blankIndexY);
        if ((diffX == 0 && diffY == 1) || (diffX == 1 && diffY == 0)) {
            ImageButton clickIB = findViewById(resId);
            ImageButton blankIB = findViewById(mBlankViewId);

            clickIB.setVisibility(View.INVISIBLE);
            blankIB.setImageResource(mFragmentResId[mFragmentIndex[index]]);
            blankIB.setVisibility(View.VISIBLE);

            swap(index, mBlankIndex);

            mBlankIndex = index;
            mBlankViewId = resId;

            isComplete();
        }
    }

    private void isComplete() {
        boolean isComplete = true;
        for (int i = 0; i < mFragmentIndex.length; i++) {
            if (mFragmentIndex[i] != i) {
                isComplete = false;
                break;
            }
        }

        if (isComplete) {
            mHandler.removeMessages(MSG_TIME_COUNTER);

            mFragmentIB00.setClickable(false);
            mFragmentIB01.setClickable(false);
            mFragmentIB02.setClickable(false);
            mFragmentIB10.setClickable(false);
            mFragmentIB11.setClickable(false);
            mFragmentIB12.setClickable(false);
            mFragmentIB20.setClickable(false);
            mFragmentIB21.setClickable(false);
            mFragmentIB22.setClickable(false);

            mFragmentIB22.setImageResource(mFragmentResId[8]);
            mFragmentIB22.setVisibility(View.VISIBLE);

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Done! time: " + mTime + "s")
                    .setPositiveButton("OK", null);
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }

    public void restart(View view) {
        mFragmentIB00.setClickable(true);
        mFragmentIB01.setClickable(true);
        mFragmentIB02.setClickable(true);
        mFragmentIB10.setClickable(true);
        mFragmentIB11.setClickable(true);
        mFragmentIB12.setClickable(true);
        mFragmentIB20.setClickable(true);
        mFragmentIB21.setClickable(true);
        mFragmentIB22.setClickable(true);

        ImageButton blankIB = findViewById(mBlankViewId);
        blankIB.setVisibility(View.VISIBLE);

        mFragmentIB22.setVisibility(View.INVISIBLE);

        mBlankIndex = 8;
        mBlankViewId = R.id.main_fragment_02x02_ib;

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