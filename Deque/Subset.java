/**
 * @author Mandeep Condle
 *
 * Coursera: Algorithms 1
 * Assignment 2: Randomized Queues and Deques
 *
 * Subset.java
 *
 */

public class Subset {

    public static void main(String[] args) {
        int k = Integer.valueOf(args[0]);

        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        String str = StdIn.readString();

        rq.enqueue(str);

        while (!StdIn.isEmpty()) {
            str = StdIn.readString();
            rq.enqueue(str);
        }

        for (int i = 0; i < k; i++) {
            System.out.println(rq.dequeue());
        }

    }
}
