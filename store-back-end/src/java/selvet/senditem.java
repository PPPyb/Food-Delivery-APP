package com.xzr.glassfishDemo.selvet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xzr.glassfishDemo.bean.*;
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
import java.util.List;

@WebServlet(name = "senditem", value="/shop/changestorage/")
public class senditem extends HttpServlet {
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

        int goodsid = Integer.parseInt(map.get("goodsid").toString());
        int storage = Integer.parseInt(map.get("storage").toString());
        JSONObject ret = new JSONObject();
        PrintWriter out = response.getWriter();
        try{
            SqlSession sqlSession = GetSqlSession.getSqlSession();
            SelectedGoods good = new SelectedGoods();
            good.setGoodsid(goodsid);
            good.setStorage(storage);
            sqlSession.update("updateStorage", good);
            ret.put("code", 1000);
            out.print(ret);
        } catch (Exception e) {
//          Fail to show
            LOGGER.error("Select error", e);
            GetSqlSession.rollback();
            out.write("Fail to show all orders");
        }finally {
            GetSqlSession.commit();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
