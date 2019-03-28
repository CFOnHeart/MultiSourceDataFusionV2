package com.iip.ui.ner.controller;

import com.iip.ui.ner.Resource.nerData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class TimeNerViewController extends RootController implements Initializable {

    @FXML
    private ListView<String> LVData;
    @FXML
    private Label LBData;
    @FXML
    private TextField TFHintLoad;
    @FXML
    private Button BtnHintLoad;
    @FXML
    private TabPane TPTimeNer;
    public TabPane getTPTimeNer(){ return TPTimeNer; }


    public Tab generateNewTab(String id, String text){
        Tab tab = new Tab();
        tab.setId(id);
        tab.setText(text);
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(TimeNerTabViewController.class.getResource("../view/TimeNerTabView.fxml"));
            BorderPane pane = loader.load();
            tab.setContent(pane);
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return null;
        }
        return tab;
    }


    @Override
    public void init(){
        // 数据如果已经加载过，就不显示加载数据的提示
        if( nerData.handledDataList.size() > 0 ){
            BtnHintLoad.setOpacity(0.0f);
            TFHintLoad.setOpacity(0.0f);
        }
        else{
            BtnHintLoad.setOpacity(1.0f);
            TFHintLoad.setOpacity(1.0f);
            BtnHintLoad.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    nerMainViewController controller =
                            (nerMainViewController) Context.controllers.get(nerMainViewController.class.getSimpleName());
                    controller.presentLoginView();
                }
            });
        }

        // 加载TabView fxml
        Tab tab1 = generateNewTab("Tab1", "时间识别");
        if(tab1 != null) TPTimeNer.getTabs().add(tab1);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        System.out.println("In TimeNer initialize");
        init();
    }


}
