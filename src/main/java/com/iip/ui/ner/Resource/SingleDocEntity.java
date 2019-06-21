package com.iip.ui.ner.Resource;

import java.util.ArrayList;
import java.util.List;
//import com.iip.data.entity.NameEntity;
//import com.iip.data.entity.OrganizationEntity;
//import com.iip.data.entity.PlaceEntity;
//import com.iip.data.entity.TimeEntity;
import com.iip.data.entity.*;
import com.iip.ui.feature_extraction.execute.Doc2vec;


/**
 * @Author Junnor.G
 * @Date 2018/12/25 下午4:13
 */
public class SingleDocEntity {
    private int id;
    private String text;
    private String personEntityResult = "";
    private String locationEntityResult = "";
    private String organizationEntityResult = "";
    private String timeEntityResult = "";
    private String allEntitySimilarityResult = "";
//    private String dateStr = "";
//    private Date date;
    private List<Entity> personEntities = new ArrayList<>();
    private List<Entity> locationEntities = new ArrayList<>();
    private List<Entity> organizationEntities = new ArrayList<>();
    private List<Entity> timeEntities = new ArrayList<>();
    private List<Entity> allEntities = new ArrayList<>();


    public static List<Entity> peosonEntityExtract(String text){
        List<Entity> list = new ArrayList<>();
        List<Entity> nameList = NameEntity.entityRecognition(text);
        list.addAll(nameList);
//        List<Entity> placeEntity = PlaceEntity.entityRecognition(text);
//        list.addAll(placeEntity);
//        List<Entity> orgnizationEntity = OrganizationEntity.entityRecognition(text);
//        list.addAll(orgnizationEntity);
        return list;
    }

    public void personEntityExtract(){
        personEntities = peosonEntityExtract(text);
        personEntityResult = personEntitytoString();
    }

    public String personEntitytoString(){
        if (personEntities.size() == 0) return "找不到人名实体信息";
        String res = "";
        for(Entity entity: personEntities){
            res += entity.word +";";
        }
        return res;
    }






    public static List<Entity> locationEntityExtract(String text){
        List<Entity> list = new ArrayList<>();
//        List<Entity> nameList = NameEntity.entityRecognition(text);
//        list.addAll(nameList);
        List<Entity> placeEntity = PlaceEntity.entityRecognition(text);
        list.addAll(placeEntity);
//        List<Entity> orgnizationEntity = OrganizationEntity.entityRecognition(text);
//        list.addAll(orgnizationEntity);
        return list;
    }

    public void locationEntityExtract(){
        locationEntities = locationEntityExtract(text);
        locationEntityResult = locationEntitytoString();
    }

    public String locationEntitytoString(){
        if (locationEntities.size() == 0) return "找不到地名实体信息";
        String res = "";
        for(Entity entity: locationEntities){
            res += entity.word +";";
        }
        return res;
    }


    public static List<Entity> organizationEntityExtract(String text){
        List<Entity> list = new ArrayList<>();
//        List<Entity> nameList = NameEntity.entityRecognition(text);
//        list.addAll(nameList);
//        List<Entity> placeEntity = PlaceEntity.entityRecognition(text);
//        list.addAll(placeEntity);
        List<Entity> orgnizationEntity = OrganizationEntity.entityRecognition(text);
        list.addAll(orgnizationEntity);
        return list;
    }

    public void organizationEntityExtract(){
        organizationEntities = organizationEntityExtract(text);
        organizationEntityResult = organizationEntitytoString();
    }

    public String organizationEntitytoString(){
        if (organizationEntities.size() == 0) return "找不到机构名实体信息";
        String res = "";
        for(Entity entity: organizationEntities){
            res += entity.word +";";
        }
        return res;
    }


