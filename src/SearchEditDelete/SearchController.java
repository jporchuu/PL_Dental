package SearchEditDelete;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.io.IOException;

public class SearchController extends Menu.PatientLinkedList{

    @FXML
    private AnchorPane SearchAnchor;

    @FXML
    private Text givenName;

    @FXML
    private Text givenGender;

    @FXML
    private Text givenAge;

    @FXML
    private Text disease1;

    @FXML
    private Text disease2;

    @FXML
    private Text disease3;

    @FXML
    private Text disease4;

    @FXML
    private Text disease5;

    public void initialize(){
        givenName.setText(searchedName);
        givenGender.setText(searchedGender);
        givenAge.setText(searchedAge);
        disease1.setText(String.valueOf(searchedCaries));
        disease2.setText(String.valueOf(searchedGingivitis));
        disease3.setText(String.valueOf(searchedDebris));
        disease4.setText(String.valueOf(searchedCalculus));
        disease5.setText(String.valueOf(searchedAnomaly));
    }

    public void back(ActionEvent event) throws IOException {
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("../Menu/MenuUI.fxml"));
        SearchAnchor.getChildren().setAll(nextAnchorPane);
    }

}
