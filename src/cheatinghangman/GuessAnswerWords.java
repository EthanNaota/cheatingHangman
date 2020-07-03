
package cheatinghangman;

import java.util.HashSet;

public class GuessAnswerWords {
    boolean guess;
    String answer;
    HashSet<String> words;
    
    public GuessAnswerWords(){
        this.guess = true;
        this.answer = "";
        this.words = new HashSet<String>();
    }

    public boolean isGuess() {
        return guess;
    }

    public void setGuess(boolean guess) {
        this.guess = guess;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public HashSet<String> getWords() {
        return words;
    }

    public void setWords(HashSet<String> words) {
        this.words = words;
    }
    
    
}
