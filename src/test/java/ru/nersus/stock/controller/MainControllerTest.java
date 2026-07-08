package ru.nersus.stock.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Disabled
class MainControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void getLandingRedirect() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isFound());
    }

    @Test
    void getLandingSuccess() throws Exception {
        mockMvc.perform(get("/?symbol=AAPL&period=WEEKLY"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("stockLabels", "stockPrices"));
    }

    @Test
    void getPortfolioSuccess() throws Exception {
        mockMvc.perform(get("/portfolio").with(httpBasic("admin2@mail.ru", "111111")))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("stocks", "portfolioCost"));
    }

}
