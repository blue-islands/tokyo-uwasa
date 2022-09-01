package jp.livlog.tokyouwasa.share;

import java.math.BigDecimal;

import jp.livlog.utility.Calculator;
import jp.livlog.utility.CheckUtil;
import jp.livlog.utility.StringUtil;
import jp.livlog.utility.Symbol;

public class RegionMesh {

    public static void main(String[] args) {

        final var RegionMesh = new RegionMesh();

        try {
            System.out.println(RegionMesh.getMeshCode(35.6845474, 139.7713197));
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private final static double MINIMUM = 0.0000000001;

    private final static double MINDEG  = 0.00000000001;

    public String getMeshCode(double lat, double lon) throws Exception {

        // final var ret = new ArrayList();
        // final var retCode = 0;

        try {
            if (lat < 0 || lon < 0) {
                throw new Exception("numeric error");
            }

            // 一次メッシュ(緯度４０分、経度１度のメッシュ）
            final var lat15m = Math.floor(lat * 1.5);
            final var mesh1 = lat15m * 100 + Math.floor(lon) - 100;

            var latResidue = lat - lat15m / 1.5;
            var lonResidue = lon - Math.floor(lon);
            if (-RegionMesh.MINIMUM < latResidue && latResidue < RegionMesh.MINIMUM) {
                latResidue = 0;
            }
            if (-RegionMesh.MINIMUM < lonResidue && lonResidue < RegionMesh.MINIMUM) {
                lonResidue = 0;
            }

            // 二次メッシュ(緯度５分、経度7.5分のメッシュ）
            final var lat2 = Math.floor(latResidue / 5.0 * 60 + RegionMesh.MINDEG);
            if (lat2 > 7) {
                throw new Exception("numeric error");
            }
            final var lon2 = Math.floor(lonResidue / 7.5 * 60 + RegionMesh.MINDEG);
            if (lon2 > 7) {
                throw new Exception("numeric error");
            }

            final var mesh2 = lat2 * 10 + lon2;
            // if (mesh2 < 10) {
            // mesh2 = this.format0(mesh2);
            // }

            latResidue -= lat2 * 5.0 / 60;
            lonResidue -= lon2 * 7.5 / 60;
            if (-RegionMesh.MINIMUM < latResidue && latResidue < RegionMesh.MINIMUM) {
                latResidue = 0;
            }
            if (-RegionMesh.MINIMUM < lonResidue && lonResidue < RegionMesh.MINIMUM) {
                lonResidue = 0;
            }

            // 三次メッシュ(緯度0.5分、経度0.75分のメッシュ）
            final var lat3 = Math.floor(latResidue / 0.5 * 60 + RegionMesh.MINDEG);
            if (lat3 > 9) {
                throw new Exception("numeric error");
            }
            final var lon3 = Math.floor(lonResidue / 0.75 * 60 + RegionMesh.MINDEG);
            if (lon3 > 9) {
                throw new Exception("numeric error");
            }

            final var mesh3 = lat3 * 10 + lon3;
            // if ( mesh3 < 10 ) mesh3 = format0( mesh3 );

            latResidue -= lat3 * 0.5 / 60;
            lonResidue -= lon3 * 0.75 / 60;
            if (-RegionMesh.MINIMUM < latResidue && latResidue < RegionMesh.MINIMUM) {
                latResidue = 0;
            }
            if (-RegionMesh.MINIMUM < lonResidue && lonResidue < RegionMesh.MINIMUM) {
                lonResidue = 0;
            } //
              // 5倍メッシュ(三次メッシュの５倍メッシュ）
            final var mesh5X = Math.floor(lat3 / 5 + RegionMesh.MINDEG) * 2 + Math.floor(lon3 / 5 + RegionMesh.MINDEG) + 1;

            // 2倍メッシュ(三次メッシュの2倍メッシュ）
            final var mesh2X = Math.floor(lat3 / 2 + RegionMesh.MINDEG) * 20 + Math.floor(lon3 / 2 + RegionMesh.MINDEG) * 2;
            // if ( mesh2X < 10 ) mesh2X = format0( mesh2X );

            // 2分の1メッシュ(三次メッシュの２分の１メッシュ）
            final var lat4 = Math.floor(latResidue / (0.5 / 2) * 60 + RegionMesh.MINDEG);
            if (lat4 > 1) {
                throw new Exception("numeric error");
            }
            final var lon4 = Math.floor(lonResidue / (0.75 / 2) * 60 + RegionMesh.MINDEG);
            if (lon4 > 1) {
                throw new Exception("numeric error");
            }

            final var mesh4 = lat4 * 2 + lon4 + 1;

            latResidue -= lat4 * (0.5 / 2) / 60;
            lonResidue -= lon4 * (0.75 / 2) / 60;
            if (-RegionMesh.MINIMUM < latResidue && latResidue < RegionMesh.MINIMUM) {
                latResidue = 0;
            }
            if (-RegionMesh.MINIMUM < lonResidue && lonResidue < RegionMesh.MINIMUM) {
                lonResidue = 0;
            }

            // 4分の1メッシュ(三次メッシュの4分の１メッシュ）
            final var lat5 = Math.floor(latResidue / (0.5 / 4) * 60 + RegionMesh.MINDEG);
            if (lat5 > 1) {
                throw new Exception("numeric error");
            }
            final var lon5 = Math.floor(lonResidue / (0.75 / 4) * 60 + RegionMesh.MINDEG);
            if (lon5 > 1) {
                throw new Exception("numeric error");
            }

            final var mesh5 = lat5 * 2 + lon5 + 1;

            latResidue -= lat5 * (0.5 / 4) / 60;
            lonResidue -= lon5 * (0.75 / 4) / 60;

            if (-RegionMesh.MINIMUM < latResidue && latResidue < RegionMesh.MINIMUM) {
                latResidue = 0;
            }
            if (-RegionMesh.MINIMUM < lonResidue && lonResidue < RegionMesh.MINIMUM) {
                lonResidue = 0;
            }

            // 8分の1メッシュ(三次メッシュの8分の１メッシュ）
            final var lat6 = Math.floor(latResidue / (0.5 / 8) * 60 + RegionMesh.MINDEG);
            if (lat6 > 1) {
                throw new Exception("numeric error");
            }
            final var lon6 = Math.floor(lonResidue / (0.75 / 8) * 60 + RegionMesh.MINDEG);
            if (lon6 > 1) {
                throw new Exception("numeric error");
            }

            final var mesh6 = lat6 * 2 + lon6 + 1;

            // // 戻り値
            // ret[1] = mesh1;
            // ret[2] = mesh2;
            // ret[3] = mesh3;
            // ret[4] = mesh4;
            // ret[5] = mesh5;
            // ret[6] = mesh6;
            // ret[7] = mesh5X;
            // ret[8] = mesh2X;
            //
            // ret[0] = retCode;
            // System.out.println("" + (int) mesh1 + (int) mesh2 + (int) mesh3);

            return String.valueOf((int) mesh1) + this.format0(mesh2) + this.format0(mesh3);

        } catch (final Exception e) {
            // retCode= e;
            // var errstr = "error:" + retCode + " lat2=" + lat2 + " lon2=" + lon2 +
            // " lat3=" + lat3 + " lon3=" + lon3 +" lat4=" + lat4 + " lon4=" + lon4 +
            // " lat5=" + lat5 + " lon5=" + lon5 +" lat6=" + lat6 + " lon6=" + lon6 +
            // " latR=" + latResidue + " lonR=" + lonResidue;
            // alert(errstr);
            throw e;
        }
    }


    private String format0(double num) {

        final var value = (int) num;

        return StringUtil.lpad(String.valueOf(value), 2, "0");
    }


    /**
     * 地域メッシュを緯度経度に変換する.
     * @param meshcode 地域メッシュ
     * @return 緯度・経度
     */
    public BigDecimal[] getLatLng(final String meshcode) {

        if (CheckUtil.chkStrSpaceTrimIsBlank(meshcode)) {
            return null;
        }

        // 緯度：=(LEFT(M1,2)/1.5*3600+MID(M1,5,1)*5*60+MID(M1,7,1)*30)/3600
        // 経度：=((MID(M1,3,2)+100)*3600+MID(M1,6,1)*7.5*60+MID(M1,8,1)*45)/3600

        final var lat = new Calculator();
        final var lng = new Calculator();
        lat.setScale(Symbol.INT_10);
        lng.setScale(Symbol.INT_10);
        if (meshcode.length() >= Symbol.INT_4) {
            final var firstLat = new Calculator(meshcode.substring(0, 2));
            final var firstLng = new Calculator(meshcode.substring(2, 4));
            firstLat.setScale(Symbol.INT_10);
            firstLng.setScale(Symbol.INT_10);
            firstLat.divide(Symbol.FLOAT_1_5);
            firstLng.add(Symbol.INT_100);
            firstLat.multiply(Symbol.INT_3600);
            firstLng.multiply(Symbol.INT_3600);
            lat.add(firstLat.answer());
            lng.add(firstLng.answer());
        }

        if (meshcode.length() >= Symbol.INT_6) {
            final var secondLat = new Calculator(meshcode.substring(4, 5));
            final var secondLng = new Calculator(meshcode.substring(5, 6));
            secondLat.setScale(Symbol.INT_10);
            secondLng.setScale(Symbol.INT_10);
            secondLat.multiply(Symbol.INT_5);
            secondLng.multiply(Symbol.FLOAT_7_5);
            secondLat.multiply(Symbol.INT_60);
            secondLng.multiply(Symbol.INT_60);
            lat.add(secondLat.answer());
            lng.add(secondLng.answer());
        }

        if (meshcode.length() >= Symbol.INT_8) {
            final var thirdLat = new Calculator(meshcode.substring(6, 7));
            final var thirdLng = new Calculator(meshcode.substring(7, 8));
            thirdLat.setScale(Symbol.INT_10);
            thirdLng.setScale(Symbol.INT_10);
            thirdLat.multiply(Symbol.INT_30);
            thirdLng.multiply(Symbol.INT_45);
            lat.add(thirdLat.answer());
            lng.add(thirdLng.answer());
        }

        lat.divide(Symbol.INT_3600);
        lng.divide(Symbol.INT_3600);

        return new BigDecimal[] { lat.answer(), lng.answer() };
    }
}
