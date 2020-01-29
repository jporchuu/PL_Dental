package Schedule;

import Menu.SchedLinkedList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.Stack;

public class ScheduleSearchController extends SchedLinkedList implements Initializable {
    public TextField nameField;
    public Button searchButton;
    public GridPane scheduleGrid;
    public Label day1;
    public Label day2;
    public Label day3;
    public Label day4;
    public Label day5;
    public Label day6;
    public Label day7;
    public Button cancelButton;
    public AnchorPane scheduleSearchAnchorPane;
    public Button clearButton1;
    Label nameLabel;
    Label serviceLabel;
    VBox root;


    public void onSearch(ActionEvent actionEvent) {
        searchSchedule(nameField.getText());
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        nameField.setText(null);
        AnchorPane cancelAnchorPane = FXMLLoader.load(getClass().getResource("../Menu/MenuUI.fxml"));
        scheduleSearchAnchorPane.getChildren().setAll(cancelAnchorPane);
    }

    public void onClearText(ActionEvent actionEvent) throws IOException {
        AnchorPane clear = FXMLLoader.load(getClass().getResource("../Schedule/ScheduleSearchUI.fxml"));
        scheduleSearchAnchorPane.getChildren().setAll(clear);
    }

    public void searchSchedule(String name){
        depthFirstSearch(0, 0, name);
    }


    public void depthFirstSearch(int x, int y, String name){
        ObservableList<Node> children = scheduleGrid.getChildren();
        boolean allVisited = false;
        Node initial = null;
        Stack<Node> nodes = new Stack<>();
        for (Node node : children) {
            System.out.println(GridPane.getRowIndex(node));
            System.out.println(GridPane.getColumnIndex(node));
            if(GridPane.getRowIndex(node) == x && GridPane.getColumnIndex(node) == y) {
                initial = node;
                break;
            }
        }
        nodes.push(initial);
        while(!nodes.isEmpty()){
            Node current = nodes.pop();
            if (current instanceof VBox) {
                if(((VBox)current).getChildren().toString().contains(name)) ((VBox) current).setStyle("-fx-background-color: #FFF200");
            }
            if(!allVisited) {
                for (Node node : scheduleGrid.getChildren()) {
                    nodes.push(node);
                }
                allVisited=true;
            }
        }
    }

    public void setSchedule(int x, int y1, int y2, String name, String service){
        /*
        StackPane pane = new StackPane();
        pane.setBackground(new Background(new BackgroundFill(Color.web("#9B1C31"), CornerRadii.EMPTY, Insets.EMPTY)));
        //Label label = new Label("Wow");
         */

        for(int y=y1; y<y2; y++){
            nameLabel = new Label(name);
            nameLabel.setStyle("-fx-font-weight: bold");
            serviceLabel = new Label(service);
            root = new VBox();
            root.setAlignment(Pos.CENTER);
            root.getChildren().addAll(nameLabel,serviceLabel);
            scheduleGrid.add(root,x,y);
        }
    }

    public void initializeSchedule(){
        if(Appointments.head==null) {
            try {
                getElementsFromExcel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        SchedLinkedList.SchedNode curr = Appointments.head;
        String[] time;
        Calendar date = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        String[] dates = new String[7];
        int delta = -date.get(GregorianCalendar.DAY_OF_WEEK) + 2; //add 2 if your week start on monday
        date.add(Calendar.DAY_OF_MONTH, delta );
        for (int i = 0; i < 7; i++)
        {
            dates[i] = format.format(date.getTime());
            date.add(Calendar.DAY_OF_MONTH, 1);
            while(curr!=null){
                if(curr.date.equals(dates[i])){
                    time = splitTime(curr.time);
                    setCurrentSched(curr.name, curr.service, i, time);
                    break;
                }
                curr=curr.next;
            }
            curr = Appointments.head;
        }
        day1.setText(dates[0]);
        day2.setText(dates[1]);
        day3.setText(dates[2]);
        day4.setText(dates[3]);
        day5.setText(dates[4]);
        day6.setText(dates[5]);
        day7.setText(dates[6]);
    }

    public void setCurrentSched(String name, String srvc, int x, String[] time){
        String fromHr = time[0];
        String fromMin = time[1];
        String toHr = time[2];
        String toMin = time[3];
        int y1=0, y2=0;
        int minInit=0, minFin=0;

        System.out.println(fromHr +":"+fromMin+" to "+toHr+":"+toMin);

        //Minutes
        if(fromMin.equals("00")){
            minInit=0;
        }
        else if(fromMin.equals("30")){
            minInit=1;
        }
        else if(toMin.equals("00")){
            minFin=0;
        }
        else if(toMin.equals("30")){
            minFin=1;
        }

        //From
        if(fromHr.equals("09")){
            y1=0+minInit;
        }
        else if(fromHr.equals("10")){
            y1=2+minInit;
        }
        else if(fromHr.equals("11")){
            y1=4+minInit;
        }
        else if(fromHr.equals("12")){
            y1=6+minInit;
        }
        else if(fromHr.equals("01")){
            y1=8+minInit;
        }
        else if(fromHr.equals("02")){
            y1=10+minInit;
        }
        else if(fromHr.equals("03")){
            y1=12+minInit;
        }
        else if(fromHr.equals("04")){
            y1=14+minInit;
        }
        else if(fromHr.equals("05")){
            y1=16+minInit;
        }

        //To
        if(toHr.equals("09")){
            y2=0+minFin;
        }
        else if(toHr.equals("10")){
            y2=2+minFin;
        }
        else if(toHr.equals("11")){
            y2=4+minFin;
        }
        else if(toHr.equals("12")){
            y2=6+minFin;
        }
        else if(toHr.equals("01")){
            y2=8+minFin;
        }
        else if(toHr.equals("02")){
            y2=10+minFin;
        }
        else if(toHr.equals("03")){
            y2=12+minFin;
        }
        else if(toHr.equals("04")){
            y2=14+minFin;
        }
        else if(toHr.equals("05")){
            y2=16+minFin;
        }

        setSchedule(x, y1, y2, name, srvc);
    }

    public String[] splitTime(String time){
        String[] splitTime = time.split("[-:]");
        //Test System.out.println(Arrays.toString(splitTime));
        return splitTime;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeSchedule();
    }
}
