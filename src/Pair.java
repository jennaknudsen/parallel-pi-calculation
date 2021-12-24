/**
 * Generic class I created for handling key-value pairs
 * @author Jenna Knudsen
 * @param <K> Generic key
 * @param <V> Generic value
 */
public class Pair<K, V> {
    /**
     * Generic key
     */
    public K key;

    /**
     * Generic value
     */
    public V value;

    /**
     * Constructor assigns the key and value
     * @param k Key to assign at initialization
     * @param v Value to assign at initialization
     */
    public Pair(K k, V v) {
        key = k;
        value = v;
    }
}
