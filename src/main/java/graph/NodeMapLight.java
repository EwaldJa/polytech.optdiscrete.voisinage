package graph;

import utils.ForEachWrapper;
import utils.RandUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * This class establish the links between the Nodes of a DeliveryTour
 *
 * @author Ewald Janin, Lucas Aupoil
 * @see DeliveryTour
 * @see Solution
 * @see Edge
 * @see Node
 */
public class NodeMapLight implements Serializable {

    private Node _deposit;
    private int _totalOrders;
    private double _totalDistance;

    /*First Edge of Couple : incoming edge, and the second is the leaving edge (for instance fist one is between n1 and n2 and second one between n2 and n3)*/
    private List<Node> _tour;

    private NodeMapLight(){}

    public NodeMapLight(Node deposit) {
        _deposit = deposit;
        _tour = new ArrayList<>();
        _tour.add(_deposit);
        _totalOrders = 0;
        _totalDistance = 0.0;
    }

    /**
     * Adds the node to the delivery tour, at the last position, meaning that the next node following the edge after the one to add will be the origin node
     * @param n the node to add
     */
    public void put(Node n) {
        _totalDistance += (_tour.get(_tour.size() - 1).getDistance(n) +_deposit.getDistance(n) - _tour.get(_tour.size() - 1).getDistance(_deposit));
        _totalOrders += n.getOrder();
        _tour.add(n);
    }

    /**
     * @return a List of all the nodes of this delivery tour
     */
    public List<Node> getNodes() { return _tour; }

