package SearchEditDelete;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;

public class EditIntroController extends Menu.PatientLinkedList{

    @FXML
    private AnchorPane EditIntroAnchor;

    @FXML
    private TextField searchName;

    @FXML
    private Text searchERR;

    public void searchPatientName(ActionEvent actionEvent) throws IOException{
        AllPatients.search(searchName.getText());

        if(searchedName.equals("")){
            FadeTransition fadeInMark = new FadeTransition(Duration.seconds(0.2), searchERR);
            fadeInMark.setFromValue(0.0);
            fadeInMark.setToValue(1.0);
            fadeInMark.play();
        }
        else{
            AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("EditUI.fxml"));
            EditIntroAnchor.getChildren().setAll(nextAnchorPane);
        }
    }

    public void back(ActionEvent event) throws IOException {
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("SearchEditDeleteIntroUI.fxml"));
        EditIntroAnchor.getChildren().setAll(nextAnchorPane);
    }

}
