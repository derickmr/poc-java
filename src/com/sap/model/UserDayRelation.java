package com.sap.model;

import javax.persistence.*;

@Entity
@Table(name = "USER_DAY_RELATION")   //change entity name
public class UserDayRelation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Day getDay() {
        return day;
    }

    public void setDay(Day day) {
        this.day = day;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_WORK_DAY")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DAY_OF_MONTH_WORK_DAY")
    private Day day;

    private String shift;

    private boolean canWorkAtHolidayOrWeekend;

    public boolean isCanWorkAtHolidayOrWeekend() {
        return canWorkAtHolidayOrWeekend;
    }

    public void setCanWorkAtHolidayOrWeekend(boolean canWorkAtHolidayOrWeekend) {
        this.canWorkAtHolidayOrWeekend = canWorkAtHolidayOrWeekend;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}
