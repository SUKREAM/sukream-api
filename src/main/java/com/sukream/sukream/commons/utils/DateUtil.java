package com.sukream.sukream.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;


public class DateUtil {
    private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    public static final String ISO_YMD = "yyyy-MM-dd";
    public static final String YYYYMMDD = "yyyyMMdd";
    public static final String YYYYMMDDHH = "yyyyMMddHH";
    public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
    public static final String YYYYMMDDHHMISS = "yyyyMMddHHmmss";
    public static final String YYYYMM = "yyyyMM";
    public static final String MMDD = "MM/dd";
    public static final String MMDDYYYY = "MM/dd/yyyy";

    private static DateTimeFormatter ISO_YMD_DF = DateTimeFormatter.ofPattern(ISO_YMD);
    private static DateTimeFormatter YYYYMMDD_DF = DateTimeFormatter.ofPattern(YYYYMMDD);
    private static DateTimeFormatter YYYYMMDDHHMM_DF = DateTimeFormatter.ofPattern(YYYYMMDDHHMM);
    private static DateTimeFormatter YYYYMMDDHHMISS_DF = DateTimeFormatter.ofPattern(YYYYMMDDHHMISS);
    private static DateTimeFormatter YYYYMM_DF = DateTimeFormatter.ofPattern(YYYYMM);
    private static DateTimeFormatter MMDD_DF = DateTimeFormatter.ofPattern(MMDD);

    public static final DateTimeFormatter DEFAULT_PATTERN_DF = YYYYMMDD_DF;

    public static final long ONE_DAY_IN_MILLISECONDS = 1000 * 60 * 60 * 24;
    public static final long ONE_HOUR_IN_MILLISECONDS = 1000 * 60 * 60;
    public static final long ONE_MONTH_IN_MILLISECONDS = 1000L * 60 * 60 * 24 * 30;

    public static final ZoneId ZERO_ZONE = ZoneOffset.ofHours(0);
    public static final ZoneId KST_ZONE = ZoneOffset.ofHours(9);
    public static final ZoneId EST_ZONE = ZoneId.of("America/New_York");

    public static final Long DEFAULT_SSE_TIMEOUT = 120L * 1000 * 60;

    /**
     * unix time ==> yyyyMMdd (String)
     */
    public static String epochToYmd(long txTime) {
        return epochToYmd(txTime, KST_ZONE);
    }

    /**
     * unix time ==> yyyyMM (String)
     */
    public static String epochToYm(long txTime) {
        return epochToYm(txTime, KST_ZONE);
    }

    /**
     * unix time ==> MMdd (String)
     */
    public static String epochToMmDd(long txTime) {
        return epochToMmDd(txTime, KST_ZONE);
    }

    /**
     * unix time ==> yyyyMMddHH (String)
     */
    public static String epochToYmdHKst(long txTime) {
        return epochToYmdH(txTime, KST_ZONE);
    }

    /**
     * unix time ==> yyyy-MM-dd (String)
     */
    public static String epochToIsoYmd(long txTime) {
        return epochToIsoYmd(txTime, KST_ZONE);
    }

    /**
     * unix time ==> yyyyMMdd (int)
     */
    public static int epochToYmdInt(long txTime) {
        return epochToYmdInt(txTime, KST_ZONE);
    }

    /**
     * 시작 시간과 종료 시간 사이의 일수
     */
    public static long getDaysBetweenStAndEt(long st, long et) {
        return getDaysBetweenStAndEt(st, et, KST_ZONE);
    }

    /**
     * unix time 을 ZonedDateTime 으로 변경 후
     * days 를 더하고 해당 ZonedDateTime 을 unix time 으로 반환
     */
    public static long addDayTime(long time, long days) {
        return addDayTime(time, days, KST_ZONE);
    }

    /**
     * unix time 을 ZonedDateTime 으로 변경 후
     * months 를 더하고 해당 ZonedDateTime 을 unix time 으로 반환
     */
    public static long addMonth(long time, long months) {
        return addMonth(time, months, KST_ZONE);
    }

    /**
     * 특정 지역의 시간을 unix time 으로 변환
     */
    public static long dateToEpochMilli(LocalDateTime date) {
        return dateToEpochMilli(date, KST_ZONE);
    }

    /**
     * unix time 을 특정 지역의 시간으로 변환
     */
    public static LocalDateTime epochToDate(long time) {
        return epochToDate(time, KST_ZONE);
    }

    /**
     * yyyyMMdd ==> yyyy-MM-dd
     */
    public static String formatBiYmd(int ymd) {
        String str = Integer.toString(ymd);
        LocalDateTime ldt = LocalDateTime.parse(str, YYYYMMDD_DF);
        return dateToString(ldt);
    }

