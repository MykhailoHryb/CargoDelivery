package com.final_project.cargo_delivery.entity;

/**
 * OrderStatusEnum has items as they are in database to make easier to check status wile validation
 *
 * @author Mykhailo Hryb
 */
public enum OrderStatusEnum {

    //Id should be the same as in a table
    NOT_REGISTERED(1), DELIVERED(2), NOT_PAYED(3), PAYED(4), CANCELED(5);

    private final int ID;

    OrderStatusEnum(int id) {
        this.ID = id;
    }

    public int getID() {
        return this.ID;
    }
}
