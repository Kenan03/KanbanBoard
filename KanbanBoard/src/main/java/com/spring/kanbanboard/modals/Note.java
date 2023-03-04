package com.spring.kanbanboard.modals;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "Notes")
public class Note {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

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

    @OneToMany(mappedBy = "noteOwner")
    private List<Comment> commentList;

    @ManyToOne
    @JoinColumn(name = "table_id", referencedColumnName = "id")
    private Tables tableOwner;

    public Note(){}
    public Note(String notes_name, String description, LocalDate timeAndDate, Tables tableOwner, LocalDate createDate, LocalTime time){
        this.time = time;
        this.notes_name = notes_name;
        this.description = description;
        this.timeAndDate = timeAndDate;
        this.tableOwner = tableOwner;
        this.createDate = createDate;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public void setTableOwner(Tables tableOwner) {
        this.tableOwner = tableOwner;
    }

    public Tables getTableOwner() {
        return tableOwner;
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
                "id=" + id + ", table_id=" + tableOwner + '\'' +
                ", note_name=" + notes_name + ", description " + description +
                ", time" + timeAndDate + "\'" + '}';
    }
}
