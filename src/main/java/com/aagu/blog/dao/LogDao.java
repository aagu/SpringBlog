package com.aagu.blog.dao;

import com.aagu.blog.model.Log;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface LogDao extends BaseDao<Log> {
    @SelectProvider(type = LogBuilder.class, method = "buildSelect")
    List<Log> getLog(Map<String, String> param);

    @Insert("insert into log(info, reason) values(#{info}, #{reason})")
    int appendLog(@Param("info") String info, @Param("reason") Integer reason);

    class LogBuilder {
        public static String buildSelect(Map<String, String> param) {
            return new SQL(){{
                SELECT("id, reason, info, time");
                FROM("log");
                if (param.containsKey("id")) {
                    WHERE("id=" + param.get("id"));
                }
                if (param.containsKey("reason")) {
                    WHERE("reason=" + param.get("reason"));
                }
                if (param.containsKey("time_start")) {
                    WHERE("time > str_to_date('" + param.get("time_start") + "', %Y-%m-%d %H:%i:%s)");
                }
                ORDER_BY("time desc");
            }}.toString() + " limit " + param.get("start") + ", " + param.get("num");
        }
    }
}
