package jp.livlog.tokyouwasa.resource;

import java.util.HashMap;
import java.util.Map;

import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;

import jp.livlog.tokyouwasa.share.AbsBaseResource;
import jp.livlog.tokyouwasa.share.RegionMesh;
import lombok.extern.slf4j.Slf4j;

/**
 * Resource which has only one representation.
 */
@Slf4j
public class LatLngResource extends AbsBaseResource {

    @Get
    public JsonRepresentation doGet() throws Exception {

        final var restletResponse = this.getResponse();
        restletResponse.setAccessControlAllowOrigin("*");

        final var form = this.getRequest().getResourceRef().getQueryAsForm();
        final var meshcode = form.getValues("meshcode");

        final var regionMesh = new RegionMesh();

        final var latLng = regionMesh.getLatLng(meshcode);

        final Map <String, Object> ret = new HashMap <>();
        ret.put("lat", latLng[0].doubleValue());
        ret.put("lng", latLng[1].doubleValue());

        return new JsonRepresentation(ret);

    }

}