package com.sap.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "SHIFT_MESSAGE")
public class NecessityMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    private String message;
    private Integer usersOnShiftAtDate;

    public Integer getUsersOnShiftAtDate() {
        return usersOnShiftAtDate;
    }

    public void setUsersOnShiftAtDate(Integer usersOnShiftAtDate) {
        this.usersOnShiftAtDate = usersOnShiftAtDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TEAM_SHIFT_MESSAGE")
    private Team team;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    private String shift;
    private LocalDate date;

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "SHIFT_MESSAGE_DAY")
    private Day day;

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void createMessageOfNecessity (){
        String message = "";
        message += "I need someone to set your shift to " + shift + " at " + date + "!";
        setMessage(message);
    }

    @Override
    public String toString (){
        return getMessage();
    }
}