    /**
     * @return a List of all the edges of this delivery tour
     */
    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        for (int i = 1; i < _tour.size(); i++) {
            edges.add(new Edge(_tour.get(i-1),_tour.get(i)));
        }
        edges.add(new Edge(_tour.get(_tour.size() - 1),_deposit));
        return edges;
    }


    /**
     * Swaps the two specified Nodes in this delivery tour. They both must be Nodes of this tour.
     * @param n one of the nodes to swap
     * @param m the other node to swap
     */
    public void internalSwapSafe(Node n, Node m) {
        int index_n = _tour.indexOf(n);
        int index_m = _tour.indexOf(m);
        //System.err.println("    ------------");
        //System.err.println("    n: " + n.toString() + ", m : " + m.toString());
        //System.err.println("    n-1: " + _tour.get(index_n - 1).toString() + ", n+1 : " + _tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).toString());
        //System.err.println("    m-1: " + _tour.get(index_m - 1).toString() + ", m+1 : " + _tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).toString());
        //System.err.println("    n-1 to n: " + _tour.get(index_n - 1).getDistance(n) + ", n+1 to n : " + _tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(n) + ", sum : " + (_tour.get(index_n - 1).getDistance(n)+_tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(n)));
        //System.err.println("    m-1 to m: " + _tour.get(index_m - 1).getDistance(m) + ", m+1 to m : " + _tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).getDistance(m) + ", sum : " + (_tour.get(index_m - 1).getDistance(m)+_tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).getDistance(m)));
        //System.err.println("    n-1 to m: " + _tour.get(index_n - 1).getDistance(m) + ", n+1 to m : " + _tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(m) + ", sum : " + (_tour.get(index_n - 1).getDistance(m)+_tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(m)));
        //System.err.println("    m-1 to n: " + _tour.get(index_m - 1).getDistance(n) + ", m+1 to n : " + _tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).getDistance(n) + ", sum : " + (_tour.get(index_m - 1).getDistance(n)+_tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).getDistance(n)));
        //System.err.print("    total dist : " + _totalDistance);

        //_totalDistance -= (_tour.get(index_n - 1).getDistance(n) + _tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(n));
        //System.err.print("    total dist : " + _totalDistance);
        //_totalDistance -= (_tour.get(index_m - 1).getDistance(m) + _tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).getDistance(m));
        //System.err.print("    total dist : " + _totalDistance);
        //_totalDistance += (_tour.get(index_n - 1).getDistance(m) + _tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(m));
        //System.err.print("    total dist : " + _totalDistance);
        //_totalDistance += (_tour.get(index_m - 1).getDistance(n) + _tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).getDistance(n));
        //System.err.println("    total dist : " + _totalDistance);


        Node n_1 = _tour.get(index_n - 1); Node n_2 = _tour.get((index_n + 1 >= _tour.size())?0:index_n + 1);
        Node m_1 = _tour.get(index_m - 1); Node m_2 = _tour.get((index_m + 1 >= _tour.size())?0:index_m + 1);

        double n_1_to_n = n_1.getDistance(n); double n_2_to_n = n_2.getDistance(n);
        double m_1_to_m = m_1.getDistance(m); double m_2_to_m = m_2.getDistance(m);

        double n_1_to_m = n_1.getDistance(m); double n_2_to_m = n_2.getDistance(m);
        double m_1_to_n = m_1.getDistance(n); double m_2_to_n = m_2.getDistance(n);

        _totalDistance += ( (n_1_to_m+ n_2_to_m + m_1_to_n + m_2_to_n) - (n_1_to_n + n_2_to_n + m_1_to_m + m_2_to_m) );


        if (index_m > index_n) { //n first
            _tour.remove(n);
            _tour.remove(m);
            _tour.add(index_n, m);
            _tour.add(index_m, n);
        }
        else {
            _tour.remove(m);
            _tour.remove(n);
            _tour.add(index_m, n);
            _tour.add(index_n, m);
        }
        //System.err.print("    total dist : " + _totalDistance);
        //System.err.println("    total dist : " + _totalDistance);
        //System.err.println("    n-1 to m: " + _tour.get(index_n - 1).getDistance(m) + ", n+1 to m : " + _tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(m) + ", sum : " + (_tour.get(index_n - 1).getDistance(m)+_tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(m)));
        //System.err.println("    m-1 to n: " + _tour.get(index_m - 1).getDistance(n) + ", m+1 to n : " + _tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).getDistance(n) + ", sum : " + (_tour.get(index_m - 1).getDistance(n)+_tour.get((index_m + 1 >= _tour.size())?0:index_m + 1).getDistance(n)));
        //System.err.println("    ------------");
    }

    /**
     * Swaps two Nodes of this delivery tour chosen randomly among all
     */
    public void internalSwapRandom() {
        int size = _tour.size();
        int indexN = RandUtils.randInt(1, size);
        int indexM = RandUtils.randInt(1, size);
        while (indexN == indexM) {
            indexM = RandUtils.randInt(1, size);
        }
        internalSwapSafe(_tour.get(indexN), _tour.get(indexM));
    }

    /**
     * Swaps two Nodes chosen randomly, one among other's, one among this instance's
     * @param other another NodeMap
     */
    public void externalSwapRandom(NodeMapLight other) {
        Node otherNode = other._tour.get(RandUtils.randInt(1, other._tour.size()));
        Node localNode = this._tour.get(RandUtils.randInt(1, this._tour.size()));
        if ((otherNode.getOrder() - localNode.getOrder()) > this.getRemainingSpace() ||
                (localNode.getOrder() - otherNode.getOrder()) > other.getRemainingSpace()) { return; }
        else {
            this.replaceNodeSafe(localNode, otherNode);
            other.replaceNodeSafe(otherNode, localNode); }
    }


    /**
     * Replaces old_n by new_n in the nodes of the delivery tour
     * @param old_n the Node to be replace
     * @param new_n the Node replacing
     */
    private void replaceNodeSafe(Node old_n, Node new_n) {
        int index_old = _tour.indexOf(old_n);
        //System.err.println("    old_n: " + old_n.toString() + ", new_n : " + new_n.toString());
        //System.err.println("    n-1: " + _tour.get(index_old - 1).toString() + ", n+1 : " + _tour.get((index_old + 1 >= _tour.size())?0:index_old + 1).toString());
        //System.err.println("    n-1 to old_n: " + _tour.get(index_old - 1).getDistance(old_n) + ", n+1 to old_n : " + _tour.get((index_old + 1 >= _tour.size())?0:index_old + 1).getDistance(old_n));
        //System.err.println("    n-1 to new_n: " + _tour.get(index_old - 1).getDistance(new_n) + ", n+1 to new_n : " + _tour.get((index_old + 1 >= _tour.size())?0:index_old + 1).getDistance(new_n));
        //System.err.print("    total dist : " + _totalDistance);
        _totalDistance -= (_tour.get(index_old - 1).getDistance(old_n) + _tour.get((index_old + 1 >= _tour.size())?0:index_old + 1).getDistance(old_n));
        //System.err.print("    total dist : " + _totalDistance);
        _tour.remove(old_n);
        _tour.add(index_old, new_n);
        _totalDistance += (_tour.get(index_old - 1).getDistance(new_n) + _tour.get((index_old + 1 >= _tour.size())?0:index_old + 1).getDistance(new_n));
        //System.err.println("    total dist : " + _totalDistance);
        _totalOrders += (new_n.getOrder() - old_n.getOrder());
    }

    /**
     * Removes a Node from other and puts it in current instance
     * @param other another NodeMap
     */
    public void changeNodeTour(NodeMapLight other) {
        Node otherNode = other._tour.get(RandUtils.randInt(1, other._tour.size()));
        if (otherNode.getOrder() > getRemainingSpace()) { return; }
        else {
            this.put(otherNode);
            other.removeSafe(otherNode); }
    }


    /**
     * Removes the specified Node from the delivery tour
     * @param n the Node to delete
     */
    public void removeSafe(Node n) {
        int index_n = _tour.indexOf(n);
        _totalDistance -= (_tour.get(index_n - 1).getDistance(n) + _tour.get((index_n + 1 >= _tour.size())?0:index_n + 1).getDistance(n));
        _totalOrders -= n.getOrder();
        _tour.remove(n);
    }

    /**
     * @return the sum of the orders of all the client of this delivery tour
     */
    public int getTotalOrders() { return _totalOrders; }


    /**
     * @return remaining space as of the capacity defined in DeliveryTour
     * @see DeliveryTour#MAX_CAPACITY
     */
    public int getRemainingSpace() { return DeliveryTour.MAX_CAPACITY - getTotalOrders(); }

    /**
     * @return the total distance between all the clients of this delivery tour
     */
    public double getTotalDistance() {
        return _totalDistance;
    }

    public double calculateTotalDistance() {
        double dist = 0.0;
        for (int i = 1; i < _tour.size(); i++) {
            dist += _tour.get(i-1).getDistance(_tour.get(i));
        }
        dist += _tour.get(_tour.size() - 1).getDistance(_deposit);
        return dist;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (int i = 1; i < _tour.size(); i++) {
            sb.append(_tour.get(i-1).toString()).append(" -> ").append(_tour.get(i).toString()).append("\n");
        }
        sb.append(_tour.get(_tour.size()-1).toString()).append(" -> ").append(_deposit.toString()).append("\n");
        return sb.toString();
    }

    @Override
    protected NodeMapLight clone() {
        NodeMapLight clone = new NodeMapLight();
        clone._totalOrders = this._totalOrders;
        clone._totalDistance = this._totalDistance;
        clone._deposit = this._deposit.clone();
        clone._tour = this._tour;
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeMapLight nodeMap = (NodeMapLight) o;
        return _totalOrders == nodeMap._totalOrders &&
                _totalDistance == nodeMap._totalDistance &&
                Objects.equals(_deposit, nodeMap._deposit) &&
                Objects.equals(_tour, nodeMap._tour);
    }

}
