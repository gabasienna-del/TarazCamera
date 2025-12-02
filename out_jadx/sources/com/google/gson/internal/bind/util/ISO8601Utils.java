package com.google.gson.internal.bind.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes.dex */
public class ISO8601Utils {
    private static final String UTC_ID = "UTC";
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(UTC_ID);

    private ISO8601Utils() {
    }

    public static String format(Date date) {
        return format(date, false, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean millis) {
        return format(date, millis, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean millis, TimeZone tz) {
        Calendar calendar = new GregorianCalendar(tz, Locale.US);
        calendar.setTime(date);
        int capacity = "yyyy-MM-ddThh:mm:ss".length();
        StringBuilder formatted = new StringBuilder(capacity + (millis ? ".sss".length() : 0) + (tz.getRawOffset() == 0 ? "Z" : "+hh:mm").length());
        padInt(formatted, calendar.get(1), "yyyy".length());
        formatted.append('-');
        padInt(formatted, calendar.get(2) + 1, "MM".length());
        formatted.append('-');
        padInt(formatted, calendar.get(5), "dd".length());
        formatted.append('T');
        padInt(formatted, calendar.get(11), "hh".length());
        formatted.append(':');
        padInt(formatted, calendar.get(12), "mm".length());
        formatted.append(':');
        padInt(formatted, calendar.get(13), "ss".length());
        if (millis) {
            formatted.append('.');
            padInt(formatted, calendar.get(14), "sss".length());
        }
        int offset = tz.getOffset(calendar.getTimeInMillis());
        if (offset != 0) {
            int hours = Math.abs((offset / 60000) / 60);
            int minutes = Math.abs((offset / 60000) % 60);
            formatted.append(offset >= 0 ? '+' : '-');
            padInt(formatted, hours, "hh".length());
            formatted.append(':');
            padInt(formatted, minutes, "mm".length());
        } else {
            formatted.append('Z');
        }
        return formatted.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0217  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0219  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0236  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x023c  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005f A[Catch: IllegalArgumentException -> 0x0052, IndexOutOfBoundsException -> 0x0054, TryCatch #6 {IllegalArgumentException -> 0x0052, IndexOutOfBoundsException -> 0x0054, blocks: (B:12:0x003a, B:14:0x0040, B:22:0x005f, B:24:0x0070, B:25:0x0072, B:27:0x007f, B:29:0x0084, B:31:0x008a, B:36:0x0096, B:42:0x00a9, B:44:0x00b1, B:57:0x00e2), top: B:112:0x003a }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00da A[Catch: IllegalArgumentException | IndexOutOfBoundsException -> 0x020f, IndexOutOfBoundsException -> 0x0211, TRY_LEAVE, TryCatch #5 {IllegalArgumentException | IndexOutOfBoundsException -> 0x020f, blocks: (B:3:0x0005, B:5:0x0017, B:6:0x0019, B:8:0x0025, B:9:0x0027, B:52:0x00d4, B:54:0x00da, B:64:0x00f7), top: B:113:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01ff A[Catch: IllegalArgumentException -> 0x020b, IndexOutOfBoundsException -> 0x020d, TryCatch #4 {IllegalArgumentException -> 0x020b, IndexOutOfBoundsException -> 0x020d, blocks: (B:89:0x01cc, B:69:0x011a, B:73:0x013a, B:75:0x0148, B:87:0x01c7, B:78:0x0158, B:80:0x017a, B:83:0x018f, B:84:0x01b9, B:72:0x0127, B:66:0x0100, B:67:0x0117, B:91:0x01ff, B:92:0x020a), top: B:115:0x00d8 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.Date parse(java.lang.String r22, java.text.ParsePosition r23) throws java.text.ParseException {
        /*
            Method dump skipped, instructions count: 656
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.util.ISO8601Utils.parse(java.lang.String, java.text.ParsePosition):java.util.Date");
    }

    private static boolean checkOffset(String value, int offset, char expected) {
        return offset < value.length() && value.charAt(offset) == expected;
    }

    private static int parseInt(String value, int beginIndex, int endIndex) throws NumberFormatException {
        if (beginIndex < 0 || endIndex > value.length() || beginIndex > endIndex) {
            throw new NumberFormatException(value);
        }
        int digit = beginIndex;
        int result = 0;
        if (digit < endIndex) {
            int i = digit + 1;
            int digit2 = Character.digit(value.charAt(digit), 10);
            if (digit2 < 0) {
                throw new NumberFormatException("Invalid number: " + value.substring(beginIndex, endIndex));
            }
            result = -digit2;
            digit = i;
        }
        while (digit < endIndex) {
            int i2 = digit + 1;
            int digit3 = Character.digit(value.charAt(digit), 10);
            if (digit3 < 0) {
                throw new NumberFormatException("Invalid number: " + value.substring(beginIndex, endIndex));
            }
            result = (result * 10) - digit3;
            digit = i2;
        }
        return -result;
    }

    private static void padInt(StringBuilder buffer, int value, int length) {
        String strValue = Integer.toString(value);
        for (int i = length - strValue.length(); i > 0; i--) {
            buffer.append('0');
        }
        buffer.append(strValue);
    }

    private static int indexOfNonDigit(String string, int offset) {
        for (int i = offset; i < string.length(); i++) {
            char c = string.charAt(i);
            if (c < '0' || c > '9') {
                return i;
            }
        }
        int i2 = string.length();
        return i2;
    }
}
