package worldofzuul;

import java.util.ArrayList;

public class Question{
    //Attributter
    private String _qText;
    private int _numOfAns;
    private ArrayList<Answer> _answers;
    private Answer _currentAnswer;


    //Constructor
    public Question(String qText, int numOfAns) {
        this._qText = qText;
        this._numOfAns = numOfAns;
        this._answers = new ArrayList<>();
        this._currentAnswer = null;
    }



    public void findAnswer(String answerRef) { //Method for finding an answer
        for (Answer answer:this._answers) {	   //for loop, which checks if rightAnswer is an answer
            if (answerRef.equals(answer.getReferenceWord())) {
                this._currentAnswer = answer;
                break;
            } else {
                this._currentAnswer = null;
            }
        }
    }

    //Method for getting possible answer.
    public String getPossibleAnswers() {
        String returnString = ""; //returns a string ""
        for (Answer answer:this._answers) {		  //for loop, which returns a String with possible answers
            returnString += answer.getReferenceWord() + ", "; //and adds a "," between the ReferenceWord = possible answers.
        }
        return returnString;
    }

    
    public String getQText() {
        return this._qText;
    }

    public int getNumOfAns() {
        return this._numOfAns;
    }


    public ArrayList<Answer> getAnswer() {
        return this._answers;
    }


    public boolean hasCurrentAnswer() {
        return this._currentAnswer != null;
    }

    public String getExecutionLine() {
        return this._currentAnswer.getExecutionLine();
    }

    public String getReactText() {
        return this._currentAnswer.getReactText();
    }

    public int getNextLineNumber() {
        return this._currentAnswer.getNextLineNumber();
    }
    
    public void addAnswer(int nextLineNumber, String referenceWord, 
                          String reactText, String exeLine) {
        _answers.add(new Answer(nextLineNumber, referenceWord, reactText, exeLine));
    }
}