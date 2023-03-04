package com.spring.kanbanboard.modals;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "Trash")
public class Trash {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "person_id")
    private int person_id;

    @Column(name = "table_id")
    private int table_id;

    @Column(name = "notes_name")
    private String notes_name;

    @Column(name = "description")
    private String description;
    @Column(name = "timeanddate")
    private LocalDate timeAndDate;

    @Column(name = "createdate")
    private LocalDate createDate;

    @Column(name = "time")
    private LocalTime time;

    public Trash(){}

    public Trash(String notes_name, String description, LocalDate timeAndDate, LocalDate createDate, LocalTime time, int person_id, int table_id) {
        this.time = time;
        this.createDate = createDate;
        this.notes_name = notes_name;
        this.description = description;
        this.timeAndDate = timeAndDate;
        this.person_id= person_id;
        this.table_id = table_id;
    }

    public int getPerson_id() {
        return person_id;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }
    public void setPerson_id(int person_id) {
        this.person_id = person_id;
    }

    public void setTable_id(int table_id) {
        this.table_id = table_id;
    }

    public int getTable_id() {
        return table_id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setNotes_name(String notes_name) {
        this.notes_name = notes_name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTimeAndDate(LocalDate timeAndDate) {
        this.timeAndDate = timeAndDate;
    }

    public int getId() {
        return id;
    }

    public String getNotes_name() {
        return notes_name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getTimeAndDate() {
        return timeAndDate;
    }
    @Override
    public String toString(){
        return "Note{" +
                "id=" + id + ", person_id=" + person_id+ ", table_id=" + table_id + '\'' +
                ", note_name=" + notes_name + ", description " + description +
                ", time" + timeAndDate + "\'" + '}';
    }
}
