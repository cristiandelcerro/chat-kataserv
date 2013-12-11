package prueba;

/**
 * Created with IntelliJ IDEA.
 * User: cristianbq
 * Date: 11/12/13
 * Time: 10:36
 * To change this template use File | Settings | File Templates.
 */
public class Saying {
    private final long id;
    private final String content;

    public Saying(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}