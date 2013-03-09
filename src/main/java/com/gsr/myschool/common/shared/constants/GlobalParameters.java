package com.gsr.myschool.common.shared.constants;

import java.util.Arrays;
import java.util.List;

public class GlobalParameters {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final Integer REFRESH_PERIODE = 60000;
    public static final String DEFAULT_NATIONALITY = "MAROCAINE";

    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    public static final String ROLE_OPERATOR = "ROLE_OPERATOR";

    public static final Long SECTION_FRANCAISE = 10l;
    public static final Long PETITE_SECTION = 1l;
    public static final Long MOYENNE_SECTION = 2l;
    public static final Long GRANDE_SECTION = 3l;
    public static final Long BAC_SGT_ECO = 46l;
    public static final Long BAC_AUTRES = 47l;
    public static final Long BAC_ECO = 7l;

    public static final List<Long> NE_toute_petite_section_ids = Arrays.asList( new Long[]{49L,50L});

    public static final String APP_STATUS_OPENED = "open";
    public static final String APP_STATUS_CLOSED = "closed";
}
