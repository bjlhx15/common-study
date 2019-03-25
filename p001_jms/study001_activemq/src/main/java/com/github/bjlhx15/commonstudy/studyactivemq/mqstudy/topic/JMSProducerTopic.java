package com.github.bjlhx15.commonstudy.studyactivemq.mqstudy.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSProducerTopic {
    private static final String UserName = ActiveMQConnection.DEFAULT_USER;
    private static final String Passwrod = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String brokerUrl = ActiveMQConnection.DEFAULT_BROKER_URL;
    private static final int sendnum = 10;

    public static void main(String[] args) {
        ConnectionFactory connectionFactory;//连接工厂
        Connection connection=null;//连接
        Session session;//会话 接收或者发送消息的线程
        Destination destination;//消息的目的地
        MessageProducer messageProducer;//消息的生产者

        connectionFactory = new ActiveMQConnectionFactory(JMSProducerTopic.UserName, JMSProducerTopic.Passwrod, JMSProducerTopic.brokerUrl);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
//            destination = session.createQueue("FirstQueue");
            destination = session.createTopic("FirstTopic");
            messageProducer = session.createProducer(destination);
            sendMessage(session,messageProducer);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(connection!=null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void sendMessage(Session session, MessageProducer messageProducer) throws Exception {
        for (int i = 0; i < JMSProducerTopic.sendnum; i++) {
            TextMessage message = session.createTextMessage("Active Mq 发送的消息"+i);
            messageProducer.send(message);
            System.out.println("Active Mq 发送的消息"+i);
        }
    }
}
