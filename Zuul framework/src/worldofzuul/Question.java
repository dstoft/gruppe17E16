package worldofzuul;

/**
 * Used to hold information for each question in a conversation.
 * It holds information about it self (the text that should be printed),
 * and the possible answers that the user can answer to this player.
 */
import java.util.ArrayList;

public class Question{
    //Attributter
    private String _qText;
    private int _numOfAns;
    private ArrayList<Answer> _answers;
    private Answer _currentAnswer;


    /**
     * Constructor
     * @param qText the text that should be printed when the played "gets" to this question
     * @param numOfAns the amount of possible answers this question has
     */
    public Question(String qText, int numOfAns) {
        this._qText = qText;
        this._numOfAns = numOfAns;
        this._answers = new ArrayList<>();
        this._currentAnswer = null;
    }
    
    /**
     * Finds the answer based on the answer reference that the player has typed in.
     * @param answerRef the string of what the player has typed in
     */
    public void findAnswer(String answerRef) { //Method for finding an answer
        for (Answer answer : this._answers) {
            if (answerRef.equals(answer.getReferenceWord())) {
                this._currentAnswer = answer;
                break;
            } else {
                this._currentAnswer = null;
            }
        }
    }

    /**
     * Gets a string with the possible answers.
     * @return a string that contains the possible answers formatted correctly
     */
    public String getPossibleAnswers() {
        String returnString = "";
        for (Answer answer : this._answers) {
            returnString += answer.getReferenceWord() + ", ";
        }
        return returnString;
    }

    // ***** GETTERS *****
    public String getQText() {
        return this._qText;
    }

    public int getNumOfAns() {
        return this._numOfAns;
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
    // ***** GETTERS END *****
    
    /**
     * Adds a answer with the parameters. See Answers class for description of these parameters.
     * @param nextLineNumber
     * @param referenceWord
     * @param reactText
     * @param exeLine 
     */
    public void addAnswer(int nextLineNumber, String referenceWord, String reactText, String exeLine) {
        this._answers.add(new Answer(nextLineNumber, referenceWord, reactText, exeLine));
    }
}