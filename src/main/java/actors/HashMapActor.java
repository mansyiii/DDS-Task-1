package actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import messages.Message;
import java.util.HashMap;
import org.slf4j.*;

public class HashMapActor extends AbstractLoggingActor {
    private static HashMap<String, Object> DB = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(HashMapActor.class);

    public static Props props(){
        return Props.create(HashMapActor.class, HashMapActor::new);
    }


    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Message.class, msg->{
                    log().info("Actor {} In progress now", getSender().toString());
                    switch (msg.getType()){
                        case PUT -> put(msg);
                        case GET -> get(msg);
                        case REMOVE -> remove(msg);
                        case REPLACE -> replace(msg);
                        case CLEAR -> clear();
                    }
                })
                .matchAny((Object o)->{})
                .build();
    }

    private void put(Message msg){
        System.out.println("Insert new record for key");
        LOGGER.info("Insert new record for key: {} , value {}", msg.getKey(), msg.getValue());
        DB.put(msg.getKey(), msg.getValue());
    }

    private void get(Message msg){
        LOGGER.info("get record data for key: {}", msg.getKey());
        LOGGER.info("record key {}, data {}", msg.getKey(), DB.get(msg.getKey()));
    }

    private void remove(Message msg){
        DB.remove(msg.getKey());
        LOGGER.warn("record with key {} removed", msg.getKey());
    }

    private void replace(Message msg){
        Object oldVal = DB.get(msg.getKey());
        LOGGER.warn("record with key {}, data {} replaced with {}", msg.getKey(), oldVal, DB.replace(msg.getKey(), msg.getValue()));
    }

    private void clear(){
        DB.clear();
        LOGGER.info("All records have been successfully cleared.");
    }
}