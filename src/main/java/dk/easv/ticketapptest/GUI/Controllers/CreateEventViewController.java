package dk.easv.ticketapptest.GUI.Controllers;

import dk.easv.ticketapptest.GUI.TemporaryDataClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class CreateEventViewController {


    public Button btnCreateEvent;
    @FXML
    private TextField txtNameEvent;
    @FXML
    private TextArea txtDescriptionEvent;
    @FXML
    private TextField txtLocationEvent;
    @FXML
    private TextField txtDateEvent;
    @FXML
    private TextField txtStartEvent;
    @FXML
    private TextField txtEndEvent;
    TemporaryDataClass data;


    private EventEventManagementController parent;

    @FXML
    private void initialize() {
        data = new TemporaryDataClass();
        String cssFile = getClass().getResource("/css/usermanagementstyle.css").toExternalForm();
        btnCreateEvent.getStylesheets().add(cssFile);
        //btnAddTicket.getStylesheets().add(cssFile);
    }



    public void setParent(EventEventManagementController parent) {
        this.parent = parent;
    }


    public void CreateEvent(ActionEvent actionEvent) {

        if (!txtNameEvent.getText().isEmpty()
                && !txtDateEvent.getText().isEmpty()
                && !txtEndEvent.getText().isEmpty()
                && !txtDescriptionEvent.getText().isEmpty()
                && !txtStartEvent.getText().isEmpty()
                && !txtLocationEvent.getText().isEmpty()) {
            parent.createEvent(txtNameEvent.getText(), txtLocationEvent.getText(), txtDateEvent.getText(), txtStartEvent.getText(), txtEndEvent.getText(), new String[]{"Early Bird $299", "Regular $399"}, data.getUsers() );
            ((Stage) txtNameEvent.getScene().getWindow()).close();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Please fill in all the fields");
            alert.setContentText("You must fill in all the fields to create an event.");
            alert.showAndWait();
        }

    }


}
