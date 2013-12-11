package chatkata;

import com.google.common.base.Optional;
import com.yammer.metrics.annotation.Timed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.LinkedList;

@Path("chat-kata/api/chat")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class ChatResource {

    private ServerResponse serverResponse;
    private  LinkedList<ChatMessage> listChatMessages;

    public ChatResource(){
        serverResponse= new ServerResponse();
        listChatMessages = new LinkedList<ChatMessage>();
    }

    @GET
    @Timed
    public ServerResponse response(@QueryParam("next_seq") Optional<String> paramNextSeq) {

        if(!paramNextSeq.isPresent())
            return serverResponse;

        int intParamNextSeq = Integer.parseInt(paramNextSeq.get().toString());
        if(intParamNextSeq > serverResponse.getNextSeq())
            return emptyServerResponse();
        else
            return createServerResponseFromNextSeq(intParamNextSeq);
    }

    private ServerResponse createServerResponseFromNextSeq(int paramNextSeq){
        LinkedList<ChatMessage> fullListOfMessages = serverResponse.getMessages();
        if(fullListOfMessages==null)
            return emptyServerResponse();

        LinkedList<ChatMessage> messagesFromNextSeq = subListFromNextSeq(paramNextSeq);

        ServerResponse serverResponseFromNextSeq = new ServerResponse(serverResponse.getNextSeq(),
                messagesFromNextSeq);

        return serverResponseFromNextSeq;

    }

    private ServerResponse emptyServerResponse(){
        ServerResponse emptyServerResponse = new ServerResponse();
        emptyServerResponse.setNextSeq(serverResponse.getNextSeq());

        return emptyServerResponse;
    }

    public LinkedList<ChatMessage> subListFromNextSeq(int nextSeq){
        LinkedList<ChatMessage> listOfMessages = serverResponse.getMessages();
        LinkedList<ChatMessage> subList = new LinkedList<ChatMessage>();
        for(int i= nextSeq; i<listOfMessages.size();i++){
            subList.add(listOfMessages.get(i));
        }

        return subList;
    }

    @POST
    public Response add(ChatMessage chatMessage) {
        if(chatMessage == null)
            return Response.status(422).build();

        listChatMessages.add(chatMessage);
        serverResponse.setMessages(listChatMessages);
        serverResponse.setNextSeq(serverResponse.getNextSeq()+1);

        return Response.ok().build();

    }

    public ServerResponse getServerResponse(){
        return serverResponse;
    }

    public void setServerResponse (ServerResponse serverResponse){
        this.serverResponse=serverResponse;
    }

    public LinkedList<ChatMessage> getListChatMessages(){
        return listChatMessages;
    }


}