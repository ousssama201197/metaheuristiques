package acs;

import java.util.Random;

public class ant implements Runnable {

    value var;
    solution solution;

    public ant(value var, solution solution) {
        this.var = var;
        this.solution = solution;
    }

    @Override
    public void run() {
        Random rand = new Random();
        var.q = rand.nextFloat();
        calcul_proba(var.q);

        build();
        maj_pheromone_online();

        solution.evaluation(var.ensemble_clauses);
        // end work
        var.cpt++;
    }

    public void calcul_proba(float q) {
        if (q <= var.q0) {

            for (int i = 0; i < var.proba.length; i++) {
                for (int j = 0; j < var.proba[0].length; j++) {

                    var.proba[i][j] = (float) (Math.pow(var.pheromones[i][j], var.alpha)
                            * Math.pow(var.couts[i][j], var.beta));
                }
            }
        } else {
            float somme = somme();
            for (int i = 0; i < var.proba.length; i++) {
                for (int j = 0; j < var.proba[0].length; j++) {
                    var.proba[i][j] = (float) ((Math.pow(var.pheromones[i][j], var.alpha)
                            * Math.pow(var.couts[i][j], var.beta)) / somme);

                }
            }
        }

    }

    public float somme() {
        float somme = 0;
        for (int i = 0; i < var.proba.length; i++) {
            for (int j = 0; j < var.proba[0].length; j++) {
                somme = somme
                        + (float) (Math.pow(var.pheromones[i][j], var.alpha) * Math.pow(var.couts[i][j], var.beta));

            }
        }
        return somme;
    }

    public solution build() {
        int[] indicemax;
        Random rand = new Random();
        solution.getSolution().set(rand.nextInt(75), rand.nextInt(2));
        while (solution.Estcomplete() == false) {
            indicemax = Selection();
            if (solution.getSolution().get(indicemax[0]) == null)
                solution.getSolution().set(indicemax[0], indicemax[1]);

        }
        //return solution.emprove_solution(var.ensemble_clauses);
        return solution;
    }

    public void maj_pheromone_online() {
        for (int i = 0; i < var.pheromones.length; i++) {
            for (int j = 0; j < var.pheromones[0].length; j++) {
                var.pheromones[i][j] = (1 - var.evaporation) * var.pheromones[i][j] + (var.evaporation * var.pheromone);

            }
        }
    }

    public int[] Selection() {
        float max = (float) 0;
        int[] indicemax = new int[2];
        Random rand = new Random();
        float prob;
        float somme;
        if (var.q <= var.q0) {
            for (int i = 0; i < var.proba.length; i++) {
                for (int j = 0; j < var.proba[0].length; j++) {

                    if (var.proba[i][j] > max) {
                        max = var.proba[i][j];
                        indicemax[0] = i;
                        indicemax[1] = j;
                    }
                }

            }
            var.proba[indicemax[0]][0] = -2;
            var.proba[indicemax[0]][1] = -2;
        } else {
            // selection roulette
            somme = 0;
            prob = rand.nextFloat();
            for (int i = 0; i < var.proba.length; i++) {
                for (int j = 0; j < var.proba[0].length; j++) {
                    somme = somme + var.proba[i][j];
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
