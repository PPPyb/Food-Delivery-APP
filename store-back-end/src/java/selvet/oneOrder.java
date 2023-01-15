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
import java.util.Iterator;
import java.util.List;
@WebServlet(name = "oneOrder", value = "/order/oneorder/")
public class oneOrder extends HttpServlet {
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

        int inputOrderid = Integer.parseInt(map.get("orderid").toString());

        try{
            SqlSession sqlSession = GetSqlSession.getSqlSession();


            JSONObject ret = new JSONObject();
            PrintWriter out = response.getWriter();


            if(!sqlSession.selectList("shopItem.sql.getAllOrderid").contains(inputOrderid)){
                LOGGER.info("The order does not exist.");
                out.print(ret);
            }
            else {
                LOGGER.info("The order exists.");
                Order selectedOrder;
                selectedOrder=sqlSession.selectOne("shopItem.sql.getinfoByOrderid",inputOrderid);
                ret.put("order_time",selectedOrder.getOrder_time());
                ret.put("total_price",selectedOrder.getTotal_price());
                ret.put("state",selectedOrder.getState());
                ret.put("address",selectedOrder.getAddress());
                ret.put("deliveryid",selectedOrder.getDeliveryid());
                ret.put("arrive_time",selectedOrder.getArrive_time());
                ret.put("comment",selectedOrder.getComment());

                int selectedCollectionid = selectedOrder.getCollection_id();
                List<SelectedGoods> goodsList;
                goodsList = sqlSession.selectList("shopItem.sql.getGoodsinfoByCollectionid",selectedCollectionid);
                ret.put("goods_collection",goodsList);
                out.print(ret);
            }
        } catch (Exception e) {
//            Fail to show
            LOGGER.error("Select error", e);
            GetSqlSession.rollback();
            JSONObject ret = new JSONObject();
            ret.put("info","Fail to check order details.");
            PrintWriter out = response.getWriter();
            out.print(ret);
        }finally {
            GetSqlSession.commit();
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
