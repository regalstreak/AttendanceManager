package me.regalstreak.AttendanceManager.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.*;
import java.util.Calendar;
import java.util.ResourceBundle;

public class StudentController implements Initializable {

    ControllerCommons controllerCommons = new ControllerCommons();
    @FXML
    private GridPane gridPane;

    @FXML
    private Label day;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        connect();
        day.setText(getDay());
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

    private String getDay() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
//        return(new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));
        return ("Monday");

    }

    public void connect() {
        Connection connection = null;
        try {
            String url = "jdbc:sqlite:/home/regalstreak/development/IdeaProjects/AttendanceManager/db/AttendanceManager.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Connection has been established F");

            String sql = "SELECT SUBJECTS.subject" +
                    " from TIMETABLE, SUBJECTS where" +
                    " TIMETABLE." + getDay() + " = SUBJECTS.id";

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            gridPane.setHgap(30);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(0, 10, 0, 10));
            int y = 0;

            while (resultSet.next()) {

                HBox hBox = new HBox();
                hBox.setSpacing(10);

                Button button1 = new Button("Attended");
                button1.setPrefSize(90, 22);

                Button button2 = new Button("Bunked");
                button2.setPrefSize(90, 22);

                Button button3 = new Button("No Class");
                button3.setPrefSize(90, 22);

                Label subject = new Label(resultSet.getString("subject"));
                subject.setPadding(new Insets(10));
                subject.setTextFill(Color.web("#B2B2B2"));

                hBox.getChildren().addAll(button1, button2, button3);

                Label percent = new Label("75%");
                percent.setPadding(new Insets(10));
                percent.setTextFill(Color.web("#B2B2B2"));

                gridPane.setConstraints(subject, 0, y);
                gridPane.setConstraints(hBox, 1, y);
                gridPane.setConstraints(percent, 2, y);

                gridPane.getChildren().addAll(subject, hBox, percent);
                y++;

            }
            y = 0;

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
