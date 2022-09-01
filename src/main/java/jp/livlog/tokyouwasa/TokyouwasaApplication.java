package jp.livlog.tokyouwasa;

import org.restlet.Application;
import org.restlet.Component;
import org.restlet.Restlet;
import org.restlet.data.Protocol;
import org.restlet.routing.Router;

import jp.livlog.tokyouwasa.resource.LatLngResource;
import jp.livlog.tokyouwasa.resource.MeshCodeResource;
import jp.livlog.tokyouwasa.resource.UwasaResource;

/**
 * アプリケーションクラス.
 *
 * @author H.Aoshima
 * @version 1.0
 */
public class TokyouwasaApplication extends Application {

    /**
     * Creates a root Restlet that will receive all incoming calls.
     */
    @Override
    public synchronized Restlet createInboundRoot() {

        final var router = new Router(this.getContext());

        router.attach("/api/uwasa", UwasaResource.class);
        router.attach("/api/meshcode", MeshCodeResource.class);
        router.attach("/api/latlng", LatLngResource.class);

        return router;
    }


    public static void main(String[] args) throws Exception {

        // Create a new Component.
        final var component = new Component();

        // Add a new HTTP server listening on port 8080.
        component.getServers().add(Protocol.HTTP, 8080);

        // Attach the austin application.
        component.getDefaultHost().attach("/tokyouwasa", new TokyouwasaApplication());

        // Start the component.
        component.start();
    }
}
