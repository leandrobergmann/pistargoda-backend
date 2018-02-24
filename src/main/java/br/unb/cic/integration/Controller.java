package br.unb.cic.integration;

import br.unb.cic.goda.model.*;
import br.unb.cic.goda.rtgoretoprism.action.PRISMCodeGenerationAction;
import br.unb.cic.goda.rtgoretoprism.action.RunParamAction;
import br.unb.cic.pistar.model.PistarActor;
import br.unb.cic.pistar.model.PistarLink;
import br.unb.cic.pistar.model.PistarModel;
import br.unb.cic.pistar.model.PistarNode;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
public class Controller {

    @CrossOrigin
    @RequestMapping(value = "/prism-dtmc", method = RequestMethod.POST)
    public ResponseEntity<InputStreamResource> prism(@RequestParam(value = "content") String content) throws IOException {
        Gson gson = new GsonBuilder().create();
        PistarModel model = gson.fromJson(content, PistarModel.class);
        Set<Actor> selectedActors = new HashSet<>();
        Set<Goal> selectedGoals = new HashSet<>();
        transformToTao4meEntities(model, selectedActors, selectedGoals);
        cleanDTMCFolder();
        new PRISMCodeGenerationAction(selectedActors, selectedGoals).run();
        FileOutputStream fos = new FileOutputStream("src/main/webapp/prism.zip");
        ZipOutputStream zos = new ZipOutputStream(fos);
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("dtmc"));
        for (Path path : directoryStream) {
            byte[] bytes = Files.readAllBytes(path);
            zos.putNextEntry(new ZipEntry(path.getFileName().toString()));
            zos.write(bytes, 0, bytes.length);
            zos.closeEntry();
        }
        zos.close();
        cleanDTMCFolder();
        File file = Paths.get("src/main/webapp/prism.zip").toFile();
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/zip"))
                .body(resource);
    }

    @CrossOrigin
    @RequestMapping(value = "/param-dtmc", method = RequestMethod.POST)
    public ResponseEntity<InputStreamResource> param(@RequestParam(value = "content") String content) throws IOException {
        Gson gson = new GsonBuilder().create();
        PistarModel model = gson.fromJson(content, PistarModel.class);
        Set<Actor> selectedActors = new HashSet<>();
        Set<Goal> selectedGoals = new HashSet<>();
        transformToTao4meEntities(model, selectedActors, selectedGoals);
        cleanDTMCFolder();
        new RunParamAction(selectedActors, selectedGoals).run();
        FileOutputStream fos = new FileOutputStream("src/main/webapp/param.zip");
        ZipOutputStream zos = new ZipOutputStream(fos);
        DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get("dtmc"));
        for (Path path : directoryStream) {
            byte[] bytes = Files.readAllBytes(path);
            zos.putNextEntry(new ZipEntry(path.getFileName().toString()));
            zos.write(bytes, 0, bytes.length);
            zos.closeEntry();
        }
        zos.close();
        cleanDTMCFolder();
        File file = Paths.get("src/main/webapp/param.zip").toFile();
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/zip"))
                .body(resource);
    }

    private void cleanDTMCFolder() throws IOException {
        Files.walk(Paths.get("dtmc"), FileVisitOption.FOLLOW_LINKS)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .forEach(f -> {
                    if (f.isFile()) {
                        f.delete();
                    }
                });
    }

    private void transformToTao4meEntities(PistarModel model, Set<Actor> selectedActors, Set<Goal> selectedGoals) {
        List<PistarActor> pistarActors = model.getActors();
        pistarActors.forEach(pistarActor -> {
            Actor actor = new ActorImpl(pistarActor);
            List<PistarNode> notDerivedPlans = new ArrayList<>();
            notDerivedPlans.addAll(pistarActor.getAllPlans());
            model.getLinks().forEach(pistarDependency -> {
                pistarActor.getAllPlans().forEach(pistarPlan -> {
                    if (pistarDependency.getSource().equals(pistarPlan.getId())) {
                        boolean planTargetIsGoal = pistarActor.getAllGoals().stream()
                                .filter(a -> a.getId().equals(pistarDependency.getTarget()))
                                .collect(Collectors.toList()).isEmpty();
                        if (planTargetIsGoal) {
                            notDerivedPlans.remove(pistarPlan);
                        }
                    }
                });
            });
            notDerivedPlans.forEach(notDerivedPlan -> {
                Plan plan = new PlanImpl(notDerivedPlan);
                actor.addToPlanList(plan);
            });
            pistarActor.getAllGoals().forEach(pistarGoal -> {
                Goal goal = fillDecompositionList(model, pistarActor, pistarGoal, new GoalImpl(pistarGoal));
                boolean isRootGoal = model.getLinks().stream().noneMatch(l -> l.getSource().equals(pistarGoal.getId()));
                goal.setRootGoal(isRootGoal);
                actor.addHardGoal(goal);
                if (goal.isSelected()) {
                    selectedGoals.add(goal);
                    if (!selectedActors.contains(actor)) {
                        selectedActors.add(actor);
                    }
                }
            });
        });
    }

    private Goal fillDecompositionList(PistarModel model, PistarActor pistarActor, PistarNode pistarGoal, Goal goal) {
        List<PistarLink> linksToGoal = model.getLinks().stream()
                .filter(d -> d.getTarget().equals(pistarGoal.getId()) && d.getType().contains("Link"))
                .collect(Collectors.toList());
        linksToGoal.forEach(l -> {
            List<PistarNode> sourceGoals = pistarActor.getAllGoals().stream()
                    .filter(g -> l.getSource().equals(g.getId()))
                    .collect(Collectors.toList());
            if (!sourceGoals.isEmpty()) {
                if (l.getType().contains("And")) {
                    goal.setAndDecomposition(true);
                } else if (l.getType().contains("Or")) {
                    goal.setOrDecomposition(true);
                }
            }
            fillMeansToAndEndPlansList(model, pistarActor, pistarGoal, goal);
            sourceGoals.forEach(g -> {
                Goal dependencyGoal = fillDecompositionList(model, pistarActor, g, new GoalImpl(g));
                goal.addToDecompositionList(dependencyGoal);
            });
        });
        return goal;
    }

    private void fillMeansToAndEndPlansList(PistarModel model, PistarActor pistarActor, PistarNode pistarGoal, Goal goal) {
        List<PistarLink> linksToGoal = model.getLinks().stream()
                .filter(l -> l.getTarget().equals(pistarGoal.getId()) && l.getType().contains("Link"))
                .collect(Collectors.toList());
        linksToGoal.forEach(link -> {
            List<PistarNode> sourcePlans = pistarActor.getAllPlans().stream()
                    .filter(p -> link.getSource().equals(p.getId()))
                    .collect(Collectors.toList());
            sourcePlans.forEach(sp -> {
                Plan meansToAnEndPlan = fillEndPlans(model, pistarActor, sp, new PlanImpl(sp));
                goal.addToMeansToAnEndPlans(meansToAnEndPlan);
            });
        });
    }

    private Plan fillEndPlans(PistarModel model, PistarActor pistarActor, PistarNode pistarPlan, Plan meansToAnEndPlan) {
        List<PistarLink> linksToPlan = model.getLinks().stream()
                .filter(l -> l.getTarget().equals(pistarPlan.getId()) && l.getType().contains("Link"))
                .collect(Collectors.toList());
        linksToPlan.forEach(link -> {
            List<PistarNode> sourcePlans = pistarActor.getAllPlans().stream()
                    .filter(p -> link.getSource().equals(p.getId()))
                    .collect(Collectors.toList());
            if (!sourcePlans.isEmpty()) {
                if (link.getType().contains("And")) {
                    meansToAnEndPlan.setAndDecomposition(true);
                } else if (link.getType().contains("Or")) {
                    meansToAnEndPlan.setOrDecomposition(true);
                }
            }
            sourcePlans.forEach(p -> {
                Plan endPlan = fillEndPlans(model, pistarActor, p, new PlanImpl(p));
                meansToAnEndPlan.addToEndPlans(endPlan);
            });
        });
        return meansToAnEndPlan;
    }
}