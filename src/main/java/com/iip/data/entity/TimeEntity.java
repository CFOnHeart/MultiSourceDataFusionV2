package com.iip.data.entity;

import com.iip.textprocess.time.nlp.TimeNormalizer;
import com.iip.textprocess.time.nlp.TimeUnit;
import com.iip.textprocess.time.util.DateUtil;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Junnor.G
 * @Date 2018/12/6 下午6:
 * 时间实体类
 */
public class TimeEntity extends Entity {

    public TimeEntity(String word) {
        super(word);
    }

    @Override
    public String getType() {
        return this.type;
    }

    public static List<Entity> entityRecognition(String sentence)
            throws URISyntaxException {
        URL url = TimeNormalizer.class.getResource("/TimeExp.m");
        System.out.println(url.toURI().toString());
        TimeNormalizer normalizer = new TimeNormalizer(url.toURI().toString());
        normalizer.setPreferFuture(true);

        normalizer.parse(sentence);// 抽取时间
        TimeUnit[] unit = normalizer.getTimeUnit();
        if (unit.length <= 0) return new ArrayList<Entity>();
        List<Entity> entities = new ArrayList<>();
        for (TimeUnit tu : unit) entities.add(new TimeEntity(tu.Time_Expression));
        System.out.println(sentence);
        for(Entity en: entities)
            System.out.println(en.word);
//        System.out.println(DateUtil.formatDateDefault(unit[0].getTime()));
//        System.out.println(out.toString() + "-" + unit[0].getIsAllDayTime());
        return entities;

    }
}
