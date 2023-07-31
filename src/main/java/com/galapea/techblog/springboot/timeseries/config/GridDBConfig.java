package com.galapea.techblog.springboot.timeseries.config;

import java.util.Properties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.galapea.techblog.springboot.timeseries.entity.Event;
import com.toshiba.mwcloud.gs.GSException;
import com.toshiba.mwcloud.gs.GridStore;
import com.toshiba.mwcloud.gs.GridStoreFactory;
import com.toshiba.mwcloud.gs.TimeSeries;

@Configuration
public class GridDBConfig {

    @Value("${GRIDDB_NOTIFICATION_MEMBER}")
    private String notificationMember;
    @Value("${GRIDDB_CLUSTER_NAME}")
    private String clusterName;
    @Value("${GRIDDB_USER}")
    private String user;
    @Value("${GRIDDB_PASSWORD}")
    private String password;

    @Bean
    public GridStore gridStore() throws GSException {
        // Acquiring a GridStore instance
        Properties properties = new Properties();
        properties.setProperty("notificationMember", notificationMember);
        properties.setProperty("clusterName", clusterName);
        properties.setProperty("user", user);
        properties.setProperty("password", password);
        GridStore store = GridStoreFactory.getInstance().getGridStore(properties);
        return store;
    }

    @Bean
    public TimeSeries<Event> eventsContainer(GridStore gridStore) throws GSException {
        return gridStore.putTimeSeries("events", Event.class);
    }

}

