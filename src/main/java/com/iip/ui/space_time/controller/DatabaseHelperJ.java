package com.iip.ui.space_time.controller;

import com.iip.ui.ner.DBHelper.DatabaseHelper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelperJ extends DatabaseHelper {

    public static List<String> read(String table, String column) {
        List<String> data = new ArrayList<>();
        try {
            String readTableSql = "SELECT * from " + table;
            resultSet = statement.executeQuery(readTableSql);
            while (resultSet.next()){
                data.add("(" + resultSet.getString(2) + ")" + resultSet.getString(3));
            }
        }catch (SQLException se){
            se.printStackTrace();
        }
        return data;
    }
}