    /**
     * yyyy-MM-dd ==> yyyyMMdd
     */
    public static int biYmdToInt(String biYmd) {
        int ymd = 0;
        try {
            if (StringUtil.isEmpty(biYmd)) {
                return ymd;
            }
            String rt = biYmd.replaceAll("-", "");
            ymd = Integer.parseInt(rt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ymd;
    }

    public static String getCurrentYmd() {
        return dateToString(LocalDateTime.now());
    }

    public static LocalDateTime stringToDate(String strYmd) {
        return LocalDate.parse(strYmd, DEFAULT_PATTERN_DF).atStartOfDay();
    }

    public static LocalDateTime stringToDate(String strDate, String pattern) {
        return LocalDateTime.parse(strDate, DateTimeFormatter.ofPattern(pattern));
    }

    public static String dateToString(LocalDateTime txDate) {
        return txDate == null ? "" : txDate.format(DEFAULT_PATTERN_DF);
    }

    public static String dateToMonthlyString(LocalDateTime txDate) {
        return txDate == null ? "" : txDate.format(YYYYMM_DF);
    }

    public static String dateToString(LocalDateTime txDate, String pattern) {
        return txDate == null ? "" : txDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static int dateToYmdInt(LocalDateTime date) {
        return date == null ? 0 : Integer.parseInt(date.format(YYYYMMDD_DF));
    }

    public static long getCurrentEpochMilli() {
        // instread of System.currentTimeMillis()
        // This clock is based on the best available system clock. This may use System.currentTimeMillis(), or a higher resolution clock if one is available.
        return Instant.now().toEpochMilli();
    }

    public static String addDaysToStringDate(String strDate, int addValue) {
        try {
            LocalDateTime addedDate = stringToDate(strDate).plusDays(addValue);
            return dateToString(addedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static int addDaysToIntDate(String strDate, int addValue) {
        try {
            LocalDateTime addedDate = stringToDate(strDate).plusDays(addValue);
            return dateToYmdInt(addedDate);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static LocalDateTime addMillisToDate(LocalDateTime date, long addValue) {
        return date.plus(addValue, ChronoUnit.MILLIS);
    }

    public static String convertDateTimeToYYYYMMStr(LocalDateTime txTime) {
        return txTime.format(YYYYMM_DF);
    }

    public static String dateToYYYYMMDDStr(LocalDateTime date) {
        return date == null ? "" : date.format(YYYYMMDD_DF);
    }

    public static long dateToYYYYMMDDHHMMLong(LocalDateTime date) {
        return date == null ? 0L : Long.parseLong(date.format(YYYYMMDDHHMM_DF));
    }

    public static long stringDateToLong(String date) {
        return dateToEpochMilli(stringToDate(date));
    }

    ///////////////// private //////////////////////////////

    private static String epochToYmd(long txTime, ZoneId zoneId) {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(txTime), zoneId);
        return dateToString(ldt);
    }

    private static String epochToYm(long txTime, ZoneId zoneId) {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(txTime), zoneId);
        return dateToString(ldt, YYYYMM);
    }

    private static String epochToMmDd(long txTime, ZoneId zoneId) {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(txTime), zoneId);
        return dateToString(ldt, MMDD);
    }

    private static String epochToIsoYmd(long txTime, ZoneId zoneId) {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(txTime), zoneId);
        return dateToString(ldt, ISO_YMD);
    }

    private static String epochToYmdH(long txTime, ZoneId zoneId) {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(txTime), zoneId);
        return dateToString(ldt, YYYYMMDDHH);
    }

    private static String epochToMmDdY(long txTime, ZoneId zoneId) {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(txTime), zoneId);
        return dateToString(ldt, MMDDYYYY);
    }

    private static int epochToYmdInt(long txTime, ZoneId zoneId) {
        LocalDateTime ldt = LocalDateTime.ofInstant(Instant.ofEpochMilli(txTime), zoneId);
        return Integer.parseInt(dateToString(ldt));
    }

    private static long getDaysBetweenStAndEt(long st, long et, ZoneId zoneId) {
        LocalDateTime start = LocalDateTime.ofInstant(Instant.ofEpochMilli(st), zoneId);
        LocalDateTime end = LocalDateTime.ofInstant(Instant.ofEpochMilli(et), zoneId);
        return Duration.between(start, end).toDays();
    }

    private static long addDayTime(long txTime, long days, ZoneId zoneId) {
        ZonedDateTime zdt = Instant.ofEpochMilli(txTime).atZone(zoneId);
        ZonedDateTime plusDays = zdt.plusDays(days);
        return plusDays.toInstant().toEpochMilli();
    }

    private static long addMonth(long txTime, long months, ZoneId zoneId) {
        ZonedDateTime zdt = Instant.ofEpochMilli(txTime).atZone(zoneId);
        ZonedDateTime plusMonths = zdt.plusMonths(months);
        return plusMonths.toInstant().toEpochMilli();
    }

    private static long dateToEpochMilli(LocalDateTime date, ZoneId zoneId) {
        return date == null ? 0 : date.atZone(zoneId).toInstant().toEpochMilli();
    }

    private static LocalDateTime epochToDate(long txTime, ZoneId zoneId) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(txTime), zoneId);
    }
}
