package chatkata;

import api.chatkata.ChatMessage;
import api.chatkata.ChatResource;
import api.chatkata.ServerResponse;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.yammer.dropwizard.testing.ResourceTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;

import java.util.LinkedList;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: cristianbq
 * Date: 11/12/13
 * Time: 16:31
 * To change this template use File | Settings | File Templates.
 */
public class ChatResourceTest extends ResourceTest {

    private ChatResource chatResource;
    private ServerResponse serverResponse;
    private ChatMessage chatMessage;

    @Before
    public void setUp(){
        chatResource = new ChatResource();
        chatMessage = ChatMessage.messageFactory("user1", "msg1") ;
    }

    @Override
    protected void setUpResources() {
        chatResource = new ChatResource();
        addResource(chatResource);
    }

    @Test
    public void correctNextSeq()throws Exception{
        ChatMessage chatmsg1 = ChatMessage.messageFactory("user1", "msg1");
        ChatMessage chatmsg2 = ChatMessage.messageFactory("user2", "msg2");
        ChatMessage chatmsg3 = ChatMessage.messageFactory("user3", "msg3");

        doPost(chatmsg1);
        doPost(chatmsg2);
        doPost(chatmsg3);

        ServerResponse getServerResponse = doGet();

        assertEquals(getServerResponse.getNextSeq(), 3);
    }

    @Test
    public void correctSubList(){
        ChatMessage chatmsg1 = ChatMessage.messageFactory("user1", "msg1");
        ChatMessage chatmsg2 = ChatMessage.messageFactory("user2", "msg2");
        ChatMessage chatmsg3 = ChatMessage.messageFactory("user3", "msg3");

        LinkedList<ChatMessage> fullList = new LinkedList<ChatMessage>();
        fullList.add(chatmsg1);
        fullList.add(chatmsg2);
        fullList.add(chatmsg3);

        ServerResponse serverResponse = new ServerResponse();
        serverResponse.setMessages(fullList);

        chatResource.setServerResponse(serverResponse);

        LinkedList<ChatMessage> subList = new LinkedList<ChatMessage>();
        subList.add(chatmsg2);
        subList.add(chatmsg3);

        assertEquals(chatResource.subListFromNextSeq(1).size(), 2);
        Assert.assertEquals(chatResource.subListFromNextSeq(1), subList);
    }

    @Test
    public void correctSaveMessageDoingPost(){
        ChatMessage chatmsg1 = ChatMessage.messageFactory("user1", "msg1");
        doPost(chatmsg1);

        ServerResponse getServerResponse = doGet();

        ChatMessage msgReceived = getServerResponse.getMessages().peek();

        assertEquals(chatmsg1.getNick(), msgReceived.getNick());
        assertEquals(chatmsg1.getMessage(), msgReceived.getMessage());
    }

    @Test
    public void withoutParameterReturnFullList(){
        ChatMessage chatmsg1 = ChatMessage.messageFactory("user1", "msg1");
        ChatMessage chatmsg2 = ChatMessage.messageFactory("user2", "msg2");
        ChatMessage chatmsg3 = ChatMessage.messageFactory("user3", "msg3");

        doPost(chatmsg1);
        doPost(chatmsg2);
        doPost(chatmsg3);

        ServerResponse getServerResponse = doGet();

        assertEquals(3, getServerResponse.getMessages().size());
    }

    @Test
    public void postResponseIsOK(){
        ChatMessage chatmsg1 = ChatMessage.messageFactory("user1", "msg1");

        assertEquals(doPost(chatmsg1).getStatus(), 200);
    }

    @Test
    public void postResponseIsBad(){
        Assert.assertEquals(doPost(null).getStatus(), 400);
    }

    @Test
    public void getReturnCorrectListWithParam(){
        ChatMessage chatmsg1 = ChatMessage.messageFactory("user1", "msg1");
        ChatMessage chatmsg2 = ChatMessage.messageFactory("user2", "msg2");
        ChatMessage chatmsg3 = ChatMessage.messageFactory("user3", "msg3");

        doPost(chatmsg1);
        doPost(chatmsg2);
        doPost(chatmsg3);

        ServerResponse getServerResponse = doGet(1);

        Assert.assertEquals(2, getServerResponse.getMessages().size());
    }

    private ClientResponse doPost(ChatMessage msg){
        WebResource.Builder builderPost = (WebResource.Builder) client().resource("/chat-kata/api/chat")
                .type(MediaType.APPLICATION_JSON);

        return builderPost.post(ClientResponse.class, msg);
    }

    private ServerResponse doGet(){
        ServerResponse getServerResponse = client().resource("/chat-kata/api/chat").
                type(MediaType.APPLICATION_JSON).get(ServerResponse.class);

        return getServerResponse;
    }

    private ServerResponse doGet(int param){
        ServerResponse getServerResponse = client().resource("/chat-kata/api/chat?next_seq="+param).
                type(MediaType.APPLICATION_JSON).get(ServerResponse.class);

        return getServerResponse;
    }
}