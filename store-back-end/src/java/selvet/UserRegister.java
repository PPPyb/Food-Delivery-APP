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

@WebServlet(name = "UserRegister", value = "/user/signup")
public class UserRegister extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectUser.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        BufferedReader reader = request.getReader();
        String readerStr = "";
        String line;
        while ((line = reader.readLine()) != null){
            readerStr = readerStr.concat(line);
        }
        HashMap map = JSONObject.parseObject(readerStr, HashMap.class);
        JSONObject ret = new JSONObject();

        User insertUser = new User();

        String username = map.get("username").toString();
        String password = map.get("password").toString();
        String phone = map.get("phone").toString();
        String rec_code = map.get("email_code").toString();
        String address = "University Road, Wenden District;" +
                "Haidian District, Beijing City";

        //new个user
        User new_user = new User();
        PrintWriter out = response.getWriter();

        try{
            SqlSession sqlSession = GetSqlSession.getSqlSession();
            EmailCode emailCode = sqlSession.selectOne("email.sql.returnRecord");

            if (emailCode.getCode().equals(rec_code)){
                //save info of user
                new_user.setUsername(username);
                new_user.setPassword(password);
                new_user.setEmail(emailCode.getEmail());
                new_user.setPhonenum(phone);
                new_user.setAddress(address);

                //add a new user
                sqlSession.insert("user.sql.addNewUser", new_user);

                //update token
                int userid = sqlSession.selectOne("user.sql.getUserid",username);
                Token newTokenItem=new Token();
                String token = GenerateToken.getInstance().makeToken();

                newTokenItem.setUserid(userid);
                newTokenItem.setToken(token);
                sqlSession.insert("token.sql.insertNewToken",newTokenItem);

                ret.put("token", token);
                out.print(ret);

            }
            //wrong code
            else {
                ret.put("info","The code is wrong");
                out.print(ret);
            }

        }
        catch (Exception e){
            // Fail to register
            LOGGER.error("Select error", e);
            GetSqlSession.rollback();
            ret.put("info","the register fail");
            out.print(ret);
        }finally {
            GetSqlSession.commit();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        String readerStr = "";
        String line;
        while ((line = reader.readLine()) != null){
            readerStr = readerStr.concat(line);
        }
        HashMap map = JSONObject.parseObject(readerStr, HashMap.class);
        JSONObject ret = new JSONObject();

        String email = request.getParameter("email");
        LOGGER.info(request.getParameter("email"));

//        ret.put("email", email);
        PrintWriter out = response.getWriter();

        //send email
        String code = SendEmail.getRandomCode();
        SendEmail.sendEmail(email, code);
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            //save code\email
            SqlSession sqlSession = GetSqlSession.getSqlSession();
            EmailCode emailCode = new EmailCode();
            emailCode.setEmail(email);
            emailCode.setCode(code);
            emailCode.setTime(dateFormat.format(date));
            sqlSession.insert("email.sql.insertEmail", emailCode);
            ret.put("code", 1000);
            out.print(ret);
        }
        catch (Exception e) {
//            Fail to sendeamil
            LOGGER.error("Return error", e);
            GetSqlSession.rollback();
            ret.put("info","Sendemail is wrong");
            out.print(ret);
        }finally {
            GetSqlSession.commit();
        }


    }



}
