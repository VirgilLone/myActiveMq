package com.lw.jms.topic;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by WYluo on 2018/3/28.
 */
public class AppConsumer {
    private static final String url ="tcp://192.168.2.116:61616";

    private static final String topicNam ="topic_test";

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
        Destination destination = session.createTopic(topicNam);

        //6.从会话创建消费者
        MessageConsumer consumer = session.createConsumer(destination);

        //7.创建监听器
        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                //回调接收到的消息的内容
                TextMessage textMessage= (TextMessage) message;
                try {
                    System.out.println("接收到消息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        //关闭连接
        //connection.close();

    }
}
