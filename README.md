# BlackJack
> Be able to play Blackjack anytime you want 

## Table of Contents
* [Overview](#overview)
* [Demo](#demo)
* [Screenshots](#screenshots)
* [Technologies Used](#technologies-used)
* [Setup Instructions](#setup-instructions)
* [How It Works](#how-it-works)
* [Code Snippets](#code-snippets)
* [Features](#features)
* [Status](#status)
* [Contributors](#contributors)
* [Contact](#contact)

## Overview
normally in order to play blackjack people would have to spend money, however this app provides users an alternative way for them to play blackjack. It also tracks the users win-loss record, so they can better visualize how well they are at the game of blackjack
## Demo
[Video Link]()

## Screenshots
During the gameplay it shows the users hand in which they can choose to either stand or hit a card. In the case a user hits it will then play an animation of the card moving toward their deck. If the hand busts or wins the user will be sent to the result screen in which their win-loss record will be shown.
<div style = {display: flex}>
  <img width="300" alt="The Cover Page of the app" src="https://github.com/user-attachments/assets/111d4237-9eec-4590-9aa3-f243bf22fd79"
>
  <img width="300" alt="The Login Page of the app. Shows what an Unsuccseful login looks like" src="https://github.com/user-attachments/assets/ea1f69f1-201a-4b72-9cbe-985ebd69774b">
</div> 


## Technologies Used
* Java 

## Setup Instructions
1. Copy URL[https://github.com/dancannotcode/BlackJackGame.git]
2. Install [Android Stuidos](https://developer.android.com/studio)
3. Run the application
4. click on `Clone Repository`
5. Paste Repository URL[https://github.com/dancannotcode/BlackJackGame.git]


## How It Works
1. Home Screen
   * Users start off on the home screen in which clicking on the `play button` then sends an intent to start the game activity for the user
2. Game Setup
   * A `HashMap` is created in order to store a relationship of <card_value, #value> so that when a user pulls a card it is parsed properly into their total score
   * a `deck array` is created and shuffled using a function in the `deck array` class before the game starts
   * Before the User can play we need to distribute cards for the begining of the game
   * `card obj` are created and then stored in a `deck array` class once that deck array is created in our game then the dealer and user will `setImageResoruce` of their decks to the proper cards
   * `deck array` removes the object from its array and decreases its size
3. User GamePlay
   * the users score is instantly checked to see if they have 21, if so a `checkScore()` function is called and says the user won the game
   * the user presses a `hit button` then a `Drawable animation` is played so the user sees which card is drawn for the `deck array`
   * Once card is dealt we remove the card from our `deck array`
   * Drawn card changes  a `setImageResoruce` to reflect which card the user has drawn as well as showing a display of the updated score of the user
   * `checkScore()` is called to compare dealer and users hand
4. Dealer AI
   * While a winner is not decided after a user clicks `stand button` then the dealer will draw cards until their score excedes 17
   * Dealers are shown the same animation and `deck array` as the user and continue to draw until a winner is decided
5. Winner Decided
   * `checkScore()` is then called to see which side won
   *  if user beats the dealer the are awarded a win by getting less than or exactly 21 as well as having a greater number than the dealer
   *  through `sharedPreferences` we save the result of the game and either add one to the lose or win data
6. Win-Loss Screen
   * Accesses the `sharedPreference` of the game
   * display wins through the `Id` of wins, and display loses through the `Id` of loses


## Code Snippets
````java
//throughout the program this function was called 6x and it allows users to
//visualize which type of card is drawn/ in their hand.
public int getCardDrawableId(game c){
    //Converts the card drawn to its drawable ID
		int resID = c.getResources().getIdentifier(this.getSuitAsString().toLowerCase() + "_" + this.getValueAsString().toLowerCase(),
				"drawable","com.example.blackjackgame");
    //returns the ID of the DrawAble
		return resID;
	}
	

````
<br>
<br>

````java
//The AI for the program contiunes to play as long as a winner is not decided
//This is importnat for the user to have a realstic BJ experience
while(winnerNotDecided){
               if(dealerCurrScore >= 17){ //if the dealers hand is !17 then the dealer keeps drawing
                   winnerNotDecided = false;
               }else if(dealerCurrScore < userCurrScore){//if the dealer doesn't have a winning hand keep drawing

                    //converts the card drawin into its drawable version
                   cardOnTopOfDeck.setImageResource(deck[deckCounter].topCard().getCardDrawableId(this));
                   final Drawable currentCardDrawable = cardOnTopOfDeck.getDrawable();
                   Animation animation = AnimationUtils.loadAnimation(this, R.anim.card_for_dealer);
                   animation.setAnimationListener(new Animation.AnimationListener() {
                       @Override
                       public void onAnimationStart(Animation animation) {

                       }

                       @Override

                      //shows animation of it being drawn
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

                    //incrments dealers score as well as updating the deck
                   dealerCurrScore = Integer.parseInt(dealersScore.getText().toString());
                   String dealtCard = deck[deckCounter].topCard().getValueAsString();
                   deckCounter++;
                   deck[deckCounter].dealCard();

                   dealersScore.setText(String.valueOf(updateScore(dealerCurrScore, dealtCard)));
               }
            }

````

## Features
* Animations
* OOP
* easy to use 
* Mobile responsiveness 

### Future Enhancements
* Add more activites to do
* Improve visuals

## Status
* _Completed_: No further updates planned, but open to feedback and collaboration.

## Challenges
* Creating the AI
* Converting a card obj into a drawable
* saving data
* deaking with updated decks

## Learnings
* Improve understanding of OOP.
* Improved skills in debugging.
* Learned to Use Animations in Android Studios.
* More practice with SharedPrefrences.

## Contributors
List all contributors involved in the project:
* [Daniel Ortega Jr](https://github.com/dancannotcode) - Responsible for getting various bits of data to save to the firebase as well as make the enemies spawn on the clicking tab.  

## Contact
Feel free to reach out for collaboration, feedback, or questions.  
**Created by:** [Daniel Ortega Jr]  

Connect with me:  
* **Email:** [dancannotcode@gmail.com](mailto:dancannotcode@gmail.com)  
* **GitHub:** [YourGitHubProfile](https://github.com/dancannotcode)  
* **LinkedIn:** [Daniel Ortega Jr](https://www.linkedin.com/in/daniel-ortega-jr-4b79b1336/)
