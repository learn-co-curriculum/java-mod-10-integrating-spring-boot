package com.example.restservice;

import javax.persistence.*;

//@Transient
@Entity
public class Plan {
    //@Transient
    @Id
    private String value;

    //protected Plan() {}

    public Plan(String string) {
        this.value = string;
    }

    public String toString() {
        return value;
    }

}