package print;

import java.nio.charset.StandardCharsets;
import java.io.InputStream;
import org.apache.commons.io.IOUtils;
// import org.apache.http.HttpHeaders;
// import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.DynamicPropertyRegistry;
// import org.springframework.test.context.DynamicPropertySource;
// import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.sap.cloud.sdk.cloudplatform.thread.ThreadContextExecutor;
// import com.sap.cloud.security.config.Service;
// import com.sap.cloud.security.test.SecurityTestRule;
// import com.sap.cloud.security.token.TokenClaims;

import static java.lang.Thread.currentThread;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
// @TestPropertySource(properties = {"sap.security.services.xsuaa.xsappname=xsapp!t0815", "sap.security.services.xsuaa.clientid=sb-clientId!t0815"})
public class HelloWorldControllerTest
{
    @Autowired
    private MockMvc mvc;

    /*
    @ClassRule
    public static final SecurityTestRule rule = SecurityTestRule.getInstance(Service.XSUAA);
    private static String jwt;
    */

    @Test
    public void test() throws Exception
    {
        /*
        jwt = rule.getPreconfiguredJwtGenerator()
                .withLocalScopes("Display")
                .withClaimValue(TokenClaims.AUTHORIZATION_PARTY, "sb-clientId!t0815")
                .createToken()
                .getTokenValue();
        jwt = "Bearer " + jwt;
        */
        final InputStream inputStream = currentThread().getContextClassLoader().getResourceAsStream("expected.json");

        ThreadContextExecutor.fromNewContext().execute(() -> {
            mvc.perform(MockMvcRequestBuilders.get("/hello")
                    //.header(HttpHeaders.AUTHORIZATION, jwt)
                    )
                    .andExpect(status().isOk())
                    .andExpect(content().json(
                            IOUtils.toString(inputStream, StandardCharsets.UTF_8)));
        });
    }
    /*
    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("sap.security.services.xsuaa.uaadomain", () -> {
            return "http://localhost:" + rule.getWireMockServer().port();
        });
    }*/
}
