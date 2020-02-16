package com.gp.beershop.controller;

import com.gp.beershop.dto.Beer;
import com.gp.beershop.dto.PriceRequest;
import com.gp.beershop.entity.BeerEntity;
import com.gp.beershop.mapper.BeerMapper;
import com.gp.beershop.repository.BeerRepository;
import com.gp.beershop.service.BeerService;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.willReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log
public class BeerControllerTest extends AbstractControllerTest {

    @MockBean
    protected BeerRepository beerRepository;
//    @SpyBean
//    private BeerService beerService;
    @SpyBean
    private BeerMapper beerMapper;
//    @BeforeEach
//    public void init() {
//        beerService.init();
//    }
    @Test
    public void testBeerGetAll() throws Exception {
//        willReturn(List.of(
//                Beer.builder()
//                        .id(1)
//                        .type("светлое")
//                        .inStock(true)
//                        .name("Лидское")
//                        .description("Лучшее пиво по бабушкиным рецептам")
//                        .alcohol(5.0)
//                        .density(11.5)
//                        .country("Республика Беларусь")
//                        .price(5D)
//                        .build(),
//                Beer.builder()
//                        .id(2)
//                        .type("темное")
//                        .inStock(true)
//                        .name("Аливария")
//                        .description("Пиво номер 1 в Беларуси")
//                        .alcohol(4.6)
//                        .density(10.2)
//                        .country("Республика Беларусь")
//                        .price(3D)
//                        .build())
//                .stream()
//                .map(beerMapper::sourceToDestination)
//                .collect(Collectors.toList()))
//                .given(beerRepository).findAll();

        mockMvc.perform(get("/api/beers")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(
                        List.of(Beer.builder()
                                        .id(1)
                                        .type("светлое")
                                        .inStock(true)
                                        .name("Лидское")
                                        .description("Лучшее пиво по бабушкиным рецептам")
                                        .alcohol(5.0)
                                        .density(11.5)
                                        .country("Республика Беларусь")
                                        .price(5D)
                                        .build(),
                                Beer.builder()
                                        .id(2)
                                        .type("темное")
                                        .inStock(true)
                                        .name("Аливария")
                                        .description("Пиво номер 1 в Беларуси")
                                        .alcohol(4.6)
                                        .density(10.2)
                                        .country("Республика Беларусь")
                                        .price(3D)
                                        .build()
                        )
                )));
    }

    @Test
    public void testBeerFilter() throws Exception {
        final String token = signInAsCustomer();
//        willReturn(List.of(
//                Beer.builder()
//                        .id(1)
//                        .type("светлое")
//                        .inStock(true)
//                        .name("Лидское")
//                        .description("Лучшее пиво по бабушкиным рецептам")
//                        .alcohol(5.0)
//                        .density(11.5)
//                        .country("Республика Беларусь")
//                        .price(5D)
//                        .build(),
//                Beer.builder()
//                        .id(2)
//                        .type("темное")
//                        .inStock(true)
//                        .name("Аливария")
//                        .description("Пиво номер 1 в Беларуси")
//                        .alcohol(4.6)
//                        .density(10.2)
//                        .country("Республика Беларусь")
//                        .price(3D)
//                        .build())
//                .stream()
//                .map(beerMapper::sourceToDestination)
//                .collect(Collectors.toList()))
//                .given(beerRepository).findAll();
        log.info("Test beeer size = " + beerRepository.findAll().stream().count());
        mockMvc.perform(get("/api/beers")
                .param("type", "темное")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(
                        List.of(
                                Beer.builder()
                                        .id(2)
                                        .type("темное")
                                        .inStock(true)
                                        .name("Аливария")
                                        .description("Пиво номер 1 в Беларуси")
                                        .alcohol(4.6)
                                        .density(10.2)
                                        .country("Республика Беларусь")
                                        .price(3D)
                                        .build()
                        ))));
    }

    @Test
    public void testNewBeer() throws Exception {
        final String token = signInAsCustomer();
        BeerEntity s = beerMapper.sourceToDestination(Beer.builder()
                .type("светлое осветлённое")
                .inStock(true)
                .name("Pilsner Urquell")
                .description("непастеризованное")
                .alcohol(4.2)
                .density(12.0)
                .country("Чехия")
                .price(8D)
                .build());
        willReturn(beerMapper.sourceToDestination(Beer.builder()
                .id(3)
                .type("светлое осветлённое")
                .inStock(true)
                .name("Pilsner Urquell")
                .description("непастеризованное")
                .alcohol(4.2)
                .density(12.0)
                .country("Чехия")
                .price(8D)
                .build()))
                .given(beerRepository)
                .saveAndFlush(s);
        mockMvc.perform(post("/api/beers").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        mapper.writeValueAsString(
                                Beer.builder()
                                        .type("светлое осветлённое")
                                        .inStock(true)
                                        .name("Pilsner Urquell")
                                        .description("непастеризованное")
                                        .alcohol(4.2)
                                        .density(12.0)
                                        .country("Чехия")
                                        .price(8D)
                                        .build()
                        )))
                .andExpect(status().isCreated())
                .andExpect(content().json(
                        mapper.writeValueAsString(3)));
    }

    @Test
    public void testUpdateBeerById() throws Exception {
        final String token = signInAsCustomer();
        willReturn(Optional.of(beerMapper.sourceToDestination(Beer.builder()
                .id(3)
                .type("светлое осветлённое")
                .inStock(true)
                .name("Pilsner Urquell")
                .description("непастеризованное")
                .alcohol(4.2)
                .density(12.0)
                .country("Чехия")
                .price(8D)
                .build())))
                .given(beerRepository)
                .findById(3);
        mockMvc.perform(patch("/api/beers/3").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        mapper.writeValueAsString(
                                PriceRequest.builder().price(8.3).build()
                        )

                ))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        mapper.writeValueAsString(3)
                ));
    }

    @Test
    public void testDeleteBeerById() throws Exception {
        final String token = signInAsCustomer();
        mockMvc.perform(delete("/api/beers/3").header("Authorization", token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(
                        mapper.writeValueAsString(3)));
    }
}
