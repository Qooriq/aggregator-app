package com.java.akdev.ridesservice.util;

import com.java.akdev.ridesservice.enumeration.SortField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
@Builder
@AllArgsConstructor
public class SortType {

    private Sort.Direction order;
    private SortField sortField;
}
