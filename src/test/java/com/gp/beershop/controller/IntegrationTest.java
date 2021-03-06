package com.gp.beershop.controller;

import com.gp.beershop.dto.AuthRequest;
import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.Orders;
import com.gp.beershop.dto.UserSignInResponse;
import com.gp.beershop.mock.BeerMock;
import com.gp.beershop.mock.OrderMock;
import com.gp.beershop.mock.OrderRequestMock;
import com.gp.beershop.mock.UsersMock;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IntegrationTest extends AbstractControllerTest {

    @Test
    public void testBusinessLogic() throws Exception {
        showAllBeers();
        showAllFilterdByTypeBeers();
        final String adminToken = signInAsAdmin();
        final Integer newBeerId = addBeerKrynica(adminToken);
        updateBeerById(adminToken, newBeerId);
        deleteBeerById(adminToken, newBeerId);
        final String customerToken = signUpAsCustomer();
        addOrder(customerToken);
        showAllOrders(adminToken);
    }


    private void showAllBeers() throws Exception {
        // when
        mockMvc.perform(get("/api/beers").contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(
                mapper.writeValueAsString(
                    List.of(BeerMock.getById(LIDSKOE), BeerMock.getById(PILSNER), BeerMock.getById(ALIVARIA)))));

    }

    private void showAllFilterdByTypeBeers() throws Exception {
        mockMvc.perform(get("/api/beers")
                            .param("type", "темное")
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(List.of(BeerMock.getById(2)))));
    }

    private String signInAsAdmin() throws Exception {
        // when
        final String responseAdmin = mockMvc.perform(
            post("/api/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(
                    new AuthRequest(UsersMock.getById(ADMIN).getEmail(), UsersMock.getById(ADMIN).getPassword()))))
            // then
            .andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        return "Bearer " + mapper.readValue(responseAdmin, UserSignInResponse.class).getToken();
    }

    private Integer addBeerKrynica(final String token) throws Exception {
        // given
        final Beer beerKrynica = BeerMock.getById(KRYNICA);
        // when
        final String responseKrynicaId =
            mockMvc.perform(post("/api/beers")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(beerKrynica)))
                // then
                .andExpect(status().isCreated())
                .andExpect(content().json(mapper.writeValueAsString(KRYNICA)))
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        return Integer.valueOf(responseKrynicaId);
    }

    private void updateBeerById(final String token, final Integer beerId) throws Exception {
        // when
        mockMvc.perform(put("/api/beers/" + beerId)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(mapper.writeValueAsString(
                                BeerMock.getById(6))))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(BeerMock.getById(6))));
    }

    private void deleteBeerById(final String token, final Integer beerId) throws Exception {
        // when
        mockMvc.perform(delete("/api/beers/" + beerId)
                            .header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk());
    }

    private String signUpAsCustomer() throws Exception {
        // when
        final String responseAnton =
            mockMvc.perform(post("/api/users/sign-up")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(
                                    UsersMock.getById(4))))
                // then
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return "Bearer " + mapper.readValue(responseAnton, UserSignInResponse.class).getToken();
    }

    private void addOrder(final String token) throws Exception {
        // given
        final Integer ANTON = 4;
        final Orders orderAntonExpected = OrderMock.getById(ANTON);
        // when
        final String responseOrderAnton =
            mockMvc.perform(post("/api/orders")
                                .header("Authorization", token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(
                                    OrderRequestMock.getOrderRequestByAnton())))
                // then
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        final Orders orderAntonActual = mapper.readValue(responseOrderAnton, Orders.class);

        assertEquals(orderAntonExpected, orderAntonActual);
        assertEquals(sortCustomerOrders(orderAntonExpected), sortCustomerOrders(orderAntonActual));
    }

    private void showAllOrders(final String token) throws Exception {
        // when
        mockMvc.perform(get("/api/orders").header("Authorization", token)
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(
                mapper.writeValueAsString(OrderMock.getAllValuesBusinessLogic())));
    }

    private void changeOrderStatus(final String token) throws Exception {
        // when
        mockMvc.perform(patch("/api/orders/" + ORDER_ID)
                            .header("Authorization", token)
                            .param("status", "true")
                            .contentType(MediaType.APPLICATION_JSON))
            // then
            .andExpect(status().isOk())
            .andExpect(content().json(mapper.writeValueAsString(ORDER_ID)));
    }
}
