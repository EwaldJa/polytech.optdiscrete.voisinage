package algo;

import graph.Solution;
import utils.MathUtils;
import utils.RandUtils;

import java.util.ArrayList;
import java.util.List;

public class TabuSearch extends Algorithm {

    public static final int MAX_ITER_SAME_VALUE = 100;

    private int _iterationNb, _neighbourNb, _tabuNb;
    private List<Solution> _tabuList;

    public TabuSearch(int iterationNb, int neighbourNb, int tabuNb) {
        this._iterationNb = iterationNb;
        this._neighbourNb = neighbourNb;
        this._tabuNb = tabuNb;
        this._tabuList = new ArrayList<>(tabuNb); //Initial capacity
    }

    @Override
    public Solution processCurrent(Solution current) {
        int iterSameValue = 0;
        Solution bestSolution = current.clone();
        double bestDistance = bestSolution.getTotalDistance();
        double oldBestDistance = bestDistance;
        Solution baseSolution = current.clone();
        double baseDistance = baseSolution.getTotalDistance();

        for(int it = 0; it < _iterationNb; it++) {
            Solution bestNeighbour = baseSolution.getBestNeighbour();
            double bestNghbDist = bestNeighbour.getTotalDistance();
            /*
            for(int neighbour = 0; neighbour < _neighbourNb; neighbour++){
                Solution rndmNeighbour = baseSolution.cloneRandom();
                while (_tabuList.contains(rndmNeighbour)) {
                    rndmNeighbour = baseSolution.cloneRandom(); }
                double rndmDist = rndmNeighbour.getTotalDistance();
                if (rndmDist < bestNghbDist) {
                    bestNeighbour = rndmNeighbour;
                    bestNghbDist = rndmDist; } }*/
            if (bestNghbDist > baseDistance) {
                if(_tabuList.size() == _tabuNb) {
                    _tabuList.remove(0);
                    _tabuList.add(baseSolution); }
                else {
                    _tabuList.add(baseSolution); } }
            else if (bestNghbDist < bestDistance) {
                bestSolution = bestNeighbour;
                bestDistance = bestNghbDist; }
            if (oldBestDistance == bestDistance) {
                iterSameValue++;
                if (iterSameValue == MAX_ITER_SAME_VALUE) {
                    return bestSolution; } }
            else {
                iterSameValue = 0; }
            oldBestDistance = bestDistance;
            baseSolution = bestNeighbour;
            baseDistance = bestNghbDist;
            System.out.println("Iteration : " + it + ", bestDistance : " + bestDistance + ", baseDistance : " + baseDistance + ", iterSameValue : " + iterSameValue);
        }
        return bestSolution;
    }
}
