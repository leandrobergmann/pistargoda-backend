package br.unb.cic.goda.model;

import java.util.List;

public interface Actor extends GeneralEntity {

    ActorType getType();

    List<Goal> getAllGoals();

    void addHardGoal(Goal goal);

    List<Plan> getPlanList();

    void addToPlanList(Plan plan);

}
