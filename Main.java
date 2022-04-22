
import java.util.ArrayList;
import java.io.*;  // Import File class
import java.util.Scanner; // Import Scanner class to read text files
public class Main{
    public static int insswaps = 0; //to count insertionSort swaps
    public static int mergemoves = 0; //to count mergeSort moves

    public static ArrayList<String> removeStopwords(String GPT3, String stopwords) throws FileNotFoundException { // using UTF-8 charset so need to throw the error exception
        ArrayList<String> stopWordlist = new ArrayList<>();
        File stopWordsObj = new File(stopwords);
        Scanner stopWordsreader = new Scanner(stopWordsObj,"UTF-8"); //reads stopwords.txt as UTF-8 format so "special" characters are removed
        while (stopWordsreader.hasNextLine()) {
            stopWordlist.add(stopWordsreader.next()); //add each word from stopwords.txt to arraylist "stopWordlist"
        }
        stopWordsreader.close();

        ArrayList<String> GPTWords = new ArrayList<>();
        File gptReaderObj = new File(GPT3);
        Scanner gptReader = new Scanner(gptReaderObj,"UTF-8"); //reads GPT3.txt as UTF-8 format so "special" characters are removed
        while (gptReader.hasNextLine()) {
            GPTWords.add(gptReader.next().toLowerCase().replaceAll("[^\\w+]","").trim());

        } // appends each word from GPT3.txt to arraylist "GPTWords"  - removing spaces, all words converted to lowercase & uses regex to remove punctuation such as apostrophes, fullstops and commas
        gptReader.close();
        ArrayList<String> final_list = new ArrayList<>();
        for (String toCompare : GPTWords) {
            if (!stopWordlist.contains(toCompare)) {
                final_list.add(toCompare);
            }
        } //loop for each string in arraylist "GPTWords", checks if string is present in arraylist "stopWordlist", if not then appends to arraylist "final_list"
        return(final_list);

    }
        public static ArrayList<String> insertionSort(ArrayList<String> GPT3){
        for(int i=1;i < GPT3.size();i++){//loops through arraylist "GPT3" to the size of the array incrementing "i" by one each time,
                String curr = GPT3.get(i); //sets the current position to be used as comparison as string "curr"
                int x = i - 1;
                while ((x > -1) && (GPT3.get(x).compareToIgnoreCase(curr)) > 0){ // x == the string just before the current position to be used as comparison
                    GPT3.set(x+1, GPT3.get(x)); // while x is positive and if the first element is bigger than the second, set it to position x+1 in the arraylist "GPT3"
                    insswaps++;
                    x--;
                }
                insswaps++;
                GPT3.set(x+1,curr); //else set the current element to position x+1
            }
        return GPT3;
    }

