package zombicide.board.generator;

public class GeneratorTest {

    public static void main(String[] args) {
        System.out.println("Hello world!");
        Leaf root = new Leaf(0, 0, 10, 10);
        boolean split = root.split();
        if (split) {
            System.out.println("Leaf has been split. Children :");
            System.out.println(root.getLeftChild());
            System.out.println(root.getRightChild());
        } else {
            System.out.println("Couldn't split leaf :(");
        }

        System.out.println("================");

        SeedGenerator gen = new SeedGenerator(12, 8);
        String seed = gen.generateRandomSeed();
        System.out.println(seed);
    }
}