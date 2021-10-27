/**
 * Class implements the WorkerThreads used in this program.
 * The exact number of WorkerThreads created should match the computer's CPU cores.
 * @author Jonas Knudsen
 */
public class WorkerThread implements Runnable {
    /**
     * Holds a final reference to the main program's taskQueue.
     * taskQueue is where the WorkerThread will dequeue the problems
     */
    private final ThreadSafeQueue<Integer> taskQueue;

    /**
     * Holds a final reference to the main program's resultQueue.
     * resultQueue is where the WorkerThread will enqueue the solutions
     */
    private final ThreadSafeQueue<Pair<Integer, Integer>> resultQueue;

    /**
     * Constructor assigns the WorkerThread's taskQueue and resultQueue.
     * @param taskQueue The main program's taskQueue
     * @param resultQueue The main program's resultQueue
     */
    public WorkerThread(ThreadSafeQueue<Integer> taskQueue, ThreadSafeQueue<Pair<Integer, Integer>> resultQueue) {
        this.taskQueue = taskQueue;
        this.resultQueue = resultQueue;
    }

    /**
     * run() function to start working on available problems
     * This code is ran with the call to Thread.start()
     */
    @Override
    public void run() {
        // while there are problems in the queue, pop a problem
        while (true) {
            // returns null if out of problems, returns an Integer with a problem index if exists
            Integer thisTask = taskQueue.dequeue();

            // when we hit a null, we are out of problems to solve
            // exit the loop
            if (thisTask == null) {
                break;
            }

            int calculatedDigitOfPi = Bpp.getSingleDigit(thisTask);

            // enqueue the result when done calculating
            resultQueue.enqueue(new Pair<>(thisTask, calculatedDigitOfPi));
        }
    }
}
