package com.xzr.glassfishDemo.selvet;

import com.alibaba.fastjson.JSON;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "showItemServlet", value = "/shop/itemlist")
public class showItemServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectUser.class);
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try{
            SqlSession sqlSession = GetSqlSession.getSqlSession();
            List<ShopItem> itemsList;
            itemsList = sqlSession.selectList("shopItem.sql.getAllItems");
            String json = JSON.toJSONString(itemsList);
//         Success to show items' infomation
            PrintWriter out = response.getWriter();
            out.write(json);
        } catch (Exception e) {
//            Fail to show
            LOGGER.error("Select error", e);
            GetSqlSession.rollback();
            PrintWriter out = response.getWriter();
            out.write("Fail to show all items");
        }finally {
            GetSqlSession.commit();
        }

    }
}
