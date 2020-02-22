package profondeur;
import java.util.ArrayList;

public class etat{
    private ArrayList<Integer> list;
public etat(ArrayList<Integer> list2){
    this.list = new ArrayList<>();
    for(Integer v:list2){
        this.list.add(v);
    }
}

public etat(){
list = new ArrayList<>();
}
    
public ArrayList<etat>  Generer_Etat(){
    ArrayList<etat> Etats_Generer = new ArrayList<>();
    // fils '1'
    etat tmp = new etat(this.getList());
    tmp.getList().add(1);
    Etats_Generer.add(tmp);
    // fils '2'
    etat tmp2 = new etat(this.getList());
    tmp2.getList().add(0);
    Etats_Generer.add(tmp2);

    return Etats_Generer;
}

public int EvaluationSat(Integer[][] ensemble_clauses){
    boolean clause;
    int cpt_clause=0;
    int j;
    for(int i=0;i<ensemble_clauses.length;i++){
        clause = false;
        j=0;
        while(clause==false && j<this.list.size()){
            if(this.list.get(j) == ensemble_clauses[i][j]){
                clause = true;
                cpt_clause++;
                break;
            }else{j++;}


        }
    }
    return cpt_clause;
}
/**
 * @return the list
 */
public ArrayList<Integer> getList() {
    return list;
}

/**
 * @param list the list to set
 */
public void setList(ArrayList<Integer> list) {
    this.list = list;
}

}