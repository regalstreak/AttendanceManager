package me.regalstreak.AttendanceManager.controller;

import com.jfoenix.controls.JFXListView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    ControllerCommons controllerCommons = new ControllerCommons();
    @FXML
    private JFXListView<HBox> subjects;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        subjects.setExpanded(true);
        subjects.depthProperty().set(1);
        connect();
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


    public void connect() {
        Connection connection = null;
        try {
            String url = "jdbc:sqlite:/home/regalstreak/development/IdeaProjects/AttendanceManager/db/AttendanceManager.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Connection has been established F");

            String sql = "SELECT * FROM TIMETABLE";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {

                HBox hBox = new HBox();
                hBox.setPadding(new Insets(15,12,15,12));
                hBox.setSpacing(10);

                Button button1 = new Button("Test");
                button1.setPrefSize(100, 20);

                Button button2 = new Button("Test2");
                button2.setPrefSize(100, 20);

                Button button3 = new Button("Test3");
                button3.setPrefSize(100, 20);

                Label label = new Label(resultSet.getString("Monday"));
                label.setPadding(new Insets(10));
                label.setTextFill(Color.web("#B2B2B2"));

                hBox.getChildren().addAll(label, button1, button2, button3);

                subjects.getItems().add(hBox);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
