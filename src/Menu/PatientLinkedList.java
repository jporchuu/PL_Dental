package Menu;

import java.io.IOException;

public class PatientLinkedList<T> extends xlsxReadWrite{

    // creates a xlsxReadWrite class object so that we can use the methods of that class to write to the excel file
    private xlsxReadWrite xlsxAddData = new xlsxReadWrite();
    public boolean read = false;
    public static boolean firstRun = true;

    public static String searchedName = "";
    public static String searchedGender = "";
    public static String searchedAge = "";
    public static int searchedCaries = 0;
    public static int searchedGingivitis = 0;
    public static int searchedDebris = 0;
    public static int searchedCalculus = 0;
    public static int searchedAnomaly = 0;

    // declaration of our patient "linked list node" which contains the necessary data for each node
    private class PatientNode {
        String patientName;
        String patientGender;
        String patientAgeGroup;
        int dentalCaries;
        int gingivitisPerio;
        int oralDebris;
        int calculus;
        int dentoFacialAnomaly;
        PatientNode next;
    }
    PatientNode head;

    // constructor
    public PatientLinkedList() {
        this.head = null;
    }

    // "pushing" the patient data to the back of the linked list
    public void pushPatientData(String name, String gender, String ageGroup, int dc, int gp, int od, int c, int dfa) throws IOException {
        PatientNode tail = new PatientNode();
        PatientNode temp = new PatientNode();

        tail.patientName = name;
        tail.patientGender = gender;
        tail.patientAgeGroup = ageGroup;
        tail.dentalCaries = dc;
        tail.gingivitisPerio = gp;
        tail.oralDebris = od;
        tail.calculus = c;
        tail.dentoFacialAnomaly = dfa;
        tail.next = null;

        if (head == null) {
            head = tail;
        }

        else {
            temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }

            temp.next = tail;
        }

        // incrementing is for the excel file to keep track of how many rows it need to traverse to place the data in the correct cell
        patientCount = patientCount + 1;

        if(!read){
            xlsxAddData.writeToExcel(name, gender, ageGroup, dc, gp, od, c, dfa);
        }

        return;
    }

    // the usual linked list method to traverse and display the data in each node of the linked list
    public void traverse(){
        PatientNode traverse = head;
        for(int i = 0; i < patientCount-1; i++) {
            System.out.println("\t\t| " + traverse.patientName + " - " + traverse.patientGender + " - " + traverse.patientAgeGroup + " |");
            traverse = traverse.next;
        }
    }

    public void editBetweenNodes(String name, String gender, String ageGroup, int dc, int gp, int od, int c, int dfa) throws IOException {
        PatientNode traverse = head;

        if (searchedIndex > 0) {
            for (int i = 1; i < searchedIndex; i++) {
                traverse = traverse.next;
            }

            traverse.patientName = name;
            traverse.patientGender = gender;
            traverse.patientAgeGroup = ageGroup;
            traverse.dentalCaries = dc;
            traverse.gingivitisPerio = gp;
            traverse.oralDebris = od;
            traverse.calculus = c;
            traverse.dentoFacialAnomaly = dfa;

            xlsxAddData.editToExcel(name, gender, ageGroup, dc, gp, od, c, dfa);

        }

        return;

    }

    public void deleteBetweenNodes() throws IOException {
        PatientNode body = new PatientNode();
        PatientNode traverse = head;

        if (searchedIndex == 1) {
            head = head.next;
            patientCount = patientCount - 1;
            xlsxAddData.deleteRow();
        }

        else if (searchedIndex > 1) {
            for (int i = 1; i < searchedIndex; i++) {
                body = traverse;
                traverse = traverse.next;
            }
            body.next = traverse.next;
            patientCount = patientCount - 1;

            xlsxAddData.deleteRow();
        }

        return;
    }

    public void search(String name){

        searchedIndex = 0;

        searchedName = "";
        searchedGender = "";
        searchedAge = "";

        PatientNode traverse = head;
        for(int i = 0; i < patientCount-1; i++) {
            if(traverse.patientName.toLowerCase().equals(name.toLowerCase())){

                searchedIndex = i + 1;

                searchedName = traverse.patientName;
                searchedGender = traverse.patientGender;
                searchedAge = traverse.patientAgeGroup;
                searchedCaries = traverse.dentalCaries;
                searchedGingivitis = traverse.gingivitisPerio;
                searchedDebris = traverse.oralDebris;
                searchedCalculus = traverse.calculus;
                searchedAnomaly = traverse.dentoFacialAnomaly;
            }
            traverse = traverse.next;
        }
    }

    public void pushToStaticLinkedList(String name, String gender, String ageGroup, int dc, int gp, int od, int c, int dfa) throws IOException{
        AllPatients.read = true;
        AllPatients.pushPatientData(name, gender, ageGroup, dc, gp, od, c, dfa);
    }

    // creation of the linked list that will be used throughout the program as the data structure to hold the data of all the patients
    public static PatientLinkedList AllPatients = new PatientLinkedList();


}

