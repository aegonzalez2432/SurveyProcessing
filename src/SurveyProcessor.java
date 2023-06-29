import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.*;
public class SurveyProcessor {
    private Scanner scanner, readLine;
    private String line, fileLoc, product, gender, ageGroup, rating, state, productWanted, criteria;
    private ArrayList<Coveted> filter = new ArrayList<>();
    private int commaIndex, commaIndex2, oneS, twoS, threeS, fourS, fiveS;
    private File data;
    public SurveyProcessor() throws IOException {

        scanner = new Scanner(System.in);

        System.out.println("Enter the input file of source data");
        fileLoc = scanner.nextLine();
        try{
            data = new File(fileLoc);
        }catch(Exception e){
            System.out.println("Trouble finding file");

        }
        try {
            readLine = new Scanner(data);
        }catch(Exception e){
            System.out.println("Error reading next line in readLine");
        }
        System.out.println("Enter the product to filter on");
        productWanted = scanner.nextLine();
        System.out.println("Please enter a demographic attribute to filter on. Enter a value contained in any of the following columns [Gender|Age Group|State of Residence]");
        criteria = scanner.nextLine();

        line = readLine.nextLine();//for first row of classifiers
        while(readLine.hasNext()){
            line = readLine.nextLine();
            separateLine();
            if(productWanted.equalsIgnoreCase(product)){
                rememberMe();
            }
        }
        oneS = 0;
        twoS = 0;
        threeS = 0;
        fourS = 0;
        fiveS = 0;
        for(int i = 0; i < filter.size(); i++){
            if(filter.get(i).getRating().contains("1")){oneS++;}
            if(filter.get(i).getRating().contains("2")){twoS++;}
            if(filter.get(i).getRating().contains("3")){threeS++;}
            if(filter.get(i).getRating().contains("4")){fourS++;}
            if(filter.get(i).getRating().contains("5")){fiveS++;}
        }
        System.out.println("Enter the location you wish your file to be written to");
        line = scanner.nextLine();
        try {
            FileWriter output = new FileWriter(line, true);
            output.append("Rating, Frequency\n\t1, " + oneS + "\n\t2, " + twoS + "\n\t3, " + threeS + "\n\t4, " + fourS + "\n\t5, " + fiveS);
            output.close();
        }catch(Exception e){
            System.out.println("Unable to create file");
        }
//        System.out.println("\t1, " + oneS);
//        System.out.println("\t2, " + twoS);
//        System.out.println("\t3, " + threeS);
//        System.out.println("\t4, " + fourS);
//        System.out.println("\t5, " + fiveS);

    }
    private void separateLine(){
        commaIndex = line.indexOf(',');             //String for gender
        gender = line.substring(0,commaIndex);
        //System.out.println(gender);
        commaIndex++;
        commaIndex2 = line.indexOf(',', commaIndex);        //String for age group
        ageGroup = line.substring(commaIndex,commaIndex2);
        //System.out.println(ageGroup);
        commaIndex2++;
        commaIndex = line.indexOf(',',commaIndex2);     //String for state
        state = line.substring(commaIndex2,commaIndex);
       // System.out.println(state);
        commaIndex++;
        commaIndex2 = line.indexOf(',', commaIndex);
        product = line.substring(commaIndex,commaIndex2);
        //System.out.println(product);
        commaIndex2++;
        rating = line.substring(commaIndex2);
        //System.out.println(rating);
        //System.exit(1);
    }
    private void rememberMe(){
        Coveted c = new Coveted(gender, ageGroup, state, rating);

        if(ageGroup.contains("-")){//criteria is age range
            if(criteria.charAt(0)> ageGroup.charAt(0) && criteria.charAt(0) < ageGroup.charAt(3)){filter.add(c);}
            if(criteria.charAt(0) >= ageGroup.charAt(0) && criteria.charAt(0) <= ageGroup.charAt(3)){//first number >= ageGroup first AND first number < ageGroup second number
                if(criteria.charAt(0) == criteria.charAt(0) && criteria.charAt(1) >= ageGroup.charAt(1)) {// if first num == and second number greater, falls in guidelines
                    filter.add(c);
                }else if(criteria.charAt(0) == ageGroup.charAt(3) && criteria.charAt(1) <= ageGroup.charAt(4)){
                    filter.add(c);
                }
            }
        }else if(ageGroup.contains("+") && (criteria.charAt(0) >= ageGroup.charAt(0) )){//if wanted above 75+
            if(criteria.charAt(0) == ageGroup.charAt(0) && criteria.charAt(1) >= ageGroup.charAt(1)) {
                filter.add(c);
            }
            if(criteria.charAt(0) > ageGroup.charAt(0)){
                filter.add(c);
            }
        }
        else if(gender.equalsIgnoreCase(criteria)){ filter.add(c); }
        else if(state.equalsIgnoreCase(criteria)){ filter.add(c);}
    }

}
