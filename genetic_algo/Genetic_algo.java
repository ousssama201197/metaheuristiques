package genetic_algo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Date;


public class Genetic_algo {

  List<solution> population = new ArrayList<solution>();
  Integer[][] ensemble_clauses = null;
  int taille_population;

  public Genetic_algo( Integer[][] ensemble_clauses,  int taille_population) {
    this.taille_population = taille_population;
    this.ensemble_clauses = ensemble_clauses;
    init();
  }

  public void init() {
    for (int i = 0; i < taille_population; i++) {
      population.add(new solution(ensemble_clauses));
    }
  }

  public int gen( long t0, float Mutation_rate, float Crossover_rate, long timer) {
    Date  t = new Date();
    List<solution> parents = new ArrayList<solution>();
     ArrayList<solution> enfants = new ArrayList<solution>();
    int NombreDeGenerationsEffectuees = 1;
  
    while ((t.getTime() - t0) < timer ) {
      //tri_population();
      
      parents =  max_fitness(taille_population / 2);
      ;////////////////////////////////// * /****/ //////// */
      // crossover

      for (int i = 0; i < parents.size() - 1; i++) {
        List<Integer> enfant1 = new ArrayList<Integer>();
        List<Integer> enfant2 = new ArrayList<Integer>();


     //   int position = rand.nextInt(parents.size());
       //  solution parent1 = parents.get(i);
        //parents.remove(position);

       // position = rand.nextInt(parents.size());
//solution parent2 = parents.get(i+1);
       //parents.remove(position);
   
          solution parent1 = parents.get(i);
          for (int j = 0; j < parent1.getS().size(); j++) {
            if (j <= (int) parent1.getS().size() / 2) {
              enfant1.add(parent1.getS().get(j));
            }else{
              enfant2.add(parent1.getS().get(j));
            }
          }
          solution parent2 = parents.get(i+1);
          for (int j = 0; j < parent2.getS().size(); j++) {
            if (j <= (int) parent2.getS().size() / 2) {
              enfant2.add(0,parent2.getS().get(j));
            }else{
              enfant1.add(parent2.getS().get(j));
            }
  
          }


        
        enfants.add(new solution(ensemble_clauses, enfant1)); 
        enfants.add(new solution(ensemble_clauses, enfant2));
      }
      // evaluationf
      mutation(enfants,Mutation_rate);
      population.addAll(enfants);
   //   tri_population();

      population =  max_fitness(taille_population);

  
      
      System.out.println(population.get(0).getFitness());

      if (population.get(0).getFitness() == ensemble_clauses.length) {
        for(int i=0;i<population.get(0).getS().size();i++){
          System.out.println("X "+(i+1)+" + " +population.get(0).getS().get(i));
      }
        System.out.println("solution");
        break;
      }

      NombreDeGenerationsEffectuees++;
      t = new Date();
    }

    for(int i=0;i<population.get(0).getS().size();i++){
      System.out.println("X "+(i+1)+" + " +population.get(0).getS().get(i));
    }
    System.out.println(population.get(0).getFitness());

    return NombreDeGenerationsEffectuees;
  }

  public ArrayList<solution> max_fitness(int taille) {
    ArrayList<solution> new_population = new ArrayList<solution>();
    ArrayList<solution> old_population = new ArrayList<solution>();
    old_population.addAll(this.population);
    int max;
    int indiceMax = 0;
    for (int j = 0; j < taille; j++) {
      max = old_population.get(0).getFitness();
      indiceMax = 0;
      for (int i = 0; i < old_population.size(); i++) {
        if (old_population.get(i).getFitness() > max) {
          max = old_population.get(i).getFitness();
          indiceMax = i;
        }
      }
      new_population.add(old_population.get(indiceMax));
      old_population.remove(indiceMax);

    }
    return new_population;

  }


  public void tri_population() {
    solution tmp;
    int j;
    for (int i = 0; i < this.population.size()-1; i++) {
      j = i+1;
      while(j<this.population.size()) {
        if (this.population.get(i).getFitness() < this.population.get(j).getFitness()) {
          tmp=this.population.get(j);
          this.population.set(j,this.population.get(i));
          this.population.set(i,tmp);
        }
        j++;

      }
 

    }
  

  }
public List<Integer> mutation(List<solution> s ,  float Mutation_rate){
   Random rand = new Random();
   List<Integer> enfant = null ;
  int i,k=0;
while(k < s.size()){
i=0;
  enfant = s.get(k).getS();
  while(i < enfant.size()){
    if (rand.nextFloat() < Mutation_rate ){
      if (enfant.get(i) == 0)
      enfant.set(i, 1);
      else
      enfant.set(i, 0);
      //break;
    }
      i++;

    }
    k++;
  }


return enfant;
}



/*
for (int i = 0; i < parents.size() - 1; i++) {
        List<Integer> enfant1 = new ArrayList<Integer>();
        List<Integer> enfant2 = new ArrayList<Integer>();
        solution parent1 = parents.get(i);
        for (int j = 0; j < parent1.getS().size(); j++) {
          if (j <= (int) parent1.getS().size() / 2) {
            enfant1.add(parent1.getS().get(j));
          }else{
            enfant2.add(parent1.getS().get(j));
          }
        }
        solution parent2 = parents.get(i+1);
        for (int j = 0; j < parent2.getS().size(); j++) {
          if (j <= (int) parent2.getS().size() / 2) {
            enfant2.add(0,parent2.getS().get(j));
          }else{
            enfant1.add(parent2.getS().get(j));
          }

        }
        // mutation enfant 1
        enfant1 = mutation(enfant1,Mutation_rate);
        enfant2 = mutation(enfant2,Mutation_rate);
 
        enfants.add(new solution(ensemble_clauses, enfant1)); 
        enfants.add(new solution(ensemble_clauses, enfant2));
      }




*/


/*
        for (int j = 0; j < parent1.getS().size(); j++) {
          if (rand.nextFloat() < Crossover_rate) {
            enfant1.add(parent1.getS().get(j));
            enfant2.add(parent2.getS().get(j));
          }else{
            enfant1.add(parent2.getS().get(j));
            enfant2.add(parent1.getS().get(j));

          }
        }        




           for (int j = 0; j < parent1.getS().size(); j++) {
          if (j < position) {
            enfant1.add(parent1.getS().get(j));
            enfant2.add(parent2.getS().get(j));
          }
          else{

            enfant2.add(j,parent1.getS().get(j));
            enfant1.add(j ,parent2.getS().get(j));
    
          }
        }     


*/
}