    public static List<Entity> timeEntityExtract(String text){
        List<Entity> list = new ArrayList<>();
        try {
            List<Entity> timeEntity = TimeEntity.entityRecognition(text);
            list.addAll(timeEntity);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return list;
    }

    public void timeEntityExtract(){
        timeEntities = timeEntityExtract(text);
        timeEntityResult = timeEntitytoString();
    }

    public String timeEntitytoString(){
        if (timeEntities.size() == 0) return "找不到时间实体信息";
        String res = "";
        for(Entity entity: timeEntities){
            res += entity.word +";";
        }
        return res;
    }


    public void allEntityExtract(){
        if(personEntityResult == "") {
            personEntityExtract();
        }
        if(locationEntityResult == "") {
            locationEntityExtract();
        }
        if(organizationEntityResult == "") {
            organizationEntityExtract();
        }
        allEntities = new ArrayList<Entity>(); // 丢弃旧值，重新赋值
        allEntities.addAll(personEntities);
        allEntities.addAll(locationEntities);
        allEntities.addAll(organizationEntities);

        allEntitySimilarityResult = allEntitytoSimilarity();
    }

    public String allEntitytoSimilarity(){
        if (allEntities.size() == 0) return "找不到实体信息";
        if (allEntities.size() == 1) return "只有一个实体";
        String res = "";
        String vecsPath = "src/main/java/com/iip/ui/feature_extraction/resources/zhwiki_2017_03.sg_50d.word2vec"; // 注意
        int i = 0;

        for(Entity entity1: allEntities){
            if(entity1.word.substring(0, 1) != "找") // 确保entity1不是"找不到xx实体信息"
            {
                List<String> docs1 = new ArrayList<String>();
                docs1.add(entity1.word);
                List<String> vec1 = Doc2vec.getVecDocs(docs1, vecsPath);
                int j = 0;
                for(j = i + 1; j < allEntities.size(); j++) // 计算所有可能的实体组合的相似度，是组合，不是排列
                {
                    Entity entity2 = allEntities.get(j);
                    if(entity2.word.substring(0, 1) != "找") // 确保entity2不是"找不到xx实体信息"
                    {
                        List<String> docs2 = new ArrayList<String>();
                        docs2.add(entity2.word);
                        List<String> vec2 = Doc2vec.getVecDocs(docs2, vecsPath);
                        if(vec1.size() == 1 && vec2.size() == 1)
                        {
                            res += entity1.word + "-" + entity2.word + ": " + calCosineSim(vec1, vec2) + ", ";
                        }
                    }
                }
            }
            i++; // 注意
//            res += entity.word +";";
        }
        return res;
    }

    public String calCosineSim(List<String> v1, List<String> v2) // assert v1和v2的size均为1
    {
        double dotRes = 0.0; // 点乘结果
        double v1Len = 0.0; // 向量v1的模
        double v2Len = 0.0; // 向量v2的模
        String str1 = v1.get(0);
        String str2 = v2.get(0);
        str1 = str1.replace("[", "");
        str1 = str1.replace("]", "");
        String[] splt1 = str1.split(",");
        str2 = str2.replace("[", "");
        str2 = str2.replace("]", "");
        String[] splt2 = str2.split(",");
        for(int i = 0; i < splt1.length; i++) // 逐位处理
        {
            double val1 = Double.parseDouble(splt1[i]);
            double val2 = Double.parseDouble(splt2[i]);
            dotRes += val1 * val2;
            v1Len += val1 * val1;
            v2Len += val2 * val2;
        }
        v1Len = Math.sqrt(v1Len);
        v2Len = Math.sqrt(v2Len);
        double cosSim = Math.abs(dotRes) / (v1Len * v2Len);
        return String.format("%.2f", cosSim); // 保留两位小数，返回String
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
//
        this.text=text;
    }

//    public String getDateStr() {
//        return dateStr;
//    }
//
//    public void setDateStr(String dateStr) {
//        this.dateStr = dateStr;
//    }
//
//    public Date getDate() {
//        return date;
//    }
//
//    public void setDate(Date date) {
//        this.date = date;
//    }

    public String getPersonEntityResult() {
        return personEntityResult;
    }

    public void setPersonEntityResult(String personEntityResult) {
        this.personEntityResult = personEntityResult;
    }

    public List<Entity> getPersonEntities() {
        return personEntities;
    }

    public void setpersonEntities(List<Entity> personEntities) {
        this.personEntities= personEntities;
    }






    public String getLocationEntityResult() {
        return locationEntityResult;
    }

    public void setLocationEntityResult(String locationEntityResult) {
        this.locationEntityResult = locationEntityResult;
    }

    public List<Entity> getLocationEntities() {
        return locationEntities;
    }

    public void setLocationEntities(List<Entity> locationEntities) {
        this.personEntities= locationEntities;
    }




    public String getOrganizationEntityResult() {
        return organizationEntityResult;
    }

    public void setOrganizationEntityResult(String organizationEntityResult) {
        this.organizationEntityResult = organizationEntityResult;
    }

    public List<Entity> getOrganizationEntities() {
        return organizationEntities;
    }

    public void setOrganizationEntities(List<Entity> organizationEntities) {
        this.organizationEntities= organizationEntities;
    }


    public String getTimeEntityResult() {
        return timeEntityResult;
    }

    public void setTimeEntityResult(String timeEntityResult) {
        this.timeEntityResult = timeEntityResult;
    }

    public List<Entity> getTimeEntities() {
        return timeEntities;
    }

    public void setTimeEntities(List<Entity> timeEntities) {
        this.timeEntities= timeEntities;
    }


    public String getAllEntitySimilarityResult() {
        return allEntitySimilarityResult;
    }

    public void setAllEntitySimilarityResult(String allEntitySimilarityResult) {
        this.allEntitySimilarityResult = allEntitySimilarityResult;
    }








    public static void main(String [] args){
        com.iip.ui.ner.Resource.SingleDocEntity item = new com.iip.ui.ner.Resource.SingleDocEntity();
        item.setId(1);
        item.setText("济南杨铭宇餐饮管理有限公司是由杨先生创办的餐饮企业，晚上九点去吃饭，2008年5月3日北京今天很热");
        System.out.println(item.text);
        item.timeEntityExtract();
        System.out.println(item.timeEntityResult);
    }
}

