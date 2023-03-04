package com.spring.kanbanboard.modals;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Имя не должно быть пустым")
    @Size(min = 2, max = 100, message = "Имя")
    @Column(name = "username")
    private String username;
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Tables> tablesList;

    public Person(){}

    public Person(String username, String email){
        this.username = username;
        this.email = email;
    }

    public void setTablesList(List<Tables> tablesList) {
        this.tablesList = tablesList;
    }

    public List<Tables> getTablesList() {
        return tablesList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString(){
        return "Person{" +
                "id=" + id + ", username=" + username + '\'' +
                ", email=" + email +
                ", password=" + password + "\'" + '}';
    }
}
