import java.util.LinkedList;

/**
 * Class that uses the standard Java LinkedList library
 * and makes it thread-safe with enqueues and dequeues.
 * @author Jenna Knudsen
 * @param <E> Generic type used for the queue
 */
public class ThreadSafeQueue<E> {
    /**
     * Holds a final reference to the ThreadSafeQueue's internal LinkedList.
     * Because the threads using the ThreadSafeQueue only care about the enqueues and dequeues,
     * not the internal LinkedList itself, we can make this private.
     */
    private final LinkedList<E> internalLinkedList;

    /**
     * Constructor initializes the LinkedList to be empty.
     */
    public ThreadSafeQueue() {
        internalLinkedList = new LinkedList<>();
    }

    /**
     * Enqueues an element while ensuring thread-safety.
     * @param element The element to enqueue
     */
    public synchronized void enqueue(E element) {
        internalLinkedList.add(element);
    }

    /**
     * Dequeues an element while ensuring thread-safety.
     * @return The first element of queue if not empty, null if queue is empty
     */
    public synchronized E dequeue() {
        if (internalLinkedList.size() > 0) {
            return internalLinkedList.pop();
        } else {
            return null;
        }
    }
}
