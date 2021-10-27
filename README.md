## Parallel Pi Calculation

This program calculates Pi to the first 1,024 decimal places. A thread is
created for each CPU core on the machine running this program. Each CPU 
thread calculates a unique digit of Pi over and over until all 1024 digits
have been calculated. The CPU threads display their progress to a Lanterna
terminal as more work is done.

## Notes

The program can be run in the base directory of this repo
by running `gradle build` followed by `java -jar build/libs/Pi.jar`.

This program does **not** use any memoization to store previously computed
values of Pi. Because the BPP formula calculates digits of Pi starting from
the first digit every time, the final digits of Pi take much longer to 
calculate than the initial digits of Pi.

## Sources

* **Shamelessly** copied BBP-Bellard formula from:
    * https://github.com/feltocraig/BBP-Bellard/blob/master/Bpp.java
* Default Java LinkedList documentation:
    * https://docs.oracle.com/javase/7/docs/api/java/util/LinkedList.html
* Get number of CPU cores:
    * https://docs.oracle.com/en/java/javase/14/docs/api/java.base/java/lang/Runtime.html
    
