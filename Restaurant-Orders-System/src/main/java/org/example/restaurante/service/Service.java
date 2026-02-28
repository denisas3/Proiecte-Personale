package org.example.restaurante.service;

import org.example.restaurante.domain.*;
import org.example.restaurante.repository.MenuItemRepository;
import org.example.restaurante.repository.OrderItemRepository;
import org.example.restaurante.repository.OrderRepository;
import org.example.restaurante.repository.TableRepository;
import org.example.restaurante.utils.observer.Observer;
import org.example.restaurante.utils.events.EntityChangeEvent;
import org.example.restaurante.utils.observer.Observable;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.example.restaurante.utils.events.ChangeEventType.ADD;

public class Service implements Observable<EntityChangeEvent> {
    TableRepository tableRepository;
    MenuItemRepository menuItemRepository;
    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;

    List<Observer<EntityChangeEvent>> observers;

    public Service(TableRepository tableRepository,  MenuItemRepository menuItemRepository,
                   OrderRepository orderRepository, OrderItemRepository orderItemRepository) {
        this.tableRepository = tableRepository;
        this.menuItemRepository = menuItemRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        observers = new java.util.ArrayList<>();
    }

    public List<Tabel> getTables() {
        return tableRepository.findAll();
    }

    @Override
    public void addObserver(Observer<EntityChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<EntityChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(EntityChangeEvent event) {
        observers.forEach(o -> o.update(event));
    }

    public List<MenuItem> getMenuItems() {
        return menuItemRepository.findAll();
    }

    public List<MenuItem> getMenuItemsByCategory(String category) {
        return menuItemRepository.findAll().stream()
                .filter(m->m.getCategory().equals(category))
                .toList();
    }


    /// PLASEZ COMANDA ADICA ADAUG IN ORDERS SI IN ORDERITEMS
    public void placeOrder(Tabel table, List<MenuItem> items) {
        Order order = new Order(table.getId(), LocalDateTime.now(), OrderStatus.PLACED);

        orderRepository.save(order);

        int orderId = order.getId();

        for (MenuItem m : items) {
            OrderItems oi = new OrderItems(orderId, m.getId());
            orderItemRepository.save(oi);
        }

        notifyObservers(new EntityChangeEvent(ADD, order));
    }


    /// EXTRAG CE TREBUIE SA AFISEZ IN TABELA CU STAFUL
    ///
    public List<PlacedOrderRow> getPlacedOrdersView() {
        // cache menu items ca să nu cauț în DB pentru fiecare
        Map<Integer, MenuItem> menuById = menuItemRepository.findAll().stream()
                .collect(Collectors.toMap(MenuItem::getId, m -> m));

        return orderRepository.findAll().stream()
                .filter(o -> o.getStatus() == OrderStatus.PLACED)
                .sorted(Comparator.comparing(Order::getDate))
                .map(o -> {
                    String names = orderItemRepository.findAll().stream()
                            .filter(oi -> oi.getOrder_id() == o.getId())
                            .map(oi -> menuById.get(oi.getMenu_item_id()))
                            .filter(mi -> mi != null)
                            .map(MenuItem::getItem)
                            .collect(Collectors.joining(", "));

                    return new PlacedOrderRow(o.getTable(), o.getDate(), names);
                })
                .toList();
    }
}
