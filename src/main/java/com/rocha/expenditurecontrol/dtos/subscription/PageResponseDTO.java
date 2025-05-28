package com.rocha.expenditurecontrol.dtos.subscription;

import java.math.BigDecimal;
import java.util.List;

public record PageResponseDTO<T>(
        List<T> content,
        int totalPages,
        long totalElements,
        int pageNumber,
        int pageSize,
        BigDecimal totalPrice) {
}
