package com.whu.hello.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.PrimitiveIterator;

@Controller
@RequestMapping("/db")
public class DBController {

    private static final Logger log = LoggerFactory.getLogger(DBController.class);
    private static final String KEY_MSG = "msg";
    private static final String TEMPLATE_INDEX = "index";
    private static final String TEMPLATE_ERROR = "error";


    @RequestMapping("/statement")
    public String statement(HttpServletRequest request){
        final int id = 1;
        final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
        final String url = "jdbc:mysql://localhost:3306/whu?serverTimezone=UTC";
        final String username = "root";
        final String password = "xiaobaicai12581";
        // 查询id为1的用户
        final String sql = "select id,username,password,nickname from user where id = " + id;
        try {
            //加载驱动 mysql8以后的驱动 com.mysql.cj.jdbc.Driver
            //可能出现 ClassNotFound 异常
            Class.forName(jdbcDriver);
            //建立连接
            //可能出现 SQL 异常，比如由于用户名密码错误等
            Connection connection = DriverManager.getConnection(url, username, password);
            //创建statement，用于执行SQL语句
            Statement st = connection.createStatement();
            //获取执行的查询结果集合
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()){
                log.info("id:       {}", rs.getInt("id"));
                log.info("username: {}", rs.getString("username"));
                log.info("password: {}", rs.getString("password"));
                log.info("nickname: {}", rs.getString("nickname"));
            }
            //关闭结果集
            rs.close();
            //关闭statement
            st.close();
            //关闭连接
            connection.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            request.setAttribute(KEY_MSG, "执行错误");
            return TEMPLATE_ERROR;
        }
        return TEMPLATE_INDEX;
    }

    @RequestMapping("prepared-statement")
    public String preparedStatement(HttpServletRequest request){
        int id = 2;
        final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
        final String url = "jdbc:mysql://localhost:3306/whu?serverTimezone=UTC";
        final String username = "root";
        final String password = "xiaobaicai12581";
        final String sql = "select id,username,password,nickname from user where id = ?;";
        try {
            Class.forName(jdbcDriver);
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()){
                log.info("id:       {}", rs.getInt("id"));
                log.info("username: {}", rs.getString("username"));
                log.info("password: {}", rs.getString("password"));
                log.info("nickname: {}", rs.getString("nickname"));
            }
            rs.close();
            pst.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            request.setAttribute(KEY_MSG, "执行错误");
            return TEMPLATE_ERROR;
        }
        return TEMPLATE_INDEX;
    }
}
