package acs;

import java.util.ArrayList;
import java.util.Random;

public class acs {


    float ants=15;
    float pheromones[][];
    float proba[][];
    ArrayList<solution> solutions;
    Integer[][] ensemble_clauses;
    int[][] couts;
    boolean find;
    value var;

    public acs(Integer[][] ensemble_clauses) {
        this.ensemble_clauses = ensemble_clauses;
        solutions = new ArrayList<solution>();
        this.proba = new float[75][2];
        this.couts = new int[75][2];
        pheromones = new float[75][2];
        for (int i = 0; i < 75; i++) {
            for (int j = 0; j < 2; j++) {
                pheromones[i][j] = (float) 0.1;
            }
        }
        var = new value(proba, pheromones, ensemble_clauses, cout());
    }

    public boolean algo() {
        solution solution = null;
        cout();
        Thread ant;
        find = false;
        while (!find) {

            var.cpt = 0;
            for (int i = 0; i < ants; i++) {
                solution = new solution();
                solutions.add(solution);
                ant a = new ant(var, solution);
                ant = new Thread(a);
                ant.start();
            }
            maj_pheromone_offline();
        }
        return false;
    }

    public void maj_pheromone_offline() {
        solution best = meilleurSolution();
        solutions.removeAll(solutions);
        solutions.add(best);
        if (best.getFitness() == ensemble_clauses.length) {
            for (Thread t : Thread.getAllStackTraces().keySet()) {
                if (t.getState() == Thread.State.RUNNABLE && !Thread.currentThread().getName().equals("main"))
                    t.stop();
            }
            System.out.println("solution");
            best.afficher();
            find = true;

        }
 
        System.out.println("///////////////////**///////////////////" + best.getFitness());

        for (int i = 0; i < var.pheromones.length; i++) {
            for (int j = 0; j < var.pheromones[0].length; j++) {
                // System.out.println(best.getSolution());
                if (j == (int) best.getSolution().get(i)) {
                    // System.out.println("off pheromones[i][j] : " +couts[i][j]);
                    var.pheromones[i][j] = (1 - var.evaporation) * var.pheromones[i][j]
                            + (var.evaporation * best.getFitness());
                    // System.out.println("off pheromones[i][j] : " +pheromones[i][j]);
                } else {
                    var.pheromones[i][j] = (1 - var.evaporation) * var.pheromones[i][j];
                }

            }
        }
    }

    public int[][] cout() {
        solution s = new solution();
        for (int i = 0; i < couts.length; i++) {
            for (int j = 0; j < couts[0].length; j++) {
                if (j == 0)
                    s.getSolution().set(i, 0);
                else
                    s.getSolution().set(i, 1);

                couts[i][j] = s.evaluation(ensemble_clauses);
                s.getSolution().set(i, null);
            }
        }
        return couts;
    }

    public solution meilleurSolution() {
        int max = solutions.get(0).getFitness();
        int indicemax = 0;
        for (int i = 0; i < solutions.size(); i++) {
            if (max <= solutions.get(i).getFitness()) {
                max = solutions.get(i).getFitness();
                indicemax = i;
            }
        }
        return solutions.get(indicemax);
    }

}