package graph;

import utils.ForEachWrapper;
import utils.RandUtils;

import java.io.Serializable;
import java.util.*;

/**
 * This class establish the links between the Nodes of a DeliveryTour
 *
 * @author Ewald Janin, Lucas Aupoil
 * @see DeliveryTour
 * @see Solution
 * @see Edge
 * @see Node
 */
public class NodeMap implements Serializable {

    private Node _deposit, _lastNode;
    private int _totalOrders;

    /*First Edge of Couple : incoming edge, and the second is the leaving edge (for instance fist one is between n1 and n2 and second one between n2 and n3)*/
    private FlexMap<Node, Couple<Edge, Edge>> _tour;

    private NodeMap(){}

    public NodeMap(Node deposit) {
        _deposit = deposit;
        _tour = new FlexMap<>();
        Edge initEdge = new Edge(_deposit, _deposit);
        _tour.put(_deposit, new Couple<>(initEdge, initEdge.clone()));
        _lastNode = _deposit;
    }

    /**
     * Adds the node to the delivery tour, at the last position, meaning that the next node following the edge after the one to add will be the origin node
     * @param n the node to add
     */
    public void put(Node n) {
        _totalOrders += n.getOrder(); /*keeps tracking of capacity up to date*/
        Edge last_with_n = _tour.get(_lastNode).getValue();
        last_with_n.changeN2(n); /*makes the node that was the last before be the one preceding the node we are adding*/
        Edge n_with_deposit = _tour.get(_deposit).getKey();
        n_with_deposit.changeN1(n); /*makes the departing edge of the newly added node leading to the origin node*/
        _tour.put(n, new Couple<>(last_with_n.clone(), n_with_deposit.clone()));
        _lastNode = n;
    }

    /**
     * @return a List of all the nodes of this delivery tour
     */
    public List<Node> getNodes() { return new ArrayList<>(_tour.keySet()); }

