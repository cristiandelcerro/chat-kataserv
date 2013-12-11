package chatkata;


public class ChatMessage {
    private String nick;
    private String message;

    static ChatMessage messageFactory(String nick, String message) {
        ChatMessage r = new ChatMessage();
        r.setNick(nick);
        r.setMessage(message);
        return r;
    }

    public String toString() {
        return nick + ": " + message;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getNick() {
        return nick;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
