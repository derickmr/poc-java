package com.sap.model;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name = "DAY")
public class Day {

    @Id
    @Column(name = "DAY_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "DAY_OF_MONTH")
    private Integer day;

    @Column(name = "MONTH")
    private Integer month;

    @Column(name = "YEAR")
    private Integer year;

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

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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
        Day date = (Day) o;
        return Objects.equals(id, date.id) &&
                Objects.equals(day, date.day) &&
                Objects.equals(month, date.month) &&
                Objects.equals(year, date.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, day, month, year);
    }
}
