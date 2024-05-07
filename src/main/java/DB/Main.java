package DB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Mendoza Perez Omar Enrique
 * @date 2024/05/07 19:07
 */
public class Main {
    private static final Logger log = LogManager.getLogger("mainLog");
    public static void main(String[] args) {
        DBconnection db = DBconnection.getInstance();
        String query = "SELECT * FROM persons";

        try {
            Statement statement = db.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Person person = new Person();
                person.setId(resultSet.getInt(1));
                person.setName(resultSet.getString(2));
                person.setSurname(resultSet.getString(3));
                person.setBirthDate(resultSet.getDate(4));
                person.setDateOfCreation(resultSet.getDate(5));
                log.info(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        db.disconnectFromDB();
    }
}
