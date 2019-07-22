package com.example.stst_tycoon;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

public class HowActivity extends AppCompatActivity {
    // 중복 터치 방지를 위한 인자
    CountDownTimer timer;
    boolean isStart = false;

    final static int num = 7; // 설명 장면 개수
    static int num_now;

    ImageView how[] = new ImageView[num]; // 설명 이미지
    int howId[] = {R.id.howto_01, R.id.howto_02, R.id.howto_03, R.id.howto_04, R.id.howto_05, R.id.howto_06, R.id.howto_07}; // 아이디를 통한 배열 사용

    // 효과음
    static MediaPlayer sound_click;

    // 액티비티 실행 시 수행 될 내용
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  전체 화면 적용
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_how);

        // 초기화
        Init();
    }

    // 초기화
    public void Init() {
        num_now = 0;

        for(int i = 0; i < num; i++) {
            how[i] = findViewById(howId[i]);
            how[i].setVisibility(View.INVISIBLE);
        }

        how[num_now].setVisibility(View.VISIBLE);

        // 효과음 적용
        sound_click = MediaPlayer.create(this, R.raw.click_button);
    }

    // 이전 이미지
    public void setPrevious(View view) {
        if(num_now != 0) {
            isPlaying(sound_click);
            how[num_now].setVisibility(View.INVISIBLE);
            how[num_now - 1].setVisibility(View.VISIBLE);
            num_now--;
        }
        else {
            if(!isStart) {
                isStart = true;
                timer = new CountDownTimer(2 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        isStart = false;
                    }
                }.start();

                isPlaying(sound_click);

                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        }
    }

    // 다음 페이지
    public void setNext(View view) {
        if(num_now != num - 1) {
            isPlaying(sound_click);
            how[num_now].setVisibility(View.INVISIBLE);
            how[num_now + 1].setVisibility(View.VISIBLE);
            num_now++;
        }
        else {
            if(!isStart) {
                isStart = true;
                timer = new CountDownTimer(2 * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        isStart = false;
                    }
                }.start();

                isPlaying(sound_click);

                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        }
    }

    // 종료
    public void toTitle(View view) {
        if(!isStart) {
            isStart = true;
            timer = new CountDownTimer(2 * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    isStart = false;
                }
            }.start();

            isPlaying(sound_click);

            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    // 효과음 재생 확인
    private void isPlaying(MediaPlayer sound) {
        // 소리가 묻힐 일이 없도록 재생 중일 경우 다시 재생
        if(sound.isPlaying()) sound.pause();
        sound.seekTo(0);
        sound.start();
    }
}