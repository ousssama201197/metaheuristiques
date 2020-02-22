package genetic_algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class solution {

    List<Integer> s = null;
    int fitness;

    public solution(Integer[][] ensemble_clauses) {
        Random rand = new Random();
        s = new ArrayList<Integer>();

        for (int i = 0; i < ensemble_clauses[0].length; i++) {
            s.add(rand.nextInt(2));
        }
        this.cout(ensemble_clauses);

    }

    public solution(Integer[][] ensemble_clauses, List<Integer> s) {
        this.s = s;
        this.cout(ensemble_clauses);

    }

    public void cout(Integer[][] ensemble_clauses) {
        int cpt_clause = 0;
        int j;
        boolean c = false;
        for (int i = 0; i < ensemble_clauses.length; i++) {
            j = 0;
            c = false;
            while (c==false && j < s.size()) {
                    if (s.get(j) == ensemble_clauses[i][j]) {
                        cpt_clause++;
                        c = true;
                    } 
                        j++;

            }
        }
        fitness = cpt_clause;
    }

    public int getFitness() {
        return fitness;
    }

    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    /**
     * @return the s
     */
    public List<Integer> getS() {
        return s;
    }

    /**
     * @param s the s to set
     */
    public void setS(List<Integer> s) {
        this.s = s;
    }

    public void afficher() {
        for (int i = 0; i < s.size(); i++) {

            System.out.println("X " + (i + 1) + " = " + s.get(i));
        }

    }

}
