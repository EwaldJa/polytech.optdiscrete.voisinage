package graph;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents a edge of a graph corresponding to the loaded dataset.
 *
 * @author Ewald Janin, Lucas Aupoil
 * @see Solution
 * @see Node
 * @see DeliveryTour
 */
public class Edge implements Serializable, Cloneable {

    private Node _n1, _n2;
    private double _dist;

    public Edge(Node n1, Node n2) {
        _n1 = n1;
        _n2 = n2;
        _dist = _n1.getDistance(_n2);
    }

    @Override
    public String toString() {
        return _n1 + "---" + _n2;
    }

    public Node getN1() { return _n1; }

    public Node getN2() { return _n2; }

    public double getDist() { return _dist; }

    public boolean contains(Node n) {
        return _n1.equals(n) || _n2.equals(n);
    }

    public double changeN1(Node newN1) {
        _n1 = newN1;
        _dist = _n2.getDistance(_n1);
        return _dist;
    }

    public double changeN2(Node newN2) {
        _n2 = newN2;
        _dist = _n1.getDistance(_n2);
        return _dist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge other = (Edge) o;
        return _n1.equals(other._n1) &&
                _n2.equals(other._n2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_n1, _n2, _dist);
    }

    public Edge clone() { return new Edge(_n1.clone(), _n2.clone()); }
}
