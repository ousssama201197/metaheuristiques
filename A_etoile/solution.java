package A_etoile;

import java.util.ArrayList;

public class solution {

    private long fitness;
    private Integer[] list;
    private int indice_niveau;

    public solution(Integer[] list2, Integer[][] ensemble_clauses) {
        this.list = list2;
        this.fitness = this.G(ensemble_clauses) + this.H1(ensemble_clauses);

    }

    public solution(Integer[] list2) {
        this.list = list2;

    }

    /**
     * @return the list
     */
    public Integer[] getList() {
        return list;
    }

    /**
     * @return the fitness
     */
    public long getFitness() {
        return fitness;
    }
    
    public int getNiveau() {
        return indice_niveau;
    }

    /**
     * @param fitness the fitness to set
     */
    public void setFitness(long fitness) {
        this.fitness = fitness;
    }

    /**
     * @param list the list to set
     */
    public void setList(Integer[] list) {
        this.list = list;
    }
    

    public boolean il_a_des_voisins() {

        for (int i = 0; i < list.length; i++) {
            if (list[i] == null) {
                this.indice_niveau = i;
                return true;
            }
        }
        return false;
    }

    public ArrayList<solution> generer_voisins(Integer[][] ensemble_clauses) {
        ArrayList<solution> voisins = new ArrayList<>();
        solution etat;
        Integer[] s=null;
        int i = 0;
     while(i< this.list.length){

        if (this.list[i] == null) {
            s = this.list.clone();
            // valeur X(i) positive
            s[i] = 0;
            etat = new solution(s, ensemble_clauses);
            voisins.add(etat);

            s = this.list.clone();
            // valeur X(i) negative
            s[i] = 1;
            etat = new solution(s, ensemble_clauses);
            voisins.add(etat);
        }
         i++;
    }

        return voisins;

    }

    public long G(Integer[][] ensemble_clauses) {
        int cpt_clause = 0;
        int j;
        for (int i = 0; i < ensemble_clauses.length; i++) {
            j = 0;
            while (j < this.list.length) {
                if (this.list[j] != null) {
                    if (this.list[j] == ensemble_clauses[i][j]) {
                        cpt_clause++;
                        break;
                    }
                }
                j++;

            }
        }
        return cpt_clause;
    }


    public long H1(Integer[][] ensemble_clauses) {
        int cpt_clause = 0;
        int j;
        for (int i = 0; i < ensemble_clauses.length; i++) {
            j = 0;
            while (j < this.list.length) {
                if (this.list[j] != null) {
                    if (ensemble_clauses[i][j] == 0 || ensemble_clauses[i][j] == 1) {
                        cpt_clause++;
                        break;
                    }
                }
                j++;
            }
        }
        return cpt_clause;
    }
}