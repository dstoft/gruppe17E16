package worldofzuul;

public class Answer{
    //Attributes
    private int _nextLineNumber;
    private String _referenceWord;		  
    private String _reactText;
    private String _executionLine; 


    //Constructor
    public Answer(int nextLineNumber, String referenceWord, String reactText, String executionLine){
        this._referenceWord = referenceWord;
        this._executionLine = executionLine;
        this._nextLineNumber = nextLineNumber;
        this._reactText = reactText;
    }

    //Getters
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
}