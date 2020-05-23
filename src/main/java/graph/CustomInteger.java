package graph;

public class CustomInteger implements Cloneable {

    public int _value;

    public CustomInteger(int value) {
        this._value = value;
    }


    @Override
    public CustomInteger clone() {
        return new CustomInteger(this._value);
    }

    @Override
    public String toString() {
        return ""+_value;
    }
}
