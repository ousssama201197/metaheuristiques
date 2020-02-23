package acs;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class acs {

    float ants = 35;
    float pheromones[][];
    float proba[][];
    ArrayList<solution> solutions;
    Integer[][] ensemble_clauses;
    int[][] couts;
    boolean find;
    float q;
    float evaporation;
    float pheromone;
    float alpha;
    float beta;
    int cpt;
    float q0 = (float) 0.5;

    public acs(Integer[][] ensemble_clauses) {
        this.pheromone = (float) 0.001;
        this.alpha = (float) 0.4;
        this.beta = (float) 0.6;
        this.evaporation = (float) 0.6;
        this.ensemble_clauses = ensemble_clauses;
        this.ensemble_clauses = ensemble_clauses;
        solutions = new ArrayList<solution>();
        this.proba = new float[75][2];
        this.couts = new int[75][2];
        pheromones = new float[75][2];
        for (int i = 0; i < 75; i++) {
            for (int j = 0; j < 2; j++) {
                pheromones[i][j] = (float) 0.001;
            }
        }
    }

    public boolean algo() {
        solution solution = null;
        cout();
        find = false;
        this.q = new Random().nextFloat();
        calcul_proba(q);
        while (!find) {

            cpt = 0;
            for (int i = 0; i < ants; i++) {
                solution = build();
                solutions.add(solution);

                if (solution.evaluation(ensemble_clauses) == ensemble_clauses.length) {

                    System.out.println("solution");
                    solution.afficher();
                    find = true;

                }
                maj_pheromone_online();
                this.q = new Random().nextFloat();
                calcul_proba(q);
            }

            maj_pheromone_offline();
        }
        return false;
    }

    public void maj_pheromone_offline() {
        solution best = meilleurSolution();
        solutions.removeAll(solutions);
        solutions.add(best);

        System.out.println("///////////////////**///////////////////" + best.getFitness());

        for (int i = 0; i < this.pheromones.length; i++) {
            for (int j = 0; j < this.pheromones[0].length; j++) {

                if (j == (int) best.getSolution().get(i)) {
                    // System.out.println("off pheromones[i][j] : " +couts[i][j]);
                    this.pheromones[i][j] = (1 - evaporation) * this.pheromones[i][j]
                            + (evaporation * this.couts[i][j]);
                    break;
                } else {
                    this.pheromones[i][j] = (1 - evaporation) * this.pheromones[i][j];
                }

            }
        }
    }

    public void cout() {
        solution s = new solution();
        for (int i = 0; i < couts.length; i++) {
            for (int j = 0; j < couts[0].length; j++) {
                if (j == 0) {
                    s.getSolution().set(i, 0);
                } else {
                    s.getSolution().set(i, 1);
                }

                couts[i][j] = s.evaluation(ensemble_clauses);
                s.getSolution().set(i, null);
            }
        }

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

    public void calcul_proba(float q) {
        if (q <= q0) {

            for (int i = 0; i < proba.length; i++) {
                for (int j = 0; j < proba[0].length; j++) {

                    proba[i][j] = (float) (Math.pow(pheromones[i][j], alpha)
                            * Math.pow(couts[i][j], beta));
                }
            }
        } else {
            float somme = somme();
            for (int i = 0; i < proba.length; i++) {
                for (int j = 0; j < proba[0].length; j++) {
                    proba[i][j] = (float) ((Math.pow(pheromones[i][j], alpha)
                            * Math.pow(couts[i][j], beta)) / somme);

                }
            }
        }

    }

    public float somme() {
        float somme = 0;
        for (int i = 0; i < proba.length; i++) {
            for (int j = 0; j < proba[0].length; j++) {
                somme = somme
                        + (float) (Math.pow(pheromones[i][j], alpha) * Math.pow(couts[i][j], beta));

            }
        }
        return somme;
    }

    public solution build() {
        solution solution = new solution();
        int[] indicemax;
        Random rand = new Random();
        solution.getSolution().set(rand.nextInt(75), rand.nextInt(2));
        while (solution.Estcomplete() == false) {
            //   solution.afficher();
            indicemax = Selection();
            if (solution.getSolution().get(indicemax[0]) == null) {
                solution.getSolution().set(indicemax[0], indicemax[1]);
            }

        }
        //return solution.emprove_solution(ensemble_clauses);
        return solution;
    }

    public void maj_pheromone_online() {
        for (int i = 0; i < this.pheromones.length; i++) {
            for (int j = 0; j < this.pheromones[0].length; j++) {
                this.pheromones[i][j] = (1 - this.evaporation) * this.pheromones[i][j] + (this.evaporation * this.pheromone);
            }
        }
    }

    public int[] Selection() {
        float max = (float) 0;
        int[] indicemax = new int[2];
        Random rand = new Random();
        float prob;
        float somme;
        if (q <= q0) {
            for (int i = 0; i < proba.length; i++) {
                for (int j = 0; j < proba[0].length; j++) {

                    if (proba[i][j] > max) {
                        max = proba[i][j];
                        indicemax[0] = i;
                        indicemax[1] = j;
                    }
                }

            }
            proba[indicemax[0]][0] = -2;
            proba[indicemax[0]][1] = -2;
        } else {
            // selection roulette
            somme = 0;
            prob = rand.nextFloat();
            for (int i = 0; i < proba.length; i++) {
                for (int j = 0; j < proba[0].length; j++) {
                    somme = somme + proba[i][j];
                    if (somme > prob) {
                        indicemax[0] = i;
                        indicemax[1] = j;
                        return indicemax;
                    }

                }
            }
        }
        return indicemax;
    }

}
