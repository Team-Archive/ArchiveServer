package site.archive.common;

public class ArchiveStringUtils {

    private static final Character MAIL_AT = '@';

    private ArchiveStringUtils() {
    }

    public static String extractIdFromMail(final String mailAddress) {
        return mailAddress.substring(0, mailAddress.indexOf(MAIL_AT));
    }

}
