package core;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.validation.ValidationFeature;

import javax.ws.rs.ApplicationPath;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
@ApplicationPath("/")
public class ApplicationJAXRS extends ResourceConfig {

    public ApplicationJAXRS() {
        // Register resources and providers using package-scanning.
        packages("service");

        register(MoxyJsonFeature.class);
        register(MultiPartFeature.class);
        register(ValidationFeature.class);
    }


}
