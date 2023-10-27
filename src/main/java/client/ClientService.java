package client;

import utils.Validator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientService {
    private PreparedStatement createSt;
    private PreparedStatement getByIdSt;
    private PreparedStatement setNameSt;
    private PreparedStatement deleteByIdSt;
    private PreparedStatement listAllSt;

    public ClientService(Connection connection) throws SQLException {
        createSt = connection.prepareStatement("INSERT INTO client (name) VALUES(?)");
        getByIdSt = connection.prepareStatement("SELECT * FROM client WHERE id = ?");
        setNameSt = connection.prepareStatement("UPDATE client SET name = ? WHERE id = ?");
        deleteByIdSt = connection.prepareStatement("""                    

                SET REFERENTIAL_INTEGRITY FALSE;
                BEGIN TRANSACTION;
                DELETE FROM client
                   WHERE id IN (SELECT id FROM project WHERE id = ?);
                DELETE FROM project WHERE client_id = ?;
                SET REFERENTIAL_INTEGRITY TRUE  
                    """);
        listAllSt = connection.prepareStatement("SELECT id, name FROM client");


    }

    public long create(String name) {
        Validator.validateName(name);


        try {
            createSt.setString(1, name);
            createSt.executeUpdate();

            try (ResultSet generatedKeys = createSt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getLong(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1;
    }


    public void setName1(long id, String name) throws SQLException {

        setNameSt.setString(1, name);
        setNameSt.setLong(2, id);


        setNameSt.executeUpdate();
    }

    public String getById(long id) {
        try {
            this.getByIdSt.setLong(1, id);
            try (ResultSet resultSet = getByIdSt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("name");


                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("Client with ID " + id + " not found");
    }


    public void deleteById(long id) throws SQLException {
        deleteByIdSt.setLong(1, id);
        deleteByIdSt.setLong(2, id);
        deleteByIdSt.executeUpdate();
    }


    public List<Client> listAll() {
        List<Client> clients = new ArrayList<>();

        try (ResultSet resultSet = listAllSt.executeQuery()) {
            while (resultSet.next()) {
                Client client = new Client();
                client.setId(resultSet.getLong("id"));
                client.setName(resultSet.getString("name"));
                clients.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clients;
    }

    public void printAllClients() {
        List<Client> clients = listAll();

        System.out.println("Список всех клиентов:");

        for (Client client : clients) {
            System.out.println(client.toString());
        }
    }
}


