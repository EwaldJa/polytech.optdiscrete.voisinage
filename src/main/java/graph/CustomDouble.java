package graph;

public class CustomDouble implements Cloneable {

    public double _value;

    public CustomDouble(double value) {
        this._value = value;
    }


    @Override
    public CustomDouble clone() {
        return new CustomDouble(this._value);
    }
}
