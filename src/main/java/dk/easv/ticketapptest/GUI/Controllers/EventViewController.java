package dk.easv.ticketapptest.GUI.Controllers;

import dk.easv.ticketapptest.BE.Ticket;
import dk.easv.ticketapptest.BE.User;
import dk.easv.ticketapptest.GUI.TemporaryDataClass;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class EventViewController {
    BorderPane root;
    @FXML
    private Label lblTitle;
    @FXML
    private Label lblLocation;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblTime;
    @FXML
    private Label lblDesc;
    @FXML
    private Button btnEditDesc;
    @FXML
    private TextField txtDesc;
    @FXML
    private Label lblTicket;
    @FXML
    private Button btnAddTicket;
    @FXML
    private TableView<Ticket> tblTicket;
    @FXML
    private Label lblCoords;
    @FXML
    private ListView lstCoords;
    @FXML
    private Button btnAddCoord;
    @FXML
    private Button btnReturn;
    @FXML
    private VBox vboxLeft;
    @FXML
    private VBox vboxRight;
    private TemporaryDataClass dataClass;
    private HashMap<User, Boolean> selectionState = new HashMap<>();
    @FXML
    private TableColumn<Ticket, String> clnTicket;

    @FXML
    private TableColumn<Ticket, String> clnDescription;

    @FXML
    private TableColumn<Ticket, Double> clnPrice;

    public void setPanel(BorderPane root)
    {
        this.root = root;
    }

        @FXML
        public void initialize() {
        dataClass = new TemporaryDataClass();
        tblTicket.getStylesheets().add("css/admineventstyle.css");
        tblTicket.getStyleClass().add("table-view");

            //TODO: FIND EN BEDRE MÅDE AT GØRE DET HER PÅ.
            lblTitle.getStyleClass().add("h1");
            lblLocation.getStyleClass().add("h2");
            lblDate.getStyleClass().add("h2");
            lblTime.getStyleClass().add("h2");
            lblDesc.getStyleClass().add("h3");
            lblTicket.getStyleClass().add("h2");
            lblCoords.getStyleClass().add("h2");
            btnEditDesc.getStyleClass().add("button2");
            btnAddTicket.getStyleClass().add("button2");
            btnAddCoord.getStyleClass().add("button2");
            btnReturn.getStyleClass().add("returnButton");
            vboxLeft.getStyleClass().add("vBoxBorder2");
            vboxRight.getStyleClass().add("vBoxBorder2");

            populateList();
            clnTicket.setCellValueFactory(cellData -> new SimpleStringProperty(( cellData.getValue()).getTicketName()));
            clnDescription.setCellValueFactory(cellData -> new SimpleStringProperty(( cellData.getValue()).getDescription()));
            clnPrice.setCellValueFactory(cellData -> new SimpleObjectProperty<>(( cellData.getValue()).getPrice()));


        }


    private void populateList(){
        lstCoords.setItems(dataClass.getUsers());
        lstCoords.setCellFactory(param -> new ListCell<User>() {
            private Label nameLabel = new Label();
            private Label emailLabel = new Label();
            private Label phoneLabel = new Label();
            private Label checkMarkLabel = new Label();

            {
                nameLabel.getStyleClass().add("h5");
                emailLabel.getStyleClass().add("h2");
                phoneLabel.getStyleClass().add("h3");

                VBox vBox = new VBox(5);
                HBox hBox = new HBox(5);
                hBox.getChildren().addAll(nameLabel, checkMarkLabel);
                vBox.getChildren().addAll(hBox, emailLabel, phoneLabel);
                setGraphic(vBox);
            }

            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    nameLabel.setText(null);
                    emailLabel.setText(null);
                    phoneLabel.setText(null);
                    checkMarkLabel.setText(null);
                } else {
                    nameLabel.setText(item.getFirstName() + " " + item.getLastName());
                    emailLabel.setText(item.getEmail());
                    phoneLabel.setText(item.getPhone());
                    if (selectionState.getOrDefault(item, false)) {
                        checkMarkLabel.setText("✓");
                    } else {
                        checkMarkLabel.setText(null);
                    }
                }
            }
        });
    }

    @FXML
    private void handleAddEvent(ActionEvent actionEvent) {
        User temp = (User  ) lstCoords.getSelectionModel().getSelectedItem();
        if (temp != null) {
            selectionState.put(temp, !selectionState.getOrDefault(temp, false));
            lstCoords.refresh();
        }
    }

    @FXML
    private void handleAddTicket(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/create-ticket-view.fxml"));
        Parent root = loader.load();
        CreateTicketViewController controller = loader.getController();
        controller.setParent(this);
        Stage stage = new Stage();
        stage.setTitle("Create Ticket");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/Base-stylesheet.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    public void addTicket(Ticket ticket) {
        tblTicket.getItems().add(ticket);
    }
}
