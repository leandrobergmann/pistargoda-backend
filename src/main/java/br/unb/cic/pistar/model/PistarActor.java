package br.unb.cic.pistar.model;

import java.util.List;
import java.util.stream.Collectors;

public class PistarActor extends BasicEntity {

    private List<PistarNode> nodes;

    public List<PistarNode> getAllGoals() {
        return nodes.stream().filter(node -> "istar.Goal".equals(node.getType())).collect(Collectors.toList());
    }

    public List<PistarNode> getAllPlans() {
        return nodes.stream().filter(node -> "istar.Task".equals(node.getType())).collect(Collectors.toList());
    }

    public List<PistarNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<PistarNode> nodes) {
        this.nodes = nodes;
    }
}
