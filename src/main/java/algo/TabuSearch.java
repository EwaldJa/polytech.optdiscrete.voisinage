package algo;

import display.DisplayResult;
import graph.Solution;
import utils.ForEachWrapper;
import utils.FormatUtils;
import utils.MathUtils;
import utils.RandUtils;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TabuSearch extends Algorithm {

    private int _iterationNb, _tabuNb, _maxIterSameValue;
    private List<Solution> _tabuList;

    public TabuSearch(int iterationNb, int tabuNb, int maxIterSameValue) {
        this._iterationNb = iterationNb;
        this._tabuNb = tabuNb;
        this._maxIterSameValue = maxIterSameValue;
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
            Solution bestNeighbour = baseSolution.getBestNeighbour(_tabuList);
            double bestNghbDist = bestNeighbour.getTotalDistance();
            if (bestNghbDist >= baseDistance) {
                if(_tabuList.size() == _tabuNb) {
                    _tabuList.remove(0);
                    _tabuList.add(baseSolution); }
                else {
                    _tabuList.add(baseSolution); } }
            else if (bestNghbDist < bestDistance) {
                bestSolution = bestNeighbour;
                bestDistance = bestNghbDist; }
            if (FormatUtils.round(oldBestDistance, 5) == FormatUtils.round(bestDistance, 5)) {
                iterSameValue++;
                if (iterSameValue == _maxIterSameValue) {
                    return bestSolution; } }
            else {
                iterSameValue = 0; }
            System.out.println("Iteration : " + it + ", bestDistance : " + bestDistance + ", bestNghbDist : " + bestNghbDist + ", iterSameValue : " + iterSameValue);
            oldBestDistance = bestDistance;
            baseSolution = bestNeighbour;
            baseDistance = bestNghbDist;
        }
        return bestSolution;
    }
}
