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

@WebServlet(name = "changeInfoServlet", value = "/user/changeinfo/")
public class changeInfoServlet extends HttpServlet {

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
        String address1 = map.get("address1").toString();
        String address2 = map.get("address2").toString();
        String name = map.get("name").toString();
        String tel = map.get("tel").toString();
        LOGGER.info("前端获取的数据:"+token+address1+address2+name+tel);

        //
        try {
            SqlSession sqlSession = GetSqlSession.getSqlSession();
            //
            int userid = sqlSession.selectOne("token.sql.getUsersByToken", token);
            //
            User user = new User();
            user.setUserid(userid);
            user.setAddress(address1+";"+address2);
            user.setUsername(name);
            user.setPhonenum(tel);
            sqlSession.update("user.sql.updateUsersByUserid", user);
            //
            JSONObject ret = new JSONObject();
            ret.put("ok",true);
            ret.put("info","Edit Successfully");
            PrintWriter out = response.getWriter();
            out.print(ret);
        } catch (Exception e) {
            LOGGER.error("select error", e);
            GetSqlSession.rollback();
            //
            JSONObject ret = new JSONObject();
            ret.put("ok",false);
            ret.put("info","Token out of date");
            PrintWriter out = response.getWriter();
            out.print(ret);
        } finally {
            GetSqlSession.commit();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        this.doPost(request, response);
    }
}
