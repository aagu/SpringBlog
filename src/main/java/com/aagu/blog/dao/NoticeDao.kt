package com.aagu.blog.dao

import com.aagu.blog.model.Notice
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.SelectKey
import org.apache.ibatis.annotations.SelectProvider
import org.apache.ibatis.jdbc.SQL
import org.springframework.stereotype.Repository

@Mapper
@Repository
interface NoticeDao: BaseDao<Notice> {

    @SelectProvider(type = NoticeBuilder::class, method = "buildPage")
    override fun getItems(params: Map<String, String>): List<Notice>

    @SelectProvider(type = NoticeBuilder::class, method = "buildTotal")
    override fun getTotal(params: Map<String, String>): Int

    @Insert("insert into notice(date,detail,title) values(" +
            "#{date},#{detail},#{title})")
    @SelectKey(statement = ["select @@IDENTITY as id"], keyProperty = "id", before = false, resultType = Int::class)
    fun insertNotice(notice: Notice): Int

    class NoticeBuilder {
        fun buildPage(params: Map<String, String>): String {
            val sql = SQL()
            // when the pojo is defined in Kotlin's data class, parameter's position is mater
            sql.SELECT("id, title, detail, status, date")
            sql.FROM("notice")
            applyWhere(sql, params)
            sql.ORDER_BY("date desc")
            return "$sql LIMIT ${params["start"]}, ${params["num"]}"
        }

        fun buildTotal(params: Map<String, String>): String {
            val sql = SQL()
            sql.SELECT("count(id)")
            sql.FROM("notice")
            applyWhere(sql, params)
            return sql.toString()
        }

        private fun applyWhere(sql: SQL, params: Map<String, String>) {
            if (params.containsKey("status")) {
                sql.WHERE("status='${params["status"]}'}")
            }
        }
    }
}