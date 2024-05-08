package DB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author Mendoza Perez Omar Enrique
 * @date 2024/05/07 21:10
 */
public class DBoperator {
    private Logger log = LogManager.getLogger("mainLog");
    private DBconnector db;
    private PreparedStatement preparedStatement;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public DBoperator(DBconnector db) {
        this.db = db;
    }

    public String addPerson(String name, String surname, String dateOfBirth) {
        String insert = "INSERT INTO persons (person_name, person_surname, person_dateOfBirth) VALUES(?,?,?)";
        int check = 0;
        try {
            preparedStatement = db.getConnection().prepareStatement(insert);

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setDate(3, Date.valueOf(LocalDate.parse(dateOfBirth, dateTimeFormatter)));

            check = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.throwing(new RuntimeException(e));
        }
        return check > 0?"The person was added.":"The person wasn't added";
    }

    public String showAll() {
        String showAll = "SELECT * FROM persons";
        StringBuilder dbList = new StringBuilder();

        try {
            preparedStatement = db.getConnection().prepareStatement(showAll);
            ResultSet resultSet = preparedStatement.executeQuery();

            showLoopPersons(resultSet, dbList);
        } catch (SQLException e) {
            log.error("Wrong SQL query.");
            log.throwing(new RuntimeException(e));
        }
        return dbList.toString();
    }

    public String showByID(int id) {
        String showByID = "SELECT * FROM persons WHERE person_id = ?";
        StringBuilder personInfo = new StringBuilder();

        try {
            preparedStatement = db.getConnection().prepareStatement(showByID);
            preparedStatement.setInt(1, id);

            showLoopPersons(preparedStatement.executeQuery(), personInfo);
        } catch (SQLException e) {
            log.error("SQL query error.");
            log.throwing(new RuntimeException(e));
        }
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
}
