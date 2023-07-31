package com.galapea.techblog.springboot.timeseries.model.ajaxpaging;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Order {

    private Integer column;
    private Direction dir;

}
