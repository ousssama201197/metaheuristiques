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
    Integer cpt;
    float q0 = (float) 0.65;

    public value(float proba[][], float pheromones[][], Integer[][] ensemble_clauses, int[][] couts, Integer cpt) {
        this.proba = proba;
        this.pheromone = (float) 0.1;
        this.alpha = (float) 0.4;
        this.beta = (float) 0.6;
        this.evaporation = (float) 0.1;
        this.pheromones = pheromones;
        this.cpt = cpt;
        this.ensemble_clauses = ensemble_clauses;
        this.couts = couts;
    }

}
