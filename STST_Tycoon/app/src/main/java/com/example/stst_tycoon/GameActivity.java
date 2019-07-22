package com.example.stst_tycoon;

import android.graphics.Point;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;

public class GameActivity extends AppCompatActivity {
    // 인터페이스 관련 인자
    static int width, height;  // 기기의 가로, 세로 길이 측정
    static final int num_character = 4;  // 캐릭터 개수
    static final int  num_stick = 5; // 꼬치 개수
    static final int num_menu = 3; // 메뉴 개수
    static int stick; // 선택된 꼬치 번호
    static int score, score_total, reputation; // 점수 및 평점
    static int price_ricecake, price_sausage, price_ketchup, price_mustard; // 각 재료의 가격
    static String txt_score, txt_reputation, txt_gameover_score;
    static float margin; // 판매 마진
    static boolean isMenu; // 메뉴판 열림 확인
    static boolean isSauce; // 소스판 열림 확인
    static boolean isGame; // 게임 진행 확인
    static long left; // 제한 시간
    CountDownTimer timer_left; // 제한 시간 타이머

    // 캐릭터 관련 인자
    static float move; // 캐릭터 이동 거리
    Random r = new Random(); // 랜덤
    static long time[] = new long[num_character]; // 캐릭터 등장 시간
    static int time_exit[] = new int[num_character]; // 캐릭터 퇴장 시간
    CountDownTimer timer_spawn[] = new CountDownTimer[num_character]; // 캐릭터 등장 타이머
    CountDownTimer timer_exit[] = new CountDownTimer[num_character]; // 캐릭터 퇴장 타이머
    static int guest_number[] = new int[num_character]; // 각 자리 별 캐릭터 번호 확인
    static boolean isAccupied[] = new boolean[num_character]; // 자리 점유 여부 확인
    static boolean isSet[] = new boolean[num_character]; // 꼬치 전달 가능 여부 확인

    // 본 게임 관련 인자
    static int maxcount;
    static int guest_count[] = new int[num_character]; // 소떡 요구량
    static int count_ricecake[] = new int[num_stick]; // 떡 개수 확인
    static int count_sausage[] = new int[num_stick]; // 소시지 개수 확인
    static float move_menu[] = new float[num_stick]; // 떡, 소시지 이동 거리
    static boolean canSauce[] = new boolean[num_stick]; // 소스 사용 가능 여부 확인
    static boolean isKetchup[] = new boolean[num_stick]; // 케첩 사용 확인
    static boolean isMustard[] = new boolean[num_stick]; // 머스타드 사용 확인
    static int time_menu[] = new int[num_stick]; //소스 사용 가능 시간
    CountDownTimer timer_menu[] = new CountDownTimer[num_stick]; // 익히기 타이머

    // 텍스트 뷰
    TextView interface_score, interface_time, interface_reputation, gameover_score; // 점수, 시간 및 평점
    TextView interface_ricecake, interface_sausage, interface_ketchup, interface_mustard; // 메뉴 가격
    TextView guest_time[] = new TextView[num_character]; // 캐릭터 퇴장 시간
    TextView guest_menu[] = new TextView[num_character]; // 캐릭터 소떡 요구량
    TextView menu_time[] = new TextView[num_stick]; // 소스 사용 가능 시간
    int guest_timeId[] = {R.id.Guest_Blue_Timer, R.id.Guest_Green_Timer, R.id.Guest_Red_Timer, R.id.Guest_Yellow_Timer};
    int guest_menuId[] = {R.id.Guest_Blue_Menu, R.id.Guest_Green_Menu, R.id.Guest_Red_Menu, R.id.Guest_Yellow_Menu};
    int menu_timeId[] = {R.id.Timer_01, R.id.Timer_02, R.id.Timer_03, R.id.Timer_04, R.id.Timer_05};

