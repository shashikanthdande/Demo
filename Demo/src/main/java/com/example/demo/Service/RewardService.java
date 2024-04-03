package com.example.demo.Service;
import com.example.demo.Entity.Transaction;
import com.example.demo.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RewardService {

    @Autowired
    private TransactionRepository transactionRepository;

    public Map<String, Object> calculateRewards(Long customerId, int year, int month) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        List<Transaction> transactions = transactionRepository.findByCustomerIdAndTransactionDateBetween(customerId, start, end);

        int monthlyPoints = transactions.stream()
                .mapToInt(this::calculatePointsForTransaction)
                .sum();

        Map<String, Object> result = new HashMap<>();
        result.put("customerId", customerId);
        result.put("year", year);
        result.put("month", month);
        result.put("points", monthlyPoints);
        return result;
    }

    private int calculatePointsForTransaction(Transaction transaction) {
        int points = 0;
        double amount = transaction.getAmount();
        if (amount > 100) {
            points += (amount - 100) * 2 + 50;
        } else if (amount > 50) {
            points += (amount - 50);
        }
        return points;
    }
}
