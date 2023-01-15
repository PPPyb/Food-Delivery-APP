package com.xzr.glassfishDemo.selvet;

import java.text.SimpleDateFormat;
import java.util.*;
import com.alibaba.fastjson.JSONObject;
import com.xzr.glassfishDemo.bean.GoodsCollection;
import com.xzr.glassfishDemo.bean.Order;
import com.xzr.glassfishDemo.utils.GetSqlSession;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@WebServlet(name = "addOrder", value = "/order/add/")
public class addOrderServlet extends HttpServlet {

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

        String token = map.get("token").toString();
        String total_price = map.get("total_price").toString();
        String comment = map.get("comment").toString();
        String address = map.get("address").toString();


        Order order = new Order();
        GoodsCollection goodsCollection = new GoodsCollection();
        try{
            SqlSession sqlSession = GetSqlSession.getSqlSession();
            order.setUserid(sqlSession.selectOne("token.sql.getUsersByToken",token));

            Date date = new Date();
            SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            order.setOrder_time(dateFormat.format(date));

            int numOfCollection = sqlSession.selectOne("goods_collection.sql.getNumOfItem");

            order.setCollection_id(numOfCollection + 1);
            order.setTotal_price(total_price);
            order.setState("Not Paid");
            order.setAddress(address);
            order.setDeliveryid("Mike 15068783597");
            long time = 30*60*1000;//30分钟
            Date arrivrTime = new Date(date.getTime() + time);//30分钟后的时间
            order.setArrive_time(dateFormat.format(arrivrTime));
            order.setComment(comment);


            sqlSession.insert("orders.sql.insertOrder", order);

            String col = map.get("collection").toString();
            List<HashMap> list = JSONObject.parseArray(col, HashMap.class);
            String test = "";
            for(HashMap hashMap : list){
                int goodsid = Integer.parseInt(hashMap.get("goodsid").toString());
                String goodsinfo = hashMap.get("goodsinfo").toString();
                int goodsnum = Integer.parseInt(hashMap.get("goodsnum").toString());
                int goodscol_id = sqlSession.selectOne("goods_collection.sql.getNumOfItem");

                goodsCollection.setGoodscol_id(goodscol_id+1);
                goodsCollection.setCollection_id(order.getCollection_id());
                goodsCollection.setGoodsinfo(goodsinfo);
                goodsCollection.setGoodsid(goodsid);
                goodsCollection.setGoodsnum(goodsnum);

                sqlSession.insert("goods_collection.sql.addGoodsCollectionRecord", goodsCollection);
            }

            PrintWriter out = response.getWriter();
            ret.put("code", 1000);
            out.print(ret);
        } catch (Exception e) {
//            Fail to submit
            LOGGER.error("Return error", e);
            GetSqlSession.rollback();
            ret.put("info","InsertOrder is wrong");
            PrintWriter out = response.getWriter();
            out.print(ret);
        }finally {
            GetSqlSession.commit();
        }
    }
}
