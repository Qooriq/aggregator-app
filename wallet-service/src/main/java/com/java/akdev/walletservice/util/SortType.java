package com.java.akdev.walletservice.util;


import com.java.akdev.walletservice.enumeration.SortField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Builder
@Data
@AllArgsConstructor
public class SortType {

    private SortField sortField;
    private Sort.Direction order;
}
