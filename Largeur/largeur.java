package Largeur;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class largeur{
public static void main(String[] args) throws IOException  {

    BufferedReader objReader = new BufferedReader(new FileReader("/home/oussama_arch/bio-inspiree/tp/partie1/A_etoile/data.cnf"));
    String strCurrentLine;
     Integer [][] dataset = new Integer [325][75];
     for (int i = 0; i < dataset.length; i++) {
         for (int j = 0; j < dataset[i].length; j++) {
             dataset[i][j] = -1;
         }
     }
     
     int k=0;
     int num;
    while ((strCurrentLine = objReader.readLine()) != null) {
        if(!objReader.equals("")) { 
         String[]  line=strCurrentLine.split(";");
         for (int i = 0; i < line.length; i++) {
             num= Integer.parseInt(line[i]);
             if(num<0) { num=num*-1;num = num-1;dataset[k][num] = 0;}
             else {num = num-1 ;dataset[k][num] = 1;}
         }}
         k++;
         }
         objReader.close();


    System.out.println(dataset.length);
    System.out.println(dataset[0].length);
Date t = new Date();
long t0 = t.getTime();
  recherche_largeur(dataset);
  t = new Date();
  long t1 = t.getTime();

 System.out.println(t1-t0 + "ms");
}

public static void recherche_largeur(Integer[][] ensemble_clauses){
    char[] animation = new char[] {'|', '/','\\','*', '\\'};
    ArrayList<etat> ouverte = new ArrayList<>();
    //initialisation
    etat e = new etat();
    e.getList().add(1);
    ouverte.add(e);
    etat e2 = new etat();
    e2.getList().add(0);
    ouverte.add(e2);
    etat solution = null;
    ArrayList<etat> fermee = new ArrayList<>();
    ArrayList<etat> Etats_Generer = new ArrayList<>();
    int nombre_clauses_sat=0;
    boolean sat = false ;
     int anim = 0;
     while(!(ouverte.isEmpty()))
        {
 
                System.out.print("Processing: " + animation[anim % 5] + "\r");
                anim++;
        solution = ouverte.get(0);
        fermee.add(solution);
        ouverte.remove(0);


        // Generer les etats
        if(solution.getList().size() < ensemble_clauses[0].length){
        Etats_Generer = solution.Generer_Etat();
        // ajouter les nouveaux a la file ouverte
         for(int i=0;i<Etats_Generer.size();i++){
             ouverte.add(Etats_Generer.get(i));
         }
        }


        //evaluation
        nombre_clauses_sat = solution.EvaluationSat(ensemble_clauses);
        if(nombre_clauses_sat == ensemble_clauses.length && 
        solution.getList().size() == ensemble_clauses[0].length){
           sat = true;
            break;
        }
    }
    if(sat == true){
    System.out.println("La solution est :");
    int i=0;
     while(i<solution.getList().size()){
     System.out.println("X"+(i+1)+" = "+solution.getList().get(i));
     i++;
     }
    }else{
        System.out.println("pas de solution");
     }

 


}





}