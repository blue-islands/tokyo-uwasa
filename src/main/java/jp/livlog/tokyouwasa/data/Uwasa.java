
package jp.livlog.tokyouwasa.data;

import javax.annotation.Generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;

@Generated ("jsonschema2pojo")
@Data
public class Uwasa {

    @SerializedName ("uwasa")
    @Expose
    private String uwasa;

    @SerializedName ("lat")
    @Expose
    private double lat;

    @SerializedName ("lng")
    @Expose
    private double lng;

    @SerializedName ("address")
    @Expose
    private String address;

    private double distance;

    private double azimuth;
}
