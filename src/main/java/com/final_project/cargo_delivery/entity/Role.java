package com.final_project.cargo_delivery.entity;

/**
 * Role has items as they are in database to make easier to check status wile validation
 *
 * @author Mykhailo Hryb
 */
public enum Role {

    //Id should be the same as in a table
    NOT_AUTHORIZED(1), AUTHORIZED(2), MANAGER(3);

    private final int ID;

    Role(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return this.ID;
    }
}
