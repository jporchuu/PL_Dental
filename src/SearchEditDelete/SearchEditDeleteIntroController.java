package SearchEditDelete;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class SearchEditDeleteIntroController extends Menu.PatientLinkedList{

    @FXML
    private AnchorPane ScheduleIntroAnchor;

    public void proceedToSearch(ActionEvent actionEvent) throws IOException {
        // this is the usual way to go from the "current page" to the "next page"
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("SearchIntroUI.fxml"));
        ScheduleIntroAnchor.getChildren().setAll(nextAnchorPane);
    }

    // upon mouse click of our button, this method will go from the scheduling menu page to the page where the user can register a patient.
    public void proceedToEdit(ActionEvent actionEvent) throws IOException {
        // this is the usual way to go from the "current page" to the "next page"
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("EditIntroUI.fxml"));
        ScheduleIntroAnchor.getChildren().setAll(nextAnchorPane);
    }

    // upon mouse click of our button, this method will go from the scheduling menu page to the page where the user can register a patient.
    public void proceedToDelete(ActionEvent actionEvent) throws IOException {
        // this is the usual way to go from the "current page" to the "next page"
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("DeleteIntroUI.fxml"));
        ScheduleIntroAnchor.getChildren().setAll(nextAnchorPane);
    }


    // upon mouse click, this goes to the previous page, which is the main menu
    public void back(ActionEvent event) throws IOException {
        // this is the usual way to go from the "current page" to the "next page"
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("../Menu/MenuUI.fxml"));
        ScheduleIntroAnchor.getChildren().setAll(nextAnchorPane);
    }

}
