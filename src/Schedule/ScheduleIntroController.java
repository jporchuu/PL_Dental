package Schedule;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ScheduleIntroController extends Menu.PatientLinkedList{

    @FXML
    private AnchorPane ScheduleIntroAnchor;

    public void proceedToScheduling(ActionEvent actionEvent) throws IOException {
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("../Schedule/ScheduleUI.fxml"));
        ScheduleIntroAnchor.getChildren().setAll(nextAnchorPane);
    }

    public void registerNewPatient(ActionEvent actionEvent) throws IOException {
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("../Register/RegisterUI.fxml"));
        ScheduleIntroAnchor.getChildren().setAll(nextAnchorPane);
    }

    public void back(ActionEvent event) throws IOException {
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("../Menu/MenuUI.fxml"));
        ScheduleIntroAnchor.getChildren().setAll(nextAnchorPane);
    }


}
