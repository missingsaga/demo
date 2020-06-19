package com.loki.demo.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.loki.demo.enums.Deleted;
import com.loki.demo.enums.Status;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自动填充字段处理
 *
 * @author Loki
 */
@Component
public class MybatisFillHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        // 如果是不同业务有不同的抽离字段，需要用到多个实体类父类，那么我们可以在handler中，使用MetaObject对象来进行判断
//        String[] setterNames = metaObject.getSetterNames();
//        HashSet<String> setterNameSet = new HashSet<>(Arrays.asList(setterNames));
//        if(setterNameSet.contains("insertTime")){
//            //... to do something
//        }
//        if(setterNameSet.contains("createUser")){
//            //... to do something
//        }
        setFieldValByName("createTime", new Date(), metaObject);
        setFieldValByName("status", Status.ENABLE, metaObject);
        setFieldValByName("version", 1, metaObject);
        setFieldValByName("deleted", Deleted.FALSE, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        setFieldValByName("updateTime", new Date(), metaObject);
    }
}
