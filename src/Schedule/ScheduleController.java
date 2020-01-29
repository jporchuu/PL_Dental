package Schedule;

import Menu.SchedLinkedList;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class ScheduleController extends SchedLinkedList implements Initializable {

    public AnchorPane ScheduleAnchor;
    public TextField nameTextField;
    public DatePicker dateInput;
    public Label minTime;
    public Label maxTime;
    public MenuButton fromHrButton;
    public MenuButton fromMinButton;
    public MenuButton toHrButton;
    public MenuButton toMinButton;
    public Button cancelButton;
    public Button confirmButton;

    //From
    public MenuItem from09hr;
    public MenuItem from10hr;
    public MenuItem from11hr;
    public MenuItem from12hr;
    public MenuItem from01hr;
    public MenuItem from02hr;
    public MenuItem from03hr;
    public MenuItem from04hr;
    public MenuItem from05hr;

    public MenuItem from00min;
    public MenuItem from30min;
    //To
    public MenuItem to09hr;
    public MenuItem to10hr;
    public MenuItem to11hr;
    public MenuItem to12hr;
    public MenuItem to01hr;
    public MenuItem to02hr;
    public MenuItem to03hr;
    public MenuItem to04hr;
    public MenuItem to05hr;

    public MenuItem to00min;
    public MenuItem to30min;
    public GridPane scheduleGrid;

    public Label day1;
    public Label day2;
    public Label day3;
    public Label day4;
    public Label day5;
    public Label day6;
    public Label day7;
    public MenuButton serviceSelection;
    public MenuItem serviceCh1;
    public MenuItem serviceCh2;
    public MenuItem serviceCh3;
    public MenuItem serviceCh4;
    public MenuItem serviceCh5;
    public MenuItem serviceCh6;
    public MenuItem serviceCh7;
    public MenuItem serviceCh8;
    public MenuItem serviceCh9;
    public Button clearButton;
    public Label errorLabel;

    public void selectMinHr(ActionEvent actionEvent) {
    }

    public void selectMinMinute(ActionEvent actionEvent) {
    }

    public void selectMaxHour(ActionEvent actionEvent) {
    }

    public void selectMaxMin(ActionEvent actionEvent) {
    }

    public void onConfirm(ActionEvent actionEvent) throws IOException {
        String fromHr = fromHrButton.getText();
        String fromMin = fromMinButton.getText();
        String toHr = toHrButton.getText();
        String toMin = toMinButton.getText();
        int x=0,y1=0, y2=0;
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

        Calendar date = Calendar.getInstance();
        date.set(dateInput.getValue().getYear(), dateInput.getValue().getMonthValue()-1, dateInput.getValue().getDayOfMonth());

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");

        String[] dates = new String[7];
        int delta = -date.get(GregorianCalendar.DAY_OF_WEEK) + 2; //add 2 if your week start on monday
        date.add(Calendar.DAY_OF_MONTH, delta );
        String dateInputString = dateInput.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
        for (int i = 0; i < 7; i++)
        {
            dates[i] = format.format(date.getTime());
            date.add(Calendar.DAY_OF_MONTH, 1);
            if(dates[i].equals(dateInputString)){
                x=i;
            }
        }
        System.out.println(Arrays.toString(dates));
        if(!isSchedAvailable(x, y1, y2)){
            errorLabel.setText("Unavailable schedule. Try again!");
            errorLabel.setTextFill(Color.web("#FF9090"));
        }
        else if(isSchedAvailable(x, y1, y2)){
            errorLabel.setText("Scheduling successful!");
            errorLabel.setTextFill(Color.web("#4DED30"));
            setSchedule(x, y1, y2, nameTextField.getText(), serviceSelection.getText());
            Appointments.read=false;
            Appointments.pushScheduleInfo(nameTextField.getText(), serviceSelection.getText(), dateInputString, minTime.getText() + "-" + maxTime.getText());
        }
    }

    public boolean isSchedAvailable(int x, int y1, int y2){
        boolean available = true;
        ObservableList<Node> childrens = scheduleGrid.getChildren();
            for(int y=y1; y<y2; y++) {
                for (Node node : childrens) {
                    if (node instanceof VBox) {
                        if (scheduleGrid.getRowIndex(node) == y && scheduleGrid.getColumnIndex(node) == x) {
                                available = false;
                        }
                    }
                }
            }

        return available;
    }

    public void setSchedule(int x, int y1, int y2, String name, String service){

        for(int y=y1; y<y2; y++){
            Label nameLabel = new Label(name);
            nameLabel.setStyle("-fx-font-weight: bold");
            Label serviceLabel = new Label(service);
            VBox root = new VBox();
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
        SchedNode curr = Appointments.head;
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

    //Selection of services
    public void onService1(ActionEvent actionEvent) {
        serviceSelection.setText(serviceCh1.getText());
    }

    public void onService2(ActionEvent actionEvent) {
        serviceSelection.setText(serviceCh2.getText());
    }

    public void onService3(ActionEvent actionEvent) {
        serviceSelection.setText(serviceCh3.getText());
    }

    public void onService4(ActionEvent actionEvent) {
        serviceSelection.setText(serviceCh4.getText());
    }

    public void onService5(ActionEvent actionEvent) {
        serviceSelection.setText(serviceCh5.getText());
    }

    public void onService6(ActionEvent actionEvent) {
        serviceSelection.setText(serviceCh6.getText());
    }

    public void onService7(ActionEvent actionEvent) {
        serviceSelection.setText(serviceCh7.getText());
    }

    public void onService8(ActionEvent actionEvent) {
        serviceSelection.setText(serviceCh8.getText());
    }

    public void onService9(ActionEvent actionEvent) {
        serviceSelection.setText(serviceCh9.getText());
    }

    //MENU BUTTON ITEMS SELECTION
    //FROM
    public void selectFrom09hr(ActionEvent actionEvent) {
        fromHrButton.setText(from09hr.getText());
        setEnabledTime();
    }

    public void selectFrom10hr(ActionEvent actionEvent) {
        fromHrButton.setText(from10hr.getText());
        setEnabledTime();
    }

    public void selectFrom11hr(ActionEvent actionEvent) {
        fromHrButton.setText(from11hr.getText());
        setEnabledTime();
    }

    public void selectFrom12hr(ActionEvent actionEvent) {
        fromHrButton.setText(from12hr.getText());
        setEnabledTime();
    }

    public void selectFrom01hr(ActionEvent actionEvent) {
        fromHrButton.setText(from01hr.getText());
        setEnabledTime();
    }

    public void selectFrom02hr(ActionEvent actionEvent) {
        fromHrButton.setText(from02hr.getText());
        setEnabledTime();
    }

    public void selectFrom03hr(ActionEvent actionEvent) {
        fromHrButton.setText(from03hr.getText());
        setEnabledTime();
    }

    public void selectFrom04hr(ActionEvent actionEvent) {
        fromHrButton.setText(from04hr.getText());
        setEnabledTime();

    }

    public void selectFrom05hr(ActionEvent actionEvent) {
        fromHrButton.setText(from05hr.getText());
        setEnabledTime();
    }


    public void selectFrom00min(ActionEvent actionEvent) {
        fromMinButton.setText(from00min.getText());
        minTime.setText(fromHrButton.getText() + ":" + fromMinButton.getText());
    }

    public void selectFrom30min(ActionEvent actionEvent) {
        fromMinButton.setText(from30min.getText());
        minTime.setText(fromHrButton.getText() + ":" + fromMinButton.getText());
        setEnabledTime();
    }


    //TO
    public void selectTo09hr(ActionEvent actionEvent) {
        toHrButton.setText(to09hr.getText());
        setEnabledTime();
    }

    public void selectTo10hr(ActionEvent actionEvent) {
        toHrButton.setText(to10hr.getText());
        setEnabledTime();
    }

    public void selectTo11hr(ActionEvent actionEvent) {
        toHrButton.setText(to11hr.getText());
        setEnabledTime();
    }

    public void selectTo12hr(ActionEvent actionEvent) {
        toHrButton.setText(to12hr.getText());
        setEnabledTime();
    }

    public void selectTo01hr(ActionEvent actionEvent) {
        toHrButton.setText(to01hr.getText());
        setEnabledTime();
    }

    public void selectTo02hr(ActionEvent actionEvent) {
        toHrButton.setText(to02hr.getText());
        setEnabledTime();
    }

    public void selectTo03hr(ActionEvent actionEvent) {
        toHrButton.setText(to03hr.getText());
        setEnabledTime();
    }

    public void selectTo04hr(ActionEvent actionEvent) {
        toHrButton.setText(to04hr.getText());
        setEnabledTime();
    }

    public void selectTo05hr(ActionEvent actionEvent) {
        toHrButton.setText(to05hr.getText());
        setEnabledTime();
    }

    public void selectTo00min(ActionEvent actionEvent) {
        toMinButton.setText(to00min.getText());
        maxTime.setText(toHrButton.getText() + ":" + toMinButton.getText());
    }

    public void selectTo30min(ActionEvent actionEvent) {
        toMinButton.setText(to30min.getText());
        maxTime.setText(toHrButton.getText() + ":" + toMinButton.getText());
    }

    //Function to set disabled items as well as to set input time data
    public void setEnabledTime(){

        if((fromMinButton.getText().equals("Min"))&&(!fromHrButton.getText().equals("Hr"))) minTime.setText(fromHrButton.getText() + ":00");
        else if((!fromMinButton.getText().equals("Min"))&&(!fromHrButton.getText().equals("Hr"))) minTime.setText(fromHrButton.getText() + ":" + fromMinButton.getText());

        if((toMinButton.getText().equals("Min"))&&(!toHrButton.getText().equals("Hr"))) maxTime.setText(toHrButton.getText() + ":00");
        else if ((!toMinButton.getText().equals("Min"))&&(!toHrButton.getText().equals("Hr"))) maxTime.setText(toHrButton.getText() + ":" + toMinButton.getText());

        if((fromHrButton.getText().equals(toHrButton.getText()))&&fromMinButton.getText().equals("00")) to00min.setDisable(true);
        else to00min.setDisable(false);

        switch (fromHrButton.getText()) {
            case "09":
                to09hr.setDisable(false);
                to10hr.setDisable(false);
                to11hr.setDisable(false);
                to12hr.setDisable(false);
                to01hr.setDisable(false);
                to02hr.setDisable(false);
                to03hr.setDisable(false);
                to04hr.setDisable(false);
                break;
            case "10":
                to09hr.setDisable(true);
                to10hr.setDisable(false);
                to11hr.setDisable(false);
                to12hr.setDisable(false);
                to01hr.setDisable(false);
                to02hr.setDisable(false);
                to03hr.setDisable(false);
                to04hr.setDisable(false);
                break;
            case "11":
                to09hr.setDisable(true);
                to10hr.setDisable(true);
                to11hr.setDisable(false);
                to12hr.setDisable(false);
                to01hr.setDisable(false);
                to02hr.setDisable(false);
                to03hr.setDisable(false);
                to04hr.setDisable(false);
                break;
            case "12":
                to09hr.setDisable(true);
                to10hr.setDisable(true);
                to11hr.setDisable(true);
                to12hr.setDisable(false);
                to01hr.setDisable(false);
                to02hr.setDisable(false);
                to03hr.setDisable(false);
                to04hr.setDisable(false);
                break;
            case "01":
                to09hr.setDisable(true);
                to10hr.setDisable(true);
                to11hr.setDisable(true);
                to12hr.setDisable(true);
                to01hr.setDisable(false);
                to02hr.setDisable(false);
                to03hr.setDisable(false);
                to04hr.setDisable(false);
                break;
            case "02":
                to09hr.setDisable(true);
                to10hr.setDisable(true);
                to11hr.setDisable(true);
                to12hr.setDisable(true);
                to01hr.setDisable(true);
                to02hr.setDisable(false);
                to03hr.setDisable(false);
                to04hr.setDisable(false);
                break;
            case "03":
                to09hr.setDisable(true);
                to10hr.setDisable(true);
                to11hr.setDisable(true);
                to12hr.setDisable(true);
                to01hr.setDisable(true);
                to02hr.setDisable(true);
                to03hr.setDisable(false);
                to04hr.setDisable(false);
                break;
            case "04":
                to09hr.setDisable(true);
                to10hr.setDisable(true);
                to11hr.setDisable(true);
                to12hr.setDisable(true);
                to01hr.setDisable(true);
                to02hr.setDisable(true);
                to03hr.setDisable(true);
                to04hr.setDisable(false);
                break;
            case "05":
                to09hr.setDisable(true);
                to10hr.setDisable(true);
                to11hr.setDisable(true);
                to12hr.setDisable(true);
                to01hr.setDisable(true);
                to02hr.setDisable(true);
                to03hr.setDisable(true);
                to04hr.setDisable(true);
                break;
        }

        switch (toHrButton.getText()) {
            case "09":
                from09hr.setDisable(false);
                from10hr.setDisable(true);
                from11hr.setDisable(true);
                from12hr.setDisable(true);
                from01hr.setDisable(true);
                from02hr.setDisable(true);
                from03hr.setDisable(true);
                from04hr.setDisable(true);
                from05hr.setDisable(true);
                break;
            case "10":
                from09hr.setDisable(false);
                from10hr.setDisable(false);
                from11hr.setDisable(true);
                from12hr.setDisable(true);
                from01hr.setDisable(true);
                from02hr.setDisable(true);
                from03hr.setDisable(true);
                from04hr.setDisable(true);
                from05hr.setDisable(true);
                break;
            case "11":
                from09hr.setDisable(false);
                from10hr.setDisable(false);
                from11hr.setDisable(false);
                from12hr.setDisable(true);
                from01hr.setDisable(true);
                from02hr.setDisable(true);
                from03hr.setDisable(true);
                from04hr.setDisable(true);
                from05hr.setDisable(true);
                break;
            case "12":
                from09hr.setDisable(false);
                from10hr.setDisable(false);
                from11hr.setDisable(false);
                from12hr.setDisable(false);
                from01hr.setDisable(true);
                from02hr.setDisable(true);
                from03hr.setDisable(true);
                from04hr.setDisable(true);
                from05hr.setDisable(true);
                break;
            case "01":
                from09hr.setDisable(false);
                from10hr.setDisable(false);
                from11hr.setDisable(false);
                from12hr.setDisable(false);
                from01hr.setDisable(false);
                from02hr.setDisable(true);
                from03hr.setDisable(true);
                from04hr.setDisable(true);
                from05hr.setDisable(true);
                break;
            case "02":
                from09hr.setDisable(false);
                from10hr.setDisable(false);
                from11hr.setDisable(false);
                from12hr.setDisable(false);
                from01hr.setDisable(false);
                from02hr.setDisable(false);
                from03hr.setDisable(true);
                from04hr.setDisable(true);
                from05hr.setDisable(true);
                break;
            case "03":
                from09hr.setDisable(false);
                from10hr.setDisable(false);
                from11hr.setDisable(false);
                from12hr.setDisable(false);
                from01hr.setDisable(false);
                from02hr.setDisable(false);
                from03hr.setDisable(false);
                from04hr.setDisable(true);
                from05hr.setDisable(true);
                break;
            case "04":
                from09hr.setDisable(false);
                from10hr.setDisable(false);
                from11hr.setDisable(false);
                from12hr.setDisable(false);
                from01hr.setDisable(false);
                from02hr.setDisable(false);
                from03hr.setDisable(false);
                from04hr.setDisable(false);
                from05hr.setDisable(true);
                break;
            case "05":
                from09hr.setDisable(false);
                from10hr.setDisable(false);
                from11hr.setDisable(false);
                from12hr.setDisable(false);
                from01hr.setDisable(false);
                from02hr.setDisable(false);
                from03hr.setDisable(false);
                from04hr.setDisable(false);
                from05hr.setDisable(false);
                break;
        }

        if(fromMinButton.getText().equals("30")){
            switch (fromHrButton.getText()) {
                case "09":
                    to09hr.setDisable(true);
                    to10hr.setDisable(false);
                    to11hr.setDisable(false);
                    to12hr.setDisable(false);
                    to01hr.setDisable(false);
                    to02hr.setDisable(false);
                    to03hr.setDisable(false);
                    to04hr.setDisable(false);
                    break;
                case "10":
                    to09hr.setDisable(true);
                    to10hr.setDisable(true);
                    to11hr.setDisable(false);
                    to12hr.setDisable(false);
                    to01hr.setDisable(false);
                    to02hr.setDisable(false);
                    to03hr.setDisable(false);
                    to04hr.setDisable(false);
                    break;
                case "11":
                    to09hr.setDisable(true);
                    to10hr.setDisable(true);
                    to11hr.setDisable(true);
                    to12hr.setDisable(false);
                    to01hr.setDisable(false);
                    to02hr.setDisable(false);
                    to03hr.setDisable(false);
                    to04hr.setDisable(false);
                    break;
                case "12":
                    to09hr.setDisable(true);
                    to10hr.setDisable(true);
                    to11hr.setDisable(true);
                    to12hr.setDisable(true);
                    to01hr.setDisable(false);
                    to02hr.setDisable(false);
                    to03hr.setDisable(false);
                    to04hr.setDisable(false);
                    break;
                case "01":
                    to09hr.setDisable(true);
                    to10hr.setDisable(true);
                    to11hr.setDisable(true);
                    to12hr.setDisable(true);
                    to01hr.setDisable(true);
                    to02hr.setDisable(false);
                    to03hr.setDisable(false);
                    to04hr.setDisable(false);
                    break;
                case "02":
                    to09hr.setDisable(true);
                    to10hr.setDisable(true);
                    to11hr.setDisable(true);
                    to12hr.setDisable(true);
                    to01hr.setDisable(true);
                    to02hr.setDisable(true);
                    to03hr.setDisable(false);
                    to04hr.setDisable(false);
                    break;
                case "03":
                    to09hr.setDisable(true);
                    to10hr.setDisable(true);
                    to11hr.setDisable(true);
                    to12hr.setDisable(true);
                    to01hr.setDisable(true);
                    to02hr.setDisable(true);
                    to03hr.setDisable(true);
                    to04hr.setDisable(false);
                    break;
                case "04":
                    to09hr.setDisable(true);
                    to10hr.setDisable(true);
                    to11hr.setDisable(true);
                    to12hr.setDisable(true);
                    to01hr.setDisable(true);
                    to02hr.setDisable(true);
                    to03hr.setDisable(true);
                    to04hr.setDisable(true);
                    break;
            }
        }
    }

    // upon mouse click, this goes to the previous page, which is the main menu
    public void back(ActionEvent actionEvent) throws IOException{
        // this is the usual way to go from the "current page" to the "next page"
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("../Menu/MenuUI.fxml"));
        ScheduleAnchor.getChildren().setAll(nextAnchorPane);
    }

    public void onClear(ActionEvent actionEvent) throws IOException {
        AnchorPane clear = FXMLLoader.load(getClass().getResource("../Schedule/ScheduleUI.fxml"));
        ScheduleAnchor.getChildren().setAll(clear);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeSchedule();
    }
}

