
package cheatinghangman;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

public class CheatingHangman {

    public static void main(String[] args) {
        Dictionary words = new Dictionary();
        HashSet<String> posWords = new HashSet();
        HashSet possibleMatches = new HashSet();
        GuessAnswerWords guessAnswerWords = new GuessAnswerWords();
        ArrayList guesses = new ArrayList();
        
        boolean tryAgain =true;
        int wrongAnswers = 0;
        int numGuesses;
        String playersGuess = "";
        String answer;
        
        // creates a hashSet of all the words with the amount of letters user enters
        guessAnswerWords.setWords(howManyLetters(words));
        
        // creates a starting point for the answer guide
        guessAnswerWords.setAnswer(startAnswer(guessAnswerWords.words.iterator().next().length()));
        
        // how many guesses before the game ends
        numGuesses = wrongGuesses();
        //System.out.println(numGuesses); // test
        
        while(true){        
            // Players Guess
            while(tryAgain){
                playersGuess = playersGuess();
                //System.out.println(playersGuess); // test
                
                if(guesses.contains(playersGuess)){
                    System.out.println("Please try a different letter you already tried this one.");
                    System.out.println("Previous Guesses: " + guesses.toString());
                } else{
                    tryAgain = false;
                }
            }
            // sort guesses into groups
            guessAnswerWords = posGuesses(playersGuess, guessAnswerWords.getWords(), guessAnswerWords.getAnswer(), guessAnswerWords);
            System.out.println("Guess is " + guessAnswerWords.isGuess());
            
            if(guessAnswerWords.isGuess()){
                guesses.add(playersGuess);
            } else {
                wrongAnswers++;
                System.out.println("Wrong Answers: " + wrongAnswers);
                guesses.add(playersGuess);
            }
            
            if((numGuesses - wrongAnswers) == 0){
                System.out.println("\nSorry! You LOSE!");
                System.out.print("The Word Is: ");

                int size = guessAnswerWords.words.size();
                int item = new Random().nextInt(size);
                
                Iterator<String> it = guessAnswerWords.words.iterator();
                while(it.hasNext()){
                    System.out.println(it.next());
                    System.exit(0);
                }
            }
            
            tryAgain = true;
            
            System.out.println("Guessed Letters: " + guesses.toString());
            System.out.println("Guesses Left: " + (numGuesses - wrongAnswers));
            System.out.println("\nHints: " + guessAnswerWords.getAnswer());
            System.out.println("\nPossible Words [TEST]: " + guessAnswerWords.words.toString());
            
            if(!guessAnswerWords.getAnswer().contains("_")){
                System.out.println("Congratulations, you've solved the puzzle!");
                System.exit(0);
            }
        }
        
    }
    
public static String startAnswer(int a){
    String b = "";
    for(int i = 0; i<a; i++){
        b = b + "_";
    }
    return b;
}    
    
public static HashSet howManyLetters(Dictionary a){
    HashSet word = new HashSet();
    Scanner s = new Scanner(System.in);
    
    boolean retry = true;
    int length;
    String temp;
    
    System.out.println("This is a game of Hangman. If you don't know how to play \n"
            + "google the game for the rules. \n \n");
    
    while(retry){
        System.out.print("Length of Word You Would Like to Guess: ");
        try{
            length = Integer.parseInt(s.nextLine());
            System.out.println(length);
            
            Iterator<String> it = a.getWords().iterator();
            
            while(it.hasNext()){
                
                temp = it.next();
                
                // iterate through the hashset and remove all words not the length of words
                if(temp.length() == length){
                    System.out.println(temp);
                    word.add(temp);
                    //System.out.println(word.getWords().toString());
                }
            }
            
            // if the list of words in not empty return false
            if(!word.isEmpty()){
                System.out.println("Has words."); //test
                //System.out.println(word.toString()); //test

                return word;
                
            } else {
                System.out.println("No words of that length found try again.");
            }
            
            
        } catch(NumberFormatException e){
            
            System.out.println("Please enter a real number");
        }
    }
    
    return word;
}

public static GuessAnswerWords posGuesses(String a, HashSet list, String alreadyGuessed, GuessAnswerWords guessAnswerWords){
    // returns an array of items [bool is guess correct, String of GuessHints, hashset of possiblematches]
    
    HashMap<String, ArrayList<String>> possibleMatches = new HashMap<String, ArrayList<String>>();
    HashSet newSet = new HashSet();
    ArrayList<String> keys = new ArrayList<String>();
    ArrayList<Object> returnThis = new ArrayList<Object>();
    
    String temp;
    String mostMatches;
    
    Iterator<String> it = list.iterator();
    
    while(it.hasNext()){
        String hashKey = "";
        
        temp = it.next();
        
        if(temp.contains(a)){
            
            for(int i = 0; i<temp.length(); i++){
                
                if(temp.charAt(i) == a.charAt(0)){
                    hashKey = hashKey + "1";
                } else{
                    hashKey = hashKey + "_";
                } // end if else
            } // end for
            
            if(!possibleMatches.containsKey(hashKey)){
                ArrayList stringA = new ArrayList();
                
                stringA.add(temp);
                possibleMatches.put(hashKey, stringA);
                
                keys.add(hashKey);
            } else {
                possibleMatches.get(hashKey).add(temp);
            } // end if

        } else {
            
            if(!possibleMatches.containsKey(alreadyGuessed)){
                ArrayList stringA = new ArrayList();
                
                stringA.add(temp);
                possibleMatches.put(alreadyGuessed, stringA);
                
                keys.add(alreadyGuessed);
            } else {
                possibleMatches.get(alreadyGuessed).add(temp);
            }
        }// end if
        
    } // end while
    
    if(possibleMatches.isEmpty()){
        
        guessAnswerWords.setGuess(false);
        return guessAnswerWords;
        
    } else {
        for(int i = 0; i < keys.size(); i++){
            for(int j = 0; j < guessAnswerWords.getAnswer().length(); j++){
                if(guessAnswerWords.getAnswer().charAt(j) != '_'){
                    for(int k = 0; k < possibleMatches.get(keys.get(i)).size(); k++){
                        if(guessAnswerWords.getAnswer().charAt(j) != possibleMatches.get(keys.get(i)).get(k).charAt(j)){
                            possibleMatches.get(keys.get(i)).remove(k);
                            k--;
                        }
                    }
                }
            }
        }
        
        mostMatches = keys.get(0);
        
        if(!(keys.size() == 1)){
            
            for(int i = 1; i < keys.size(); i++){
                
                if(possibleMatches.get(mostMatches).size() <= possibleMatches.get(keys.get(i)).size()){
                    
                    mostMatches = keys.get(i);
                } // end if
            } // end for            
        } // end if
    }
    
    guessAnswerWords.setWords(new HashSet<String>(possibleMatches.get(mostMatches)));
    
    //System.out.println(guessAnswerWords.words.toString());
    String answerTemp = "";
    //System.out.println(mostMatches);
    if (!mostMatches.matches(".*[a-z].*")) { 
        for(int i = 0; i < guessAnswerWords.getAnswer().length(); i++){
            if(mostMatches.charAt(i) == '_'){
                answerTemp = answerTemp + guessAnswerWords.getAnswer().charAt(i);
            } else {
                answerTemp = answerTemp + a;
            }
        }
        if(answerTemp.matches(guessAnswerWords.getAnswer())){
            guessAnswerWords.setGuess(false);
            return guessAnswerWords;
        }
        guessAnswerWords.setGuess(true);
        guessAnswerWords.setAnswer(answerTemp);
        //System.out.println(guessAnswerWords.getAnswer());
        return guessAnswerWords;
    } else{
        guessAnswerWords.setGuess(false);
        return guessAnswerWords;
    } 
    
}

public static String playersGuess(){
    Scanner sc = new Scanner(System.in);
    String a;
    
    while(true){
        System.out.print("\nEnter your guess [a letter A-Z]: ");
        a = sc.nextLine();
        
        if(a.length() == 1 && a.matches("[a-zA-Z]")){
            return a;
        } else {
            System.out.println("\nPlease enter a single letter.");
        }
        
    }
}

public static int wrongGuesses(){
    System.out.print("\nEnter how many guesses would you like to be given: ");
    Scanner sc = new Scanner(System.in);
    
    int a;
    
    while(true){
        try{
            a = Integer.parseInt(sc.nextLine());
            if(a > 0){
                return a;
            }
        }catch(NumberFormatException e){

            System.out.println("Please enter a real number greater than 0.");
        }
    }
    
}
    
}


