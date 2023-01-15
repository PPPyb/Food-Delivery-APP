package com.xzr.glassfishDemo.selvet;

import com.alibaba.fastjson.JSONObject;
import com.xzr.glassfishDemo.bean.User;
import com.xzr.glassfishDemo.utils.GetSqlSession;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(name = "logoutServlet", value = "/user/logout")
public class logoutServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectUser.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        LOGGER.info("要退出登录用户的token:"+token);

        try{
            SqlSession sqlSession = GetSqlSession.getSqlSession();
            int userid = sqlSession.selectOne("token.sql.getUsersByToken", token);
            sqlSession.delete("token.sql.deleteToken",userid);
            //LOGGER.info("返回输出"+sqlSession.update("token.sql.updateTokenNull",token));
//         Success to logout
            JSONObject ret = new JSONObject();
            ret.put("ok",true);
            ret.put("info","Log out successfully");
            PrintWriter out = response.getWriter();
            out.print(ret);
        } catch (Exception e) {
//            Fail to logout
            LOGGER.error("Logout error", e);
            LOGGER.info("如果报错");
            GetSqlSession.rollback();
            JSONObject ret = new JSONObject();
            ret.put("ok",false);
            ret.put("info","Fail to log out");
            PrintWriter out = response.getWriter();
            out.print(ret);
        }finally {
            GetSqlSession.commit();
        }
    }
}
