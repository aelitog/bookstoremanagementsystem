
package bookstoremanagementsystem;
//Login Interface Controller
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class FXMLDocumentController implements Initializable {
    
    /*
    Username: admin Password: 12345
    Username: cashier Password: 01234
    */
    
    public Label wronglabel;
    private String[] users = {"admin", "cashier"};
    private String[] passwords = {"12345", "01234"};

    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;


    public void MouseEvent(MouseEvent e) {
        Platform.exit();
    }

    @FXML
    public void handle(Event event) throws IOException {

        if ((username.getText().equals(users[0]) && password.getText().equals(passwords[0]))) {
            try {
                Parent enter = FXMLLoader.load(getClass().getResource("/bookstoremanagementsystem/loginfolder/admin.fxml"));
                createStage(enter, event, "Admin");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ((username.getText().equals(users[1]) && password.getText().equals(passwords[1]))) {
            try {
                Parent enter = FXMLLoader.load(getClass().getResource("/bookstoremanagementsystem/loginfolder/cashier.fxml"));
                createStage(enter, event, "Cashier");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            wronglabel.setText("Access denied!");

        }
    }

    @FXML
    public void keyHandle(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            handle(event);
        }

    }

    public void createStage(Parent p, Event event, String title) {
        Stage closedwindow = (Stage) ((Node) event.getSource()).getScene().getWindow();
        closedwindow.close();
        Scene signScene = new Scene(p);
        Stage window = new Stage();
        window.setScene(signScene);
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double w = (primaryScreenBounds.getMaxX() - 1024) / 2;
        double h = (primaryScreenBounds.getMaxY() - 720) / 2;
        window.setX(w);
        window.setY(h);
        window.setResizable(false);
        window.setTitle(title);
        window.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {

                try {
                    window.close();
                    Thread.sleep(250);
                    Stage stage = new Stage();
                    BookStoreManagementSystem back = new BookStoreManagementSystem();
                    back.start(stage);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        
        window.setScene(signScene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

    }

}
