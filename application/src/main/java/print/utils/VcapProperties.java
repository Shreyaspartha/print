package print.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class VcapProperties {

    @Value("${vcap.services.print.credentials.clientId}")
    private String clientId;

    @Value("${vcap.services.print.credentials.clientSecret}")
    private String clientSecret;

    @Value("${vcap.services.print.credentials.uaaDomain}")
    private String uaaDomain;

    @Value("${vcap.services.print.credentials.subscriberDomain}")
    private String subscriberDomain;
}
