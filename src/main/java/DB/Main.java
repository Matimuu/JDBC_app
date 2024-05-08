package DB;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;

/**
 * @author Mendoza Perez Omar Enrique
 * @date 2024/05/07 19:07
 */
public class Main {
    /*
    Class-interface.
     */

    //Variables.
    private static DBconnector db = DBconnector.getInstance();
    private static DBoperator dBoperator = new DBoperator(db);
    private static final Logger log = LogManager.getLogger("mainLog");
    private static BufferedReader bufferedReader;

    //Main executable method.
    public static void main(String[] args) throws IOException {
            try{
                UI();
            } finally {
                db.disconnectFromDB();
                bufferedReader.close();
            }
    }

    //Interface method.
    public static void UI() {
        while(true) {
            bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            try {
                log.info("""
                        Chose an option:
                        1.Show all data
                        2.Show person by ID
                        3.Add new Person
                        4.Delete all data
                        5.Delete person by ID
                        6.Update person info
                        Type everything else to leave.
                        Type it: """);

                int option = Integer.parseInt(bufferedReader.readLine());
                log.info(option);

                switch (option) {
                    case 1 -> log.info(dBoperator.showAll());
                    case 2 -> {
                        log.info("Type id: ");
                        try {
                            log.info(dBoperator.showByID(Integer.parseInt(bufferedReader.readLine())));
                        } catch (NumberFormatException e) {
                            log.error("It's not a number. Program is closing.");
                            log.error(e.getStackTrace());
                            return;
                        }
                    }
                    case 3 -> {
                        log.info("Type name:");
                        String name = bufferedReader.readLine();
                        log.info("Type surname:");
                        String surname = bufferedReader.readLine();
                        log.info("Type date of birth in format (yyyy-mm-dd):");
                        String dateOfBirth = bufferedReader.readLine();

                        log.info(dBoperator.addPerson(name,surname,dateOfBirth));
                    }
                    case 4 -> log.info(dBoperator.deleteAll());
                    case 5 -> {
                        log.info("Type id:");
                        try {
                            log.info(dBoperator.deleteByID(Integer.parseInt(bufferedReader.readLine())));
                        } catch (NumberFormatException e) {
                            log.error("It's not a number. Program is closing.");
                            log.error(e.getStackTrace());
                            return;
                        }
                    }
                    case 6 -> {
                        log.info(dBoperator.showAll());
                        log.info("Type interested ID: ");
                        int id = 0;
                        try {
                            id = Integer.parseInt(bufferedReader.readLine());
                        } catch (NumberFormatException e) {
                            log.error("It's not an number. Program is closing.");
                            log.error(e.getStackTrace());
                            return;
                        }
                        log.info("Type name:");
                        String name = bufferedReader.readLine();
                        log.info("Type surname:");
                        String surname = bufferedReader.readLine();
                        log.info("Type date of birth in format (yyyy-mm-dd):");
                        String dateOfBirth = bufferedReader.readLine();

                        log.info(dBoperator.updateByID(id, name, surname, dateOfBirth));
                    }
                    default -> {
                        log.info("Program is closed.");
                        return;
                    }
                }
            } catch (IOException | SQLException e) {
                log.error("IO or SQL error.");
                log.error(e.getStackTrace());
                return;
            }
        }
    }
}
