package com.galapea.techblog.springboot.timeseries.model.ajaxpaging;

import java.util.List;
import org.springframework.core.annotation.MergedAnnotations.Search;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PagingRequest {
    private int start;
    private int length;
    private int draw;
    private List<Order> order;
    private List<Column> columns;
    private Search search;
}