    /**
     * @return a List of all the edges of this delivery tour
     */
    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        _tour.values().parallelStream().forEachOrdered(cp -> edges.add(cp.getValue()));
        return edges;
    }

    /**
     * Swaps the two specified Nodes in this delivery tour. They both must be Nodes of this tour.
     * @param n1 the nodes to swap
     * @param n2 the nodes to swap
     */
    public void internalSwap(Node n1, Node n2) {
        Couple<Edge, Edge> n1_edges = null;
        Couple<Edge, Edge> n2_edges = null;
        try {
            if (n1.equals(_lastNode)) {
                _lastNode = n2; }
            else if (n2.equals(_lastNode)) {
                _lastNode = n1; }
            n1_edges = _tour.get(n1);
            n2_edges = _tour.get(n2);
            /*Replaceing n1 by n2*/
            _tour.get(n1_edges.getKey().getN1()).getValue().changeN2(n2); /*edge between preceding node of n1 and n1 now lead to n2*/
            _tour.get(n1_edges.getValue().getN2()).getKey().changeN1(n2); /*edge between n1 and following node of n1 now starts from n2*/
            n1_edges.getKey().changeN2(n2);
            n1_edges.getValue().changeN1(n2);
            /*Replaceing n2 by n1*/
            Edge key = n2_edges.getKey();
            Node keyn1 = key.getN1();
            Couple<Edge,Edge> couple = _tour.get(keyn1);
            Edge value = couple.getValue();
            Node valuen2 = value.getN2();
            value.changeN2(n1);



            //_tour.get(n2_edges.getKey().getN1()).getValue().changeN2(n1); /*edge between preceding node of n2 and n2 now lead to n1*/
            _tour.get(n2_edges.getValue().getN2()).getKey().changeN1(n1); /*edge between n2 and following node of n2 now starts from n1*/
            n2_edges.getKey().changeN2(n1);
            n2_edges.getValue().changeN1(n1);
            _tour.swapKeys(n1, n2);

        } catch (Exception e) {
            System.err.println("internalSwap");
            e.printStackTrace();
            System.err.println(this.toString());
            System.err.println(n1_edges);
            System.err.println(n1_edges.getKey().toString());
            System.err.println(n1_edges.getValue().toString());
            System.err.println(n2_edges);
            System.err.println(n2_edges.getKey().toString());
            System.err.println(n2_edges.getValue().toString());
            System.err.println(n1.toString());
            System.err.println(n2.toString());
        }
    }

    /**
     * Swaps two Nodes of this delivery tour chosen randomly among all
     */
    public void internalSwapRandom() {
        int indexN1 = RandUtils.randInt(1, _tour.size());
        int indexN2 = RandUtils.randInt(1, _tour.size());
        while (indexN1 == indexN2) {
            indexN2 = RandUtils.randInt(1, _tour.size());
        }
        List<Node> nodes = new ArrayList<>(_tour.keySet());
        internalSwap(nodes.get(indexN1), nodes.get(indexN2));
    }

    /**
     * Swaps two Nodes chosen randomly, one among other's, one among this instance's
     * @param other another NodeMap
     */
    public void externalSwapRandom(NodeMap other) {
        Node otherNode = new ArrayList<>(other._tour.keySet()).get(RandUtils.randInt(1, other._tour.size()));
        Node localNode = new ArrayList<>(this._tour.keySet()).get(RandUtils.randInt(1, this._tour.size()));
        this.replaceNode(localNode, otherNode);
        other.replaceNode(otherNode, localNode);
    }

    /**
     * Replaces old_n by new_n in the nodes of the delivery tour
     * @param old_n the Node to be replace
     * @param new_n the Node replacing
     */
    private void replaceNode(Node old_n, Node new_n) {
        Couple<Edge, Edge> old_edges = null;
        try {
            if (old_n.equals(_lastNode)) {
                _lastNode = new_n; }
            old_edges = _tour.get(old_n);
            _tour.get(old_edges.getKey().getN1()).getValue().changeN2(new_n);
            _tour.get(old_edges.getValue().getN2()).getKey().changeN1(new_n);
            old_edges.getKey().changeN2(new_n);
            old_edges.getValue().changeN1(new_n);
            _tour.replaceKey(old_n, new_n);
        }catch (Exception e) {
            System.err.println("replaceNode");
            e.printStackTrace();
            System.err.println(this.toString());
            System.err.println(old_edges.toString());
            System.err.println(old_edges.getKey().toString());
            System.err.println(old_edges.getValue().toString());
            System.err.println(old_n);
            System.err.println(new_n);
        }
    }

    /**
     * Removes a Node from other and puts it in current instance
     * @param other another NodeMap
     */
    public void changeNodeTour(NodeMap other) {
        Node otherNode = null;
        try {
            otherNode = new ArrayList<>(other._tour.keySet()).get(RandUtils.randInt(1, other._tour.size()));
            this.put(otherNode);
            other.remove(otherNode);
        } catch (Exception e) {
            System.err.println("changeNodeTour");
            e.printStackTrace();
            System.err.println(this.toString());
            System.err.println(other.toString());
            System.err.println(otherNode);
        }
    }

    /**
     * Removes the specified Node from the delivery tour
     * @param n the Node to delete
     */
    public void remove(Node n) {
        Couple<Edge, Edge> n_edges = _tour.get(n);
        if (n.equals(_lastNode)) {
            _lastNode = n_edges.getKey().getN1();
        }
        _tour.get(n_edges.getKey().getN1()).getValue().changeN2(n_edges.getValue().getN2());
        _tour.get(n_edges.getValue().getN2()).getKey().changeN1(n_edges.getKey().getN1());
        _tour.remove(n);
        _totalOrders -= n.getOrder();
    }

    /**
     * @return the sum of the orders of all the client of this delivery tour
     */
    public int getTotalOrders() { return _totalOrders; }

    public double getTotalDistance() {
        ForEachWrapper<Double> sum = new ForEachWrapper<>(0.0);
        getEdges().parallelStream().forEachOrdered(edge -> sum.value+=(edge.getDist()));
        return sum.value;
    }


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        getEdges().parallelStream().forEachOrdered(edge -> sb.append(edge.toString()).append("\n"));
        return sb.toString();
    }

    @Override
    protected NodeMap clone() {
        NodeMap clone = new NodeMap();
        clone._totalOrders = this._totalOrders;
        clone._deposit = this._deposit.clone();
        clone._lastNode = this._lastNode.clone();
        clone._tour = this._tour.clone();
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeMap nodeMap = (NodeMap) o;
        return _totalOrders == nodeMap._totalOrders &&
                Objects.equals(_deposit, nodeMap._deposit) &&
                Objects.equals(_lastNode, nodeMap._lastNode) &&
                Objects.equals(_tour, nodeMap._tour);
    }

}
