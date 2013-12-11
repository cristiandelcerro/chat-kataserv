package chatkata;

import java.util.LinkedList;

public class ServerResponse {
    private int nextSeq;
    private LinkedList<ChatMessage> messages;

    public ServerResponse(){

    }

    public ServerResponse(int nextSeq, LinkedList<ChatMessage> messages){
        this.nextSeq= nextSeq;
        this.messages = messages;
    }

    public int getNextSeq() {
        return nextSeq;
    }

    public void setNextSeq(int nextSeq) {
        this.nextSeq = nextSeq;
    }

    public LinkedList<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(LinkedList<ChatMessage> messages) {
        this.messages = messages;
    }
}