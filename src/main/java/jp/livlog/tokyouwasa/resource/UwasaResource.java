package jp.livlog.tokyouwasa.resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GeodeticCurve;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jp.livlog.tokyouwasa.data.Uwasa;
import jp.livlog.tokyouwasa.data.UwasaKey0Comp;
import jp.livlog.tokyouwasa.share.AbsBaseResource;
import lombok.extern.slf4j.Slf4j;

/**
 * Resource which has only one representation.
 */
@Slf4j
public class UwasaResource extends AbsBaseResource {

    @Get
    public JsonRepresentation doGet() throws Exception {

        final var restletResponse = this.getResponse();
        restletResponse.setAccessControlAllowOrigin("*");

        final var form = this.getRequest().getResourceRef().getQueryAsForm();
        final var lat = form.getValues("lat");
        final var lng = form.getValues("lng");

        try (Reader r = new InputStreamReader(UwasaResource.class.getResourceAsStream("/output.json"))) {
            Gson gson = new Gson();
            Type listType = new TypeToken <List <Uwasa>>() {
            }.getType();
            List <Uwasa> uwasaList = gson.fromJson(r, listType);

            // instantiate the calculator
            final GeodeticCalculator geoCalc = new GeodeticCalculator();

            // select a reference elllipsoid
            final Ellipsoid reference = Ellipsoid.WGS84;

            // set Lincoln Memorial coordinates
            GlobalCoordinates lincolnMemorial;
            lincolnMemorial = new GlobalCoordinates(
                    Double.parseDouble(lat),
                    Double.parseDouble(lng));

            for (Uwasa uwasa : uwasaList) {
                // set Eiffel Tower coordinates
                GlobalCoordinates eiffelTower;
                eiffelTower = new GlobalCoordinates(uwasa.getLat(), uwasa.getLng());

                // calculate the geodetic curve
                final GeodeticCurve geoCurve = geoCalc.calculateGeodeticCurve(reference, lincolnMemorial, eiffelTower);
                final double distance = geoCurve.getEllipsoidalDistance();
                final double azimuth = geoCurve.getAzimuth();
                uwasa.setDistance(distance);
                uwasa.setAzimuth(azimuth);
            }

            Collections.sort(uwasaList, new UwasaKey0Comp());

            List <Uwasa> subList = uwasaList.subList(0, 20);

            Collections.shuffle(subList);

            Uwasa ret = subList.get(0);

            // set Lincoln Memorial coordinates
            lincolnMemorial = new GlobalCoordinates(ret.getLat(), ret.getLng());

            // set the direction and distance
            final double startBearing = (int) Math.ceil(Math.random() * 360);
            final double distance = (int) Math.ceil(Math.random() * 100);

            // find the destination
            final double[] endBearing = new double[1];
            final GlobalCoordinates dest = geoCalc.calculateEndingGlobalCoordinates(reference, lincolnMemorial, startBearing, distance, endBearing);
            ret.setLat(dest.getLatitude());
            ret.setLng(dest.getLongitude());

            return new JsonRepresentation(ret);

        } catch (final IOException e) {
            throw new Exception(e);
        }

    }

}