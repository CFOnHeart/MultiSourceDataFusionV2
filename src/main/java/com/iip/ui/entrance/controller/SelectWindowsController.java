package com.iip.ui.entrance.controller;

import com.iip.ui.ner.MainNer;
import com.iip.ui.space_time.SpaceTimeMain;
import com.iip.ui.feature_extraction.Main;
import javafx.event.ActionEvent;

public class SelectWindowsController {
    public void changeWindow0() throws Exception{
        com.iip.ui.feature_extraction.Main fe=new Main();
        fe.showWindow();
    }

    public void changeWindow1() throws Exception{
        com.iip.ui.ner.MainNer ner=new MainNer();
        ner.showWindow();
    }

    public void changeWindow2() throws Exception{
        com.iip.ui.space_time.SpaceTimeMain st=new SpaceTimeMain();
        st.showWindow();
    }

}
