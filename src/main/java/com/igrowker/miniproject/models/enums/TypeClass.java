package com.igrowker.miniproject.models.enums;

import com.igrowker.miniproject.models.*;

public enum TypeClass {
    ACCOMMODATION(Accommodation.class.getName()),
    ACTIVITY(Activity.class.getName()),
    DESTINATION(Destination.class.getName()),
    MEAL(Meal.class.getName()),
    TRANSPORT(Transport.class.getName()),
    TRAVEL(Travel.class.getName()),
    USER(User.class.getName());

    public final String name;

    private TypeClass(String name) {
        this.name = name();
    }
}
