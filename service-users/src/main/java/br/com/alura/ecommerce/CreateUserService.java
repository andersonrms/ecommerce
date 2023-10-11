package br.com.alura.ecommerce;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ExecutionException;


public class CreateUserService {

    private final Connection connection;

    CreateUserService() throws SQLException {
        String url = "jdbc:sqlite:target/users_database.db";
        connection = DriverManager.getConnection(url);
        try{
            connection.createStatement().execute("create table Users (" +
                    "uuid varchar(200) primary key," +
                    "email varchar(200))");

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static  void main(String[] args) throws SQLException {
        var createUserService = new CreateUserService();
        try(var service = new KafkaService<Order>(
                Constants.ECOMMERCE_NEW_ORDER_SUBJECT,
                createUserService::parse,
                CreateUserService.class.getSimpleName(),
                Order.class,
                Map.of())){
            service.run();
        }
    }

    private void parse(ConsumerRecord<String, Order> record) throws ExecutionException, InterruptedException, SQLException {
        System.out.println("###############################################");
        System.out.println("PROCESSING NEW ORDER, CHECKING NEW USER");
        System.out.println(record.value());

        var order = record.value();
        if(isNewUser(order.getEmail())){
            insertNewUser(order.getUserId(), order.getEmail());
        }

        System.out.println("###############################################");
    }

    private void insertNewUser(String uuid, String email) throws SQLException {
        var insertStatement = connection.prepareStatement("insert into Users (uuid, email) ");
        insertStatement.setString(1, uuid);
        insertStatement.setString(2, email);
        insertStatement.execute();
        System.out.println("USER " + email + " ADDED");
    }

    private boolean isNewUser(String email) throws SQLException {
        var existsStatement = connection.prepareStatement("select uuid from Users where email = ? limit 1");
        existsStatement.setString(1, email);
        var results = existsStatement.executeQuery();
        return !results.next();
    }

}
