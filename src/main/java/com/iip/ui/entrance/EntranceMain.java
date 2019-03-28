package com.iip.ui.entrance;
/**
 * Created by zhangkai on 2019/3/4.
 */

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class EntranceMain extends Application {

    private double xOffset;
    private double yOffset;

    @Override
    public void init() throws Exception {
        // 初始化参数配置
        super.init();
        xOffset = 0.0;
        yOffset = 0.0;

    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // 主程序开始
        try {
            Parent root = FXMLLoader.load(getClass().getResource("view/E1LoginView.fxml"));
            // 设定stage可以通过鼠标拖动到屏幕的其他地方
            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    event.consume();
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                }
            });

            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent event) {
                    event.consume();
                    primaryStage.setX(event.getScreenX() - xOffset);
                    primaryStage.setY(event.getScreenY() - yOffset);
                }
            });
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);

            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
