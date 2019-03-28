package com.iip.ui.feature_extraction.controller;

import com.iip.ui.feature_extraction.Main;
import com.iip.ui.feature_extraction.execute.Doc2vec;
import com.iip.ui.feature_extraction.execute.Participle;
import com.iip.ui.feature_extraction.execute.connection.DatabaseOperations;
import com.iip.ui.feature_extraction.execute.Summary;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import com.hankcs.hanlp.HanLP;

import java.util.ArrayList;
import java.util.List;

public class FE4TextsummyController {

    @FXML
    private ListView<String> rawDataListView;
    private ObservableList<String> rawDataList = FXCollections.observableArrayList();

    @FXML
    private ListView<String> handledDataListView;
    private ObservableList<String> handledDataList = FXCollections.observableArrayList();

    @FXML
    private Label dbname, dbname_, dataOrigin, dataOutput, hint;

    public static List<String> docs = new ArrayList<String>();
    private List<String> summaryDocs = new ArrayList<String>();

    public void initialize(){
        dbname.setText("数据库: database");
        dbname_.setText("数据库: database");
        dataOrigin.setText("源数据表格: table");
        dataOutput.setText("导出数据表格: table");
    }

    @FXML
    private void loadDataClicked(MouseEvent event){
        if(docs.size()>0) return;
        rawDataList.addAll(getDocs());
        rawDataListView.setItems(rawDataList);
    }

    @FXML
    private void handleDataClicked(MouseEvent event){
        if(summaryDocs.size()>0) return;
        handledDataList.addAll(getsummaryDocs(docs));
        handledDataListView.setItems(handledDataList);
    }

    @FXML
    private void exportDataClicked(MouseEvent event){
        if(!DatabaseOperations.isReady){
            DatabaseOperations.print("数据库还没链接好");
            Main.f_alert_informationDialog("操作失败", "数据库还没链接好!");
            return;
        }
        DatabaseOperations.write(DatabaseOperations.outputTableNames[3], DatabaseOperations.dataColumn, summaryDocs);
        DatabaseOperations.print("导出表完成!");
        Main.f_alert_informationDialog("操作成功", "导出表完成!");
    }

    // 从数据源获取数据
    private List<String> getDocs() {
//        String doc = "通过物资扶持，技术扶持，技能培训等帮扶措施，综合政策，实现脱贫";
//        docs.add(doc); docs.add(doc); docs.add(doc);
        if(!DatabaseOperations.isReady){
            DatabaseOperations.print("数据库还没链接好");
            Main.f_alert_informationDialog("操作失败", "数据库还没链接好!");
            return docs;
        }
        docs = DatabaseOperations.read(DatabaseOperations.originalDataTables[3], DatabaseOperations.dataColumn);
        dbname.setText("数据库: "+DatabaseOperations.DBNAME);
        dbname_.setText("数据库: "+DatabaseOperations.DBNAME);
        dataOrigin.setText("源数据表格: "+DatabaseOperations.originalDataTables[3]);
        dataOutput.setText("导出数据表格: "+DatabaseOperations.outputTableNames[3]);
        DatabaseOperations.print("导入表完成!");
        Main.f_alert_informationDialog("操作成功", "导入表完成!");
        return docs;
    }

    //提取文本摘要操作
    private List<String> getsummaryDocs(List<String> docs){
//        hint.setText("该操作比较耗时，请稍等......");
//        partedDocs = Doc2vec.getVecDocs(docs, DatabaseOperations.path);
//          partedDocs = TextRank.topKDocs(docs, Integer.valueOf(DatabaseOperations.k));
          String document = "算法可大致分为基本算法、数据结构的算法、数论算法、计算几何的算法、图的算法、动态规划以及数值分析、加密算法、排序算法、检索算法、随机化算法、并行算法、厄米变形模型、随机森林算法。\n"
          + "算法可以宽泛的分为三类，\n" + "一，有限的确定性算法，这类算法在有限的一段时间内终止。他们可能要花很长时间来执行指定的任务，但仍将在一定的时间内终止。这类算法得出的结果常取决于输入值。\n"
          + "二，有限的非确定算法，这类算法在有限的时间内终止。而，对于一个（或一些）给定的数值，算法的结果并不是唯一的或确定的。\n"
          + "三，无限的算法，是那些由于没有定义终止定义条件，或定义的条件无法由输入的数据满足而不终止运行的算法。通常，无限算法的产生是由于未能确定的定义终止条件。";

          //List<String> sentenceList = HanLP.extractSummary(document, 3);

          //print(sentenceList);
          summaryDocs = Summary.getSummaryDocs(docs, Integer.valueOf(DatabaseOperations.ksm));
//        hint.setText("操作完成!");
        return summaryDocs;
    }

    public static void print(Object str){System.out.println(str);}

}
