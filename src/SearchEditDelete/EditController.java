package SearchEditDelete;

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

public class EditController extends Menu.PatientLinkedList{

    // This creates a list of strings that will be the different age ranges of our "age range" choice box
    ObservableList<String> ageGroups = FXCollections.observableArrayList("0 - 5 Years Old", "6 - 9 Years Old", "10 - 24 Years Old", "25 - 59 Years Old", "60+ Years Old");

    @FXML
    private AnchorPane EditAnchor;

    @FXML
    private ChoiceBox ageChoice;

    @FXML
    private ToggleGroup genderRadio;

    @FXML
    private JFXRadioButton maleRadio;

    @FXML
    private JFXRadioButton femaleRadio;

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

    public void initialize(){
        SpinnerValueFactory<Integer> cariesAmount = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,99,1);
        this.cariesAmt.setValueFactory(cariesAmount);

        SpinnerValueFactory<Integer> gingiPerioAmount = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,30,1);
        this.gingiPerioAmt.setValueFactory(gingiPerioAmount);

        SpinnerValueFactory<Integer> debrisAmount = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,99,1);
        this.debrisAmt.setValueFactory(debrisAmount);

        patientName.setText(searchedName);

        maleRadio.setSelected(false);
        femaleRadio.setSelected(false);
        if(searchedGender.equals("Male")) maleRadio.setSelected(true);
        else if(searchedGender.equals("Female")) femaleRadio.setSelected(true);

        ageChoice.setValue(searchedAge);
        ageChoice.setItems(ageGroups);

        if(searchedCaries > 0){
            dentalCaries.setSelected(true);
            cariesAmt.setDisable(false);
            cariesAmt.getValueFactory().setValue(searchedCaries);
        }

        if(searchedGingivitis > 0){
            gingiPerio.setSelected(true);
            gingiPerioAmt.setDisable(false);
            gingiPerioAmt.getValueFactory().setValue(searchedGingivitis);
        }

        if(searchedDebris > 0){
            oralDebris.setSelected(true);
            debrisAmt.setDisable(false);
            debrisAmt.getValueFactory().setValue(searchedDebris);
        }

        if(searchedCalculus > 0){
            calculus.setSelected(true);
        }

        if(searchedAnomaly > 0){
            dentoFacialAnomaly.setSelected(true);
        }

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

    public void editPatientData(ActionEvent event) throws IOException{

        String name =  patientName.getText();
        JFXRadioButton selectedGender = (JFXRadioButton) genderRadio.getSelectedToggle();
        String gender = selectedGender.getText();
        String age = (String) ageChoice.getValue();

        int dc = 0, gp = 0, od = 0, c = 0, dfa = 0;

        if(dentalCaries.isSelected()) dc = (int) cariesAmt.getValue();

        if(gingiPerio.isSelected()) gp = (int) gingiPerioAmt.getValue();

        if(oralDebris.isSelected()) od = (int) debrisAmt.getValue();

        if(calculus.isSelected()) c = 1;

        if(dentoFacialAnomaly.isSelected()) dfa = 1;

        AllPatients.read = false;
        AllPatients.editBetweenNodes(name, gender, age, dc, gp, od, c, dfa);

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

    public void back(ActionEvent event) throws IOException {
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("../Menu/MenuUI.fxml"));
        EditAnchor.getChildren().setAll(nextAnchorPane);
    }

}
