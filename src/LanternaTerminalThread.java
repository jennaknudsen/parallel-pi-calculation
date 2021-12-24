import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

/**
 * Class implements Runnable, used to create a Lanterna terminal
 * to print the digits of Pi to the screen.
 * @author Jenna Knudsen
 */
public class LanternaTerminalThread implements Runnable {
    /**
     * Holds a final reference to the resultQueue used by the entire program
     */
    private final ThreadSafeQueue<Pair<Integer, Integer>> resultQueue;

    /**
     * Used to keep track of how many digits have been outputted to screen so far.
     * Max digits outputted: 1024
     */
    private int digitsOutputted = 0;

    /**
     * Constructor sets the resultQueue to the main resultQueue
     * @param resultQueue A reference to the program's main resultQueue
     */
    public LanternaTerminalThread(ThreadSafeQueue<Pair<Integer, Integer>> resultQueue) {
        this.resultQueue = resultQueue;
    }

    /**
     * run() function to display the digits of pi on a separate thread
     * This code is ran with the call to Thread.start()
     */
    @Override
    public void run() {
        DefaultTerminalFactory defaultTerminalFactory = new DefaultTerminalFactory();

        Terminal terminal = null;
        try {
            terminal = defaultTerminalFactory.createTerminal();
            terminal.clearScreen();

            // write the '3.' characters
            terminal.setCursorPosition(0, 1);
            terminal.putCharacter('3');
            terminal.setCursorPosition(1, 1);
            terminal.putCharacter('.');
            terminal.flush();

            while (digitsOutputted < 1024) {
                Pair<Integer, Integer> result = resultQueue.dequeue();
                // if we get a null here, the worker threads are all busy at work
                // not necessarily completely done calculating digits; just have nothing to output for now
                // just continue the loop
                if (result == null) {
                    continue;
                }

                // must subtract 1 from key, as the key is 1-index based instead of 0-index based
                int xPosition = ((result.key - 1) % 64) + 2;
                int yPosition = ((result.key - 1) / 64) + 1;

                terminal.setCursorPosition(xPosition, yPosition);
                // convert the value of pi at that index to a char
                terminal.putCharacter(result.value.toString().charAt(0));
                terminal.flush();

                digitsOutputted++;
            }

            // at the end of the thread execution, put the cursor at 0, 18
            terminal.setCursorPosition(0, 18);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
