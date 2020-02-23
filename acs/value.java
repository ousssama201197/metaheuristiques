package acs;

public class value {

    float proba[][];
    int[][] couts;
    float q;
    float evaporation;
    float pheromones[][];
    Integer[][] ensemble_clauses;
    float pheromone;
    float alpha;
    float beta;
    int cpt;
    float q0 = (float) 0.65;

    public value(float proba[][], float pheromones[][], Integer[][] ensemble_clauses, int[][] couts) {
        this.proba = proba;
        this.pheromone = (float) 0.1;
        this.alpha = (float) 0.4;
        this.beta = (float) 0.6;
        this.evaporation = (float) 0.45;
        this.pheromones = pheromones;
        this.ensemble_clauses = ensemble_clauses;
        this.couts = couts;
    }

}
