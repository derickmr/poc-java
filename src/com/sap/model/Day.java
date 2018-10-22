package com.sap.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "DAY")
public class Day implements Comparable<Day> {

    @Id
    @Column(name = "DAY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "DATE_OF_DAY")
    private LocalDate date;

    @OneToMany(mappedBy = "day", targetEntity = UserOnShiftNotification.class, fetch = FetchType.EAGER)
    private Set<UserOnShiftNotification> necessityMessages;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    private boolean holiday;

    public boolean isHoliday() {
        return holiday;
    }

    public void setHoliday(boolean holiday) {
        this.holiday = holiday;
    }

    public boolean isWeekend() {
        return weekend;
    }

    public void setWeekend(boolean weekend) {
        this.weekend = weekend;
    }

    private boolean weekend;

    private Integer usersNeededOnLate;
    private Integer usersNeededOnDay;

    private Integer usersOnLate;
    private Integer usersOnDay;

    public Integer getUsersNeededOnLate() {
        return usersNeededOnLate;
    }

    public void setUsersNeededOnLate(Integer usersNeededOnLate) {
        this.usersNeededOnLate = usersNeededOnLate;
    }

    public Integer getUsersNeededOnDay() {
        return usersNeededOnDay;
    }

    public void setUsersNeededOnDay(Integer usersNeededOnDay) {
        this.usersNeededOnDay = usersNeededOnDay;
    }

    public Integer getUsersOnLate() {
        return usersOnLate;
    }

    public void setUsersOnLate(Integer usersOnLate) {
        this.usersOnLate = usersOnLate;
    }

    public Integer getUsersOnDay() {
        return usersOnDay;
    }

    public void setUsersOnDay(Integer usersOnDay) {
        this.usersOnDay = usersOnDay;
    }

    public Set<UserOnShiftNotification> getNecessityMessages() {
        return necessityMessages;
    }

    public void setNecessityMessages(Set<UserOnShiftNotification> necessityMessages) {
        this.necessityMessages = necessityMessages;
    }

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "DAY_TEAM_CALENDAR")
    private TeamCalendar teamCalendar;

    @OneToMany(mappedBy = "day", targetEntity = UserDayRelation.class, fetch = FetchType.EAGER)
    private Set<UserDayRelation> userDayRelations;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TeamCalendar getTeamCalendar() {
        return teamCalendar;
    }

    public void setTeamCalendar(TeamCalendar teamCalendar) {
        this.teamCalendar = teamCalendar;
    }

    public Set<UserDayRelation> getUserDayRelations() {
        return userDayRelations;
    }

    public void setUserDayRelations(Set<UserDayRelation> userDayRelations) {
        this.userDayRelations = userDayRelations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Day day = (Day) o;
        return Objects.equals(id, day.id) &&
                Objects.equals(date, day.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }

    @Override
    public int compareTo(Day o) {
        if (date.isBefore(o.getDate()))
            return 1;
        else if (date.isAfter(o.getDate()))
            return -1;
        return 0;
    }
}
