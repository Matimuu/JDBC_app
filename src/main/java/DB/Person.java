package DB;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Mendoza Perez Omar Enrique
 * @date 2024/05/07 19:07
 */
public class Person {
    /*
    Class entity to represent DB data.
     */

    //Variables.
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private int id;
    private String name;
    private String surname;
    private Date birthDate;
    private Date dateOfCreation;

    //Constructors.
    public Person() {

    }

    public Person(String name, String surname, Date birthDate) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    //Methods.
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public int getId() {
        return id;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate='" +  simpleDateFormat.format(birthDate) + '\'' +
                ", dateOfCreation='" + simpleDateFormat.format(dateOfCreation) + "'\n";
    }
}
