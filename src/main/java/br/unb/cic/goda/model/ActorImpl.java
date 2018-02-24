package br.unb.cic.goda.model;

import br.unb.cic.goda.model.Actor;
import br.unb.cic.goda.model.ActorType;
import br.unb.cic.goda.model.Goal;
import br.unb.cic.goda.model.Plan;
import br.unb.cic.pistar.model.PistarActor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ActorImpl implements Actor, Serializable {

    protected ActorType type = ActorType.ACTOR_LITERAL;
    private String name;
    private List<Goal> allGoals = new ArrayList<>();
    private List<Plan> planList = new ArrayList<>();

    public ActorImpl(PistarActor pistarActor) {
        name = pistarActor.getText();
    }

    public String getNamePrefix() {
        if (getType().equals(ActorType.ACTOR_LITERAL))
            return "Actor ";
        return "Role ";
    }

    public ActorType getType() {
        return type;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public List<Goal> getAllGoals() {
        return allGoals;
    }

    @Override
    public void addHardGoal(Goal goal) {
        allGoals.add(goal);
    }

    @Override
    public List<Plan> getPlanList() {
        return planList;
    }

    @Override
    public void addToPlanList(Plan plan) {
        planList.add(plan);
    }
}
