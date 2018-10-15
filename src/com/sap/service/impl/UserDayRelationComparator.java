package com.sap.service.impl;

import com.sap.model.UserDayRelation;

import java.util.Comparator;

public class UserDayRelationComparator implements Comparator<UserDayRelation> {

    @Override
    public int compare(UserDayRelation o1, UserDayRelation o2) {

        if (o1.getDay().getDate().isBefore(o2.getDay().getDate()))
            return -1;
        else if (o1.getDay().getDate().isAfter(o2.getDay().getDate()))
            return 1;
        return 0;
    }
}
