package com.example.speedmatchgame;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class GameFragment extends Fragment {

    private final int CLICK_ON_BOTH = 0;
    private final int CLICK_ON_SHAPE = 1;
    private final int CLICK_ON_COLOR = 2;
    private final int CLICK_ON_NONE = 3;


    private ImageView imageFrame;

    private TextView level;
    private TextView score;
    private TextView timer;
    private TextView countDown;
    private TextView test;

    private Button color;
    private Button shape;
    private Button both;
    private Button none;

    private int imageIndex;
    private int countNumber = 3;
    private int lastUserScore;
    private int currentUserScore = 0;
    private int userLevel = 0;
    private int imageID;
    private int currentImageID;
    private int lastImageID;

    private String name;

    CountDownTimer countDownTimer;
    AnimatorSet animatorSet;

    private boolean finish_game = false;

    private ArrayList<Integer> squareTriangleList;
    private ArrayList<Integer> circleTriangleList;
    private ArrayList<Integer> circleSquareList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.game_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findView(view);
        startGame();

    }

    private void findView(View view) {
        color = view.findViewById(R.id.color_only);
        shape = view.findViewById(R.id.shape_only);
        both = view.findViewById(R.id.shape_and_color);
        none = view.findViewById(R.id.neither);
        imageFrame = view.findViewById(R.id.image_frame);
        score = view.findViewById(R.id.score);
        timer = view.findViewById(R.id.timer);
        countDown = view.findViewById(R.id.count_down);
        level = view.findViewById(R.id.level);
        test = view.findViewById(R.id.test);
    }

    private int setImageIndex() {
        Random random = new Random();
        imageID = random.nextInt();
        imageID = imageID % 12;
        if (imageID < 0) {
            imageID = Math.abs(imageID);
        }
        return imageID;
    }

    private void exitImage() {
        ObjectAnimator lastImage = ObjectAnimator.ofFloat(
                imageFrame,
                "translationX",
                0f, -1000f
        );
        lastImage.setDuration(500);
        lastImage.start();
    }

    private void enterImage() {
        ObjectAnimator newImage = ObjectAnimator.ofFloat(
                imageFrame,
                "translationX",
                1000f, 0f
        );
        newImage.setDuration(500);
        newImage.start();
    }

    private void newLevel() {
        if (!finish_game) {
            lastImageID = (int) imageFrame.getTag();
            exitImage();
            imageID = setImageIndex();
            imageFrame.setImageResource(Cards.setInstance().imageList[imageID]);
            imageFrame.setTag(imageID);
            currentImageID = (int) imageFrame.getTag();
            enterImage();
            userLevel++;
            level.setText(getString(R.string.level, userLevel));
        } else {
            return;
        }


    }

    private void clickOnButtons() {

        both.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserScore(lastImageID, currentImageID, CLICK_ON_BOTH);
                newLevel();
            }
        });

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserScore(lastImageID, currentImageID, CLICK_ON_COLOR);
                newLevel();
            }
        });

        shape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserScore(lastImageID, currentImageID, CLICK_ON_SHAPE);
                newLevel();
            }
        });

        none.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserScore(lastImageID, currentImageID, CLICK_ON_NONE);
                newLevel();
            }
        });
    }

    private void countDownAnimation() {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(
                countDown,
                "scaleX",
                1f, 2f
        );
        scaleX.setDuration(1000);

        ObjectAnimator scaleY = ObjectAnimator.ofFloat(
                countDown,
                "scaleY",
                1f, 2f
        );
        scaleY.setDuration(1000);

        ObjectAnimator alpha = ObjectAnimator.ofFloat(
                countDown,
                "alpha",
                1f, 0f
        );
        alpha.setDuration(1000);

        animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY, alpha);
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                countDown.setText(String.valueOf(countNumber));
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                countNumber--;
                if (countNumber == 0) {
                    countDownTimer = new CountDownTimer(20000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            timer.setText(getString(R.string.timer, (int) millisUntilFinished / 1000));
                        }

                        @Override
                        public void onFinish() {
                            timer.setText(getString(R.string.game_finish));
                            finish_game = true;
                            updatePlayerData();
                        }
                    };
                    countDownTimer.start();
                    newLevel();
                } else {
                    countDownAnimation();
                }
            }
        });
        animatorSet.start();
    }

    private void setUserScore(int firstImageID, int secondImageID, int userClick) {
        if (!finish_game) {
            if (userClick == CLICK_ON_BOTH) {
                if (firstImageID == secondImageID) {
                    currentUserScore++;
                    score.setText(getString(R.string.score, currentUserScore));
                }
            } else if (userClick == CLICK_ON_SHAPE) {
                if (Cards.setInstance().circleShape.contains(firstImageID)) {
                    if (Cards.setInstance().circleShape.contains(secondImageID)) {
                        currentUserScore++;
                        score.setText(getString(R.string.score, currentUserScore));
                    }
                } else if (Cards.setInstance().squareShape.contains(firstImageID)) {
                    if (Cards.setInstance().squareShape.contains((secondImageID))) {
                        currentUserScore++;
                        score.setText(getString(R.string.score, currentUserScore));
                    }
                } else if (Cards.setInstance().triangleShape.contains(firstImageID)) {
                    if (Cards.setInstance().triangleShape.contains(secondImageID)) {
                        currentUserScore++;
                        score.setText(getString(R.string.score, currentUserScore));
                    }
                }
            } else if (userClick == CLICK_ON_COLOR) {
                if (Cards.setInstance().redColor.contains(firstImageID)) {
                    if (Cards.setInstance().redColor.contains(secondImageID)) {
                        currentUserScore++;
                        score.setText(getString(R.string.score, currentUserScore));
                    }
                } else if (Cards.setInstance().blueColor.contains(firstImageID)) {
                    if (Cards.setInstance().blueColor.contains(secondImageID)) {
                        currentUserScore++;
                        score.setText(getString(R.string.score, currentUserScore));
                    }
                } else if (Cards.setInstance().purpleColor.contains(firstImageID)) {
                    if (Cards.setInstance().purpleColor.contains(secondImageID)) {
                        currentUserScore++;
                        score.setText(getString(R.string.score, currentUserScore));
                    }
                } else if (Cards.setInstance().yellowColor.contains(firstImageID)) {
                    if (Cards.setInstance().yellowColor.contains(secondImageID)) {
                        currentUserScore++;
                        score.setText(getString(R.string.score, currentUserScore));
                    }
                }
            } else if (userClick == CLICK_ON_NONE) {
                if (Cards.setInstance().circleShape.contains(firstImageID)) {
                    setSquareTriangleList();
                    imageIndex = Cards.setInstance().circleShape.indexOf(firstImageID);
                    squareTriangleList.remove(imageIndex);
                    squareTriangleList.remove(imageIndex + 3);
                    if (squareTriangleList.contains(secondImageID)) {
                        currentUserScore++;
                        score.setText(getString(R.string.score, currentUserScore));
                    }
                } else if (Cards.setInstance().squareShape.contains(firstImageID)) {
                    setCircleTriangleList();
                    imageIndex = Cards.setInstance().squareShape.indexOf(firstImageID);
                    circleTriangleList.remove(imageIndex);
                    circleTriangleList.remove(imageIndex + 3);
                    if (circleTriangleList.contains(secondImageID)) {
                        currentUserScore++;
                        score.setText(getString(R.string.score, currentUserScore));
                    }
                } else if (Cards.setInstance().triangleShape.contains(firstImageID)) {
                    setCircleSquareList();
                    imageIndex = Cards.setInstance().triangleShape.indexOf(firstImageID);
                    circleSquareList.remove(imageIndex);
                    circleSquareList.remove(imageIndex + 3);
                    if (circleSquareList.contains(secondImageID)) {
                        currentUserScore++;
                        score.setText(getString(R.string.score, currentUserScore));
                    }
                }
            }
        } else {
            return;
        }
    }

    private void setSquareTriangleList() {
        squareTriangleList = new ArrayList<>();
        squareTriangleList.add(0, 4);
        squareTriangleList.add(1, 5);
        squareTriangleList.add(2, 6);
        squareTriangleList.add(3, 7);
        squareTriangleList.add(4, 8);
        squareTriangleList.add(5, 9);
        squareTriangleList.add(6, 10);
        squareTriangleList.add(7, 11);
    }

    private void setCircleTriangleList() {
        circleTriangleList = new ArrayList<>();
        circleTriangleList.add(0, 0);
        circleTriangleList.add(1, 1);
        circleTriangleList.add(2, 2);
        circleTriangleList.add(3, 3);
        circleTriangleList.add(4, 8);
        circleTriangleList.add(5, 9);
        circleTriangleList.add(6, 10);
        circleTriangleList.add(7, 11);
    }

    private void setCircleSquareList() {
        circleSquareList = new ArrayList<>();
        circleSquareList.add(0, 0);
        circleSquareList.add(1, 1);
        circleSquareList.add(2, 2);
        circleSquareList.add(3, 3);
        circleSquareList.add(4, 4);
        circleSquareList.add(5, 5);
        circleSquareList.add(6, 6);
        circleSquareList.add(7, 7);
    }

    private void startGame() {
        imageID = setImageIndex();
        imageFrame.setImageResource(Cards.setInstance().imageList[imageID]);
        imageFrame.setTag(imageID);
        countDownAnimation();
        clickOnButtons();
    }

    private void updatePlayerData() {
        PlayerAttributes lastPlayer = PlayerPreferencesManager.setInstance(getActivity()).getPlayerManager();
        PlayerAttributes currentPlayer = new PlayerAttributes();
        currentPlayer.setName(getArguments().getString("player_name", null));
        currentPlayer.setScore(currentUserScore);
        currentPlayer.setLevel(userLevel);
        if (lastPlayer != null){
            lastUserScore = lastPlayer.getScore();
            if (currentUserScore >= lastUserScore){
                PlayerPreferencesManager.setInstance(getActivity()).putPlayerManager(currentPlayer);
            }
        }
        else{
            PlayerPreferencesManager.setInstance(getActivity()).putPlayerManager(currentPlayer);
        }


    }

    @Override
    public void onStop() {
        super.onStop();
        countDownTimer.cancel();
    }
}

