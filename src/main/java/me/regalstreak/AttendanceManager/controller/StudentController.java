package me.regalstreak.AttendanceManager.controller;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    ControllerCommons controllerCommons = new ControllerCommons();
    @FXML
    private JFXListView<Label> subjects;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        subjects.setExpanded(true);
        subjects.depthProperty().set(1);

        try {
            for (int i = 0; i < 4; i++) {
                Label label = new Label("Item" + i);
                label.setPadding(new Insets(10));
                label.setTextFill(Color.web("#B2B2B2"));
                subjects.getItems().add(label);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void close(MouseEvent event) {
        controllerCommons.close(event);
    }

    @FXML
    void max(MouseEvent event) {
        controllerCommons.max(event);
    }

    @FXML
    void min(MouseEvent event) {
        controllerCommons.min(event);
    }
}
