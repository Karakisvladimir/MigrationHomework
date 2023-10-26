package org.example;


import client.ClientService;
import storage.ConnectionProvider;
import storage.DataBaseInitService;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        //Init DB using flyway
        new DataBaseInitService().initDb();


        ConnectionProvider connectionProvider = new ConnectionProvider();
        ClientService clientService = new ClientService(connectionProvider.createConnection());

        //встановлює нове ім'я name для клієнта з ідентифікатором id
        clientService.setName1(1, "ZYZY DDD");

        //додає нового клієнта з іменем name. Повертає ідентифікатор щойно створеного клієнта.
        clientService.create("OPOPOPOP");

        //повертає назву клієнта з ідентифікатором id
        clientService.getById(1);
        String res = clientService.getById(1);
        System.out.println("res = " + res);
        // видаляє клієнта з ідентифікатором id
        clientService.deleteById(2);

        clientService.printAllClients();
        connectionProvider.close();
    }
}