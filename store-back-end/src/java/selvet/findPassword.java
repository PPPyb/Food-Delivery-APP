package com.xzr.glassfishDemo.selvet;

import com.alibaba.fastjson.JSONObject;
import com.xzr.glassfishDemo.bean.EmailCode;
import com.xzr.glassfishDemo.bean.Token;
import com.xzr.glassfishDemo.bean.User;
import com.xzr.glassfishDemo.utils.GenerateToken;
import com.xzr.glassfishDemo.utils.GetSqlSession;
import com.xzr.glassfishDemo.utils.SendEmail;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
//import com.xzr.glassfishDemo.utils.SendEmail;

@WebServlet(name = "findpassword", value = "/user/findpass/")
public class findPassword extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectUser.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  /*

         */
        BufferedReader reader = request.getReader();
        String readerStr = "";
        String line;
        while ((line = reader.readLine()) != null){
            readerStr = readerStr.concat(line);
        }
        HashMap map = JSONObject.parseObject(readerStr, HashMap.class);
        JSONObject ret = new JSONObject();

        String email = map.get("email").toString();
        String code = map.get("code").toString();
        String newpass = map.get("newpass").toString();

        //new个user
        User new_user = new User();
        PrintWriter out = response.getWriter();

        try{
            SqlSession sqlSession = GetSqlSession.getSqlSession();
            EmailCode emailCode = sqlSession.selectOne("email.sql.returnRecord");

            if (emailCode.getCode().equals(code)){

                new_user.setUserid(sqlSession.selectOne("user.sql.getUseridByEmail", email));
                new_user.setPassword(newpass);
                sqlSession.update("user.sql.updateUsersPassByUserid", new_user);

                ret.put("ok", true);
                ret.put("info","Change password successfully!");
                out.print(ret);
            }
            //wrong code
            else {
                ret.put("ok", false);
                ret.put("info","Fail to change password");
                out.print(ret);
            }

        }
        catch (Exception e){
            // Fail to change password
            LOGGER.error("Select error", e);
            GetSqlSession.rollback();
            ret.put("info","Fail to change password");
            out.print(ret);
        }finally {
            GetSqlSession.commit();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
