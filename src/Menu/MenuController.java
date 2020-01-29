package Menu;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;

public class MenuController extends PatientLinkedList{

    @FXML
    private AnchorPane MenuAnchor;

    @FXML
    private ImageView plLOGO;

    public void initialize() throws IOException {
        if(firstRun == true){
            readProfileToExcel();
            generateReceiptCount();
        }
        firstRun = false;

        FadeTransition fadeInMark = new FadeTransition(Duration.seconds(0.7), plLOGO);
        fadeInMark.setFromValue(0.0);
        fadeInMark.setToValue(1.0);
        fadeInMark.play();

    }

    // upon mouse click of our button, this method will go from the main menu to the page where the user can register a patient.
    public void registerNewPatient(ActionEvent actionEvent) throws IOException {
        // this is the usual way to go from the "current page" to the "next page"
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("../Register/RegisterUI.fxml"));
        MenuAnchor.getChildren().setAll(nextAnchorPane);
    }


    public void billing(ActionEvent actionEvent) throws IOException {
        // this is the usual way to go from the "current page" to the "next page"
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("../Bill/BillIntroUI.fxml"));
        MenuAnchor.getChildren().setAll(nextAnchorPane);
    }

    public void searchEditPatient(ActionEvent actionEvent) throws IOException {
        // this is the usual way to go from the "current page" to the "next page"
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("../SearchEditDelete/SearchEditDeleteIntroUI.fxml"));
        MenuAnchor.getChildren().setAll(nextAnchorPane);
    }

    // upon mouse click, this goes to the main menu to the "schedule appointment" page.
    public void scheduleAppointment(ActionEvent actionEvent) throws IOException {
        // this is the usual way to go from the "current page" to the "next page"
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("../Schedule/ScheduleIntroUI.fxml"));
        MenuAnchor.getChildren().setAll(nextAnchorPane);
    }

    public void searchSchedule(ActionEvent actionEvent) throws IOException {
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("../Schedule/ScheduleSearchUI.fxml"));
        MenuAnchor.getChildren().setAll(nextAnchorPane);
    }

    public void traverse(ActionEvent actionEvent) throws IOException {
        AllPatients.traverse();
    }

    // method for the test button, basically just calling the method for traversing the existing linked list which holds out patient data
    public void showData(ActionEvent actionEvent){
        AllPatients.traverse();
    }

}
