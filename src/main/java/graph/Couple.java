package graph;

public class Couple<K,V> {

    private K _key;
    private V _value;

    public Couple(K key, V value) {
        _key = key;
        _value = value;
    }

    public K getKey() { return  _key; }
    public V getValue() { return _value; }

    public void setKey(K _key) { this._key = _key; }
    public void setValue(V _value) { this._value = _value; }
}
