package com.hotspots.publi_connect.iam.domain.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.hotspots.publi_connect.iam.vo.AccountTypeVo;
import com.hotspots.publi_connect.iam.vo.EmailVo;
import com.hotspots.publi_connect.iam.vo.PhoneNoVo;
import com.hotspots.publi_connect.iam.vo.StampsVo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Table("account")
/* Just for reading on DB */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Account {
    
    @Id
    private UUID accountId;
    
	private String email;
	private String phoneNo;
    private String accountType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Account(EmailVo emailVo, PhoneNoVo phoneNoVo, AccountTypeVo accountTypeVo, StampsVo stampsVo) {
        this.email = emailVo.email();
        this.phoneNo = phoneNoVo.phoneNo();
        this.accountType = accountTypeVo.accountType();
        this.createdAt = stampsVo.createdAt();
        this.updatedAt = stampsVo.updatedAt();
    }

}
