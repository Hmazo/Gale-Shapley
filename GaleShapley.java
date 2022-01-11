
//************************************************************
       //*                                                          *
       //*                                                          *
       //*        Hicham Mazouzi  300145076                         *
       //*                                                          *
       //************************************************************
       





import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.util.ArrayList;  // Import the File class
import java.util.Stack;
import java.io.*;  // Import the File class
import java.util.*;




public class GaleShapley{
	//Variable pour stocker les noms des etudiants et des employeurs du fichier
	private static ArrayList<String> employeName = new ArrayList<String>();
	private static ArrayList<String> studentName = new ArrayList<String>();

	//Variable pour creer la matrice du classement des etudiants
	private static String[][][] rankingSplit; 
	private static int[][] studentRanking;

	//Prority Queue
	private static HeapPriorityQueue[] pq;

	//  la pile Sue contenant les employeurs non appariés
	private static Stack<Integer> sue = new Stack<Integer>();;

	//variable pour  représenter l'appariement
	private static int[] employers;
	private static int[] students;
	//Dimension 
	private static int number1;

	//la liste des etudiants et employeurs matche
	private static String[][] matched;





	public static void main(String args[]){
    //lecture du nom du fichier
    Scanner myObj2 = new Scanner(System.in);  
    System.out.println("Entrer le nom du fichier sans l'extention :");
    String filename = myObj2.nextLine();

    // appelle des methodes
		initialize(filename);
		execute();
		save(filename);
	}


	public static void initialize(String filename){
		//declaration des variable:

		// variable pour lecture du fichier
		String value;

		// variables utiliser pour la methode split
      	String[] splitParts;
      	String[] splitParts1;
      	String[] finalSplited;
      	int index= 0;



    try {
    	//ouverture du fichier et creation du scanner
      	File myObj = new File(filename+".txt");
     	Scanner myReader = new Scanner(myObj);
    	

     	number1 = Integer.parseInt(myReader.nextLine());

     	// Initialisation des variables selon la dimension
      	finalSplited = new String[number1]; 	
      	studentRanking = new int[number1][number1]; 
      	pq = new HeapPriorityQueue[number1];
      	employers = new int[number1];
      	students = new int[number1];
      	rankingSplit = new String[number1][number1][2]; // 2 represente les rangs que letudiant et lemployeur ont donne a chacun dentre eux
      	matched = new String[number1][2];// 2 refere a letudiant et a lemployeur


      	//remplir les tableaux avec la valeur comme defaut et remplir la pile

      	for(int i = 0; i< number1; i++){
      		employers[i] = -1;
      		students[i] = -1;
      		sue.push(i);
      	}


      	//recuperation des info du fichiers Texte


		for(int i = 0;i <(number1*3);i++){
	    	value = myReader.nextLine();
        	if(i <number1){
        		employeName.add(value);
        	}
        	else if(i<(number1*2)){
        		studentName.add(value);
        	}
        	else{
        		splitParts = value.split(" ");

        			
        			for(int j=0;j<number1;j++){

        				splitParts1= splitParts[j].split(",");

        				rankingSplit[index][j]=splitParts1; 
        		}
        		index++;
        	}
        		
        }
        
      
     	myReader.close();


     	
     	// on enregistre les classement que les etudiants on donne dans une matrice


     	for(int i = 0;i<number1;i++){ // i represente lemployeur
      		for(int j =0; j<number1;j++){ // j represente l'etudiant

      			String c = rankingSplit[i][j][1]; // on recupere le classement que letudiant a donne 

      			studentRanking[i][j] = Integer.parseInt(c);

      		}
        }

      	

      	// on enregistre les preferences des employeurs

        for(int i =0; i<number1;i++){ // i represente lemployeur
        	pq[i] = new HeapPriorityQueue(number1);
        	for(int j =0; j<number1; j++){ // j represente letudiant
        		String rc = rankingSplit[i][j][0]; // on recupere le classement attribue a letudiant

        		int rank = Integer.parseInt(rc);
        		pq[i].insert(rank,j);

        		
        	}
        }


       



    } 
    catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }


	}





	// methode execute()

	public static String[][] execute(){

			//declaration des variables

			int employee; // numero de lemployeur qui pop de la pile
			int studentchoice; // etudiant choisie
			int comparaison;  // variable utilise pour la comparaison au cad au deux employeurs ont choisis le meme etudiant
			Entry entry;  // variable pour stock le return du removeMin


			while(!sue.isEmpty()){

				employee = sue.pop();
			
				entry = pq[employee].removeMin();
				studentchoice = (Integer) entry.getValue();
			    // si etudiant nest pas matche on le choisis directement
				if(students[studentchoice] == -1){
					students[studentchoice] = employee;
				}
				//si le student est matched avec un employer moin bien classed que lemployer qu on a pop de sue
    			// on match le student avec lemployer on met la valeur a defaut de lancien employee et on le push 
    			//dans la pile
					else{
				
					comparaison = students[studentchoice];
					
					if(studentRanking[employee][studentchoice] <  studentRanking[comparaison][studentchoice] ){
						sue.push(comparaison);

						students[studentchoice] = employee;
						
					}
					else{
						sue.push(employee);
					}
				}
				employers[employee] = studentchoice;
				

			}
			for(int i=0;i<number1;i++){
				matched[i][0]=employeName.get(i);
				matched[i][1]=studentName.get(employers[i]);
			}


			return matched;
	}


	public static void save(String filename) {

    	try {
     		File myObj = new File("matches"+filename+".txt");
      		if (myObj.createNewFile()) {
        		System.out.println("File created: " + myObj.getName());
      		} 
      		else {
        		System.out.println("File already exists.");
      		}
   	 	} 
   	 	catch (IOException e) {
      		System.out.println("An error occurred.");
      		e.printStackTrace(); 
      	}
      	try {

     		FileWriter myWriter = new FileWriter("matches"+filename+".txt");



        	for(int i=0;i<number1;i++){

         		String line =("Match "+i+": "+matched[i][0]+" - "+matched[i][1]+"\n");

      			myWriter.write(line);

          	

      }
      myWriter.close();
    } catch (IOException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }





 }


}