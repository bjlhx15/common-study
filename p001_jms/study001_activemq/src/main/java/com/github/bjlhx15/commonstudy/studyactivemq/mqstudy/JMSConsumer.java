package com.github.bjlhx15.commonstudy.studyactivemq.mqstudy;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSConsumer {
    private static final String UserName = ActiveMQConnection.DEFAULT_USER;
    private static final String Passwrod = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String brokerUrl = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {
        ConnectionFactory connectionFactory;//连接工厂
        Connection connection=null;//连接
        Session session;//会话 接收或者发送消息的线程
        Destination destination;//消息的目的地
        MessageConsumer messageConsumer;//消息的生产者

        connectionFactory = new ActiveMQConnectionFactory(JMSConsumer.UserName, JMSConsumer.Passwrod, JMSConsumer.brokerUrl);
        try {
            connection = connectionFactory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);//创建session，不用事务了
            destination = session.createQueue("FirstQueue");
            messageConsumer = session.createConsumer(destination);
            while (true){
                TextMessage message = (TextMessage) messageConsumer.receive(10000);
                if(message!=null){
                    System.out.println(message.getText());
                }else{
                    break;
                }
            }
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
}
