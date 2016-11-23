package worldofzuul;

/**
 * Holds information regarding each answer. This is associated with the Question class,
 * and both are a part of the Conversation "system".
 */
public class Answer{
    //Attributes
    private int _nextLineNumber;
    private String _referenceWord;		  
    private String _reactText;
    private String _executionLine; 
    
    /**
     * Constructor
     * @param nextLineNumber the next question to proceed to, if this answer is answered
     * @param referenceWord what word are used by the user to "answer" this answer
     * @param reactText what the NPC replies
     * @param executionLine what methods / affects this answer has, if the answer is answered
     */
    public Answer(int nextLineNumber, String referenceWord, String reactText, String executionLine){
        this._referenceWord = referenceWord;
        this._executionLine = executionLine;
        this._nextLineNumber = nextLineNumber;
        this._reactText = reactText;
    }

    // ***** GETTERS *****
    public String getReferenceWord() {
        return this._referenceWord;
    }

    public String getExecutionLine() {
        return this._executionLine;
    }

    public String getReactText() {
        return this._reactText;
    }

    public int getNextLineNumber() {
        return this._nextLineNumber;
    }
    // ***** GETTERS END *****
}