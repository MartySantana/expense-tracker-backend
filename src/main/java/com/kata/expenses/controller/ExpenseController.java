package com.kata.expenses.controller;

import com.kata.expenses.model.Expense;
import com.kata.expenses.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping
    public Expense addExpense(@Valid @RequestBody Expense expense) {
        return expenseService.addExpense(expense);
    }

    @GetMapping
    public List<Expense> getExpenses(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate
    ) {
        return expenseService.getFilteredExpenses(category, startDate, endDate);
    }

    @GetMapping("/totals")
    public BigDecimal getTotalExpenses() {
        return expenseService.getTotalExpenses();
    }

    @GetMapping("/totals-by-category")
    public Map<String, BigDecimal> getTotalsByCategory() {
        return expenseService.getTotalsByCategory();
    }
}