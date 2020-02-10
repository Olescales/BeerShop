package com.gp.beershop.mock;

import com.gp.beershop.dto.Order;
import com.gp.beershop.dto.Orders;
import lombok.Data;
import org.springframework.stereotype.Service;

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
                .order(List.of(
                        Order.builder()
                                .beer(BeerMock.getById(1))
                                .count(2)
                                .build(),
                        Order.builder().beer(BeerMock.getById(2))
                                .count(5)
                                .build()
                ))
                .build());
    }};


    public static void put(Integer id, Orders orders) {
        ordersMap.put(id, orders);
    }

    public static Orders getById(Integer id) {
        return ordersMap.get(id);
    }

    public static Integer size() {
        return ordersMap.size();
    }

    public static Map<Integer, Orders> getAll() {
        return ordersMap;
    }
}