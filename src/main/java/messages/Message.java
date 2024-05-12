package messages;

import org.slf4j.*;

public class Message {
    private static final Logger LOGGER = LoggerFactory.getLogger(Message.class);
    private MessageType type;
    private String key;
    private Object value;

    public Message(MessageType type, String key, Object value) {
        if (type != MessageType.PUT && type != MessageType.REPLACE){
            LOGGER.error("Invalid Message Type.");
            return;
        }
        this.type = type;
        this.value = value;
        this.key = key;
    }

    public Message(MessageType type, String key) {
        if (type != MessageType.GET && type != MessageType.REMOVE){
            LOGGER.error("Invalid Message Type.");
            return;
        }
        this.type = type;
        this.key = key;
    }

    public Message(MessageType type) {
        if (type != MessageType.CLEAR){
            LOGGER.error("Invalid Message Type.");
            return;
        }
        this.type = type;
    }

    public MessageType getType() {
        return this.type;
    }

    public String getKey() {
        return this.key;
    }

    public Object getValue() {
        return this.value;
    }
}
