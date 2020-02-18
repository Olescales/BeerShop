package com.gp.beershop.mock;

import com.gp.beershop.dto.CustomerOrder;
import com.gp.beershop.dto.Orders;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Service
public class OrderMock {
    private static Map<Integer, Orders> ordersMap = new HashMap<>() {{
        put(1, Orders.builder()
            .id(1)
            .customer(CustomersMock.getById(1))
            .processed(true)
            .total(25D)
            .customerOrders(
                List.of(
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(1))
                        .count(2)
                        .build(),
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(2))
                        .count(5)
                        .build()
                       ))
            .build());
        put(2, Orders.builder()
            .id(2)
            .customer(CustomersMock.getById(2))
            .processed(false)
            .total(27D)
            .customerOrders(
                List.of(
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(2))
                        .count(1)
                        .build(),
                    CustomerOrder.builder()
                        .beer(BeerMock.getById(3))
                        .count(3)
                        .build()
                       ))
            .build());
    }};

    public static void defaultState() {
        ordersMap = new HashMap<>() {{
            put(1, Orders.builder()
                .id(1)
                .customer(CustomersMock.getById(1))
                .processed(true)
                .total(25D)
                .customerOrders(
                    List.of(
                        CustomerOrder.builder()
                            .beer(BeerMock.getById(1))
                            .count(2)
                            .build(),
                        CustomerOrder.builder()
                            .beer(BeerMock.getById(2))
                            .count(5)
                            .build()
                           ))
                .build());
            put(2, Orders.builder()
                .id(2)
                .customer(CustomersMock.getById(2))
                .processed(true)
                .total(27D)
                .customerOrders(
                    List.of(
                        CustomerOrder.builder()
                            .beer(BeerMock.getById(2))
                            .count(1)
                            .build(),
                        CustomerOrder.builder()
                            .beer(BeerMock.getById(3))
                            .count(3)
                            .build()
                           ))
                .build());
        }};
    }

    public static void put(final Integer id, final Orders orders) {
        ordersMap.put(id, orders);
    }

    public static Orders getById(final Integer id) {
        return ordersMap.get(id);
    }

    public static Integer size() {
        return ordersMap.size();
    }

    public static void delete(final Integer id) {
        ordersMap.remove(id);
    }

    public static Map<Integer, Orders> getAll() {
        return ordersMap;
    }

    public static List<Orders> getAllValues() {
        return new ArrayList<>(ordersMap.values());
    }
}
