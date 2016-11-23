package worldofzuul;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A new object of conversation gets created everytime a player meets an NPC
 * This handles all of the question and answers the question has
 * To create the conversation, you need to read the equivalent JSON file
 * The creation of a conversation is done in the method "createWholeConversation"
 * and therefore not in the constructor
 */
public class Conversation{

    private int _currentQuestionNumber; 
    private int _conversationId;
    private UUID _npcId;
    private Question _currentQuestion;
    private ArrayList<Question> _questionList;
    
    public Conversation(int conversationId) {
        this._conversationId = conversationId;
        this._currentQuestionNumber = 0;
        this._questionList = new ArrayList<>();
    }

    /**
     * Creates the whole conversation based on a list of strings coming from a file
     * This is soon to be changed, when JSON starts working
     * @param text the list fetched from a file
     */
    public void createWholeConversation(List<String> text) {
        for(int i=1; i <= text.size(); ) {
            String qText = text.get(i);
            int numOfAns = Character.getNumericValue(text.get(i+1).charAt(0));
            this._questionList.add(new Question(qText, numOfAns));
            
            int count = 1;
            int n = i;
            while(count <= numOfAns) {
                String ansText = text.get(n+3);
                String reactText = text.get(n+4);
                int nextLineNumber = Character.getNumericValue(text.get(n+5).charAt(0));
                String exeLine = text.get(n+6);
                
                this._questionList.get(this._questionList.size()-1).addAnswer(nextLineNumber, ansText, reactText, exeLine);
                
                count++;
                n+=5;
            }
            i += 3 + 5*numOfAns;
        }
        this._currentQuestion = this._questionList.get(this._currentQuestionNumber);

    }

    /**
     * Gets the current question of the conversation to try and find an answer from the parameter
     * @param userAns a string, which is the second word the use typed in along with the command "say"
     */
    public void processAnswer(String userAns) {
        this._currentQuestion.findAnswer(userAns);
    }

    /**
     * Gets all of the possible answers as a probably formatted string
     * @return a string
     */
    public String getPossibleAnswers() {
       return this._currentQuestion.getPossibleAnswers();
    }

    /**
     * Sets the next answer to be equal to the parameter
     * @param questionNumber the number of the question
     */
    public void setNextQuestion(int questionNumber) {
        this._currentQuestionNumber = questionNumber;
        this._currentQuestion = this._questionList.get(this._currentQuestionNumber);
    }
    // ***** GETTERS *****
    public int getConversationId() {
        return this._conversationId;
    }
    
    public UUID getNpcId() {
        return this._npcId;
    }
    
    public String getQText() {
        return this._currentQuestion.getQText();
    }
    // ***** GETTERS END *****
    
    // ***** SETTERS *****
    public void setNpcId(UUID npcId) {
        this._npcId = npcId;
    }
    // ***** SETTERS END *****

    // ***** GETTERS FROM ANSWER *****
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
    // ***** GETTERS FROM ANSWER END *****
}