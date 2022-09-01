package jp.livlog.tokyouwasa.resource;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.gavaghan.geodesy.Ellipsoid;
import org.gavaghan.geodesy.GeodeticCalculator;
import org.gavaghan.geodesy.GlobalCoordinates;
import org.restlet.ext.json.JsonRepresentation;
import org.restlet.resource.Get;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import jp.livlog.tokyouwasa.data.Uwasa;
import jp.livlog.tokyouwasa.share.AbsBaseResource;
import lombok.extern.slf4j.Slf4j;

/**
 * Resource which has only one representation.
 */
@Slf4j
public class UwasaResource extends AbsBaseResource {

    @SuppressWarnings ("unchecked")
    @Get
    public JsonRepresentation doGet() throws Exception {

        final var restletResponse = this.getResponse();
        restletResponse.setAccessControlAllowOrigin("*");

        final var form = this.getRequest().getResourceRef().getQueryAsForm();
        final var lat = form.getValues("lat");
        final var lng = form.getValues("lng");

        try (Reader r = new InputStreamReader(UwasaResource.class.getResourceAsStream("/output.json"))) {
            final var gson = new Gson();
            final var listType = new TypeToken <List <Uwasa>>() {
            }.getType();
            final List <Uwasa> uwasaList = gson.fromJson(r, listType);

            // instantiate the calculator
            final var geoCalc = new GeodeticCalculator();

            // select a reference elllipsoid
            final var reference = Ellipsoid.WGS84;

            // set Lincoln Memorial coordinates
            GlobalCoordinates lincolnMemorial;
            lincolnMemorial = new GlobalCoordinates(
                    Double.parseDouble(lat),
                    Double.parseDouble(lng));

            final List <Uwasa> list1 = new ArrayList <>();
            final List <Uwasa> list2 = new ArrayList <>();
            for (final Uwasa uwasa : uwasaList) {
                // set Eiffel Tower coordinates
                GlobalCoordinates eiffelTower;
                eiffelTower = new GlobalCoordinates(uwasa.getLat(), uwasa.getLng());

                // calculate the geodetic curve
                final var geoCurve = geoCalc.calculateGeodeticCurve(reference, lincolnMemorial, eiffelTower);
                final var distance = geoCurve.getEllipsoidalDistance();
                final var azimuth = geoCurve.getAzimuth();
                uwasa.setDistance(distance);
                uwasa.setAzimuth(azimuth);

                final var lastCharacter = uwasa.getAddress().substring(uwasa.getAddress().length() - 1);
                if ("区".equals(lastCharacter)) {
                    list1.add(uwasa);
                } else {
                    list2.add(uwasa);
                }
            }

            final Map <Double, List <Uwasa>> map1 = new TreeMap <>();
            for (final Uwasa uwasa : list1) {
                var list = map1.get(uwasa.getDistance());
                if (list == null) {
                    list = new ArrayList <>();
                    list.add(uwasa);
                    map1.put(uwasa.getDistance(), list);
                } else {
                    list.add(uwasa);
                    map1.put(uwasa.getDistance(), list);
                }
            }
            final Map <Double, List <Uwasa>> map2 = new TreeMap <>();
            for (final Uwasa uwasa : list2) {
                var list = map2.get(uwasa.getDistance());
                if (list == null) {
                    list = new ArrayList <>();
                    list.add(uwasa);
                    map2.put(uwasa.getDistance(), list);
                } else {
                    list.add(uwasa);
                    map2.put(uwasa.getDistance(), list);
                }
            }
            //
            final List <Uwasa> subList = new ArrayList <>();
            final var wkList1 = new ArrayList <>(map1.values()).get(0);
            final var wkList2 = new ArrayList <>(map2.values()).get(0);

            Collections.shuffle(wkList1);

            subList.addAll(wkList1.subList(0, 3));
            subList.addAll(wkList2);
            // Collections.sort(uwasaList, new UwasaKey0Comp());

            Collections.shuffle(subList);

            final var ret = subList.get(0);

            // set Lincoln Memorial coordinates
            lincolnMemorial = new GlobalCoordinates(ret.getLat(), ret.getLng());

            // set the direction and distance
            final double startBearing = (int) Math.ceil(Math.random() * 360);
            final double distance = (int) Math.ceil(Math.random() * 100);

            // find the destination
            final var endBearing = new double[1];
            final var dest = geoCalc.calculateEndingGlobalCoordinates(reference, lincolnMemorial, startBearing, distance, endBearing);
            ret.setLat(dest.getLatitude());
            ret.setLng(dest.getLongitude());

            return new JsonRepresentation(ret);

        } catch (final IOException e) {
            throw new Exception(e);
        }

    }

}