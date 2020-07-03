
package cheatinghangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Dictionary {
    
    HashSet words = new HashSet();
    
    public Dictionary(){
        addToLibrary();
    }
    
    private void addToLibrary(){
        File file = new File("dictionary.txt");
        
        try{
            Scanner sc = new Scanner(file);
            
            while(sc.hasNextLine()){
                this.words.add(sc.nextLine());
            }
        } catch (FileNotFoundException e){
            System.out.println("Dictionary File Cannot Be Found");
            System.exit(0);
        }
        
    }

    public HashSet getWords() {
        return words;
    }

    public void setWords(HashSet words) {
        this.words = words;
    }
    
}




