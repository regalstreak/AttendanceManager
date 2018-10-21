package me.regalstreak.AttendanceManager.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

public class StudentController implements Initializable {
    Connection connection = null;

    int userID = LoginController.ourUser.index;
    int totalattendance = 100;
    int subj = 0;
    int totalsubj = 0;

    ControllerCommons controllerCommons = new ControllerCommons();
    @FXML
    private GridPane gridPane;

    @FXML
    private Label day;

    @FXML
    private Label username;

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
        return (new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));

    }

    void connect() {

        try {

            if (connection != null) {
                connection.close();
            }

            username.setText(LoginController.ourUser.userName + "!");

            gridPane.getChildren().clear();

            String url = "jdbc:sqlite:/home/regalstreak/development/IdeaProjects/AttendanceManager/db/AttendanceManager.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Connection has been established in Student main F");

            String sql = "SELECT SUBJECTS.id, SUBJECTS.subject" +
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

                String currentSubject = resultSet.getString("subject");

                String currentSubjectAttendanceQuery = String.format("SELECT %s, %s from USERS where id=%s", currentSubject, currentSubject.toLowerCase() + "total", userID);
                Statement statement1 = connection.createStatement();
                ResultSet resultSet1 = statement1.executeQuery(currentSubjectAttendanceQuery);
                int currentSubjectAttendance = Integer.parseInt(resultSet1.getString(currentSubject));
                int currentSubjectTotalAttendance = Integer.parseInt(resultSet1.getString(currentSubject.toLowerCase() + "total"));

                JFXButton button1 = new JFXButton("Attended");
                button1.setPrefSize(90, 22);
                button1.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        String attend = String.format("update USERS " +
                                "SET totalattendance=?,%s=?,%s=? where id=?", currentSubject, currentSubject.toLowerCase() + "total");

                        try {
                            PreparedStatement attended = connection.prepareStatement(attend);
                            attended.setInt(1, 100); // totalattendance
                            attended.setInt(2, currentSubjectAttendance + 1); // subjA
                            attended.setInt(3, currentSubjectTotalAttendance + 1); // subjtotalA
                            attended.setInt(4, userID); // id

                            attended.executeUpdate();

                            connection.close();
                            resultSet.close();
                            resultSet1.close();

                        } catch (SQLException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                connection.close();
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }

                            connect();
                        }


                    }
                });

                JFXButton button2 = new JFXButton("Bunked");
                button2.setPrefSize(90, 22);
                button2.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                        String attend = String.format("update USERS " +
                                "SET totalattendance=?,%s=? where id=?", currentSubject.toLowerCase() + "total");

                        try {
                            PreparedStatement attended = connection.prepareStatement(attend);
                            attended.setInt(1, 100); // totalattendance
                            attended.setInt(2, currentSubjectTotalAttendance + 1); // subjtotalA
                            attended.setInt(3, userID); // id

                            attended.executeUpdate();


                            connection.close();
                            resultSet.close();
                            resultSet1.close();

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

                            connect();
                        }

                    }
                });

                JFXButton button3 = new JFXButton("No Class");
                button3.setPrefSize(90, 22);
                button3.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {

                    }
                });

                Label subject = new Label(resultSet.getString("subject"));
                subject.setPadding(new Insets(10));
                subject.setTextFill(Color.web("#B2B2B2"));

                hBox.getChildren().addAll(button1, button2, button3);

                float subjectPercentage = 0;
                if (currentSubjectAttendance != 0 && currentSubjectTotalAttendance != 0) {
                    subjectPercentage = Math.round(currentSubjectAttendance * 100 / currentSubjectTotalAttendance);
                }

                Label percent = new Label(subjectPercentage + "%");
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
        }
    }
}

