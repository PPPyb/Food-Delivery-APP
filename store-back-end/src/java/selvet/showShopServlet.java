package com.xzr.glassfishDemo.selvet;

import com.alibaba.fastjson.JSONObject;
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

@WebServlet(name = "showShopServlet", value = "/shop/getshopitem/")
public class showShopServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectUser.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
         * 解析json请求demo
         * */

        BufferedReader reader = request.getReader();
        String readerStr = "";
        String line;
        while ((line = reader.readLine()) != null){
            readerStr = readerStr.concat(line);
        }
        HashMap map = JSONObject.parseObject(readerStr, HashMap.class);

        String shopname = map.get("shopname").toString();
        LOGGER.info("要查找用户的token:"+shopname);
//        查询对应用户信息操作
        try{
            SqlSession sqlSession = GetSqlSession.getSqlSession();
//         Success to show user's infomation
            JSONObject ret = new JSONObject();
            ret.put("info",sqlSession.selectList("shopItem.sql.getinfoByShopname",shopname));
            PrintWriter out = response.getWriter();
            out.print(ret);
        } catch (Exception e) {
//            Fail to show
            LOGGER.error("Select error", e);
            GetSqlSession.rollback();
            JSONObject ret = new JSONObject();
            ret.put("info","The shop could not be found.");
            PrintWriter out = response.getWriter();
            out.print(ret);
        }finally {
            GetSqlSession.commit();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
