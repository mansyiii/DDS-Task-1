import actors.FirstActor;
import akka.actor.*;
import java.util.*;
import messages.*;

public class Main {
    private static HashMap<String, String> optionsMapper = new HashMap<>();

    public static void main(String[] args) {
        optionsMapper.put("1", "put");
        optionsMapper.put("2", "update");
        optionsMapper.put("3", "get");
        optionsMapper.put("4", "delete");
        optionsMapper.put("5", "clear");

        ActorSystem system = ActorSystem.create("DDS-Task");
        ActorRef firstActor = system.actorOf(FirstActor.props(), "actor-1");
        Class<?>[] parameterTypes = new Class<?>[1];
        parameterTypes[0] = ActorRef.class;
        Scanner scanner = new Scanner(System.in);

        while (true) {
            try {
                System.out.println("Choose an option:");
                optionsMapper.forEach((key, value) -> System.out.printf("%s. %s%n", key, value));
                int choice = scanner.nextInt();
                System.out.println(optionsMapper.get(String.valueOf(choice)));
                Main.class.getDeclaredMethod(
                        optionsMapper.get(String.valueOf(choice)), parameterTypes
                ).invoke(Main.class, firstActor);
            } catch (Exception ex) {
                System.out.println(ex);
                break;
            }
        }
    }

    private static void put(ActorRef firstActor) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the key: ");
        String key = scanner.nextLine();
        System.out.print("Enter the value: ");
        String value = scanner.nextLine();

        Message msg = new Message(MessageType.PUT, key, value);
        firstActor.tell(msg, ActorRef.noSender());
    }

    private static void update(ActorRef firstActor) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the key: ");
        String key = scanner.nextLine();
        System.out.print("Enter the value: ");
        String value = scanner.nextLine();
        Message msg = new Message(MessageType.REPLACE, key, value);
        firstActor.tell(msg, ActorRef.noSender());
    }

    private static void get(ActorRef firstActor) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the key: ");
        String key = scanner.nextLine();
        Message msg = new Message(MessageType.GET, key);
        firstActor.tell(msg, ActorRef.noSender());
    }

    private static void delete(ActorRef firstActor) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the key: ");
        String key = scanner.nextLine();
        Message msg = new Message(MessageType.REMOVE, key);
        firstActor.tell(msg, ActorRef.noSender());
    }

    private static void clear(ActorRef firstActor) {
        Message msg = new Message(MessageType.CLEAR);
        firstActor.tell(msg, ActorRef.noSender());
    }
}
