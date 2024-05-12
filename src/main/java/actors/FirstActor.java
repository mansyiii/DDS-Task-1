package actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import messages.Message;

public class FirstActor extends AbstractActor {

    public static Props props() {
        return Props.create(FirstActor.class, FirstActor::new);
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Message.class, msg-> getContext()
                        .actorOf(HashMapActor.props())
                        .tell(msg, getSelf())
                )
                .matchAny((Object o) -> {})
                .build();
    }
}