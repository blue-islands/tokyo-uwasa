package jp.livlog.tokyouwasa.data;

import java.io.Serializable;
import java.util.Comparator;

/**
 * UwasaKey0Compクラス.
 *
 * @author H.Aoshima
 * @version 1.0
 */
public class UwasaKey0Comp implements Comparator <Uwasa>, Serializable {

    /**
     * シリアルバージョンUID.
     */
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(final Uwasa arg0, final Uwasa arg1) {

        // 距離の比較(昇順)
        double ret = arg0.getDistance() - arg1.getDistance();

        return (int) (ret * 1000);
    }

}