package com.xzr.glassfishDemo.selvet;

import com.alibaba.fastjson.JSONObject;
import com.xzr.glassfishDemo.bean.Goods;
import com.xzr.glassfishDemo.bean.Order;
import com.xzr.glassfishDemo.bean.SelectedGoods;
import com.xzr.glassfishDemo.utils.GetSqlSession;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.lang.model.element.NestingKind;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "ReturnPreviewServlet", value = "/shop/getshop")
public class ReturnPreviewServlet extends HttpServlet {
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

        Goods goods = null;
        String shopname = map.get("shopname").toString();
        ret.put("info", "ok");


        try{
            SqlSession sqlSession = GetSqlSession.getSqlSession();
            ret.put("info", "ok");
            ret.put("cover", sqlSession.selectList("goods.sql.getPreview", shopname));
            PrintWriter out = response.getWriter();
            out.print(ret);

        } catch (Exception e) {
            // Fail to show
            LOGGER.error("Select error", e);
            GetSqlSession.rollback();
            ret.put("info","Fail to preview the covers.");
            PrintWriter out = response.getWriter();
            out.print(ret);
        }finally {
            GetSqlSession.commit();
        }
    }
}
