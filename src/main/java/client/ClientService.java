package client;

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
        deleteByIdSt = connection.prepareStatement("DELETE FROM client WHERE id = ?");
        listAllSt =connection.prepareStatement("SELECT id, name FROM client");


    }

    public long create(String name) {
        //validateName(name);

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


//  insertSt = conn.prepareStatement(
//          "INSERT INTO human (name, birthday) VALUES(?, ?)"
//          );
//          selectByIdSt = conn.prepareStatement(
//          "SELECT name, birthday FROM human WHERE id = ?"
//          );
//          selectAllSt = conn.prepareStatement("SELECT id FROM human");
//
//          renameSt = conn.prepareStatement("UPDATE human SET name = ? WHERE name = ?");
//          }
//
//public void createNewHumans(String[] names, LocalDate[] birthdays) throws SQLException {
//        for (int i = 0; i < names.length; i++) {
//        String name = names[i];
//        LocalDate birthday = birthdays[i];
//
//        insertSt.setString(1, name);
//        insertSt.setString(2, birthday.toString());
//
//        insertSt.addBatch();
//        }
//
//        insertSt.executeBatch();
//        }
//
//public boolean createNewHuman(String name, LocalDate birthday) {
//        try {
//        insertSt.setString(1, name);
//        insertSt.setString(2, birthday.toString());
//        return insertSt.executeUpdate() == 1;
//        } catch (Exception ex) {
//        ex.printStackTrace();
//        }
//
//        return false;
//        }
//
//public String getHumanInfo(long id) {
//        try {
//        selectByIdSt.setLong(1, id);
//        } catch (Exception ex) {
//        ex.printStackTrace();
//        return null;
//        }
//
//        try(ResultSet rs = selectByIdSt.executeQuery()) {
//        if (!rs.next()) {
//        System.out.println("Human with id " + id + " not found!");
//        return null;
//        }
//
//        String name = rs.getString("name");
//        String birthday = rs.getString("birthday");
//
//        return "name: " + name + ", birthday: " + birthday;
//        } catch (Exception ex) {
//        return null;
//        }
//        }
//
//public List<Long> getIds() {
//        List<Long> result = new ArrayList<>();
//
//        try (ResultSet rs = selectAllSt.executeQuery()) {
//        while(rs.next()) {
//        result.add(rs.getLong("id"));
//        }
//        } catch (Exception ex) {
//        ex.printStackTrace();
//        }
//
//        return result;
//        }
//
//
//public void rename(Map<String, String> renameMap) throws SQLException {
//        conn.setAutoCommit(false);
//
//        for (Map.Entry<String, String> keyValue : renameMap.entrySet()) {
//        renameSt.setString(1, keyValue.getKey());
//        renameSt.setString(2, keyValue.getKey());
//
//        renameSt.addBatch();
//        }
//
//        try {
//        renameSt.executeBatch();
//
//        conn.commit();
//        } catch (Exception ex) {
//        conn.rollback();
//        } finally {
//        conn.setAutoCommit(true);
//        }
//        }
//        }


//    public PassengerDaoService(Connection connection) throws SQLException {
//        createSt = connection.prepareStatement("INSERT INTO passenger (passport, name) VALUES(?, ?)");
//        getByPassportSt = connection.prepareStatement("SELECT id, name FROM passenger WHERE passport = ?");
//    }
//
//    public void create(Passenger passenger) throws SQLException {
//        createSt.setString(1, passenger.getPassport());
//        createSt.setString(2, passenger.getName());
//        createSt.executeUpdate();
//    }
//
//    public Passenger getByPassport(String passport) throws SQLException {
//        getByPassportSt.setString(1, passport);
//
//        try(ResultSet rs = getByPassportSt.executeQuery()) {
//            if (!rs.next()) {
//                return null;
//            }
//
//            Passenger result = new Passenger();
//            result.setId(rs.getLong("id"));
//            result.setName(rs.getString("name"));
//            result.setPassport(passport);
//
//            return result;
//        }
//    }
//}