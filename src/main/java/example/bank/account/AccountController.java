/*
 * Copyright 2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package example.bank.account;

import example.bank.account.transfer.TransferRequest;
import example.bank.account.transfer.TransferRequestRepository;
import example.bank.user.User;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @author Rob Winch
 */
@Controller
@RequestMapping("/account")
public class AccountController {
    final AccountRepository accounts;

    final TransferRequestRepository transfers;

    public AccountController(AccountRepository accounts, TransferRequestRepository transfers) {
        this.accounts = accounts;
        this.transfers = transfers;
    }

    @GetMapping
    String account(@AuthenticationPrincipal User currentUser, Map<String, Object> model) {
        Long currentUserId = currentUser.getId();
        Account account = this.accounts.findAccountByOwnerId(currentUserId);
        Iterable<TransferRequest> transfers = this.transfers.findByTransferFromOwnerId(currentUserId);
        model.put("account", account);
        model.put("transfers", transfers);
        model.put("currentUser", currentUser);
        return "account/index";
    }


}
