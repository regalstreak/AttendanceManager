package me.regalstreak.AttendanceManager.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    Connection connection = null;

    int userID = 0;
    int totalattendance = 100;
    int subj = 0;
    int totalsubj = 0;

    ControllerCommons controllerCommons = new ControllerCommons();
    @FXML
    private GridPane gridPane;

    @FXML
    private Label day;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

    private String getDay() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date date = calendar.getTime();
        return (new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime()));

    }

    void connect() {
        gridPane.setGridLinesVisible(true);
        try {

            if (connection != null) {
                connection.close();
            }

            gridPane.getChildren().clear();

            String url = "jdbc:sqlite:/home/regalstreak/development/IdeaProjects/AttendanceManager/db/AttendanceManager.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Connection has been established in Admin main F");

            String sql = "SELECT * from USERS";

            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            gridPane.setHgap(30);
            gridPane.setVgap(10);
            gridPane.setPadding(new Insets(0, 10, 0, 10));
            int y = 1;

            while (resultSet.next()) {


                Label noText = new Label("No.");
                noText.setPadding(new Insets(10));
                noText.setTextFill(Color.web("#B2B2B2"));

                Label no = new Label(Integer.toString(y));
                no.setPadding(new Insets(10));
                no.setTextFill(Color.web("#B2B2B2"));

                Label username = new Label(resultSet.getString("username"));
                username.setPadding(new Insets(10));
                username.setTextFill(Color.web("#B2B2B2"));

                Label usernameText = new Label("USERNAME");
                usernameText.setPadding(new Insets(10));
                usernameText.setTextFill(Color.web("#B2B2B2"));

                Label AM3 = new Label(resultSet.getString("AM3"));
                AM3.setPadding(new Insets(10));
                AM3.setTextFill(Color.web("#B2B2B2"));

                Label AM3Text = new Label("AM3");
                AM3Text.setPadding(new Insets(10));
                AM3Text.setTextFill(Color.web("#B2B2B2"));

                Label ECCF = new Label(resultSet.getString("ECCF"));
                ECCF.setPadding(new Insets(10));
                ECCF.setTextFill(Color.web("#B2B2B2"));

                Label ECCFText = new Label("ECCF");
                ECCFText.setPadding(new Insets(10));
                ECCFText.setTextFill(Color.web("#B2B2B2"));

                Label OOPM = new Label(resultSet.getString("OOPM"));
                OOPM.setPadding(new Insets(10));
                OOPM.setTextFill(Color.web("#B2B2B2"));

                Label OOPMText = new Label("OOPM");
                OOPMText.setPadding(new Insets(10));
                OOPMText.setTextFill(Color.web("#B2B2B2"));

                Label DLDA = new Label(resultSet.getString("DLDA"));
                DLDA.setPadding(new Insets(10));
                DLDA.setTextFill(Color.web("#B2B2B2"));

                Label DLDAText = new Label("DLDA");
                DLDAText.setPadding(new Insets(10));
                DLDAText.setTextFill(Color.web("#B2B2B2"));

                Label DIS = new Label(resultSet.getString("DIS"));
                DIS.setPadding(new Insets(10));
                DIS.setTextFill(Color.web("#B2B2B2"));

                Label DISText = new Label("DIS");
                DISText.setPadding(new Insets(10));
                DISText.setTextFill(Color.web("#B2B2B2"));

                Label DS = new Label(resultSet.getString("DS"));
                DS.setPadding(new Insets(10));
                DS.setTextFill(Color.web("#B2B2B2"));

                Label DSText = new Label("DS");
                DSText.setPadding(new Insets(10));
                DSText.setTextFill(Color.web("#B2B2B2"));

                Label DSL = new Label(resultSet.getString("DSL"));
                DSL.setPadding(new Insets(10));
                DSL.setTextFill(Color.web("#B2B2B2"));

                Label DSLText = new Label("DSL");
                DSLText.setPadding(new Insets(10));
                DSLText.setTextFill(Color.web("#B2B2B2"));

                Label DSYL = new Label(resultSet.getString("DSYL"));
                DSYL.setPadding(new Insets(10));
                DSYL.setTextFill(Color.web("#B2B2B2"));

                Label DSYLText = new Label("DSYL");
                DSYLText.setPadding(new Insets(10));
                DSYLText.setTextFill(Color.web("#B2B2B2"));

                Label BEL = new Label(resultSet.getString("BEL"));
                BEL.setPadding(new Insets(10));
                BEL.setTextFill(Color.web("#B2B2B2"));

                Label BELText = new Label("BEL");
                BELText.setPadding(new Insets(10));
                BELText.setTextFill(Color.web("#B2B2B2"));

                Label OOPML = new Label(resultSet.getString("OOPML"));
                OOPML.setPadding(new Insets(10));
                OOPML.setTextFill(Color.web("#B2B2B2"));

                Label OOPMLText = new Label("OOPML");
                OOPMLText.setPadding(new Insets(10));
                OOPMLText.setTextFill(Color.web("#B2B2B2"));


                gridPane.setConstraints(no, 0, y);
                gridPane.setConstraints(username, 1, y);
                gridPane.setConstraints(AM3, 2, y);
                gridPane.setConstraints(OOPM, 3, y);
                gridPane.setConstraints(OOPML, 4, y);
                gridPane.setConstraints(DS, 5, y);
                gridPane.setConstraints(DIS, 6, y);
                gridPane.setConstraints(DLDA, 7, y);
                gridPane.setConstraints(DSL, 8, y);
                gridPane.setConstraints(DSYL, 9, y);

                gridPane.setConstraints(noText, 0, 0);
                gridPane.setConstraints(usernameText, 1, 0);
                gridPane.setConstraints(AM3Text, 2, 0);
                gridPane.setConstraints(OOPMText, 3, 0);
                gridPane.setConstraints(OOPMLText, 4, 0);
                gridPane.setConstraints(DSText, 5, 0);
                gridPane.setConstraints(DISText, 6, 0);
                gridPane.setConstraints(DLDAText, 7, 0);
                gridPane.setConstraints(DSLText, 8, 0);
                gridPane.setConstraints(DSYLText, 9, 0);

                gridPane.getChildren().addAll(no, username, AM3, OOPM, OOPML, DS, DIS, DLDA, DSL, DSYL,
                        noText, usernameText, AM3Text, OOPMText, OOPMLText, DSText, DISText, DLDAText, DSLText, DSYLText);
                y++;

            }
            y = 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

