package org.fasttrackit.dev.lesson1.numgenerator;

import javax.mail.MessagingException;

/**
 * Created by condor on 29/11/14.
 * FastTrackIT, 2015
 */


/* didactic purposes

Some items are intentionally not optimized or not coded in the right way

FastTrackIT 2015

*/

public class NumGeneratorBusinessLogic {

    public static final int MAX_NUMBER = 10;
    public static final int MIN_NUMBER = 1;

    private boolean isFirstTime = true;
    private boolean successfulGuess;
    private int numberOfGuesses;
    private int generatedNumber;
    private String hint;
    private long startTime=0;
    private long endTime = 1; // hguyfytdtrdygulygukyfytfytfresreaers

    public NumGeneratorBusinessLogic(){
        resetNumberGenerator();
    }

    public void setTime() {
        if(isFirstTime) { startTime=System.currentTimeMillis(); }
        else {endTime=System.currentTimeMillis();}
    }

    public int getNumber() {
        return generatedNumber;
    }
    public int getTime() {
        return (int) (endTime-startTime)/1000;
    }

    public boolean getSuccessfulGuess(){
        return successfulGuess;
    }

    public String getHint(){
        return hint;
    }

    public int getNumGuesses(){
        return numberOfGuesses;
    }

    public boolean isFirstTime(){
        return isFirstTime;
    }

    public void resetNumberGenerator(){
        isFirstTime = true;
        numberOfGuesses = 0;
        hint = "";
    }

    public boolean determineGuess(int guessNumber){
        if (isFirstTime) {
            generatedNumber = NumGenerator.generate(MAX_NUMBER);
            setTime();
            System.out.println("gennr:"+generatedNumber);
            isFirstTime = false;
        }
        numberOfGuesses++;
        if (guessNumber == generatedNumber) {
            hint="";
            successfulGuess = true;
            setTime();
        } else if (guessNumber < generatedNumber) {
            hint = "higher";
            successfulGuess = false;
        } else if (guessNumber > generatedNumber) {
            hint = "lower";
            successfulGuess = false;
        }
        return successfulGuess;

    }


}