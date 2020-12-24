package jp.co.abs.jankenver2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class StartActivity extends AppCompatActivity {
    private static final String TAG = StartActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);
    }

    public void onClickStartGame(View view) {
        // データ初期化
        SharedPreferences countPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = countPref.edit();
        editor.clear();
        editor.commit();

        // プルダウンリストの値を勝負回数として保存
        Spinner fightCount = (Spinner) this.findViewById(R.id.count);
        String fightCS = (String) fightCount.getSelectedItem();
        int fightInt = Integer.parseInt(fightCS);
        Log.i(TAG, "Fight Count:" + fightInt);
        editor.putInt(PrefConst.KEY_MAX_FIGHT, fightInt);
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
