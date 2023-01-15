package com.xzr.glassfishDemo.selvet;

import com.alibaba.fastjson.JSON;
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

@WebServlet(name = "getInfoServlet",value = "/user/getinfo/")
public class getInfoServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectUser.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         *
         * */

        BufferedReader reader = request.getReader();
        String readerStr = "";
        String line;
        while ((line = reader.readLine()) != null){
            readerStr = readerStr.concat(line);
        }
        HashMap map = JSONObject.parseObject(readerStr, HashMap.class);

        String token = map.get("token").toString();
        LOGGER.info("要查找用户的token:"+token);

        User selectedUser;
        try{
            SqlSession sqlSession = GetSqlSession.getSqlSession();
            int userid = sqlSession.selectOne("token.sql.getUsersByToken",token);
            User user = new User();
            user.setUserid(userid);
            selectedUser = sqlSession.selectOne("user.sql.getUsersByUserid",user);
//         Success to show user's infomation
            JSONObject ret = new JSONObject();
            ret.put("info","");
            ret.put("code", 1000);
            ret.put("user",selectedUser);
            PrintWriter out = response.getWriter();
            out.print(ret);
        } catch (Exception e) {
//            Fail to show
            LOGGER.error("Select error", e);
            GetSqlSession.rollback();
            JSONObject ret = new JSONObject();
            ret.put("info","Fail to get info");
            PrintWriter out = response.getWriter();
            out.print(ret);
        }finally {
            GetSqlSession.commit();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
