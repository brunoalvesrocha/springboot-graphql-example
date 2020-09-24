package me.bruno.graphqldemo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class GraphqldemoApplicationTests {

    @Autowired
    MockMvc mockMvc;
    String expectedResponse;
    String expectedResponseBefore;
    String expectedResponseAfter;

    @BeforeEach
    public void beforeEach() {
        expectedResponse = "{\"data\":{\"foods\":[" +
                "{\"id\":1,\"name\":\"Pizza\",\"isGood\":true}," +
                "{\"id\":2,\"name\":\"Spam\",\"isGood\":false}," +
                "{\"id\":3,\"name\":\"Eggs\",\"isGood\":true}," +
                "{\"id\":4,\"name\":\"Avocado\",\"isGood\":false}" +
                "]}}";

        expectedResponseBefore = "{\"data\":{\"foods\":[" +
                "{\"id\":1,\"name\":\"Pizza\"}," +
                "{\"id\":2,\"name\":\"Spam\"}," +
                "{\"id\":3,\"name\":\"Eggs\"}," +
                "{\"id\":4,\"name\":\"Avocado\"}" +
                "]}}";
        expectedResponseAfter = "{\"data\":{\"foods\":[" +
                "{\"id\":1,\"name\":\"Pizza\"}," +
                "{\"id\":2,\"name\":\"Spam\"}," +
                "{\"id\":3,\"name\":\"Eggs\"}," +
                "{\"id\":4,\"name\":\"Avocado\"}," +
                "{\"id\":5,\"name\":\"Pasta\"}" +
                "]}}";
    }

    @Test
    @Order(0)
    void shouldListAllFoods() throws Exception {

        mockMvc.perform(post("/graphql")
                .content("{\"query\":\"{ foods { id name isGood } }\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse))
                .andReturn();
    }

    @Test
    @Order(1)
    void addAndRemoveFood() throws Exception {

        // List foods, expect 'New Food' to not be there
        mockMvc.perform(post("/graphql")
                .content("{\"query\":\"{ foods { id name } }\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBefore))
                .andReturn();

        // Add 'New Food'
        mockMvc.perform(post("/graphql")
                .content("{\"query\":\"mutation { saveFood(food: { name: \\\"Pasta\\\" }) { id name } }\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"data\":{\"saveFood\":{\"id\":5,\"name\":\"Pasta\"}}}"))
                .andReturn();

        // List foods, expect 'New Food' to be there
        mockMvc.perform(post("/graphql")
                .content("{\"query\":\"{ foods { id name } }\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseAfter))
                .andReturn();

        // Remove 'New Food'
        mockMvc.perform(post("/graphql")
                .content("{\"query\":\"mutation { deleteFood(id: 5) }\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // List foods, expect 'New Food' to not be there
        mockMvc.perform(post("/graphql")
                .content("{\"query\":\"{ foods { id name } }\"}")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponseBefore))
                .andReturn();
    }
}


