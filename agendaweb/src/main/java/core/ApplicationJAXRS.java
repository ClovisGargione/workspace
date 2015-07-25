/**
 * 
 */
package core;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.moxy.json.MoxyJsonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.validation.ValidationFeature;

/**
 * @author c_r_s_000
 *
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
