package jp.co.abs.jankenver2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int GU = 0;
    private static final int CHOKI = 1;
    private static final int PA = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
    }

    public void onClickUserHand(View view) {

        int userHand = GU;
        switch (view.getId()) {
            case R.id.gu:
                userHand = GU;
                break;
            case R.id.ch:
                userHand = CHOKI;
                break;
            case R.id.pa:
                userHand = PA;
                break;
        }
        Log.i(TAG,"Choiced UserHand:" + userHand);
        startActivity(ResultActivity.createStartActivityIntent(this,userHand));
        finish();
    }
}