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

public class SearchIntroController extends Menu.PatientLinkedList{

    @FXML
    private AnchorPane SearchIntroAnchor;

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
            AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("SearchUI.fxml"));
            SearchIntroAnchor.getChildren().setAll(nextAnchorPane);
        }
    }

    // upon mouse click, this goes to the previous page, which is the main menu
    public void back(ActionEvent event) throws IOException {
        // this is the usual way to go from the "current page" to the "next page"
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("SearchEditDeleteIntroUI.fxml"));
        SearchIntroAnchor.getChildren().setAll(nextAnchorPane);
    }

}