    public static ArrayList<String> mergeSort(ArrayList<String> GPT3){
        int arrsize = GPT3.size();
        if (arrsize <=1){
            return GPT3; //if the size of arraylist "GPT3" is less than or equal to 1, return (used to stop recursion overflow)
        }
        int midpoint = arrsize/2; //halves the size of list
        ArrayList<String> leftsect = new ArrayList<>();
        ArrayList<String> rightsect = new ArrayList<>();
        for (int x = 0; x < midpoint;x++){
            leftsect.add(GPT3.get(x));
        } //for each item/string in arraylist "GPT3" until the midpoint, place the item/string into the arraylist "leftsect"
        for (int z = midpoint; z < arrsize; z++){
            rightsect.add(GPT3.get(z));
        } //for each item/string in arraylist "GPT3" from the midpoint to the size of the arraylist, place the item/string into the arraylist "rightsect"

        leftsect = mergeSort(leftsect);
        rightsect = mergeSort(rightsect); //recursively calls mergeSort with the arraylists "leftsect" & "rightsect"
        return merge(leftsect,rightsect); //after the list has been split up, call upon the merge function on the arraylists "leftsect" & "rightsect"
    }
    public static ArrayList<String> merge(ArrayList<String> leftsect,ArrayList<String> rightsect){
        ArrayList<String> final_list = new ArrayList<>();
        //merge
        while (leftsect.isEmpty() == false && rightsect.isEmpty() == false){ //checks if left list AND right list are NOT empty
            if (leftsect.get(0).compareToIgnoreCase(rightsect.get(0)) < 0 ){// checks if the element the arraylist "leftsect" is larger than the element in "rightsect"
                final_list.add(leftsect.remove(0));
            } //appends the element in arraylist "leftsect" to arraylist "final_list" and removes that element from the original "leftsect" arraylist
            else{
                final_list.add(rightsect.remove(0));
            }//else appends the element in arraylist "rightsect" to arraylist "final_list" and removes that element from the original "rightsect" arraylist
            mergemoves++;
        }
        //if one of the arraylists "leftsect" OR "rightsect" are empty (cleaning up), append the element to final_list and remove it from the original arraylist
        while (leftsect.isEmpty() == false){
            final_list.add(leftsect.remove(0));
            mergemoves++;
        }
        while (rightsect.isEmpty() == false){
            final_list.add(rightsect.remove(0));
            mergemoves++;
        }
        return final_list;
    }
    public static void performance() throws IOException {
        ArrayList<String> GPT3 = removeStopwords("GPT3.txt", "stopwords.txt");
        ArrayList<String> performanceList = new ArrayList<>();
        int x;
        long start_insertion, end_insertion, insertion_duration = 0;
        long start_merge, end_merge, merge_duration = 0;
        for (x = 1; x <= GPT3.size(); x++) {
            performanceList.add(GPT3.get(x - 1)); //adds each element from GPT3 to arraylist "performanceList" where x is 1 and is incrementing by 1 until the size of the arraylist "GPT3" (x - 1as array's are 0 based in java)
            if (x % 100 == 0 && x != 0) {
                System.out.println("\n>>>Sort Size: " + x);
                start_insertion = System.nanoTime();
                insertionSort(performanceList);
                end_insertion = System.nanoTime();
                insertion_duration = (end_insertion - start_insertion);
                System.out.println("Insertion Swap Duration = " + insertion_duration + " nanoseconds");
                System.out.println("Insertion Swap No. of swaps performed = " + insswaps);

                start_merge = System.nanoTime();
                mergeSort(performanceList);
                end_merge = System.nanoTime();
                merge_duration = (end_merge - start_merge);
                System.out.println("Merge Swap Duration = " + merge_duration + " nanoseconds");
                System.out.println("Merge Swap No. of moves performed = " + mergemoves);
                mergemoves = 0;
            } //when x is divisible by 100 (100,200,300 until its not possible) it outputs the speed & swap/move count for each swap using the arraylist "preformancelist"
            if (x == GPT3.size()){
                System.out.println("\n>>>Sort Size: "+ x + " [MAX SIZE]");
                start_insertion = System.nanoTime();
                insertionSort(performanceList);
                end_insertion = System.nanoTime();
                insertion_duration = (end_insertion - start_insertion);
                System.out.println("Insertion Swap Duration = " + insertion_duration + " nanoseconds");
                System.out.println("Insertion Swap No. of swaps performed = " + insswaps);
                insswaps = 0;

                start_merge = System.nanoTime();
                mergeSort(performanceList);
                end_merge = System.nanoTime();
                merge_duration = (end_merge - start_merge);
                System.out.println("Merge Swap Duration = " + merge_duration + " nanoseconds");
                System.out.println("Merge Swap No. of moves performed = " + mergemoves);
                mergemoves = 0;
            } //does the same as if statement above only when x == size of GPT3 (when performanceList is full)
        }
    }




    public static void main(String []args) throws IOException { //IOException due to specifying the "UTF-8" character set within the scanner function

        ArrayList<String> GPT3 = removeStopwords("GPT3.txt","stopwords.txt"); //Main GPT3 ArrayList used by both sorting procedures - Question 2 & 3.
        //Uncomment below to Return final ArrayList of Question 1
        //System.out.println(GPT3);

        ArrayList<String> insertionSorted = insertionSort(GPT3);
        //Uncomment below to Return final sorted ArrayList of Question 2
        //System.out.println(insertionSorted);

        ArrayList<String> mergeSorted = mergeSort(GPT3);
        //Uncomment below to Return final sorted ArrayList of Question 3
        //System.out.println(mergeSorted);

        performance();

        }
}