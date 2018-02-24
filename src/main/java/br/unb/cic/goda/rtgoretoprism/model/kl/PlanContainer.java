package br.unb.cic.goda.rtgoretoprism.model.kl;

import br.unb.cic.goda.model.Plan;

import java.util.ArrayList;

public class PlanContainer extends RTContainer {
    private ArrayList<GoalContainer> meGoals;
    private ArrayList<PlanContainer> parentlist;

    public PlanContainer(Plan p) {
        super(p);
        meGoals = new ArrayList<>();
        parentlist = new ArrayList<>();
        this.addFulfillmentConditions(p.getCreationProperty());
    }

    public void addMEGoal(GoalContainer gc) {
        meGoals.add(gc);
    }

    public PlanContainer addDecomp(PlanContainer child) {
        plans.add(child);
        child.setRoot(this);
        if (decomposition == Const.OR || decomposition == Const.ME) {
            //mm: 'assert' commented to make ME goals possible
            // assert decomposition == Const.OR;// otherwise there is an error elsewhere!
            // for this goals dispatch-plans are created (not needed for AND-goals)
            // (needed to add more triggering goals to one dispatch plan)
            child.addParent(this);
        }
        return child;
    }

    public ArrayList<GoalContainer> getMEGoals() {
        return meGoals;
    }

    private void addParent(PlanContainer pc) {
        parentlist.add(pc);
    }

    @Override
    public String getElId() {
        return getUid() + '_' + super.getElId();
    }

    @Override
    public String getClearElId() {
        return (getUid() + '_' + super.getElId()).replace(".", "_");
    }

    @Override
    public String getClearElName() {
        String rtRegex = getRtRegex() != null ? getRtRegex() : "";
        StringBuilder sb = new StringBuilder(getUid() + '_');
        for (String word : getName().split("_")) {
            if (word.isEmpty())
                continue;
            StringBuilder sbb = new StringBuilder(word);
            sbb.setCharAt(0, Character.toUpperCase(word.charAt(0)));
            sb.append(sbb);
        }
        return sb.toString().replaceAll("[:\\.-]", "_").replace("[" + rtRegex.replace(".", "_") + "]", "");
    }

    public void setRoot(RTContainer root) {
        super.setRoot(root);
        setUid(root.getUid());
    }
}