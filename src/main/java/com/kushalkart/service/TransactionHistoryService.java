package com.kushalkart.service;

import com.kushalkart.dto.TransactionHistoryResponse;
import com.kushalkart.entity.MasterTransactionHistory;
import com.kushalkart.repository.MasterTransactionHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class TransactionHistoryService {

    @Autowired
    private MasterTransactionHistoryRepository transactionHistoryRepository;

    public Page<TransactionHistoryResponse> getAllTransactionHistory(
            Long userId,
            Long bookingId,
            Long balanceTypeId,
            Long transactionTypeId,
            Long paymentTypeId,
            Long transactionStatusId,
            BigDecimal amountMin,
            BigDecimal amountMax,
            LocalDateTime from,
            LocalDateTime to,
            String reference,
            String remarks,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {
        Pageable pageable = PageRequest.of(
                Math.max(page, 0),
                Math.max(size, 1),
                Sort.by("asc".equalsIgnoreCase(sortDir) ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy)
        );

        return transactionHistoryRepository.findTransactionsWithDetails(
                userId, bookingId, balanceTypeId, transactionTypeId,
                paymentTypeId, transactionStatusId, amountMin, amountMax,
                from, to, reference, remarks, pageable
        );
    }
}
