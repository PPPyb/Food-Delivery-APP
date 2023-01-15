/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sendsqlclient;

import com.github.shyiko.mysql.binlog.BinaryLogFileReader;
import com.github.shyiko.mysql.binlog.event.Event;
import com.github.shyiko.mysql.binlog.event.EventData;
import com.github.shyiko.mysql.binlog.event.QueryEventData;
import com.github.shyiko.mysql.binlog.event.deserialization.EventDeserializer;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;

/**
 *
 * @author 11849
 */
public class Sendsqlclient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        File file = new File("C:\\ProgramData\\MySQL\\MySQL Server 8.0\\Data\\LAPTOP-VNOS4K9F-bin.000042");
        // get the position of binlog file
        EventDeserializer eventDeserializer = new EventDeserializer();
        // set an event deserializer to call the functions that operate binlog
        eventDeserializer.setCompatibilityMode(
                EventDeserializer.CompatibilityMode.DATE_AND_TIME_AS_LONG,
                EventDeserializer.CompatibilityMode.CHAR_AND_BINARY_AS_BYTE_ARRAY);
        BinaryLogFileReader reader = new BinaryLogFileReader(file, eventDeserializer);
        // create a reader to read the binlog file
        // because binlog file is a binary file, so a binary log file reader is needed

        // here use three text files to store the contents of binlog before update, after update,
        // and the two contents' difference
        File fileA = new File("E:\\10\\sql_A.txt");
        // file A is used to store the contents of binlog before update
        File fileB = new File("E:\\10\\sql_B.txt");
        // file B is used to store the contents of binlog after update
        File fileNewSQL = new File("E:\\10\\sql_new.txt");
        // fileNewSQL is used to store the new SQL statement (the different content between A and B)

        try {
            // read the content of certain binlog, and write it to the specified file
            FileWriter fileWriter = new FileWriter(fileB,false);
            // append = false, to overwrite the content in file B
            for (Event event; (event = reader.readEvent()) != null; ) {
                EventData data = event.getData();
                // read the data content in the binlog
                if (data != null && data.getClass().isAssignableFrom(QueryEventData.class)) {
                    //  only choose the SQL statement parts in the binlog, and read them
                    QueryEventData sqlData = (QueryEventData) data;
                    // get the SQL statements for further file writing
                    System.out.println(sqlData.getSql());
                    fileWriter.write(sqlData.getSql().toString());
                    fileWriter.write("\r\n");
                    // write the SQL statements into file B
                }
            }
            fileWriter.close();
            // finish the file writing

            // the following steps aim at getting the number of lines in file A
            FileReader fileReaderA = new FileReader(fileA);
            LineNumberReader lineNumberReaderA = new LineNumberReader(fileReaderA);
            // create a line number reader to get how many lines of contents in file A
            lineNumberReaderA.skip(Long.MAX_VALUE);
            long numberOfLineA = lineNumberReaderA.getLineNumber() + 1;
            // save the line number + 1, for further comparison
            System.out.println(numberOfLineA);
            fileReaderA.close();
            lineNumberReaderA.close();
            // finish the file reading and line number reading

            // the following steps aim at getting the different content between A and B,
            // which is the SQL statement for updating
            StringBuffer sb = new StringBuffer();
            // record the content into the String type, so that the text file can write
            FileReader fileReaderB = new FileReader(fileB);
            LineNumberReader lineNumberReaderB = new LineNumberReader(fileReaderB);
            lineNumberReaderB.skip(numberOfLineA);
            // skip the same content of A and B, so that the left content is the new SQL statement
            String lineB = lineNumberReaderB.readLine();
            // record the new SQL statement, then write it into the fileNewSQL file
            while(lineB != null){
                if(lineNumberReaderB.getLineNumber() >= numberOfLineA){
                    // read start from the different part by skipping the former lines
                    sb.append(lineB);
                    sb.append("\r\n");
                }
                lineB = lineNumberReaderB.readLine();
                // move to the next line to continue the reading process
            }
            System.out.println("\n");

            System.out.println(sb.toString());
            // output the new SQL statement in the console
            FileWriter fileWriterNew = new FileWriter(fileNewSQL,false);
            fileWriterNew.write(sb.toString());
            // write the new SQL statement into the specific file
            fileWriterNew.close();

            lineNumberReaderB.close();
            fileReaderB.close();
            // finish the writing process and reading processes

            // change the file name of file A and file B, so that next time the two same files can be reused
            fileA.renameTo(new File("E:\\10\\sql_B_temp.txt"));
            fileB.renameTo(new File("E:\\10\\sql_A.txt"));
            new File("E:\\10\\sql_B_temp.txt").renameTo(new File("E:\\10\\sql_B.txt"));
            
            // Get Webservice
            System.out.println(updatedb(sb.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
    }

    private static String updatedb(java.lang.String sql) {
        sqlclient.Soapservice_Service service = new sqlclient.Soapservice_Service();
        sqlclient.Soapservice port = service.getSoapservicePort();
        return port.updatedb(sql);
    }
    
}
