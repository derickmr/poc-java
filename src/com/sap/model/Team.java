package com.sap.model;


import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TEAM")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotEmpty
    @Column(name = "team_name")
    private String name;

    @OneToMany(mappedBy = "team", targetEntity = User.class, fetch = FetchType.EAGER)
    private List<User> users;

    @OneToOne
    private User teamOwner;
	
	@OneToMany(mappedBy = "team", targetEntity = Message.class, fetch = FetchType.EAGER)
    private Set<Message> messages;

    @OneToMany(mappedBy = "team", targetEntity = NecessityMessage.class, fetch = FetchType.EAGER)
    private Set<NecessityMessage> shiftMessages;

    public Set<NecessityMessage> getShiftMessages() {
        return shiftMessages;
    }

    public void setShiftMessages(Set<NecessityMessage> shiftMessages) {
        this.shiftMessages = shiftMessages;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }


    @OneToMany(mappedBy = "team", targetEntity = TeamCalendar.class, fetch = FetchType.EAGER)
    private Set<TeamCalendar> teamCalendars;

    public Set<TeamCalendar> getTeamCalendars() {
        return teamCalendars;
    }

    public void setTeamCalendars(Set<TeamCalendar> teamCalendars) {
        this.teamCalendars = teamCalendars;
    }

    public User getTeamOwner() {
        return teamOwner;
    }

    public void setTeamOwner(User teamOwner) {
        this.teamOwner = teamOwner;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(id, team.id) &&
                Objects.equals(name, team.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
//