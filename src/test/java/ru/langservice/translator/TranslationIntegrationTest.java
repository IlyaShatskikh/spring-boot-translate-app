package ru.langservice.translator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@WithUserDetails(value = "user1")
@Transactional
public class TranslationIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @Sql(value = {"/sql/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void translationPageTest() throws Exception {
        mockMvc.perform(get("/translation"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//html/body/nav/div[2]").string("user1"));
    }

    @Test
    @Sql(value = {"/sql/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/sql/add-translations-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void translationTableTest() throws Exception {
        mockMvc.perform(get("/translation"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div/table/tbody/tr").nodeCount(3));
    }

    @Test
    @Sql(value = {"/sql/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/sql/add-translations-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void translationTableFilterTest() throws Exception {
        mockMvc.perform(get("/translation").param("filter", "ru-en"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div/table/tbody/tr").nodeCount(2));
    }

    @Test
    @Sql(value = {"/sql/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void translationTableAddMessageTest() throws Exception {
        mockMvc.perform(post("/post").param("text", "test text").param("lang", "en-fr")
                .with(csrf()))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div/table/tbody/tr").nodeCount(1));
    }

}
