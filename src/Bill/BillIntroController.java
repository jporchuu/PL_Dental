package Bill;

import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BillIntroController extends Menu.SchedLinkedList{

    public static int priceTreatment = 0, quantityTreatment = 0, totalPrice = 0;
    public static String currentTime;

    @FXML
    private AnchorPane billAnchor;

    @FXML
    private Text givenName;

    @FXML
    private Text serviceRendered;

    @FXML
    private Text dateOfTreatment;

    @FXML
    private Text timeOfTreatment;

    @FXML
    private ChoiceBox choiceAppointments;

    @FXML
    private Spinner appointmentQuantity;

    @FXML
    private JFXTextField appointmentPrice;

    @FXML
    private Text noPatientERR;


    public void initialize(){
        initializeSchedule();
        appointmentsList.clear();
        Appointments.seekList();
        choiceAppointments.setItems(appointmentsList);

        SpinnerValueFactory<Integer> quantities = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,99,1);
        this.appointmentQuantity.setValueFactory(quantities);
    }

    public void seekAppointment(ActionEvent event) throws IOException {
        StringBuilder chosenAppointment = sliceString((String) choiceAppointments.getValue());

        Appointments.search(chosenAppointment.toString());

        givenName.setText(searchedName);
        serviceRendered.setText(searchedService);
        dateOfTreatment.setText(searchedDate);
        timeOfTreatment.setText(searchedTime);
    }

    public void billPatient(ActionEvent event) throws IOException {

        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        currentTime = formatter.format(date);

        if(givenName.getText().equals("")){
            FadeTransition fadeInMark = new FadeTransition(Duration.seconds(0.2), noPatientERR);
            fadeInMark.setFromValue(0.0);
            fadeInMark.setToValue(1.0);
            fadeInMark.play();
        }
        else{
            priceTreatment = Integer.valueOf(appointmentPrice.getText());
            quantityTreatment = (int) appointmentQuantity.getValue();

            totalPrice = priceTreatment * quantityTreatment;

            AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("../Bill/BillUI.fxml"));
            billAnchor.getChildren().setAll(nextAnchorPane);
        }
    }

    private StringBuilder sliceString(String appointmentDetails){

        StringBuilder patientName = new StringBuilder();

        for (int i = 0; i < appointmentDetails.length(); i++) {
            if (appointmentDetails.charAt(i) == ' '){
                if (appointmentDetails.charAt(i+1) != '-'){
                    patientName.append(appointmentDetails.charAt(i));
                }
                else{
                    break;
                }
            }
            else patientName.append(appointmentDetails.charAt(i));
        }

        return patientName;
    }

    public void back(ActionEvent event) throws IOException {
        AnchorPane nextAnchorPane = FXMLLoader.load(getClass().getResource("../Menu/MenuUI.fxml"));
        billAnchor.getChildren().setAll(nextAnchorPane);
    }

    public void initializeSchedule(){
        if(Appointments.head==null) {
            try {
                getElementsFromExcel();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
