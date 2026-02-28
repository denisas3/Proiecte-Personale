package org.example.restaurante.domain;

public class OrderItems extends Entity<Integer>{
    private int order_id;
    private int menu_item_id;

    public OrderItems(int order_id, int menu_item_id) {
        this.order_id = order_id;
        this.menu_item_id = menu_item_id;
    }

    public int getOrder_id() {
        return order_id;
    }
    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getMenu_item_id() {
        return menu_item_id;
    }
    public void setMenu_item_id(int menu_item_id) {
        this.menu_item_id = menu_item_id;
    }
}
