// Main Class
public class Main {
    public static void main(String[] args) {

        // Create list of threads
        CustomThread[] threads = {
            new CustomThread("Number1"),
            new CustomThread("Number2"),
            new CustomThread("Number3"),
            new CustomThread("Number4"),
            new CustomThread("Number5"),
            new CustomThread("Number6"),
            new CustomThread("Number7"),
            new CustomThread("Number8"),
            new CustomThread("Number9")
        };

        threads[0].setEdges(threads[3]);
        threads[1].setEdges(threads[3]);
        threads[2].setEdges(threads[3]);
        threads[3].setEdges(threads[4], threads[5], threads[6]);
        threads[7].setEdges(threads[4]);
        threads[8].setEdges(threads[6]);

        for (CustomThread thread : threads) {
            thread.start();
        }
    }
}