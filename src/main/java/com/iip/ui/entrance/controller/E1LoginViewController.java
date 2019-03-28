package com.iip.ui.entrance.controller;

import com.iip.ui.feature_extraction.Main;
import com.iip.ui.feature_extraction.execute.connection.DatabaseOperations;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.io.IOException;


/**
 * Created by zhangkai on 2019/3/4.
 */
public class E1LoginViewController {
    @FXML
    private BorderPane enMainViewPane;

    @FXML
    private Label un;
    @FXML
    private TextField user;
    @FXML
    private PasswordField pw;

    private String u, p = "";
    private boolean isLogin = false;

    @FXML
    private void login(MouseEvent mouseEvent){
        if(!isLogin){
            drawSelectFunctionPage();
            ((Button)(mouseEvent.getSource())).getScene().getWindow().hide();
            Main.f_alert_informationDialog("已登录!", "请进入配置界面进行数据源的配置!");
            return;
        }
        u = user.getText().trim();
        p = pw.getText().trim();
        if(u.equals("")||p.equals("")){
            DatabaseOperations.print("用户名或者密码为空");
            Main.f_alert_informationDialog("用户名和密码为空!", "请检查用户名和密码!");
        }else if(u.equals("root")&&p.equals("123456")){
            DatabaseOperations.print("登录成功");
            Main.f_alert_informationDialog("已登录!", "请进入配置界面进行数据源的配置!");
            isLogin = true;
        }else{
            DatabaseOperations.print("用户名或者密码错误");
            Main.f_alert_informationDialog("用户名或密码错误!", "请检查用户名和密码!");
        }
    }

    public void initialize(){
        un.setText("用户名:");
        user.setPromptText("输入用户名");
    }

    /**
     * 登录成功后跳转至功能选择窗口
     */
    public void drawSelectFunctionPage(){
        try {
            Parent target = FXMLLoader.load(getClass().getResource("../view/SelectWindows.fxml"));
            Scene scene = new Scene(target);
            Stage stg = new Stage();
            stg.setScene(scene);
            stg.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void menuButtonClicked(MouseEvent mouseEvent) {
        if(!isLogin){
            DatabaseOperations.print("未登录");
            Main.f_alert_informationDialog("未登录!", "请先登录!");
            return;
        }
        mouseEvent.consume();
        AnchorPane selectedMenuButton = (AnchorPane) mouseEvent.getTarget();

    }

    @FXML
    private void exit(MouseEvent mouseEvent){
        DatabaseOperations.disconnect();
        mouseEvent.consume();
        Platform.exit();
    }

}

