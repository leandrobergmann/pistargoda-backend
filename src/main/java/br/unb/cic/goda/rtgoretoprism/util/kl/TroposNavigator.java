package br.unb.cic.goda.rtgoretoprism.util.kl;

import br.unb.cic.goda.model.Actor;
import br.unb.cic.goda.model.Goal;

import java.util.LinkedList;
import java.util.List;

public class TroposNavigator {

    public List<Goal> getRootGoals(Actor a) {
        LinkedList<Goal> rootgoals = new LinkedList<>();
        List<Goal> allGoals = a.getAllGoals();
        for (Goal goal : allGoals) {
            if (goal.isRootGoal()) {
                rootgoals.add(goal);
            }
        }
        return rootgoals;
    }

}