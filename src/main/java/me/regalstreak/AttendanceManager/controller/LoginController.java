package me.regalstreak.AttendanceManager.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import me.regalstreak.AttendanceManager.model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    ControllerCommons controllerCommons = new ControllerCommons();

    public static User ourUser;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label register;

    @FXML
    JFXTextField username;

    @FXML
    JFXPasswordField password;

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

        if (!username.getText().isEmpty() && !password.getText().isEmpty()) {
            connectDB();
        } else {
            System.out.println("Fill stuff");
        }
    }

    @FXML
    void registerUser(MouseEvent event) throws IOException {
        AnchorPane registerUser = FXMLLoader.load(getClass().getResource("/fxml/register_user.fxml"));
        rootPane.getChildren().setAll(registerUser);
    }

    public User connectDB() {
        Connection connection = null;

        try {
            String query = "select * from USERS where username=? and password=?";
            String url = "jdbc:sqlite:/home/regalstreak/development/IdeaProjects/AttendanceManager/db/AttendanceManager.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Connection has been established in Login main F");
            String user = username.getText();
            String pass = password.getText();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, user);
            stmt.setString(2, pass);

            ResultSet res = stmt.executeQuery();

            if (res.next()) {

                System.out.println("Logged in");
                ourUser = new User(user, res.getInt("id"));
                AnchorPane studentMain = FXMLLoader.load(getClass().getResource("/fxml/student_main.fxml"));
                rootPane.getChildren().setAll(studentMain);
            } else {
                String queryAdmin = "select * from ADMINS where username=? and password=?";
                PreparedStatement stmtadmin = connection.prepareStatement(queryAdmin);
                stmtadmin.setString(1, user);
                stmtadmin.setString(2, pass);

                ResultSet resadmin = stmtadmin.executeQuery();

                if (resadmin.next()) {
                    System.out.println("Logged in admin");
                    ourUser = new User(user, res.getInt("id"));
                    AnchorPane adminMain = FXMLLoader.load(getClass().getResource("/fxml/admin_main.fxml"));
                    rootPane.getChildren().setAll(adminMain);
                } else {
                    System.out.println("Invalid user");
                }
            }

            return ourUser;

        } catch (SQLException e) {
            e.printStackTrace();
            return ourUser;
        } catch (IOException e) {
            e.printStackTrace();
            return ourUser;
        } finally {
            try {
                connection.close ();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }
}
