package com.iip.ui.ner.controller;

import com.iip.ui.ner.DBHelper.DatabaseHelper;
import com.iip.ui.ner.Resource.SingleDocEntity;
import com.iip.ui.ner.Resource.nerData;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.iip.ui.ner.MainNer.show_dialog;

public class EntitySimilarityViewController extends RootController implements Initializable {
    @FXML
    private TextField TFHintLoad;
    @FXML
    private Button BtnHintLoad;

    @FXML
    private TableView TVEntitySim;
    @FXML
    private TableColumn TCID;

    @FXML
    private TableColumn TCEntitySimilarity;

//    @FXML
//    private Button BtnImportEntity;
    @FXML
    public void entityAllClicked(){
        for(SingleDocEntity item: nerData.entityItems){
            item.allEntityExtract();
        }
        refreshTableData();
    }

    @FXML
    public void entitySelectClicked(){
        com.iip.ui.ner.controller.EntitySimilarityViewController controller =
                (com.iip.ui.ner.controller.EntitySimilarityViewController) com.iip.ui.ner.controller.Context.controllers.get("EntitySimilarityViewController"); // 注意
        int selectRowIndex = TVEntitySim.getSelectionModel().getSelectedIndex();
        if (selectRowIndex < 0){
            // 未选中
            System.out.println("请先选中表格中的一行");
        }
        else{
            nerData.entityItems.get(selectRowIndex).allEntityExtract();
            refreshTableData();
        }
    }

    @FXML
    public void entityImportClicked(){
        if (!DatabaseHelper.hasSet){
            show_dialog("请先在设置中填写相关数据");
            return;
        }
        List<String> dataforDB = new ArrayList<>();
        for (SingleDocEntity item: nerData.entityItems){
            System.out.print(item.personEntitytoString());
            dataforDB.add(item.personEntitytoString());
        }
        DatabaseHelper.write(DatabaseHelper.outputTableNames[0],"data",dataforDB);
        dataforDB.clear();
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
    }

    public void refreshTableData(){
        TVEntitySim.setItems(nerData.entityItems);
        TVEntitySim.refresh();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        init();
        refreshTableData(); // 注意
        TCID.setCellValueFactory( new PropertyValueFactory("id") );
        TCEntitySimilarity.setCellValueFactory( new PropertyValueFactory("allEntitySimilarityResult") );
    }

}
