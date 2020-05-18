package algo;

import graph.Solution;
import utils.MathUtils;
import utils.RandUtils;

public class SimulatedAnnealing extends Algorithm {

    public static final int MAX_ITER_SAME_VALUE = 10;

    private double _temperature, _coolingFactor, _epsilon;
    private int _neighbourNb;

    public SimulatedAnnealing(double temperature, double coolingFactor, int neighbourNb, double epsilon) {
        this._temperature = temperature;
        this._coolingFactor = coolingFactor;
        this._neighbourNb = neighbourNb;
    }

    @Override
    public Solution processCurrent(Solution current) {
        int iter_same_value = 0;
        Solution bestSolution = current.clone();
        double bestDistance = bestSolution.getTotalDistance();
        Solution baseSolution = current.clone();
        double baseDistance = baseSolution.getTotalDistance();
        for(double t = _temperature; t > 1; t *= _coolingFactor) {
            System.out.println("Temperature : " + t + ", bestDistance : " + bestDistance);
            Solution bestNeighbour = null;
            for(int i = 0; i < _neighbourNb; i++) {
                Solution rndmNeighbour = baseSolution.cloneRandom();
                if (rndmNeighbour.isBetterThan(bestNeighbour)) { bestNeighbour = rndmNeighbour; }
            }
            if (bestNeighbour.getTotalDistance() < baseDistance) {
                baseSolution = bestNeighbour;
                baseDistance = bestNeighbour.getTotalDistance();
                if (bestNeighbour.getTotalDistance() < bestDistance) {
                    bestSolution = bestNeighbour;
                    bestDistance = bestNeighbour.getTotalDistance();
                } }
            else if (RandUtils.randDouble(0,1) <= MathUtils.exp(baseDistance, bestNeighbour.getTotalDistance(), t)) {
                baseSolution = bestNeighbour;
                baseDistance = bestNeighbour.getTotalDistance();
            }
            if (MathUtils.areEquals(bestDistance, baseDistance, _epsilon)) {
                iter_same_value++;
                if (iter_same_value >= 10) {
                    return bestSolution; } }
            else {
                iter_same_value = 0; }
        }

        return bestSolution;
    }
}
