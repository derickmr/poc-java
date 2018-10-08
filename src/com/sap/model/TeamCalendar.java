package com.sap.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "TEAM_CALENDAR")
public class TeamCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer startDay;
    private Integer endDay;
    private Integer startMonth;
    private Integer endMonth;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CALENDAR_ID")
    private Team team;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "teamCalendar", targetEntity = Day.class)
    private Set<Day> days;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStartDay() {
        return startDay;
    }

    public void setStartDay(Integer startDay) {
        this.startDay = startDay;
    }

    public Integer getEndDay() {
        return endDay;
    }

    public void setEndDay(Integer endDay) {
        this.endDay = endDay;
    }

    public Integer getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Integer startMonth) {
        this.startMonth = startMonth;
    }

    public Integer getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(Integer endMonth) {
        this.endMonth = endMonth;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Set<Day> getDays() {
        return days;
    }

    public void setDays(Set<Day> dates) {
        this.days = dates;
    }
}
