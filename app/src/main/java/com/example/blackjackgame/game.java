package com.example.blackjackgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.HashMap;
import java.util.Map;

public class game extends AppCompatActivity {
    SharedPreferences settings;
    private TextView dealersScore;
    private int dealerCurrScore;
    private ImageView dealerCard1;
    private ImageView dealerCard2;
    private TextView usersScore;
    private  int userCurrScore;
    private ImageView userCard1;
    private ImageView userCard2;
    private Button hit;
    private Button stand;
    private ImageView cardOnTopOfDeck;
    private Deck[] deck;
    private int deckCounter = 0;
    public Map<String, Integer> wordToNumberMap;
    public boolean winnerNotDecided = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_game);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        settings = this.getSharedPreferences("game", 0);

        wordToNumberMap = new HashMap<>();
        wordToNumberMap.put("Two", 2);
        wordToNumberMap.put("Three", 3);
        wordToNumberMap.put("Four", 4);
        wordToNumberMap.put("Five", 5);
        wordToNumberMap.put("Six", 6);
        wordToNumberMap.put("Seven", 7);
        wordToNumberMap.put("Eight", 8);
        wordToNumberMap.put("Nine", 9);
        wordToNumberMap.put("Ten", 10);
        wordToNumberMap.put("Jack", 10);
        wordToNumberMap.put("Queen", 10);
        wordToNumberMap.put("King", 10);
        wordToNumberMap.put("Ace", 11);

        dealersScore = findViewById(R.id.DealersScore);
        usersScore = findViewById(R.id.UsersScore);
        dealerCard1 = findViewById(R.id.DealerCard1);
        dealerCard2 = findViewById(R.id.DealerCard2);
        userCard1 = findViewById(R.id.UserCard1);
        userCard2 = findViewById(R.id.UserCard2);
        cardOnTopOfDeck = findViewById(R.id.imageView);
        stand = findViewById(R.id.Stand);
        hit = findViewById(R.id.Hit);

        deck = new Deck[52];
        for(int i = 0; i <= 51; i++){
            deck[i] = new Deck();
            deck[i].shuffle();
        }
        //sets up game and makes sure to check if user got 21 on their first hand
        setUpGame(wordToNumberMap);
        if(userCurrScore == 21){
            checkScore();
        }
        //user draws a card from the deck
        hit.setOnClickListener(v -> {
            cardOnTopOfDeck.setImageResource(deck[deckCounter].topCard().getCardDrawableId(this));
            final Drawable currentCardDrawable = cardOnTopOfDeck.getDrawable();
            Animation animation = AnimationUtils.loadAnimation(this, R.anim.card_dealt);
            animation.setAnimationListener(new Animation.AnimationListener(){
                @Override
                public void onAnimationStart(Animation animation) {

                }

                public void onAnimationEnd(Animation animation) {
                    cardOnTopOfDeck.setImageResource(R.drawable.card_back);
                    userCard1.setImageDrawable(userCard2.getDrawable());
                    userCard2.setImageDrawable(currentCardDrawable);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            cardOnTopOfDeck.startAnimation(animation);
            userCurrScore = Integer.parseInt(usersScore.getText().toString());
            String dealtCard = deck[deckCounter].topCard().getValueAsString();

            deckCounter++;
            deck[deckCounter].dealCard();

            usersScore.setText(String.valueOf(updateScore(userCurrScore, dealtCard)));
            userCurrScore = Integer.parseInt(usersScore.getText().toString());
            if(userCurrScore >= 21){
                checkScore();
            }
        });

        //dealers turn to pick cards if they have a score of less than 17, if the score is greater
        //than or exactly 17 then they would need to stop pulling cards and that is when a winner
        //is decided
        stand.setOnClickListener(v -> {
            dealerCard1.setImageResource(deck[deckCounter].topCard().getCardDrawableId(this));
            dealerCurrScore = Integer.parseInt(dealersScore.getText().toString());
            dealersScore.setText(String.valueOf(updateScore(dealerCurrScore, deck[deckCounter].topCard().getValueAsString())));
            while(winnerNotDecided){
               if(dealerCurrScore >= 17){
                   winnerNotDecided = false;
               }else if(dealerCurrScore < userCurrScore){
                   cardOnTopOfDeck.setImageResource(deck[deckCounter].topCard().getCardDrawableId(this));
                   final Drawable currentCardDrawable = cardOnTopOfDeck.getDrawable();
                   Animation animation = AnimationUtils.loadAnimation(this, R.anim.card_for_dealer);
                   animation.setAnimationListener(new Animation.AnimationListener() {
                       @Override
                       public void onAnimationStart(Animation animation) {

                       }

                       @Override
                       public void onAnimationEnd(Animation animation) {
                           cardOnTopOfDeck.setImageResource(R.drawable.card_back);
                           dealerCard1.setImageDrawable(dealerCard2.getDrawable());
                           dealerCard2.setImageDrawable(currentCardDrawable);
                       }

                       @Override
                       public void onAnimationRepeat(Animation animation) {

                       }
                   });
                   cardOnTopOfDeck.startAnimation(animation);
                   dealerCurrScore = Integer.parseInt(dealersScore.getText().toString());
                   String dealtCard = deck[deckCounter].topCard().getValueAsString();
                   deckCounter++;
                   deck[deckCounter].dealCard();

                   dealersScore.setText(String.valueOf(updateScore(dealerCurrScore, dealtCard)));
               }
            }
           checkScore();
        });

    }

    public void setUpGame(Map<String, Integer> wordToNumberMap){
        //distributes the cards for the beginning of the game
        String tempVal;
        dealerCard2.setImageResource(deck[deckCounter].topCard().getCardDrawableId(this));
        dealerCurrScore = Integer.parseInt(dealersScore.getText().toString());
        tempVal = deck[deckCounter].topCard().getValueAsString();
        dealerCurrScore+=  wordToNumberMap.get(tempVal);
        dealersScore.setText(String.valueOf(dealerCurrScore));
        deckCounter++;
        deck[deckCounter].dealCard();

        userCard1.setImageResource(deck[deckCounter].topCard().getCardDrawableId(this));
        userCurrScore = Integer.parseInt(usersScore.getText().toString());
        tempVal = deck[deckCounter].topCard().getValueAsString();
        userCurrScore+=  wordToNumberMap.get(tempVal);
        deckCounter++;
        deck[deckCounter].dealCard();

        userCard2.setImageResource(deck[deckCounter].topCard().getCardDrawableId(this));
        tempVal = deck[deckCounter].topCard().getValueAsString();
        userCurrScore+=  wordToNumberMap.get(tempVal);
        usersScore.setText(String.valueOf(userCurrScore));
        deckCounter++;
        deck[deckCounter].dealCard();
    }
    //checks to see who is winning the game
    public void checkScore(){
        dealerCurrScore = Integer.parseInt(dealersScore.getText().toString());
        userCurrScore = Integer.parseInt(usersScore.getText().toString());
        ///if user loses to the dealer the are awarded a lose by getting greater than 21 or
        // the dealer having a bigger number than them
        if((userCurrScore < dealerCurrScore || userCurrScore > 21 || dealerCurrScore == 21) && dealerCurrScore <= 21){
            Toast.makeText(this,"lose",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), WinOrLossScreen.class);
            int loses = settings.getInt("Loses", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("Loses", loses+1);

            editor.commit();
            startActivity(intent);

        }
        ///if user beats the dealer the are awarded a win by getting less than or exactly 21 as well
        // as having a greater number than the dealer
        if((dealerCurrScore > 21 || userCurrScore == 21 || userCurrScore > dealerCurrScore || userCurrScore == dealerCurrScore) && userCurrScore <= 21){
            Toast.makeText(this, "you win", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(getApplicationContext(), WinOrLossScreen.class);
            int wins = settings.getInt("Wins", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("Wins", wins+1);

            editor.commit();
            startActivity(intent);
        }
    }

    //add the cards value to the users score
    public int updateScore(int score, String cardVal){
        score += wordToNumberMap.get(cardVal);
        return score;
    }
}