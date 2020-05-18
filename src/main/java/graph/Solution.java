package graph;

import utils.ForEachWrapper;
import utils.RandUtils;

import java.io.Serializable;
import java.util.*;

/**
 * This class models a graph corresponding to the loaded dataset.
 * It is used in the project to find optimum solutions to the Capacitated Vehicule Routing Problem.
 *
 * @author Ewald Janin, Lucas Aupoil
 * @see Edge
 * @see Node
 * @see DeliveryTour
 */
public class Solution implements Serializable, Cloneable {

    private Node _deposit;
    private List<Node> _clients;
    private List<DeliveryTour> _deliveryTours;

    private Solution() {}

    public Solution(Node deposit, List<Node> clients) {
        _deposit = deposit;
        _clients = new ArrayList<>();
        _deliveryTours = new ArrayList<>();

        int nbOfClients = clients.size();
        //TODO : refactorer avec Collections.shuffle
        while (clients.size() > 0 && _clients.size() <= nbOfClients) {
            DeliveryTour dt = new DeliveryTour(_deposit);
            int clientIndex = RandUtils.randInt(0, clients.size());
            while(clients.size() > 0 && clients.get(clientIndex).getOrder() <= dt.remainingSpaceInit()) {
                dt.append(clients.get(clientIndex));
                _clients.add(clients.get(clientIndex));
                clients.remove(clients.get(clientIndex));
                clientIndex = RandUtils.randInt(0, clients.size());
            }
            _deliveryTours.add(dt);
        }
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Node list : \n\n").append(_deposit).append("\n");
        _clients.forEach(node -> sb.append(node).append("\n"));
        sb.append("\nDelivery tours:\n\n");
        _deliveryTours.parallelStream().forEach(dt -> sb.append(dt.toString()));
        return sb.toString();
    }


    public List<DeliveryTour> getDeliveryTours() { return _deliveryTours; }

    public Node getDeposit() { return  _deposit; }

    public double getTotalDistance() {
        ForEachWrapper<Double> val = new ForEachWrapper<>(0.0);
        _deliveryTours.parallelStream().forEachOrdered(dt -> val.value+=(dt.getTotalDistance()));
        return val.value;
    }

    public Solution clone() {
        Solution clone = new Solution();
        clone._deposit = this._deposit.clone();
        clone._clients = new ArrayList<>();
        this._clients.parallelStream().forEachOrdered(node -> clone._clients.add(node.clone()));
        clone._deliveryTours = new ArrayList<>();
        this._deliveryTours.parallelStream().forEachOrdered(dt -> clone._deliveryTours.add(dt.clone()));
        return clone;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution other = (Solution) o;
        return Objects.equals(_deposit, other._deposit) &&
                _clients.equals(other._clients) &&
                _deliveryTours.equals(other._deliveryTours);
    }

}
