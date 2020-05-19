package algo;

import graph.Solution;
import utils.MathUtils;
import utils.RandUtils;

public class SimulatedAnnealing extends Algorithm {

    public static final int MAX_ITER_SAME_VALUE = 50;

    private double _temperature, _coolingFactor, _epsilon;
    private int _neighbourNb;

    public SimulatedAnnealing(double temperature, double coolingFactor, int neighbourNb, double epsilon) {
        this._temperature = temperature;
        this._coolingFactor = coolingFactor;
        this._neighbourNb = neighbourNb;
        this._epsilon = epsilon;
    }

    @Override
    public Solution processCurrent(Solution current) {
        int iter_same_value = 0;
        Solution bestSolution = current.clone();
        double bestDistance = bestSolution.getTotalDistance();
        Solution baseSolution = current.clone();
        double baseDistance = baseSolution.getTotalDistance();
        double oldBaseDistance = baseDistance;
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
                    //System.err.println("    Exploration, baseDistance : " + baseDistance + " expo: " + MathUtils.exp(baseDistance, rndmDist, t));
                }
            }
/*
            if (MathUtils.areEquals(oldBaseDistance, baseDistance, _epsilon)) {
                System.out.println("    Iter same value : "+iter_same_value + ", oldBaseDistance : " + oldBaseDistance);
                iter_same_value++;
                if (iter_same_value >= MAX_ITER_SAME_VALUE) {
                    return bestSolution; } }
            else {
                iter_same_value = 0; }
            oldBaseDistance = baseDistance;*/

            System.out.println("Temperature : " + t + ", bestDistance : " + bestDistance + ", baseDistance : " + baseDistance);
        }
        return bestSolution;
    }
}
