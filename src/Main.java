import java.util.ArrayList;
import java.util.Collections;

/**
 * The main class for this project.
 * @author Jonas Knudsen
 */
public class Main {
    /**
     * The main function for this program.
     * Takes an optional command line argument: -reverse for reverse calculation, -random for random calculation.
     * @param args command-line args
     */
    public static void main(String[] args) {
        // create the list of 1024 digits to compute
        ArrayList<Integer> listOfInts = new ArrayList<>();
        for (int i = 1; i <= 1024; i++) {
            listOfInts.add(i);
        }

        // determine whether to sort in place, reverse, or randomize
        // unknown arguments -- keep sorted in place
        if (args.length > 0) {
            if (args[0].equals("-reverse")) {
                Collections.reverse(listOfInts);
            } else if (args[0].equals("-random")) {
                Collections.shuffle(listOfInts);
            }
        }

        // create the ThreadSafeQueue for tasks
        // the object used should be an Integer (just holds the digit index to compute)
        ThreadSafeQueue<Integer> taskQueue = new ThreadSafeQueue<>();

        // create the ThreadSafeQueue for results
        // the object used should be a Pair (of two Integers) (holds digit index as key and value of digit as value)
        ThreadSafeQueue<Pair<Integer, Integer>> resultQueue = new ThreadSafeQueue<>();

        // populate the taskQueue with all of the jobs from listOfInts
        for (Integer i : listOfInts) {
            taskQueue.enqueue(i);
        }

        // set up child threads
        int numCPUs = java.lang.Runtime.getRuntime().availableProcessors();
        Thread[] workerThreads = new Thread[numCPUs];
        for (int i = 0; i < workerThreads.length; i++) {
            workerThreads[i] = new Thread(new WorkerThread(taskQueue, resultQueue));
        }

        // start the timer after problems are set up, but before worker threads begin work
        long startTime = System.currentTimeMillis();

        // start running dequeues on child thread
        for (Thread innerThread : workerThreads) {
            innerThread.start();
        }

        LanternaTerminalThread ltt = new LanternaTerminalThread(resultQueue);
        Thread lttThread = new Thread(ltt);
        lttThread.start();

        // wait for all child threads to exit
        for (Thread innerThread : workerThreads) {
            try {
                innerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // once all child threads are exited, the Lanterna terminal thread must finish its job, then exit
        try {
            lttThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // output (to STDOUT) the total time
        long totalTime = System.currentTimeMillis() - startTime;
        float totalTimeSeconds = (float) totalTime / 1000.0f;
        System.out.println("\n\nFirst 1024 digits of pi calculated in " + totalTimeSeconds + " seconds, " +
                "using " + numCPUs + " CPUs");
    }
}
