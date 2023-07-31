package com.galapea.techblog.springboot.timeseries;

import java.util.List;

public final class EventTrackerConstants {
    private EventTrackerConstants() {}

    public static final List<String> EVENT_TYPE_LIST = List.of("ADD_CART", "ADD_TO_WISHLIST",
            "IN_APP_AD_CLICK", "SEARCH", "LOGIN", "LAUNCH_APP", "START_TRIAL");


}
