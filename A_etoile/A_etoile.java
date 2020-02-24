package A_etoile;

import java.util.ArrayList;

public class A_etoile {

    public static void Algo_globale(Integer[][] ensemble_clauses) {
        long satClauses = 0;
        ArrayList<solution> ouverte = new ArrayList<>();
        solution s = null;
        int indice_max;
        long Max_sat = 0;
        // initialisation
        ouverte = generer_solution_initial(ensemble_clauses);
        while (!(ouverte.isEmpty())) {
            // calculer le Max
            indice_max = Max(ouverte);
            s = ouverte.get(indice_max);
            ouverte.remove(indice_max);

            if (s.il_a_des_voisins()) {
                ouverte.addAll(s.generer_voisins(ensemble_clauses));
            }

            satClauses = s.G(ensemble_clauses);
            if (Max_sat < satClauses) {
                Max_sat = satClauses;
            }
            System.out.println("clauses sat : " + satClauses + "    Max SAT : " + Max_sat);
            if (satClauses == ensemble_clauses.length && !(s.il_a_des_voisins())) {

                break;

            }
        }
        System.out.println("clauses sat : " + satClauses);

    }

    public static ArrayList<solution> generer_solution_initial(Integer[][] ensemble_clauses) {
        int i = 0;
        ArrayList<solution> S0 = new ArrayList<>();
        Integer[] s = null;
        solution etat;
        while (i < ensemble_clauses[0].length) {
            s = new Integer[ensemble_clauses[0].length];
            // valeur X(i) positive
            s[i] = 1;
            etat = new solution(s);
            etat.setFitness(etat.G(ensemble_clauses) + etat.H1(ensemble_clauses));
            S0.add(etat);
            // valeur X(i) negative
            s = new Integer[ensemble_clauses[0].length];
            s[i] = 0;
            etat = new solution(s);
            etat.setFitness(etat.G(ensemble_clauses) + etat.H1(ensemble_clauses));
            S0.add(etat);
            i++;
        }
        return S0;

    }

    // calcule du max
    public static int Max(ArrayList<solution> ouverte) {
        long Max = ouverte.get(0).getFitness();
        int indice_max = 0;
        for (int i = 0; i < ouverte.size(); i++) {
            if (Max < ouverte.get(i).getFitness()) {
                Max = ouverte.get(i).getFitness();
                indice_max = i;
            }
        }
        return indice_max;
    }

}