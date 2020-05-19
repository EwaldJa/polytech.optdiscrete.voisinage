package algo;

import graph.Solution;
import utils.MathUtils;
import utils.RandUtils;

public class SimulatedAnnealing extends Algorithm {

    public static final int MAX_ITER_SAME_VALUE = 100;

    private double _temperature, _coolingFactor;
    private int _neighbourNb;

    public SimulatedAnnealing(double temperature, double coolingFactor, int neighbourNb) {
        this._temperature = temperature;
        this._coolingFactor = coolingFactor;
        this._neighbourNb = neighbourNb;
    }

    @Override
    public Solution processCurrent(Solution current) {
        Solution bestSolution = current.clone();
        double bestDistance = bestSolution.getTotalDistance();
        Solution baseSolution = current.clone();
        double baseDistance = baseSolution.getTotalDistance();
        for(double t = _temperature; t > 1; t *= _coolingFactor) {
            for(int i = 0; i < _neighbourNb; i++) {
                Solution rndmNeighbour = baseSolution.cloneRandom();
                double rndmDist = rndmNeighbour.getTotalDistance();

                if (rndmDist < baseDistance) {
                    baseSolution = rndmNeighbour;
                    baseDistance = rndmDist;
                    if (rndmDist < bestDistance) {
                        bestSolution = rndmNeighbour;
                        bestDistance = rndmDist;
                    } }
                else if (RandUtils.randDouble(0,1) <= MathUtils.exp(baseDistance, rndmDist, t)) {
                    baseSolution = rndmNeighbour;
                    baseDistance = rndmDist;
                }
            }
            System.out.println("Temperature : " + t + ", bestDistance : " + bestDistance + ", baseDistance : " + baseDistance);
        }
        return bestSolution;
    }
}