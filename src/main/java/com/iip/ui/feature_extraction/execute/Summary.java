package com.iip.ui.feature_extraction.execute;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.util.ArrayList;
import java.util.List;

public class Summary {

    public static String appendWords = "";
    public static List<String> appendDocs = new ArrayList<String>();
    //public static List<List<String>> summaryDocs = new ArrayList<List<String>>();

    public static String getSummaryWords(String doc, int numS){

        List<String> words = HanLP.extractSummary(doc, numS);
        appendWords = "";
        for(int i=0; i<words.size(); i++){
            appendWords += words.get(i) + "。 ";
        }

        //print(appendWords);

        return appendWords;
    }

    public static List<String> getSummaryDocs(List<String> docs, int numS){

        appendDocs.clear();
        for(String doc: docs){
            appendWords = getSummaryWords(doc, numS); // split words
            appendDocs.add(appendWords);
        }
        return appendDocs;
    }

    public static void print(Object str){System.out.println(str);}

}
