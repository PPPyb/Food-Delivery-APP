/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package soaps;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import static sqlmethods.SqlExcution.executeSql;
import static sqlmethods.SqlExcution.getMySqlConnection;
import static sqlmethods.SqlExcution.readFile;

/**
 *
 * @author Aiden
 */
@WebService(serviceName = "soapservice")
public class soapservice {

    /**
     * This is a sample web service operation
     */
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }

    /**
     * Web service operation
     */
    @WebMethod(operationName = "updatedb")
    public String updatedb(@WebParam(name = "sql") String sql){
        //TODO write your implementation code here:
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter("E:\\aaa.txt"));
            out.write(sql);
            out.close();
            
            String filename = "E:\\aaa.txt";
            executeSql(readFile(filename));
            return "OKK";
        }catch(Exception e){
            return e.toString();
        }
        
        
    }
    
}
