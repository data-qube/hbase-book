/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hbase_dp.ch8;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;

public class UserInsert {

    // TODO : update the table name with your username
    static String tableName = "users";
    static String familyName = "info";

    public static void main(String[] args) throws Exception {
        Configuration config = HBaseConfiguration.create();
        // change the following to connect to remote clusters
        // config.set("hbase.zookeeper.quorum", "localhost");
        long t1a = System.currentTimeMillis();
        HTable htable = new HTable(config, tableName);
        long t1b = System.currentTimeMillis();
        System.out.println ("Connected to HTable in : " + (t1b-t1a) + " ms");
//        htable.setAutoFlush(false); // PERF
//        htable.setWriteBufferSize(1024*1024*12); // 12M; PERF
        int total = 100;
        long t2a = System.currentTimeMillis();
        for (int i = 0; i < total; i++) {
            int userid = i;
            String email = "user-" + i + "@foo.com";
            String phone = "555-1234";

            byte[] key = Bytes.toBytes(userid);
            Put put = new Put(key);

            put.add(Bytes.toBytes(familyName), Bytes.toBytes("email"), Bytes.toBytes(email));  
            put.add(Bytes.toBytes(familyName), Bytes.toBytes("phone"), Bytes.toBytes(phone));  
            htable.put(put);

        }
        long t2b = System.currentTimeMillis();
        System.out.println("inserted " + total + " users  in " + (t2b - t2a) + " ms");
        htable.close();

    }
}