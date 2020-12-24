package jp.co.abs.jankenver2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class ResultActivity extends AppCompatActivity {
    private static final String TAG = ResultActivity.class.getSimpleName();

    private static final String INTENT_PARAM_USER_HAND = "UserHand";
    private static final int GU = 0;
    private static final int CHOKI = 1;
    private static final int PA = 2;
    //変数定義
    private ImageView mComputerHandView; //コンピュータの手画像
    private ImageView mUserHandView; //自分の手画像
    private long mSeed = System.currentTimeMillis();
    private Random mRnd = new Random(mSeed);

    public static Intent createStartActivityIntent(Context context, int userHand) {
        Intent intent = new Intent(context,ResultActivity.class);
        intent.putExtra(INTENT_PARAM_USER_HAND, userHand);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences countPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = countPref.edit();

        int fightCount = countPref.getInt(PrefConst.KEY_FIGHT_COUNT, 0); //勝負した回数
        int winCount = countPref.getInt(PrefConst.KEY_WIN_COUNT, 0);     //勝利回数
        int loseCount = countPref.getInt(PrefConst.KEY_LOSE_COUNT, 0);   //敗北回数
        int drawCount = countPref.getInt(PrefConst.KEY_DRAW_COUNT, 0);   //引き分け回数
        int maxFight = countPref.getInt(PrefConst.KEY_MAX_FIGHT, 0);      //選択した勝負回数

        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_layout);

        //相手の選んだ手を表示
        mComputerHandView = findViewById(R.id.com_hand);
        int comHand = mRnd.nextInt(3);//乱数
        Log.i(TAG, "Computer Hand:" + comHand);
        switch (comHand) {
            case GU:
                mComputerHandView.setImageResource(R.drawable.j_gu02);
                break;
            case CHOKI:
                mComputerHandView.setImageResource(R.drawable.j_ch02);
                break;
            case PA:
                mComputerHandView.setImageResource(R.drawable.j_pa02);
                break;
        }

        // 自分の選んだ手を表示
        mUserHandView = findViewById(R.id.my_hand);
        Intent intent = getIntent();
        int userHand = intent.getIntExtra(INTENT_PARAM_USER_HAND, 0);
        Log.i(TAG, "User Hand:" + userHand);
        switch (userHand) {
            case GU:
                mUserHandView.setImageResource(R.drawable.j_gu02);
                break;
            case CHOKI:
                mUserHandView.setImageResource(R.drawable.j_ch02);
                break;
            case PA:
                mUserHandView.setImageResource(R.drawable.j_pa02);
                break;
        }

        // 勝負結果判定　結果データ保存
        ImageView resultImage = findViewById(R.id.result_pic);
        TextView resultText = findViewById(R.id.result_text);
        if (comHand == userHand) {
            //あいこ
            editor.putInt(PrefConst.KEY_DRAW_COUNT, drawCount + 1);
            editor.commit();
            resultImage.setImageResource(R.drawable.draw);
            resultText.setText(R.string.draw);
        } else if (userHand == comHand + 1 || userHand + 2 == comHand) {
            //負け
            editor.putInt(PrefConst.KEY_LOSE_COUNT, loseCount + 1);
            editor.commit();
            resultImage.setImageResource(R.drawable.lose);
            resultText.setText(R.string.lose);
        } else {
            //勝ち
            editor.putInt(PrefConst.KEY_WIN_COUNT, winCount + 1);
            editor.commit();
            resultImage.setImageResource(R.drawable.win);
            resultText.setText(R.string.win);
        }
        // 勝負回数に応じた画面遷移ボタン　
        Button returnButton = findViewById(R.id.Return);
        editor.putInt(PrefConst.KEY_FIGHT_COUNT, fightCount + 1);
        editor.commit();
        fightCount = countPref.getInt(PrefConst.KEY_FIGHT_COUNT, 0);
        if (maxFight > fightCount) {
            returnButton.setText(R.string.next);
        } else if (maxFight == fightCount) {
            returnButton.setText(R.string.result);
        }
    }

    public void onClickResult(View view) {
        SharedPreferences countPref = PreferenceManager.getDefaultSharedPreferences(this);

        // 勝負回数に応じた画面へ遷移
        int fightCount = countPref.getInt(PrefConst.KEY_FIGHT_COUNT, 0);
        int maxFight = countPref.getInt(PrefConst.KEY_MAX_FIGHT, 0);
        if (maxFight == fightCount) {
            Intent intent = new Intent(this, EndActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}