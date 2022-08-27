package jp.livlog.tokyouwasa.share;

import org.restlet.resource.ServerResource;
import org.restlet.resource.Status;

/**
 * ベース用リソースクラス.
 *
 * @author H.Aoshima
 * @version 1.0
 */
public abstract class AbsBaseResource extends ServerResource {

    /** エラー. */
    protected static final int      ERROR = -1;

    /** 正常. */
    protected static final int      OK    = 0;

    /** E0001:パラメータがありません. */
    protected static final String[] E0001 = { "E0001", "パラメータがありません。" };

    /** E0002:フォーマットが違います. */
    protected static final String[] E0002 = { "E0002", "フォーマットが違います。" };

    /** E0003:すでに登録されています. */
    protected static final String[] E0003 = { "E0003", "すでに登録されています。" };

    /** E0004:登録されていません. */
    protected static final String[] E0004 = { "E0004", "登録されていません。" };

    /** E0005:取得できません. */
    protected static final String[] E0005 = { "E0005", "取得できません。" };

    /** E9999:システム例外. */
    protected static final String[] E9999 = { "E9999", "システム例外" };

    @Status (403)
    public class NotspecifiedDomainError extends Exception {

        public NotspecifiedDomainError(String message) {

            super(message);
        }
    }
}