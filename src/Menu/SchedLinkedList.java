package Menu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;

public class SchedLinkedList<T> extends xlsxReadWrite{
    private xlsxReadWrite excelData = new xlsxReadWrite();
    public boolean read = false;


    ///////

    public static ObservableList<String> appointmentsList = FXCollections.observableArrayList();



    public static String searchedName = "";
    public static String searchedService = "";
    public static String searchedDate = "";
    public static String searchedTime = "";


    /////

    public class SchedNode{
        public String name;
        public String service;
        public String date;
        public String time;
        public SchedNode next;
    }
    public SchedNode head;

    public SchedLinkedList(){
        this.head = null;
    }


    /////
    public void search(String name){

        searchedName = "";
        searchedService = "";
        searchedDate = "";
        searchedTime = "";

        SchedNode traverse = head;
        while(traverse != null){
            if(traverse.name.equals(name)){
                searchedName = traverse.name;
                searchedService = traverse.service;
                searchedDate = traverse.date;
                searchedTime = traverse.time;
            }
            traverse = traverse.next;
        }
    }

    public void seekList(){
        String scheduleNode;
        SchedNode curr = head;
        while(curr!=null) {
            scheduleNode = (curr.name + " - " + curr.service + " - " + curr.date + " - " + curr.time);
            appointmentsList.add(scheduleNode);
            curr = curr.next;
        }
    }

    //////

    public void pushScheduleInfo(String patientName, String srvc, String aptDate, String aptTime) throws IOException {
        SchedNode tail = new SchedNode();
        SchedNode current = new SchedNode();

        tail.name = patientName;
        tail.service = srvc;
        tail.date = aptDate;
        tail.time = aptTime;

        if (head==null){
            head = tail;
        }
        else{
            current = head;
            while (current.next!=null){
                current = current.next;
            }
            current.next=tail;
        }
        traverse();

        schedCount++;
        if(!read){
            excelData.writeSchedToExcel(patientName, srvc, aptDate, aptTime);
        }

        return;
    }

    public void pushToStaticLinkedList(String patientName, String srvc, String aptDate, String aptTime) throws IOException{
        Appointments.read=true;
        Appointments.pushScheduleInfo(patientName, srvc, aptDate, aptTime);
    }

    public void getElementsFromExcel() throws IOException {
        excelData.readSchedFromExcel();
    }

    public void traverse(){
        SchedNode curr = head;
        while(curr!=null) {
            System.out.println("\t\t| " + curr.name + " - " + curr.service + " - " + curr.date + " - " + curr.time + " |");
            curr = curr.next;
        }
    }

    public static SchedLinkedList Appointments = new SchedLinkedList();
}
