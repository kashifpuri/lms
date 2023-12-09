package com.app.lms.features.patrons;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.app.lms.JsonUtils;
import com.app.lms.book.data.dto.Book;
import com.app.lms.patron.data.dto.Patron;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@SpringBootTest
@AutoConfigureMockMvc
public class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetPatronssUnAuthenticated() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patrons")
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isForbidden())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetPatrons() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patrons")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.patrons").exists())
                .andExpect(jsonPath("$.patrons.size()").value(3))
                .andExpect(jsonPath("$.patrons[*].patronId").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testGetPatronDetails() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patron/1")
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.patronId").value(1))
                .andExpect(jsonPath("$.name").value("Walter White"))
                .andExpect(jsonPath("$.addressLine1").value("3828 Piermont Dr"))
                .andExpect(jsonPath("$.city").value("Albuquerque, NM"))
                .andExpect(jsonPath("$.country").value("USA"))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void addPatron() throws Exception{

        Patron newPatron = new Patron();
        newPatron.setName("Name");
        newPatron.setCellNumber("+123123123");
        newPatron.setAddressLine1("Address");
        newPatron.setCity("Atmore");
        newPatron.setCountry("USA");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/patron")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com"))
                        .content(JsonUtils.asJsonString(newPatron)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.patron.name").value("Name"))
                .andExpect(jsonPath("$.patron.cellNumber").value("+123123123"))
                .andExpect(jsonPath("$.patron.addressLine1").value("Address"))
                .andExpect(jsonPath("$.patron.city").value("Atmore"))
                .andExpect(jsonPath("$.patron.country").value("USA"))
                .andExpect(jsonPath("$.patron.patronId").isNumber())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updatePatron() throws Exception{

        Patron newPatron = new Patron();
        newPatron.setPatronId(2);
        newPatron.setName("Name");
        newPatron.setCellNumber("+123123123");
        newPatron.setAddressLine1("Address");
        newPatron.setCity("Atmore");
        newPatron.setCountry("USA");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/patron/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com"))
                        .content(JsonUtils.asJsonString(newPatron)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patron/2")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.name").value("Name"))
                .andExpect(jsonPath("$.cellNumber").value("+123123123"))
                .andExpect(jsonPath("$.addressLine1").value("Address"))
                .andExpect(jsonPath("$.city").value("Atmore"))
                .andExpect(jsonPath("$.country").value("USA"))
                .andExpect(jsonPath("$.patronId").isNumber())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void updateNotAvailablePatron() throws Exception{

        Patron newPatron = new Patron();
        newPatron.setPatronId(10);
        newPatron.setName("Name");
        newPatron.setCellNumber("+123123123");
        newPatron.setAddressLine1("Address");
        newPatron.setCity("Atmore");
        newPatron.setCountry("USA");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/patron/10")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(JsonUtils.asJsonString(newPatron))
                        .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com")))
                .andExpect(status().isNotFound())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void testDeletePatron() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/patron/3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com")))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patron/3")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePatronNotFound() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/patron/6")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .with(SecurityMockMvcRequestPostProcessors.user("kashif@puri.com")))
                .andExpect(status().isNotFound());
    }
}
