package com.route_management_system.RMS.service;

import com.route_management_system.RMS.model.Delivery;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Saving implements Comparable<Saving> {

    private final Delivery from;
    private final Delivery to;
    private final double amount;



    @Override
    public int compareTo(Saving other) {
        return Double.compare (other.amount, this.amount);
    }




}
