package DB;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Mendoza Perez Omar Enrique
 * @date 2024/05/07 21:10
 */
public class DBoperator {
    /*
    Class to make operations with DB.
     */

    //Variables.
    private DBconnector db;
    private PreparedStatement preparedStatement;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private int check = 0;

    //Constructors.
    public DBoperator(DBconnector db) {
        this.db = db;
    }

    //Methods.
    public String addPerson(String name, String surname, String dateOfBirth) throws SQLException {
        String insert = "INSERT INTO persons (person_name, person_surname, person_dateOfBirth) VALUES(?,?,?)";

        preparedStatement = db.getConnection().prepareStatement(insert);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, surname);
        preparedStatement.setDate(3, Date.valueOf(LocalDate.parse(dateOfBirth, dateTimeFormatter)));

        check = preparedStatement.executeUpdate();
        preparedStatement.clearParameters();
        return check > 0 ? "The person was added." : "The person wasn't added";
    }

    public String showAll() throws SQLException {
        String showAll = "SELECT * FROM persons";
        StringBuilder dbList = new StringBuilder("\n");

        preparedStatement = db.getConnection().prepareStatement(showAll);
        ResultSet resultSet = preparedStatement.executeQuery();

        showLoopPersons(resultSet, dbList);
        preparedStatement.clearParameters();
        return dbList.toString();
    }

    public String showByID(int id) throws SQLException {
        String showByID = "SELECT * FROM persons WHERE person_id = ?";
        StringBuilder personInfo = new StringBuilder();

        preparedStatement = db.getConnection().prepareStatement(showByID);
        preparedStatement.setInt(1, id);

        showLoopPersons(preparedStatement.executeQuery(), personInfo);
        preparedStatement.clearParameters();
        return personInfo.toString();
    }

    private void showLoopPersons(ResultSet resultSet, StringBuilder dbList) throws SQLException {
        while (resultSet.next()) {
            Person person = new Person();

            person.setId(resultSet.getInt(1));
            person.setName(resultSet.getString(2));
            person.setSurname(resultSet.getString(3));
            person.setBirthDate(resultSet.getDate(4));
            person.setDateOfCreation(resultSet.getDate(5));

            dbList.append(person);
        }
    }

    public String deleteAll() throws SQLException {
        String deleteSQL = "DELETE FROM persons";

        preparedStatement = db.getConnection().prepareStatement(deleteSQL);
        check = preparedStatement.executeUpdate();
        preparedStatement.clearParameters();

        return check > 0 ? "DB is clear." : "DB still contain information.";
    }

    public String deleteByID(int id) throws SQLException {
        String deleteByID = "DELETE FROM persons WHERE person_id = ?";
        String deletedPerson = showByID(id);

        preparedStatement = db.getConnection().prepareStatement(deleteByID);
        preparedStatement.setInt(1, id);

        check = preparedStatement.executeUpdate();
        preparedStatement.clearParameters();

        return check > 0 ? "Person " +showByID(id)+ " was deleted" : "Person wasn't deleted";
    }

    public String updateByID(int id, String newName, String newSurname, String newDateOfBirth) throws SQLException {
        String updateSQL = "UPDATE persons SET person_name=?, person_surname=?, person_dateOfBirth=? WHERE person_id=?";
        preparedStatement = db.getConnection().prepareStatement(updateSQL);

        preparedStatement.setString(1, newName);
        preparedStatement.setString(2, newSurname);
        preparedStatement.setDate(3, Date.valueOf(LocalDate.parse(newDateOfBirth, dateTimeFormatter)));
        preparedStatement.setInt(4, id);

        check = preparedStatement.executeUpdate();
        preparedStatement.clearParameters();

        return check > 0 ? "Person was updated. "+showByID(id) : "Person wasn't updated. "+showByID(id);
    }
}
