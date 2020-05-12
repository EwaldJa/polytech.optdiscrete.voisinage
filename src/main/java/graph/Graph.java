package graph;

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
public class Graph {

    private Node _transportDepot;
    private List<Node> _clients;
}
