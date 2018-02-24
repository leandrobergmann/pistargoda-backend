package br.unb.cic.pistar.model;

import java.util.List;

public class PistarModel {

    private List<PistarActor> actors;
    private List<PistarDependency> dependencies;
    private List<PistarLink> links;
    private String tool;
    private String istar;
    private String saveDate;
    private PistarDiagram diagram;

    public List<PistarActor> getActors() {
        return actors;
    }

    public List<PistarDependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<PistarDependency> dependencies) {
        this.dependencies = dependencies;
    }

    public void setActors(List<PistarActor> actors) {
        this.actors = actors;
    }

    public List<PistarLink> getLinks() {
        return links;
    }

    public void setLinks(List<PistarLink> links) {
        this.links = links;
    }

    public String getTool() {
        return tool;
    }

    public void setTool(String tool) {
        this.tool = tool;
    }

    public String getIstar() {
        return istar;
    }

    public void setIstar(String istar) {
        this.istar = istar;
    }

    public String getSaveDate() {
        return saveDate;
    }

    public void setSaveDate(String saveDate) {
        this.saveDate = saveDate;
    }

    public PistarDiagram getDiagram() {
        return diagram;
    }

    public void setDiagram(PistarDiagram diagram) {
        this.diagram = diagram;
    }
}
