import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/*
-Full name : Alae Boufarrachene
-Student number : 300188539
-Course : CSI2110-D
-Assignment : P2
-Question : 1
*/

class Main {

    /**
     * Helper method used to extract the textual content of each tested file
     * @throws NumberFormatException
     * @throws IOException
     */
    static void readContentOfTextualFile() throws NumberFormatException, IOException {

        BufferedReader textualInput = new BufferedReader(new InputStreamReader(System.in));

        int tempCharInt, counter=0, inputNumber=Integer.parseInt(textualInput.readLine());
        
        List<String> allWordsWithDuplicates = new ArrayList<String>();
        HashMap<String,Integer> wordsWithoutDuplicates = new HashMap<String,Integer>();
        StringBuilder tempWord = new StringBuilder();

        /**
         * Traverses the entirety of the file character by character
         */
        while ((tempCharInt=textualInput.read())!=-1) {
            
            char currChar = (char) tempCharInt; //Current character being read converted from its int symbol
            
            //List used to check when the last three characters are equivalent to "END"
            ArrayList<Character> endCharacters = new ArrayList<Character>();
            endCharacters.add('E');
            endCharacters.add('N');
            endCharacters.add('D');

            /***
             * Base case : Current character is lowercase or is one of the three characters in "END"
             */
            if (Character.isLowerCase(currChar) || endCharacters.contains(currChar)) {
                tempWord.append(currChar);        
            }
            
            else {
                if (!tempWord.toString().equals("END") && !tempWord.toString().equals("")) {
                    allWordsWithDuplicates.add(tempWord.toString());
                    wordsWithoutDuplicates.put(tempWord.toString(), -1);
                } 
                if (tempWord.toString().length()==3) {
                    if (endCharacters.contains(tempWord.charAt(0)) 
                    && endCharacters.contains(tempWord.charAt(1))
                    && endCharacters.contains(tempWord.charAt(2))) {
                        counter++;
                        String[] allWords = new String[allWordsWithDuplicates.size()];
                        allWordsWithDuplicates.toArray(allWords);
                        docAnalyzer(counter, allWords, wordsWithoutDuplicates);
                        allWordsWithDuplicates.clear(); //Empties the list of all words before moving on to the next document 
                        wordsWithoutDuplicates.clear(); //Empties the HashMap of unique words words before moving on to the next document 
                    }
                }
                tempWord.delete(0, tempWord.length()); //Empties the string builder 
            }

            /***
             * Base case : Empties the string builder when we reach the end of a line in the document
             */
            if (currChar=='\n') {
                tempWord.delete(0, tempWord.length());
            }

            /***
             * Base case : Stops traversing the file once it reaches the entered number of documents
             */
            if (counter==inputNumber) {
                break;
            }
        }
    }

    static void docAnalyzer(int documentNumber, String[] allWords, HashMap<String,Integer> uniqueWords) {
        
        int actualP=-1, actualQ=-1; //The interval bounds we're looking for
        int listSize=allWords.length; //Total number of words

        HashMap<String,Integer> tempMap = new HashMap<String,Integer>(); //Used to find an interval that contains all unique words
        int lowest=0, highest=0; 
        int startInterval = 1; //We number the words starting from 1 (1,2,3,...,n)
        int i=0;
        while (i<allWords.length) {

            String tempKey = allWords[i];

            /**
             * Base case : Current word is unique
             */
            if (uniqueWords.containsKey(tempKey)) {
                tempMap.put(tempKey,i+1);
                highest = startInterval;
            }
            /**
             * Base case : The current interval contains all words that are unique
             */
            if (tempMap.size()==uniqueWords.size()) {
                lowest = startInterval;
                for (Integer element : tempMap.values()) {
                    if (element<lowest) {
                        lowest = element;
                    }
                }

                int potentialP=lowest, potentialQ=highest, currIntervalSize=potentialQ-potentialP;
                
                if (currIntervalSize<listSize) { //Ensures that the list size gets redefined as the interval size
                    listSize = currIntervalSize;
                    actualP = potentialP;
                    actualQ = potentialQ;
                }
                if (currIntervalSize==listSize && potentialP<actualP) {
                    actualP = potentialP;
                    actualQ = potentialQ;
                }

            }
            startInterval++;
            i++;
        }
        System.out.println("Document "+documentNumber+": "+actualP+" "+actualQ); //Well-formatted output for the Online Judge
    }

    public static void main(String[] args) throws NumberFormatException, IOException {
        Main test = new Main();
        test.readContentOfTextualFile();
    }
}