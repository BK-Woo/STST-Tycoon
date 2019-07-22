package com.example.stst_tycoon;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // 중복 터치 방지를 위한 인자
    CountDownTimer timer;
    boolean isStart = false;

    // 텍스트 뷰
    TextView setStart, setHow, setExit;

    // 효과음
    static MediaPlayer sound_click;
    static MediaPlayer sound_start;

    // 액티비티 실행 시 수행 될 내용
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  전체 화면 적용
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // 효과음 적용
        sound_click = MediaPlayer.create(this, R.raw.click_button);
        sound_start = MediaPlayer.create(this, R.raw.start);

        // 시작
        setStart = findViewById(R.id.main_start);
        setStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    sound_start.start();

                    Intent i = new Intent(MainActivity.this, GameActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        // 게임 설명
        setHow = findViewById(R.id.main_how);
        setHow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                    Intent i = new Intent(MainActivity.this, HowActivity.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
            }
        });

        // 종료
        setExit = findViewById(R.id.main_exit);
        setExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isPlaying(sound_click);
                finish();
            }
        });
    }

    // 효과음 재생 확인
    private void isPlaying(MediaPlayer sound) {
        // 소리가 묻힐 일이 없도록 재생 중일 경우 다시 재생
        if(sound.isPlaying()) sound.pause();
        sound.seekTo(0);
        sound.start();
    }
}
