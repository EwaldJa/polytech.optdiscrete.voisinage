package graph;

/**
 * This class represents a node of a graph corresponding to the loaded dataset.
 * It models a customer, with his position, and his order.
 * The transport depot is a node with empty order.
 *
 * @author Ewald Janin, Lucas Aupoil
 * @see Graph
 * @see Node
 * @see DeliveryTour
 */
public class Node {

    private int _id, _xPos, _yPos, _order;

    public Node(int id, int xPos, int yPos, int order) {
        _id = id;
        _xPos = xPos;
        _yPos = yPos;
        _order = order;
    }

    public double getDistance(Node other) {
        return Math.sqrt( ((this._xPos - other._xPos)^2) + ((this._yPos - other._yPos)^2) );
    }

    public int getId() { return _id; }

    public int getXPos() { return _xPos; }

    public int getYPos() { return _yPos; }

    public int getOrder() { return _order; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        return _id == other._id &&
                _xPos == other._xPos &&
                _yPos == other._yPos &&
                _order == other._order;
    }
}
