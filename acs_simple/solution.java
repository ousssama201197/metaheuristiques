package acs_simple;

import java.util.ArrayList;

public class solution {

    ArrayList<Integer> solution;
    int fitness;

    public solution() {
        nouvelleSolution();
    }

    /**
     * @return the solution
     */
    public ArrayList<Integer> getSolution() {
        return solution;
    }

    /**
     * @param solution the solution to set
     */
    public void setSolution(ArrayList<Integer> solution) {
        this.solution = solution;
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness(int fitness) {
        this.fitness = fitness;
    }

    /**
     * @return the fitness
     */
    public int getFitness() {
        return fitness;
    }

    public void nouvelleSolution() {
        solution = new ArrayList<Integer>();
        for (int i = 0; i < 75; i++) {
            solution.add(null);
        }
    }

    public boolean Estcomplete() {
        int i = 0;

        while (i < this.getSolution().size()) {
            if (this.getSolution().get(i) == null)
                return false;
            i++;
        }

        return true;

    }

    public Integer evaluation(Integer[][] ensemble_clauses) {
        Integer cpt_clause = 0;
        int j;
        for (int i = 0; i < ensemble_clauses.length; i++) {
            j = 0;
            while (j < solution.size()) {
                if (solution.get(j) != null) {
                    if (solution.get(j) == ensemble_clauses[i][j]) {
                        cpt_clause++;
                        break;
                    }
                }
                j++;

            }
        }
        this.fitness = cpt_clause;
        return cpt_clause;
    }

    public int get_unsat_clauses(Integer[][] ensemble_clauses) {
        Integer cpt_clause = 0;
        int j = 0;
        for (int i = 0; i < ensemble_clauses.length; i++) {
            j = 0;
            while (j < solution.size()) {
                if (solution.get(j) != null) {

                    if (solution.get(j) == ensemble_clauses[i][j]) {
                        cpt_clause++;
                        break;
                    }
                    if (solution.size() == (j + 1))
                        return j;
                }
                j++;

            }
        }
        return -1;
    }

    public void afficher() {
        for (int i = 0; i < this.solution.size(); i++) {
            System.out.println(solution.get(i));
        }
    }

    public solution emprove_solution(Integer[][] ensemble_clauses) {
        int indice = get_unsat_clauses(ensemble_clauses);
        solution tmp = new solution();
        for (int i = 0; i < this.solution.size(); i++) {
            tmp.getSolution().set(i, this.getSolution().get(i));
        }

        if (indice != -1) {
            for (int i = 0; i < ensemble_clauses[indice].length; i++) {
                if (ensemble_clauses[indice][i] == 0 || ensemble_clauses[indice][i] == 1)
                    tmp.getSolution().set(i, ensemble_clauses[indice][i]);

            }
            if (tmp.evaluation(ensemble_clauses) > this.evaluation(ensemble_clauses)) {
                return tmp;
            } else {
                return this;
            }

        }
        return this;
    }

}