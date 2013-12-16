package api.chatkata;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;

/**
 * Created with IntelliJ IDEA.
 * User: cristianbq
 * Date: 11/12/13
 * Time: 11:31
 * To change this template use File | Settings | File Templates.
 */
public class ChatService extends Service<ChatConfiguration> {
    public static void main(String[] args) throws Exception {
        new ChatService().run(args);
    }


    @Override
    public void initialize(Bootstrap bootstrap) {
        bootstrap.setName("chat");
    }

    @Override
    public void run(ChatConfiguration configuration ,Environment environment) {
        environment.addResource(new ChatResource());
    }

}
