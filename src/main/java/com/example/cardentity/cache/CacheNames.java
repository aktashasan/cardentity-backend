package com.example.cardentity.cache;

/**
 * Central cache name registry to prevent typos and ease cache maintenance.
 */
public final class CacheNames {

    private CacheNames() {
        // utility class
    }

    public static final String PERSON_BY_ID = "personById";
    public static final String PERSON_BY_CODE = "personByCode";
    public static final String PERSON_LIST = "personList";

    public static final String USER_BY_ID = "userById";
    public static final String USER_BY_USERNAME = "userByUsername";
    public static final String USER_LIST = "userList";

    public static final String RECORD_BY_ID = "recordById";
    public static final String RECORD_BY_OPERATION = "recordByOperation";
}
