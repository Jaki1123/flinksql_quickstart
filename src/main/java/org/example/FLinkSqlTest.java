package org.example;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.example.Pojo.Event;

public class FLinkSqlTest {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<Event> eventStream = env.addSource(new ClickSource());

        //将数据流转换为表
        StreamTableEnvironment tableEnv = StreamTableEnvironment.create(env);

        //
        Table eventTable = tableEnv.fromDataStream(eventStream);
        //获取表数据
        Table table = tableEnv.sqlQuery("select * from " + eventTable);
        //将表转换成数据流，打印输出
        tableEnv.toDataStream(table).print();

        env.execute();


    }
}
