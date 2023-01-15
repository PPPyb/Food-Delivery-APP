package com.xzr.glassfishDemo.selvet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xzr.glassfishDemo.bean.Order;
import com.xzr.glassfishDemo.bean.ShopItem;
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
import java.util.List;

@WebServlet(name = "showOrderServlet", value="/shop/orderlist/")
public class showOrderServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectUser.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        BufferedReader reader = request.getReader();
        String readerStr = "";
        String line;
        while ((line = reader.readLine()) != null){
            readerStr = readerStr.concat(line);
        }
        HashMap map = JSONObject.parseObject(readerStr, HashMap.class);
        String token = map.get("token").toString();
        LOGGER.info("要查找用户的token:"+token);
        try{
            SqlSession sqlSession = GetSqlSession.getSqlSession();
            int userid = sqlSession.selectOne("token.sql.getUsersByToken",token);
            Order order = new Order();
            order.setUserid(userid);
            List<Order> orderList;
            orderList = sqlSession.selectList("shopItem.sql.getOrderByUserid",order);
//          Success to show orders' infomation
            String json = JSON.toJSONString(orderList);
            PrintWriter out = response.getWriter();
            out.write(json);
        } catch (Exception e) {
//          Fail to show
            LOGGER.error("Select error", e);
            GetSqlSession.rollback();
            PrintWriter out = response.getWriter();
            out.write("Fail to show all orders");
        }finally {
            GetSqlSession.commit();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
