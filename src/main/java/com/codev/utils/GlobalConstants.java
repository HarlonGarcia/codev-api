package com.codev.utils;

import java.util.UUID;

public class GlobalConstants {
    public static final boolean ACTIVE = true;
    public static final boolean DEACTIVATE = false;

    public static final UUID USER_ROLE_ID = UUID.fromString("fc136e23-4bc9-468c-b4dd-cf99d0f1bd8b");
    public static final UUID ADMIN_ROLE_ID = UUID.fromString("28439d95-3df9-41ac-9718-dc4a906ef9a9");

    public static final long ACCESS_TOKEN_DURATION = 60 * 20;
    public static final long REFRESH_TOKEN_DURATION = 60 * 60 * 24 * 30;
}
