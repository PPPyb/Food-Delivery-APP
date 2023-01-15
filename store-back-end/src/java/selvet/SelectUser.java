package com.xzr.glassfishDemo.selvet;

import com.alibaba.fastjson.JSONObject;
import com.xzr.glassfishDemo.bean.User;
import com.xzr.glassfishDemo.utils.GetSqlSession;
import org.apache.ibatis.jdbc.Null;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "SelectUser", value = "/getUsername")
public class SelectUser extends javax.servlet.http.HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectUser.class);


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        *
        * */
        LOGGER.info("Hello");
        LOGGER.info(request.getParameter("username"));
        LOGGER.info(request.getParameter("password"));

        // address
        User user = null;
        try {
            SqlSession sqlSession = GetSqlSession.getSqlSession();
            user = sqlSession.selectOne("user.sql.getUsersByUserid", 1);
        } catch (Exception e) {
            LOGGER.error("select error", e);
            GetSqlSession.rollback();
        } finally {
            GetSqlSession.commit();
        }
//        assert user != null
    }
}
