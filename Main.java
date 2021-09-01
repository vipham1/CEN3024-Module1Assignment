public class Main {
    public static void main(String[] args) {
        Thread rabbit = new Thread(new Animal("Rabbit", 0, 5, 150));
        Thread turtle = new Thread(new Animal("Turtle", 0, 3, 100));

        rabbit.start();
        turtle.start();

    }
}
