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

@WebServlet(name = "changePassServlet",value = "/user/changepass/")
public class changePassServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectUser.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        解析Json请求demo
        BufferedReader reader = request.getReader();
        String readerStr = "";
        String line;
        while((line = reader.readLine()) != null){
            readerStr = readerStr.concat(line);
        }
        HashMap map = JSONObject.parseObject(readerStr,HashMap.class);
//       Getting data
        String token = map.get("token").toString();
        String oldpassword = map.get("oldpassword").toString();
        String newpassword = map.get("newpassword").toString();
        LOGGER.info("获取的token和对应密码"+token+"-"+newpassword);
//        Process data
        try{
            SqlSession sqlSession = GetSqlSession.getSqlSession();
//
            int userid = sqlSession.selectOne("token.sql.getUsersByToken",token);
            if(oldpassword.equals(sqlSession.selectOne("user.sql.getpassByUserid",userid))){
            User user = new User();
            user.setUserid(userid);
            user.setPassword(newpassword);
            sqlSession.update("updateUsersPassByUserid",user);

            JSONObject ret = new JSONObject();
            ret.put("OK",true);
            ret.put("info","Your password has been changed successfully.");
            PrintWriter out = response.getWriter();
            out.print(ret);}
            else{
                JSONObject ret = new JSONObject();
                ret.put("OK",false);
                ret.put("info","The old password you entered is not correct.");
                PrintWriter out = response.getWriter();
                out.print(ret);
            }
        } catch (IOException e) {
//            Fail to change password
            LOGGER.error("update password error",e);
            GetSqlSession.rollback();
            JSONObject ret = new JSONObject();
            ret.put("False",true);
            ret.put("info","Sorry, failed to change password.");
            PrintWriter out = response.getWriter();
            out.print(ret);
        }finally {
            GetSqlSession.commit();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
