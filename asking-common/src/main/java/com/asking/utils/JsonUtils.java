package com.asking.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * @ClassName JsonUtils
 * @Description TODO
 * @Author wangs
 * @Date 2019/7/16 12:53
 * @Version 1.0
 **/
public class JsonUtils {
    public static final ObjectMapper mapper=new ObjectMapper();

    private static final Logger logger= LoggerFactory.getLogger(JsonUtils.class);

    @Nullable
    public static String serialize(Object obj){
        if (obj==null)
            return null;
        if (obj.getClass()==String.class){
            return (String)obj;
        }
        try {
            return mapper.writeValueAsString(obj);
        }catch (JsonProcessingException e){
            logger.info("序列号出错"+obj,e);
            return null;
        }
    }

    @Nullable
    public static<T> T parse(String json,Class<T> tClass){
        try {
            return mapper.readValue(json,tClass);
        }catch (IOException e){
            logger.info("json解析出错"+json,e);
            return null;
        }
    }

}
