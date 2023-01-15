package sqlmethods;

import java.io.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class SqlExcution {
    private static String dbHost="localhost";
    // the address of database
    private static String dbName="food";
    // the name of database
    private static String username = "root";
    // user name
    private static String password = "flzx3000c,ysyhl9t";
    // password
    private static String dbPort="3306";
    // the port number of database

    public static String readFile (String fileName) {
        // read the file to get the SQL statement it stores
        File file = new File(fileName);
        BufferedReader reader = null;
        StringBuffer sb = new StringBuffer();
        String skipStr = "BEGIN";
        // move the useless part when executing the SQL statement
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            // use the String type to store the SQL statements so that it can be identified by MySQL
            while ((tempStr = reader.readLine()) != null) {
                if(!(tempStr = reader.readLine()).equalsIgnoreCase(skipStr)) {
                    // use the function equalsIgnoreCase() to skip the useless content
                    sb.append(tempStr);
                }
            }
//              System.out.println(sb.toString());
            reader.close();
            // finish the reading process
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public static Connection getMySqlConnection() throws Exception {
        // create the connection between java and mysql
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url="jdbc:mysql://"+dbHost+":"+dbPort+"/"+dbName+"?useUnicode=true&characterEncoding=utf-8&port="+dbPort+"&autoReconnect=true&&useSSL=false";
        return DriverManager.getConnection(url,username,password);
    }

    public static void executeSql(String sql) throws Exception{
       
        Connection conn = getMySqlConnection();
        // build the connection
        Statement stmt = conn.createStatement();
        // call the function that can execute the SQL statements
        stmt.execute(sql);
        conn.close();
        // close the connection
        
    }

    public static void main(String[] args) throws Exception {
        String filename = "E:\\aaa.txt";
        readFile(filename);
        System.out.println(readFile(filename));
        executeSql(readFile(filename));
    }
}