    // 이미지 뷰
    ImageView guest[] = new ImageView[num_character]; // 캐릭터
    ImageView arrow[] = new ImageView[num_character]; // 화살표
    ImageView ricecake[] = new ImageView[num_stick * num_menu * 2]; // 떡
    ImageView sausage[] = new ImageView[num_stick * num_menu * 2]; // 소시지
    ImageView ketchup[] = new ImageView[num_stick]; // 케첩
    ImageView mustard[] = new ImageView[num_stick]; // 머스타드
    AnimationDrawable frameAnimation[] = new AnimationDrawable[num_character];  // 애니메이션 사용 (캐릭터)
    AnimationDrawable frameAnimation_arrow[] = new AnimationDrawable[num_character];  // 애니메이션 사용 (화살표)
    int guestId[] = {R.id.Guest_Blue, R.id.Guest_Green, R.id.Guest_Red, R.id.Guest_Yellow}; // 아이디를 통한 배열 사용
    int arrowId[] = {R.id.Guest_Blue_Arrow, R.id.Guest_Green_Arrow, R.id.Guest_Red_Arrow, R.id.Guest_Yellow_Arrow};
    int ricecakeId[] = {R.id.ricecake_01, R.id.ricecake_02, R.id.ricecake_03, R.id.ricecake_04, R.id.ricecake_05, R.id.ricecake_06, R.id.ricecake_07, R.id.ricecake_08, R.id.ricecake_09, R.id.ricecake_10,
                        R.id.ricecake_11, R.id.ricecake_12, R.id.ricecake_13, R.id.ricecake_14, R.id.ricecake_15, R.id.ricecake_16, R.id.ricecake_17, R.id.ricecake_18, R.id.ricecake_19, R.id.ricecake_20,
                        R.id.ricecake_21, R.id.ricecake_22, R.id.ricecake_23, R.id.ricecake_24, R.id.ricecake_25, R.id.ricecake_26, R.id.ricecake_27, R.id.ricecake_28, R.id.ricecake_29, R.id.ricecake_30};
    int sausageId[] = {R.id.sausage_01, R.id.sausage_02, R.id.sausage_03, R.id.sausage_04, R.id.sausage_05, R.id.sausage_06, R.id.sausage_07, R.id.sausage_08, R.id.sausage_09, R.id.sausage_10,
                       R.id.sausage_11, R.id.sausage_12, R.id.sausage_13, R.id.sausage_14, R.id.sausage_15, R.id.sausage_16, R.id.sausage_17, R.id.sausage_18, R.id.sausage_19, R.id.sausage_20,
                       R.id.sausage_21, R.id.sausage_22, R.id.sausage_23, R.id.sausage_24, R.id.sausage_25, R.id.sausage_26, R.id.sausage_27, R.id.sausage_28, R.id.sausage_29, R.id.sausage_30};
    int ketchupId[] = {R.id.ketchup_01, R.id.ketchup_02, R.id.ketchup_03, R.id.ketchup_04, R.id.ketchup_05};
    int mustardId[] = {R.id.mustard_01, R.id.mustard_02, R.id.mustard_03, R.id.mustard_04, R.id.mustard_05};

    // 레이아웃 뷰
    RelativeLayout menu, sauce, gameover;

    // 소리
    static MediaPlayer sound_bgm; // 배경음 (bgm)
    static MediaPlayer sound_click_button; // 일반 버튼 클릭 효과음 (click_button)
    static MediaPlayer sound_click_trash; // 폐기 버튼 클릭 효과음 (click_trash)
    static MediaPlayer sound_click_menu; // 메뉴 버튼 클릭 효과음 (click_menu)
    static MediaPlayer sound_click_sauce; // 메뉴 버튼 클릭 효과음 (click_sauce)
    static MediaPlayer sound_click_stick; // 스틱 클릭 효과음 (click_stick)
    static MediaPlayer sound_guest_success; // 캐릭터 성공 효과음 (success)
    static MediaPlayer sound_guest_fail; // 캐릭터 실패 효과음 (fail)
    static MediaPlayer sound_sauce; // 소스 사용 가능 효과음 (sauce)
    static MediaPlayer sound_gameover; // 게임 오버 효과음 (gameover)
    static MediaPlayer sound_timer; // 제한 시간 효과음 (timer)

    // 액티비티 실행 시 수행 될 내용
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //  전체 화면 적용
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        // 기기의 가로, 세로 길이 측정
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        // 초기화
        ViewCreate();
        Init();

        // 배경음 재생
        isPlaying(sound_bgm);
        sound_bgm.setLooping(true);

        // 제한 시간 재생
        timer_left = new CountDownTimer(left * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                left = millisUntilFinished;
                int minutes = (int) (left / 1000) / 60;
                int seconds = (int) (left / 1000) % 60;
                String timeLeft = String.format(Locale.getDefault(), "%d:%02d", minutes, seconds);
                interface_time.setText(timeLeft);
            }

