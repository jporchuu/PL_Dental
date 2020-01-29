package Register;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class RegisterController extends Menu.PatientLinkedList{

    // This creates a list of strings that will be the different age ranges of our "age range" choice box
    ObservableList<String> ageGroups = FXCollections.observableArrayList("0 - 5 Years Old", "6 - 9 Years Old", "10 - 24 Years Old", "25 - 59 Years Old", "60+ Years Old");

    @FXML
    private AnchorPane RegisterAnchor;

    @FXML
    private ChoiceBox ageChoice;

    @FXML
    private ToggleGroup genderRadio;

    @FXML
    private TextField patientName;

    @FXML
    private Spinner cariesAmt;

    @FXML
    private Spinner gingiPerioAmt;

    @FXML
    private Spinner debrisAmt;

    @FXML
    private JFXCheckBox dentalCaries;

    @FXML
    private JFXCheckBox gingiPerio;

    @FXML
    private JFXCheckBox oralDebris;

    @FXML
    private JFXCheckBox calculus;

    @FXML
    private JFXCheckBox dentoFacialAnomaly;

    // when the program goes to this page, the program immediately initializes by putting the age ranges for our age range choicebox
    public void initialize(){
        ageChoice.setItems(ageGroups);

        SpinnerValueFactory<Integer> cariesAmount = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,99,1);
        this.cariesAmt.setValueFactory(cariesAmount);

        SpinnerValueFactory<Integer> gingiPerioAmount = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,30,1);
        this.gingiPerioAmt.setValueFactory(gingiPerioAmount);

        SpinnerValueFactory<Integer> debrisAmount = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,99,1);
        this.debrisAmt.setValueFactory(debrisAmount);
    }

    public void dentalCariesSelected(ActionEvent event) throws IOException{
        if(dentalCaries.isSelected()) cariesAmt.setDisable(false);
        else cariesAmt.setDisable(true);
    }
    public void gingiPerioSelected(ActionEvent event) throws IOException{
        if(gingiPerio.isSelected()) gingiPerioAmt.setDisable(false);
        else gingiPerioAmt.setDisable(true);
    }
    public void oralDebrisSelected(ActionEvent event) throws IOException{
        if(oralDebris.isSelected()) debrisAmt.setDisable(false);
        else debrisAmt.setDisable(true);
    }


    // upon mouse click, this method identifies the different user-inputted values for the patient data
    public void getPatientData(ActionEvent event) throws IOException{

        // this gets the text of the text field and puts it into a string variable "name"
        String name =  patientName.getText();

        // this gets the patients gender as selected by the user, as well as getting the string of the selected gender and puts it into a string variable "gender"
        JFXRadioButton selectedGender = (JFXRadioButton) genderRadio.getSelectedToggle();
        String gender = selectedGender.getText();

        // this gets the string of the selected age range and puts it into a string variable "age"
        String age = (String) ageChoice.getValue();

        int dc = 0, gp = 0, od = 0, c = 0, dfa = 0;

        if(dentalCaries.isSelected()){
            dc = (int) cariesAmt.getValue();
        }
        if(gingiPerio.isSelected()){
            gp = (int) gingiPerioAmt.getValue();
        }
        if(oralDebris.isSelected()){
            od = (int) debrisAmt.getValue();
        }

        if(calculus.isSelected()) c = 1;

        if(dentoFacialAnomaly.isSelected()) dfa = 1;

        AllPatients.read = false;
        AllPatients.pushPatientData(name, gender, age, dc, gp, od, c, dfa);

        // clears the textfield's data, indicating that the next set of data is ready to be inputted
        patientName.setText("");

        dentalCaries.setSelected(false);
        gingiPerio.setSelected(false);
        oralDebris.setSelected(false);
        calculus.setSelected(false);
        dentoFacialAnomaly.setSelected(false);
        cariesAmt.setDisable(true);
        gingiPerioAmt.setDisable(true);
        debrisAmt.setDisable(true);
    }

    // upon mouse click, this goes to the previous page, which is the main menu
    public void back(ActionEvent event) throws IOException {
        // this is the usual way to go from the "current page" to the "next page"
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("../Menu/MenuUI.fxml"));
        RegisterAnchor.getChildren().setAll(nextAnchorPane);
    }

}
