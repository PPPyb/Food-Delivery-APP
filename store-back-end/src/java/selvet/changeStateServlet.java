package com.xzr.glassfishDemo.selvet;

import com.alibaba.fastjson.JSONObject;
import com.xzr.glassfishDemo.bean.Order;
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
import java.util.HashMap;

@WebServlet(name = "changeStateServlet", value = "/order/changestate/")
public class changeStateServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectUser.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

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


        int orderid = Integer.parseInt(map.get("orderid").toString());
        String token = map.get("token").toString();
        String state = map.get("state").toString();

        Order order = new Order();
        try {
            SqlSession sqlSession = GetSqlSession.getSqlSession();

            int userid = sqlSession.selectOne("orders.sql.getUseridByOrder", orderid);

            if(userid == Integer.parseInt(sqlSession.selectOne("token.sql.getUsersByToken", token).toString())){
                order.setState(state);
                order.setOrderid(orderid);
                sqlSession.update("orders.sql.updateOrderState", order);
            }

            ret.put("code",1000);
            PrintWriter out = response.getWriter();
            out.print(ret);
        } catch (Exception e) {
            //Fail to change
            LOGGER.error("Return error", e);
            GetSqlSession.rollback();
            ret.put("info","Fail to change the state");
            PrintWriter out = response.getWriter();
            out.print(ret);
        }finally {
            GetSqlSession.commit();
        }

    }
}
