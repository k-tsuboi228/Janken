package jp.co.abs.jankenver2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EndActivity extends AppCompatActivity {
    private static final String TAG = StartActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end_layout);
        SharedPreferences countPref = PreferenceManager.getDefaultSharedPreferences(this);

        int winCount = countPref.getInt(PrefConst.KEY_WIN_COUNT, 0);
        int loseCount = countPref.getInt(PrefConst.KEY_LOSE_COUNT, 0);
        int drawCount = countPref.getInt(PrefConst.KEY_DRAW_COUNT, 0);
        Log.i(TAG,"Win Count:" + winCount);
        Log.i(TAG,"Lose Count:" + loseCount);
        Log.i(TAG,"Draw Count:" + drawCount);

        ImageView resultView = findViewById(R.id.result1_pic);
        TextView resultText = findViewById(R.id.result1_text);
        TextView resultCount = findViewById(R.id.result_count);
        resultCount.setText(winCount + "勝" + loseCount + "負" + drawCount + "引き分け");

        //　勝敗に応じた結果を出力
        if(winCount > loseCount) {
            resultText.setText(R.string.win);
            resultView.setImageResource(R.drawable.win);
        }else if(winCount < loseCount) {
            resultText.setText(R.string.lose);
            resultView.setImageResource(R.drawable.lose);
        }else{
            resultText.setText(R.string.draw);
            resultView.setImageResource(R.drawable.draw);
        }
    }

    public void onClickReturnStartActivity(View view){
        Intent intent = new Intent(this, StartActivity.class);
        startActivity(intent);
        finish();
    }
}
