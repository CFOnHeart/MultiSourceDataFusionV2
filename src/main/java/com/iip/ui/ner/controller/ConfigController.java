package com.iip.ui.ner.controller;

import com.iip.ui.ner.DBHelper.DatabaseHelper;
import com.iip.ui.ner.Resource.SingleDocEntity;
import com.iip.ui.ner.Resource.SingleDocParticiple;
import com.iip.ui.ner.Resource.nerData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static com.iip.ui.ner.MainNer.show_dialog;

public class ConfigController extends RootController implements Initializable {
    @FXML
    private TextField inputsql, outputsql_name, outputsql_loc, outputsql_org, outputsql_time, databaseName;
    private String inputsql_name = "";
    private String output_name = "";
    private String output_loc = "";
    private String output_org = "";
    private String output_time = "";
    private String databaseid = "";
    private Boolean hasSetTable = false;

    @Override
    public void init() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        databaseName.setText("forKJB");
        inputsql.setText("datasouce1");
        outputsql_name.setText("dataout1");
        outputsql_loc.setText("dataout2");
        outputsql_org.setText("dataout3");
        outputsql_time.setText("dataout4");
    }

    @FXML
    private void loadDataClicked(MouseEvent mouseEvent) {
        databaseid = databaseName.getText();
        if ("".equals(databaseid)) {
            show_dialog("请输入数据库名称");
            return;
        }
        if (!hasSetTable) {
            show_dialog("请输入导入导出表名");
            return;
        }
        // 连接数据库
        DatabaseHelper.DBNAME = databaseid;
        DatabaseHelper.DB_URL = DatabaseHelper.DB_URL_prefix + DatabaseHelper.DBNAME + "?characterEncoding=utf-8";
        if (!DatabaseHelper.connectDB()) {
            show_dialog("连接数据库失败");
            return;
        }
        DatabaseHelper.print("数据加载完成");


    }

    @FXML
    private void exit(MouseEvent mouseEvent) {
        //断开数据库连接
        DatabaseHelper.disconnect();
        System.exit(0);
    }

    @FXML
    private void resetDababase(MouseEvent mouseEvent) {
        inputsql.setText("");
        outputsql_name.setText("");
        outputsql_loc.setText("");
        outputsql_org.setText("");
        outputsql_time.setText("");
        databaseName.setText("");
        hasSetTable = false;
        DatabaseHelper.hasSet = false;
        inputsql_name = "";
        output_loc = "";
        output_name = "";
        output_org = "";
        output_time = "";
    }

    @FXML
    private void confirm(MouseEvent mouseEvent) {
        inputsql_name = inputsql.getText().trim();
        output_time = outputsql_time.getText().trim();
        output_loc = outputsql_loc.getText().trim();
        output_name = outputsql_name.getText().trim();
        output_org = outputsql_org.getText().trim();
        if ("".equals(inputsql_name) || "".equals(output_loc) || "".equals(output_name) || "".equals(output_org) || "".equals(output_time)) {
            show_dialog("请填写全部表格");
            return;
        }
        // 送入数据库的设置
        DatabaseHelper.originalDataTables[0] = inputsql_name;
        DatabaseHelper.outputTableNames[0] = output_name;
        DatabaseHelper.outputTableNames[1] = output_loc;
        DatabaseHelper.outputTableNames[2] = output_org;
        DatabaseHelper.outputTableNames[3] = output_time;
        hasSetTable = true;
        DatabaseHelper.hasSet = true;

    }


}
