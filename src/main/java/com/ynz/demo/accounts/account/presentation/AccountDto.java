package com.ynz.demo.accounts.account.presentation;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/** Account may have other attributes. */
public class AccountDto {

  @NotBlank(message = "Name must not be empty")
  @Valid
  private String name;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