            @Override
            public void onFinish() {
                if(isGame)
                    isPlaying(sound_timer);
                    Gameover();
            }
        }.start();
    }

    // 버튼 및 이미지 정의
    public void ViewCreate() {
        // 텍스트 뷰 정의
        interface_score = findViewById(R.id.text_score);
        interface_time = findViewById(R.id.text_time);
        interface_reputation = findViewById(R.id.text_reputation);
        gameover_score = findViewById(R.id.Gameover_Score);
        interface_ricecake = findViewById(R.id.price_ricacake);
        interface_sausage = findViewById(R.id.price_sausage);
        interface_ketchup = findViewById(R.id.price_ketchup);
        interface_mustard = findViewById(R.id.price_mustard);

        // 캐릭터 정의
        for(int i = 0; i < num_character; i++) {
            guest[i] = findViewById(guestId[i]);
            arrow[i] = findViewById(arrowId[i]);
            guest_time[i] = findViewById(guest_timeId[i]);
            guest_menu[i] = findViewById(guest_menuId[i]);
            guest[i].setVisibility(View.INVISIBLE);
            arrow[i].setVisibility(View.INVISIBLE);
            guest_time[i].setVisibility(View.INVISIBLE);
            guest_menu[i].setVisibility(View.INVISIBLE);
            frameAnimation[i] = (AnimationDrawable) guest[i].getDrawable();
            frameAnimation_arrow[i] = (AnimationDrawable) arrow[i].getDrawable();
        }

        // 사이드 바 (메뉴, 소스) 정의
        menu = findViewById(R.id.InterfaceGame_Side_Left);
        menu.setVisibility(View.INVISIBLE);
        menu.animate().setStartDelay(0).setInterpolator(null).translationX(-width).setDuration(0).withEndAction(new Runnable() {
            @Override
            public void run() {
                menu.post(new Runnable() {
                    @Override
                    public void run() {
                        menu.setVisibility(View.VISIBLE);
                        isMenu = false;
                        }
                });
            }
        });

        sauce = findViewById(R.id.InterfaceGame_Side_Right);
        sauce.setVisibility(View.INVISIBLE);
        sauce.animate().setStartDelay(0).setInterpolator(null).translationX(width).setDuration(0).withEndAction(new Runnable() {
            @Override
            public void run() {
                sauce.post(new Runnable() {
                    @Override
                    public void run() {
                        sauce.setVisibility(View.VISIBLE);
                        isSauce = false;
                    }
                });
            }
        });

        // 떡, 소시지 정의
        for(int i = 0; i < num_stick * num_menu * 2; i++) {
            ricecake[i] = findViewById(ricecakeId[i]);
            sausage[i] = findViewById(sausageId[i]);
            ricecake[i].setVisibility(View.INVISIBLE);
            sausage[i].setVisibility(View.INVISIBLE);
        }

        // 소스, 소스 사용 가능 시간 정의
        for(int i = 0; i < num_stick; i++) {
            ketchup[i] = findViewById(ketchupId[i]);
            mustard[i] = findViewById(mustardId[i]);
            menu_time[i] = findViewById(menu_timeId[i]);
            ketchup[i].setVisibility(View.INVISIBLE);
            mustard[i].setVisibility(View.INVISIBLE);
            menu_time[i].setVisibility(View.INVISIBLE);
        }

        // 게임 오버 정의
        gameover = findViewById(R.id.Gameover);
        gameover.setVisibility(View.INVISIBLE);
        gameover.animate().setStartDelay(0).setInterpolator(null).translationY(-height).setDuration(0).start();
    }

    // 초기화 설정
    public void Init() {
        // 인자 정의
        score = 12000;
        score_total = 0;
        left = 60 * 3;
        reputation = 30;
        move = 0;
        stick = 2;
        maxcount = 4;
        price_ricecake = 300;
        price_sausage = 400;
        price_ketchup = 300;
        price_mustard = 400;
        isGame = true;

        // 텍스트 뷰 정의
        txt_score = String.format(Locale.getDefault(), "%06d", score);
        txt_reputation = String.format(Locale.getDefault(), "%03d", reputation);
        txt_gameover_score = String.format(Locale.getDefault(), "Score: %08d", score_total);
        interface_score.setText(txt_score);
        interface_reputation.setText(txt_reputation);
        gameover_score.setText(txt_gameover_score);
        interface_ricecake.setText(String.valueOf(price_ricecake));
        interface_sausage.setText(String.valueOf(price_sausage));
        interface_ketchup.setText(String.valueOf(price_ketchup));
        interface_mustard.setText(String.valueOf(price_mustard));

        // 캐릭터 정의
        for(int i = 0; i < num_character; i++) {
            isAccupied[i] = false;
            isSet[i] = false;
            if(timer_spawn[i] != null)
                timer_spawn[i].cancel();
            if(timer_exit[i] != null)
                timer_exit[i].cancel();
            setTime(i);
        }

        // 메뉴 정의
        for(int i = 0; i < num_stick; i++) {
            move_menu[i] = 3 * (height / 10);
            count_ricecake[i] = 0;
            count_sausage[i] = 0;
            time_menu[i] = 5;
            isKetchup[i] = false;
            isMustard[i] = false;
            if(timer_menu[i] != null)
                timer_menu[i].cancel();
        }

        // 소리 정의
        sound_bgm = MediaPlayer.create(this, R.raw.bgm);
        sound_click_button = MediaPlayer.create(this, R.raw.click_button);
        sound_click_trash = MediaPlayer.create(this, R.raw.click_trash);
        sound_click_menu = MediaPlayer.create(this, R.raw.click_menu);
        sound_click_sauce = MediaPlayer.create(this, R.raw.click_sauce);
        sound_click_stick = MediaPlayer.create(this, R.raw.click_stick);
        sound_guest_success = MediaPlayer.create(this, R.raw.success);
        sound_guest_fail = MediaPlayer.create(this, R.raw.fail);
        sound_sauce = MediaPlayer.create(this, R.raw.sauce);
        sound_gameover = MediaPlayer.create(this, R.raw.gameover);
        sound_timer = MediaPlayer.create(this, R.raw.timer);
    }

    // 출발 및 퇴장 시간과 소떡 요구량 정의
    public void setTime(int i) {
        if(reputation <= 50) {
            time[i] = r.nextInt(30);
            time_exit[i] = r.nextInt(10) + 20;
            guest_count[i] = r.nextInt(maxcount - 2) + 1;
            margin = 0.1f;
        }
        else if(reputation <= 80) {
            time[i] = r.nextInt(20);
            time_exit[i] = r.nextInt(15) + 20;
            guest_count[i] = r.nextInt(maxcount - 1) + 1;
            margin = 0.15f;
        }
        else {
            time[i] = r.nextInt(15);
            time_exit[i] = r.nextInt(20) + 20;
            guest_count[i] = r.nextInt(maxcount - 1) + 1;
            margin = 0.25f;
        }

        guest_menu[i].setText(String.valueOf(guest_count[i]));
        MoveGuest(i);
    }

    // 캐릭터 이동 정의
    public void MoveGuest(final int i) {
        timer_spawn[i] = new CountDownTimer(time[i] * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {}

            @Override
            public void onFinish() {
                Spawn(i);

                timer_exit[i] = new CountDownTimer(time_exit[i] * 1000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        time_exit[i] = (int) (millisUntilFinished / 1000) % 60;
                        String timeLeft = String.format(Locale.getDefault(), "%02d", time_exit[i]);
                        guest_time[i].setText(timeLeft);
                    }

                    @Override
                    public void onFinish() {
                        if(isGame)
                            isPlaying(sound_guest_fail);
                        Exit(i);
                        if(reputation <= 50)
                            setReputation(3, false);
                        else if(reputation <= 80)
                            setReputation(5, false);
                        else
                            setReputation(7, false);
                    }
                }.start();
            }
        }.start();
    }

    // 캐릭터 등장
    public void Spawn(final int i) {
        setPosition(i, true);
        frameAnimation[i].start();
        frameAnimation_arrow[i].start();
        guest[i].setVisibility(View.VISIBLE);
        arrow[i].animate().setStartDelay(0).setInterpolator(null).translationX(-(width / 1.75f) + move).setDuration(3000).start();
        guest_menu[i].animate().setStartDelay(0).setInterpolator(null).translationX(-(width / 1.75f) + move).setDuration(3000).start();
        guest_time[i].animate().setStartDelay(0).setInterpolator(null).translationX(-(width / 1.75f) + move).setDuration(3000).start();
        guest[i].animate().setStartDelay(0).setInterpolator(null).translationX(-(width / 1.75f) + move).setDuration(3000).withEndAction(new Runnable() {
            @Override
            public void run() {
                guest[i].post(new Runnable() {
                    @Override
                    public void run() {
                        arrow[i].setVisibility(View.VISIBLE);
                        guest_menu[i].setVisibility(View.VISIBLE);
                        guest_time[i].setVisibility(View.VISIBLE);
                        frameAnimation[i].stop();
                        frameAnimation[i].selectDrawable(0);
                        isSet[i] = true;
                    }
                });
            }
        });
    }

    // 캐릭터 퇴장
    public void Exit(final int i) {
        setPosition(i, false);
        isSet[i] = false;
        frameAnimation[i].start();
        arrow[i].setVisibility(View.INVISIBLE);
        guest_menu[i].setVisibility(View.INVISIBLE);
        guest_time[i].setVisibility(View.INVISIBLE);
        guest[i].animate().setStartDelay(0).setInterpolator(null).translationX(-width).setDuration(3000).withEndAction(new Runnable() {
            @Override
            public void run() {
                guest[i].post(new Runnable() {
                    @Override
                    public void run() {
                        guest[i].setVisibility(View.INVISIBLE);
                        guest[i].setX(width);
                        arrow[i].setX(width);
                        guest_menu[i].setX(width);
                        guest_time[i].setX(width);
                        frameAnimation[i].stop();
                        frameAnimation[i].selectDrawable(0);
                        frameAnimation_arrow[i].stop();
                        setTime(i);
                    }
                });
            }
        });
    }

    // 캐릭터 등장 시 빈 공간 탐색
    public void setPosition(int num, boolean select) {
        if(select) { // 등장 시
            for (int i = 0; i < num_character; i++) {
                if (!isAccupied[i]) {
                    guest_number[i] = num;
                    isAccupied[i] = true;
                    move = i * (float) (width / 15);
                    break;
                }
            }
        }
        else { // 퇴장 시
            for (int i = 0; i < num_character; i++) {
                if (guest_number[i] == num)
                    isAccupied[i] = false;
            }
        }
    }

    // 점수 변동
    public void setScore(int price, boolean select) {
        if(select) {
            score += price;
            score_total += price;
        }
        else
            score -= price;
        txt_score = String.format(Locale.getDefault(), "%06d", score);
        interface_score.setText(txt_score);

        if(score < price_ricecake && isGame)
            Gameover();
    }

    // 평점 변동
    public void setReputation(int count, boolean select) {
        if(select)
            reputation += count;
        else
            reputation -= count;
        txt_reputation = String.format(Locale.getDefault(), "%03d", reputation);
        interface_reputation.setText(txt_reputation);

        if(reputation <= 0 && isGame)
            Gameover();
    }

    // 메뉴 슬라이드 함수
    public void toMenu(View view) {
        if(isGame) {
            isPlaying(sound_click_button);
            if (!isMenu) {
                menu.animate().setStartDelay(0).setInterpolator(null).translationX(0).setDuration(200).start();
                isMenu = true;
            } else {
                menu.animate().setStartDelay(0).setInterpolator(null).translationX(-width).setDuration(200).start();
                isMenu = false;
            }
        }
    }

    // 소스 슬라이드 함수
    public void toSauce(View view) {
        if(isGame) {
            isPlaying(sound_click_button);
            if (!isSauce) {
                sauce.animate().setStartDelay(0).setInterpolator(null).translationX(0).setDuration(200).start();
                isSauce = true;
            } else {
                sauce.animate().setStartDelay(0).setInterpolator(null).translationX(width).setDuration(200).start();
                isSauce = false;
            }
        }
    }

    // 떡 버튼 클릭 시 해당 꼬치에 떡 추가
    public void setRiceCake(View view) {
        if(isGame) {
            if (count_ricecake[stick] + count_sausage[stick] != 6 && score >= price_ricecake) {
                isPlaying(sound_click_menu);
                setScore(price_ricecake, false);
                ricecake[count_ricecake[stick] + (stick * 6)].setVisibility(View.VISIBLE);
                ricecake[count_ricecake[stick] + (stick * 6)].animate().setStartDelay(0).setInterpolator(null).translationY(move_menu[stick]).setDuration(200).start();
                count_ricecake[stick]++;
                move_menu[stick] -= ((float) height / 10) / 2;
                checkSauce(stick);
            }
        }
    }

    // 소시지 버튼 클릭 시 해당 꼬치에 소시지 추가
    public void setSausage(View view) {
        if(isGame) {
            if (count_ricecake[stick] + count_sausage[stick] != 6 && score >= price_sausage) {
                isPlaying(sound_click_menu);
                setScore(price_sausage, false);
                sausage[count_sausage[stick] + (stick * 6)].setVisibility(View.VISIBLE);
                sausage[count_sausage[stick] + (stick * 6)].animate().setStartDelay(0).setInterpolator(null).translationY(move_menu[stick]).setDuration(200).start();
                count_sausage[stick]++;
                move_menu[stick] -= ((float) height / 10) / 2;
                checkSauce(stick);
            }
        }
    }

    // 떡과 소시지가 다 채워질 경우 확인
    public void checkSauce(final int stick) {
        if (count_ricecake[stick] + count_sausage[stick] == 6) {
            menu_time[stick].setVisibility(View.VISIBLE);
            timer_menu[stick] = new CountDownTimer(5000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    time_menu[stick] = (int) (millisUntilFinished / 1000) % 60;
                    menu_time[stick].setText(String.valueOf(time_menu[stick]));
                }

                @Override
                public void onFinish() {
                    if(isGame)
                        isPlaying(sound_sauce);
                    canSauce[stick] = true;
                    menu_time[stick].setVisibility(View.INVISIBLE);
                    time_menu[stick] = 5;
                }
            }.start();
        }
    }

    // 케첩 버튼 클릭 시 해당 꼬치에 케첩 추가
    public void setKetchup(View view) {
        if(isGame) {
            if (!isKetchup[stick] && canSauce[stick] && score >= price_ketchup) {
                isPlaying(sound_click_sauce);
                setScore(price_ketchup, false);
                interface_score.setText(txt_score);
                ketchup[stick].setVisibility(View.VISIBLE);
                isKetchup[stick] = true;
            }
        }
    }

    // 머스타드 버튼 클릭 시 해당 꼬치에 머스타드 추가
    public void setMustard(View view) {
        if(isGame) {
            if (!isMustard[stick] && canSauce[stick] && score >= price_mustard) {
                isPlaying(sound_click_sauce);
                setScore(price_mustard, false);
                interface_score.setText(txt_score);
                mustard[stick].setVisibility(View.VISIBLE);
                isMustard[stick] = true;
            }
        }
    }

    // 꼬치 초기화
    public void setRestore(int stick) {
        if(isGame) {
            for (int i = stick * 6; i < (stick + 1) * 6; i++) {
                ricecake[i].setY(0);
                sausage[i].setY(0);

                ricecake[i].setVisibility(View.INVISIBLE);
                sausage[i].setVisibility(View.INVISIBLE);
            }

            ketchup[stick].setVisibility(View.INVISIBLE);
            mustard[stick].setVisibility(View.INVISIBLE);

            if (timer_menu[stick] != null) {
                timer_menu[stick].cancel();
                menu_time[stick].setVisibility(View.INVISIBLE);
            }

            count_ricecake[stick] = 0;
            count_sausage[stick] = 0;
            canSauce[stick] = false;
            isKetchup[stick] = false;
            isMustard[stick] = false;
            move_menu[stick] = 3 * (height / 10);
        }
    }

    // 꼬치 전달
    public void setGive(int number) {
        if(isGame) {
            if (isSet[number] && count_ricecake[stick] + count_sausage[stick] == 6 && isKetchup[stick] && isMustard[stick]) {
                if (count_ricecake[stick] == 3 && count_sausage[stick] == 3) {
                    setScore((int) (((price_ricecake * count_ricecake[stick]) + (price_sausage * count_sausage[stick]) + price_ketchup + price_mustard) * (1 + margin)), true);
                    setReputation(2, true);
                    isPlaying(sound_guest_success);
                } else {
                    setScore((int) (((price_ricecake * count_ricecake[stick]) + (price_sausage * count_sausage[stick]) + price_ketchup + price_mustard) * (margin)), true);
                    setReputation(3, false);
                    isPlaying(sound_guest_fail);
                }

                setRestore(stick);

                guest_count[number]--;
                guest_menu[number].setText(String.valueOf(guest_count[number]));

                if (guest_count[number] <= 0) {
                    if (timer_exit[number] != null)
                        timer_exit[number].cancel();
                    Exit(number);
                }
            }
        }
    }

    // 꼬치 선택
    public void setStick_01(View view) {
        if(!isMenu && isGame) {
            isPlaying(sound_click_stick);
            stick = 0;
        }
    }

    public void setStick_02(View view) {
        if (isGame) {
            isPlaying(sound_click_stick);
            stick = 1;
        }
    }

    public void setStick_03(View view) {
        if (isGame) {
            isPlaying(sound_click_stick);
            stick = 2;
        }
    }

    public void setStick_04(View view) {
        if (isGame) {
            isPlaying(sound_click_stick);
            stick = 3;
        }
    }

    public void setStick_05(View view) {
        if(!isSauce && isGame) {
            isPlaying(sound_click_stick);
            stick = 4;
        }
    }

    // 꼬치 전달
    public void giveMenu_01(View view) {
        setGive(0);
    }

    public void giveMenu_02(View view) {
        setGive(1);
    }

    public void giveMenu_03(View view) {
        setGive(2);
    }

    public void giveMenu_04(View view) {
        setGive(3);
    }

    // 꼬치 폐기
    public void setEmpty_01(View view) {
        if(!isMenu && isGame && count_ricecake[0] + count_sausage[0] > 0) {
            isPlaying(sound_click_trash);
            setRestore(0);
        }
    }

    public void setEmpty_02(View view) {
        if(isGame && count_ricecake[1] + count_sausage[1] > 0) {
            isPlaying(sound_click_trash);
            setRestore(1);
        }
    }

    public void setEmpty_03(View view) {
        if(isGame && count_ricecake[2] + count_sausage[2] > 0) {
            isPlaying(sound_click_trash);
            setRestore(2);
        }
    }

    public void setEmpty_04(View view) {
        if(isGame && count_ricecake[3] + count_sausage[3] > 0) {
            isPlaying(sound_click_trash);
            setRestore(3);
        }
    }

    public void setEmpty_05(View view) {
        if(!isSauce && isGame && count_ricecake[4] + count_sausage[4] > 0) {
            isPlaying(sound_click_trash);
            setRestore(4);
        }
    }

    // 게임 오버
    private void Gameover() {
        txt_gameover_score = String.format(Locale.getDefault(), "Score: %06d", score_total);
        gameover_score.setText(txt_gameover_score);
        isGame = false;
        sound_bgm.stop();
        isPlaying(sound_gameover);
        gameover.setVisibility(View.VISIBLE);
        gameover_score.setVisibility(View.VISIBLE);
        gameover.animate().setStartDelay(0).setInterpolator(null).translationY(0).setDuration(1000).start();
    }

    // 메인 화면 이동 (게임 중)
    public void toTitle(View view) {
        if (isGame) {
            sound_bgm.stop();
            timer_left.cancel();
            isPlaying(sound_click_button);
            for (int i = 0; i < num_character; i++) {
                if (timer_spawn[i] != null)
                    timer_spawn[i].cancel();
                if (timer_exit[i] != null)
                    timer_exit[i].cancel();
            }

            for (int i = 0; i < num_stick; i++) {
                if (timer_menu[i] != null)
                    timer_menu[i].cancel();
                count_ricecake[i] = 0;
                count_sausage[i] = 0;
                canSauce[i] = false;
                isKetchup[i] = false;
                isMustard[i] = false;
            }

            isGame = false;

            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    // 메인 화면 이동 (게임 오버)
    public void Gameover_toTitle(View view) {
        isPlaying(sound_click_button);
        timer_left.cancel();
        for (int i = 0; i < num_character; i++) {
            if (timer_spawn[i] != null)
                timer_spawn[i].cancel();
            if (timer_exit[i] != null)
                timer_exit[i].cancel();
        }

        for (int i = 0; i < num_stick; i++) {
            if (timer_menu[i] != null)
                timer_menu[i].cancel();
            count_ricecake[i] = 0;
            count_sausage[i] = 0;
            canSauce[i] = false;
            isKetchup[i] = false;
            isMustard[i] = false;
        }

        isGame = false;

        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    // 효과음 재생 확인
    private void isPlaying(MediaPlayer sound) {
        // 소리가 묻힐 일이 없도록 재생 중일 경우 다시 재생
        if(sound.isPlaying()) sound.pause();
        sound.seekTo(0);
        sound.start();
    }
}