package com.ynz.demo.accounts.balance.presentation;

import com.ynz.demo.accounts.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BalanceController {

    private TransactionService balanceService;

    @Autowired
    public BalanceController(TransactionService balanceService) {
        this.balanceService = balanceService;
    }


}
