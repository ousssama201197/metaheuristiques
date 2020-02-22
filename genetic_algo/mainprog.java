package genetic_algo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import acs.acs;

public class mainprog {

    public static void main(String[] args) throws IOException {
        /*
         * Integer[][] ensemble_clauses = { { 1, 0, 1 }, { -1, 1, 1 }, { 0, 0, -1 } };
         * List<Integer> l1 = new ArrayList<Integer>(); l1.add(0); l1.add(1); l1.add(2);
         * List<Integer> l2 = l1.subList(2, 3);
         * 
         * for (int i = 0; i < l2.size(); i++) {
         * 
         * System.out.println("\nl : " + l2.get(i)); }
         * 
         *//*
            * ArrayList<String> al = new ArrayList<String>();
            * 
            * //Addition of elements in ArrayList al.add("Steve"); al.add("Justin");
            * al.add("Ajeet"); al.add("John"); al.add("Arnold"); al.add("Chaitanya");
            * 
            * System.out.println("Original ArrayList Content: "+al);
            * 
            * //Sublist to ArrayList ArrayList<String> al2 = new
            * ArrayList<String>(al.subList(0,6));
            * System.out.println("SubList stored in ArrayList: "+al2);
            * 
            * //Sublist to List List<String> list = al.subList(1, 4);
            * System.out.println("SubList stored in List: "+list);
            */
        BufferedReader objReader = new BufferedReader(
                new FileReader("/home/oussama_arch/bio-inspiree/tp/partie1/A_etoile/uf75-01.cnf"));
        String strCurrentLine;
        Integer[][] dataset = new Integer[325][75];
        for (int i = 0; i < dataset.length; i++) {
            for (int j = 0; j < dataset[i].length; j++) {
                dataset[i][j] = -1;
            }
        }

        int k = 0;
        int num;
        while ((strCurrentLine = objReader.readLine()) != null) {
            if (!objReader.equals("")) {
                String[] line = strCurrentLine.split(";");
                for (int i = 0; i < line.length; i++) {
                    num = Integer.parseInt(line[i]);
                    if (num < 0) {
                        num = num * -1;
                        num = num - 1;
                        dataset[k][num] = 0;
                    } else {
                        num = num - 1;
                        dataset[k][num] = 1;
                    }
                }
            }
            k++;
        }
        objReader.close();

        System.out.println(dataset.length);
        System.out.println(dataset[0].length);
        Date t = new Date();
        long t0 = t.getTime();

       // float Mutation_rate = (float) 0.04;
     //   float Crossover_rate = (float) 0.7;
      //  long timer = 200000000;
        acs test = new acs(dataset);
        boolean sol = test.algo();
        // Genetic_algo test2 = new Genetic_algo(dataset,600);
        // System.out.println("GenCount : " +
        // test2.gen(t0,Mutation_rate,Crossover_rate,timer));

        t = new Date();
        long t1 = t.getTime();
        System.out.println((t1 - t0) + "ms");

    }

}
