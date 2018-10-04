package me.regalstreak.AttendanceManager;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    private double mouseDragDeltaX;
    private double mouseDragDeltaY;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent login = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));

        primaryStage.initStyle(StageStyle.UNDECORATED);

        login.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                mouseDragDeltaX = login.getLayoutX() - mouseEvent.getSceneX();
                mouseDragDeltaY = login.getLayoutY() - mouseEvent.getSceneY();
            }
        });

        login.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                primaryStage.setX(mouseEvent.getScreenX() + mouseDragDeltaX);
                primaryStage.setY(mouseEvent.getScreenY() + mouseDragDeltaY);
            }
        });

        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(login));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
