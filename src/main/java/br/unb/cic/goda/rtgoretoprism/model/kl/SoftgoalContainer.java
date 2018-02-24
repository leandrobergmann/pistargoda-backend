package br.unb.cic.goda.rtgoretoprism.model.kl;

import br.unb.cic.goda.model.Goal;

public class SoftgoalContainer extends ElementContainer {
    private double importance = 0.5;

    public SoftgoalContainer(Goal sg) {
        super(sg);
    }

    public double getImportance() {
        return importance;
    }
}