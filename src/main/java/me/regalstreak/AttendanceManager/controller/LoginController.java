package me.regalstreak.AttendanceManager.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    ControllerCommons controllerCommons = new ControllerCommons();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label register;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    @FXML
    void loadStudent(ActionEvent event) throws IOException {
        AnchorPane studentMain = FXMLLoader.load(getClass().getResource("/fxml/student_main.fxml"));
        rootPane.getChildren().setAll(studentMain);
    }

    @FXML
    void registerUser(MouseEvent event) throws IOException {
        AnchorPane registerUser = FXMLLoader.load(getClass().getResource("/fxml/register_user.fxml"));
        rootPane.getChildren().setAll(registerUser);
    }
}
