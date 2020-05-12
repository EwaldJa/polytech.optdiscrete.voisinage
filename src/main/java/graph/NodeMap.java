package graph;

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
public class NodeMap {

    private Node _deposit, _lastNode;
    private int _totalOrders;

    /*First Edge of Couple : incoming edge, and the second is the leaving edge (for instance fist one is between n1 and n2 and second one between n2 and n3)*/
    private Map<Node, Couple<Edge, Edge>> _tour;


    public NodeMap(Node deposit) {
        _deposit = deposit;
        _tour = new LinkedHashMap<>();
        Edge initEdge = new Edge(_deposit, _deposit);
        _tour.put(_deposit, new Couple<>(initEdge, initEdge.clone()));
        _lastNode = _deposit;
    }

    public void put(Node n) {
        _totalOrders += n.getOrder();
        Edge last_with_n = _tour.get(_lastNode).getValue();
        last_with_n.changeN2(n);
        Edge n_with_deposit = _tour.get(_deposit).getKey();
        n_with_deposit.changeN1(n);
        _tour.put(n, new Couple<>(last_with_n.clone(), n_with_deposit.clone()));
        _lastNode = n;
    }

    public List<Node> getNodes() { return new ArrayList<>(_tour.keySet()); }

    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        _tour.values().forEach(cp -> edges.add(cp.getKey()));
        return edges;
    }

    public void swap(Node oldN, Node newN) {

    }

    public int getTotalOrders() { return _totalOrders; }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for(Couple<Edge,Edge> cp :_tour.values()) {
            sb.append(cp.getValue().toString()).append("\n");
        }
        return sb.toString();
    }
}
