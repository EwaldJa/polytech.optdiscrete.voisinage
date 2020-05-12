package graph;

import java.io.Serializable;
import java.util.List;

/**
 * This class models a delivery tour, with a subset of the Nodes of a Solution
 *
 * @author Ewald Janin, Lucas Aupoil
 * @see NodeMap
 * @see Solution
 * @see Edge
 * @see Node
 */
public class DeliveryTour implements Serializable {

    public static final int MAX_CAPACITY = 100;

    private NodeMap _tour;

    public DeliveryTour(Node deposit) {
        _tour = new NodeMap(deposit);
    }

    public DeliveryTour append(Node n) {
        _tour.put(n);
        return this;
    }

    public int remainingSpace() { return MAX_CAPACITY - _tour.getTotalOrders(); }

    public int getTotalOrders() { return _tour.getTotalOrders(); }

    public List<Node> getNodes() { return _tour.getNodes(); }

    public List<Edge> getEdges() { return _tour.getEdges(); }

    @Override
    public String toString() {
        return "Remaining capacity : " + remainingSpace() + "\n" + _tour.toString();
    }
}
