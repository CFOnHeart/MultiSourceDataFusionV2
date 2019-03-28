package com.iip.ui.ner.controller;

import com.iip.ui.ner.DBHelper.DatabaseHelper;
import com.iip.ui.ner.Resource.SingleDocEntity;
import com.iip.ui.ner.Resource.nerData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.iip.ui.ner.MainNer.show_dialog;

public class TimeNerTabViewController implements Initializable{

    @FXML
    private TableView TVEntity;
    @FXML
    private TableColumn TCID;

    @FXML
    private TableColumn TCRawData;
    @FXML
    private TableColumn TCEntityRes;

    @FXML
    public void timeAllClicked(){
        for(SingleDocEntity item: nerData.entityItems){
            item.timeEntityExtract();
        }
        refreshTableData();
    }

    @FXML
    public void timeSelectClicked(){
        com.iip.ui.ner.controller.TimeNerViewController controller =
                (com.iip.ui.ner.controller.TimeNerViewController) com.iip.ui.ner.controller.Context.controllers.get("TimeNerViewController");
        int selectRowIndex = TVEntity.getSelectionModel().getSelectedIndex();
        if (selectRowIndex < 0){
            // 未选中
            System.out.println("请先选中表格中的一行");
        }
        else{
            nerData.entityItems.get(selectRowIndex).timeEntityExtract();
            refreshTableData();
        }
    }


    @FXML
    public void timeImportClicked(){
        if (!DatabaseHelper.hasSet){
            show_dialog("请先在设置中填写相关数据");
            return;
        }
        List<String> dataforDB = new ArrayList<>();
        for (SingleDocEntity item:nerData.entityItems){
            System.out.print(item.timeEntitytoString());
            dataforDB.add(item.timeEntitytoString());
        }
        DatabaseHelper.write(DatabaseHelper.outputTableNames[3],"data",dataforDB);
        dataforDB.clear();
    }


    public void refreshTableData(){
        TVEntity.setItems(nerData.entityItems);
        TVEntity.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        refreshTableData();
        TCID.setCellValueFactory( new PropertyValueFactory("id") );
//        TCDate.setCellValueFactory(new PropertyValueFactory("dateStr"));
        TCRawData.setCellValueFactory( new PropertyValueFactory("text"));
        TCEntityRes.setCellValueFactory( new PropertyValueFactory("timeEntityResult") );
    }
}
