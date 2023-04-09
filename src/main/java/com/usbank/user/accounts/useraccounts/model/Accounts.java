package com.usbank.user.accounts.useraccounts.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;


@Entity
@ToString
@Setter
@Getter
@Table(name = "user_accounts",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "accountId")
        })
public class Accounts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private long userId;

    @NotBlank
    @Size(max = 100)
    private String accountId;

    @NotNull
    private LocalDate openingDate;

    @NotBlank
    @Size(max = 20)
    private String accountType; // joint account or single account

    @NotNull
    private boolean isMinor;

    @NotBlank
    @Size(max = 20)
    private String branchName;

    @NotBlank
    @Size(max = 20)
    private String branchLocation;

    @NotBlank
    @Size(max = 20)
    private String branchIFSC;
}
