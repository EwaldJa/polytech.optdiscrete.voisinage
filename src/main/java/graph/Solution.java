package graph;

import utils.RandUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This class models a graph corresponding to the loaded dataset.
 * It is used in the project to find optimum solutions to the Capacitated Vehicule Routing Problem.
 *
 * @author Ewald Janin, Lucas Aupoil
 * @see Edge
 * @see Node
 * @see DeliveryTour
 */
public class Solution implements Serializable {

    private Node _deposit;
    private List<Node> _clients;
    private List<DeliveryTour> _deliveryTours;

    public Solution(Node deposit, List<Node> clients) {
        _deposit = deposit;
        _clients = new ArrayList<>();
        _deliveryTours = new ArrayList<>();

        int nbOfClients = clients.size();
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

    /*
    public DeliveryTour testTour() {
        DeliveryTour dt = new DeliveryTour(_deposit);
        int i = 0;
        while(_clients.get(i).getOrder() <= dt.remainingSpace()) {
            dt.append(_clients.get(i));
            i++;
        }
        dt.internalSwap(_clients.get(0), _clients.get(6));
        return dt;
    }
    */

    //TODO : a method to fill randomly DeliveryTours
}
