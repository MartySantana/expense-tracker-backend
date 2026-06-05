package com.kata.expenses.service;

import com.kata.expenses.model.Expense;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    private final List<Expense> expenses = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Expense addExpense(Expense expense) {
        expense.setId(idGenerator.getAndIncrement());
        expenses.add(expense);
        return expense;
    }

    public BigDecimal getTotalExpenses() {
        return expenses.stream()
                .map(Expense::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public List<Expense> getFilteredExpenses(
            String category,
            LocalDate startDate,
            LocalDate endDate
    ) {
        return expenses.stream()
                .filter(expense ->
                        category == null
                                || category.isBlank()
                                || expense.getCategory().equalsIgnoreCase(category)
                )
                .filter(expense ->
                        startDate == null
                                || !expense.getDate().isBefore(startDate)
                )
                .filter(expense ->
                        endDate == null
                                || !expense.getDate().isAfter(endDate)
                )
                .toList();
    }

    public Map<String, BigDecimal> getTotalsByCategory() {
        return expenses.stream()
                .collect(Collectors.groupingBy(
                        Expense::getCategory,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                Expense::getAmount,
                                BigDecimal::add
                        )
                ));
    }

}