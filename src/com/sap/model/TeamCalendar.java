package com.sap.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "TEAM_CALENDAR")
public class TeamCalendar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private LocalDate startDate;
    private LocalDate endDate;
	private Integer numberOfUsersOnTeamAtCreationOfTheCalendar;
	private Integer initialUsersNeededOnDay;
    private Integer initialUsersNeededOnLate;

    public Integer getInitialUsersNeededOnDay() {
        return initialUsersNeededOnDay;
    }

    public void setInitialUsersNeededOnDay(Integer initialUsersNeededOnDay) {
        this.initialUsersNeededOnDay = initialUsersNeededOnDay;
    }

    public Integer getInitialUsersNeededOnLate() {
        return initialUsersNeededOnLate;
    }

    public void setInitialUsersNeededOnLate(Integer initialUsersNeededOnLate) {
        this.initialUsersNeededOnLate = initialUsersNeededOnLate;
    }
	
	public Integer getNumberOfUsersOnTeamAtCreationOfTheCalendar() {
        return numberOfUsersOnTeamAtCreationOfTheCalendar;
    }

    public void setNumberOfUsersOnTeamAtCreationOfTheCalendar(Integer numberOfUsersOnTeamAtCreationOfTheCalendar) {
        this.numberOfUsersOnTeamAtCreationOfTheCalendar = numberOfUsersOnTeamAtCreationOfTheCalendar;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

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
