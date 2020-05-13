package utils;

/**
 * This class allows to use variables in lambdas defined out of lambdas
 *
 * @author Ewald Janin, Lucas Aupoil
 */
public class ForEachWrapper<K> {
    public K value;

    public ForEachWrapper(K val) { this.value = val; }
}
