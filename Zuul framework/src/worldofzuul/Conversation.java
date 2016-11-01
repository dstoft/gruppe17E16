package worldofzuul;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Conversation{

    private int _currentLineNumber;
    private int _conversationId;
    private UUID _npcId;
    private Question _currentQuestion;
    private ArrayList<Question> _questionList;
    
    
    public Conversation(int conversationId) {
        this._conversationId = conversationId;
        this._currentLineNumber = 0;
        this._questionList = new ArrayList<>();
            
    }

    public void createWholeConversation(List<String> text) {

        //int n = 2 + 5*(text.get(2));
        for(int i=1; i <= text.size(); i += 2 + 5*(i+1)) {
            String qText = text.get(i);
            int numOfAns = Character.getNumericValue(text.get(i+1).charAt(0));
            this._questionList.add(new Question(qText, numOfAns)); //Prøver at indsætte spørgsmål med samme index, som i .txt
            
            //ArrayList<String> answers;
            int count = 1;
            int n = i;
            while(count <= numOfAns) {
                String ansText = text.get(n+2);
                String reactText = text.get(n+3);
                int nextLineNumber = Character.getNumericValue(text.get(n+4).charAt(0));
                String exeLine = text.get(n+5);
                
                this._questionList.get(this._questionList.size()-1).addAnswer(nextLineNumber, ansText, reactText, exeLine);
                
                count++;
                n+=5;
            }
                        
        }
        this._currentQuestion = this._questionList.get(this._currentLineNumber);

    }

    public void processAnswer(String userAns) {
        this._currentQuestion.findAnswer(userAns);
        
        
    }

    public String getPossibleAnswers() {
       return this._currentQuestion.getPossibleAnswers();
    }

    public void setNextQuestion(int lineNumber) {
        if(lineNumber == -1) {
            System.out.println("Please go away.");
        }
        this._currentLineNumber = lineNumber;
        this._questionList.get(this._currentLineNumber);
    }
    
    public int getConversationId() {
        return this._conversationId;
    }
    
    public UUID getNpcId() {
        return this._npcId;
    }
    
    public String getQText() {
        return this._currentQuestion.getQText();
    }

	
    public boolean hasCurrentAnswer() {
        return this._currentQuestion.hasCurrentAnswer();
    }

    public String getExecutionLine() {
        return this._currentQuestion.getExecutionLine();
    }

    public String getReactText() {
        return this._currentQuestion.getReactText();
    }

    public int getNextLineNumber() {
        return this._currentQuestion.getNextLineNumber();
    }
}