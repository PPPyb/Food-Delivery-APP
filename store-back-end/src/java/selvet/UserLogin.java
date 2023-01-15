package com.xzr.glassfishDemo.selvet;

import com.alibaba.fastjson.JSONObject;
import com.xzr.glassfishDemo.bean.Token;
import com.xzr.glassfishDemo.bean.User;
import com.xzr.glassfishDemo.utils.GenerateToken;
import com.xzr.glassfishDemo.utils.GetSqlSession;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import com.xzr.glassfishDemo.utils.encryptAES;

@WebServlet(name = "UserLogin", value = "/user/login")
public class UserLogin extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SelectUser.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        JSONObject ret = new JSONObject();


        BufferedReader reader = request.getReader();
        String readerStr = "";
        String line;
        while ((line = reader.readLine()) != null){
            readerStr = readerStr.concat(line);
        }
        HashMap map = JSONObject.parseObject(readerStr, HashMap.class);

        String enusername = map.get("username").toString();
        String enpassword = map.get("password").toString();

        //decrypt
        String username = encryptAES.decodeBase64(enusername);
        String password = encryptAES.decodeBase64(enpassword);

        String username1 = encryptAES.decrypt("eGViRHZYUzZwSkNWeVJsNXNuWHE2dz09");

        LOGGER.info("账号：:" + username + "密码：" + password);

        User selectedUser;
        Token newTokenItem=new Token();
        try{
            SqlSession sqlSession = GetSqlSession.getSqlSession();


            if(!sqlSession.selectList("user.sql.getAllUsername").contains(username)){
                LOGGER.info("Username is wrong.");
                ret.put("info","The username does not exist.");
                PrintWriter out = response.getWriter();
                out.print(ret);
            }
            else {
                selectedUser = sqlSession.selectOne("user.sql.getUserByUsername",username);
                if (selectedUser.getPassword().equals(password)){
                    String token = GenerateToken.getInstance().makeToken();
                    newTokenItem.setUserid(selectedUser.getUserid());
                    newTokenItem.setToken(token);
                    if(!sqlSession.selectList("user.sql.getAllUserid").contains(selectedUser.getUserid())){
                        //update token table
                        sqlSession.insert("token.sql.insertNewToken",newTokenItem);
                    }

                    else{
                        //update token
                        sqlSession.update("token.sql.updateTokenByUserid", newTokenItem);
                    }
                    ret.put("info", "success");
                    ret.put("code", 1000);
                    ret.put("token", token);
                }

                else
                {
                    ret.put("info", "The password is wrong");
                }


                PrintWriter out = response.getWriter();
                out.print(ret);
            }

        } catch (Exception e) {
//            Fail to show
            LOGGER.error("Select error", e);
            GetSqlSession.rollback();
            ret.put("info","the login has expired");
            PrintWriter out = response.getWriter();
            out.print(ret);
        }finally {
            GetSqlSession.commit();
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }



}
