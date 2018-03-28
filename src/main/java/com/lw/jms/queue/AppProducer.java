package com.lw.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by WYluo on 2018/3/28.
 */
public class AppProducer {

    private static final String url ="tcp://192.168.2.116:61616";

    private static final String queueNam ="queue_test";

    public static void main(String[] args) throws JMSException {

        //1.创建ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);

        //2.创建Connection
        Connection connection = connectionFactory.createConnection();

        //3.启动物理连接
        connection.start();

        //4.创建会话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        //5.创建一个目标 Queue是Destination的一个子类
        Destination destination = session.createQueue(queueNam);

        //6.从会话创建生产者
        MessageProducer producer = session.createProducer(destination);

        for (int i=0;i<100;i++) {
            //7.创建消息
            TextMessage message=session.createTextMessage("test"+i);
            //8。发布消息
            producer.send(message);

            System.out.println("已发送消息："+message.getText());
        }
        //关闭连接
        connection.close();

    }
}
