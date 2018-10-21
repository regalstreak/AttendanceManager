package me.regalstreak.AttendanceManager.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class RegisterUserController {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXPasswordField confirmpassword;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField batch;


    ControllerCommons controllerCommons = new ControllerCommons();

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
    void registerStudent(ActionEvent event) throws IOException {
        String userEmail = email.getText();
        String userPassword = password.getText();
        String userConfirmPassword = confirmpassword.getText();
        String userBatch = batch.getText();
        String userName = username.getText();

        String[] userInfo = {userName, userEmail, userBatch, userPassword};

        if (!userEmail.isEmpty() && !userBatch.isEmpty() && !userPassword.isEmpty() && !userConfirmPassword.isEmpty() && !userEmail.isEmpty()) {
            if (userName.length() < 9) {
                if (emailIsValid(userEmail)) {
                    if (userPassword != userConfirmPassword) {
                        if (userBatch.length() < 3) {
                            connectDB(userInfo);
                        } else {
                            System.out.println("Batch invalid");
                        }
                    } else {
                        System.out.println("Passwords don't match");
                    }
                } else {
                    System.out.println("Email invalid");
                }
            } else {
                System.out.println("Username invalid");
            }
        } else {
            System.out.println("All fields are required");
        }
    }

    void connectDB(String[] args) {
        Connection connection = null;

        try {
            String url = "jdbc:sqlite:/home/regalstreak/development/IdeaProjects/AttendanceManager/db/AttendanceManager.db";
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);

            System.out.println("Connection has been established in Registration F");

            String insertUser = "insert into USERS (username,email,batch,password,totalattendance,AM3,am3total,OOPM,oopmtotal,OOPML,oopmltotal,DS,dstotal,DSYL,dsyltotal,DSL,dsltotal,BEL,beltotal,ECCF,eccftotal,DLDA,dldatotal,DIS,distotal) values (?,?,?,?,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0)";

            PreparedStatement statement = connection.prepareStatement(insertUser);

            statement.setString(1, args[0]);
            statement.setString(2, args[1]);
            statement.setString(3, args[2]);
            statement.setString(4, args[3]);
            statement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException excep) {
                    excep.printStackTrace();
                }
            }
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        try {
            if (connection != null) {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    boolean emailIsValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}
