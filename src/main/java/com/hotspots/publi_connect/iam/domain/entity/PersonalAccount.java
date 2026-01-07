package com.hotspots.publi_connect.iam.domain.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.Nullable;

import com.hotspots.publi_connect.iam.vo.AgeVo;
import com.hotspots.publi_connect.iam.vo.AuthProviderVo;
import com.hotspots.publi_connect.iam.vo.GenderVo;
import com.hotspots.publi_connect.iam.vo.UUIDVo;
import com.hotspots.publi_connect.iam.vo.ZipCodeVo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Table("personal_account")
/* Just for reading on DB */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PersonalAccount implements Persistable<UUID> {

	@Id
	private UUID accountId;

	private String name;
	private int age;
	private String gender;
	private String zipCode;
	private String authProvider;
	private boolean isActive;

	@Transient
	private boolean isNew;

	public PersonalAccount(UUIDVo accountIdVo, String name, AgeVo age, GenderVo gender, ZipCodeVo zipCode, AuthProviderVo authProvider, boolean isActive) {
		this.accountId = UUID.fromString(accountIdVo.id());
		this.name = name;
		this.age = age.age();
		this.gender = gender.gender();
		this.zipCode = zipCode.zipCode();
		this.authProvider = authProvider.authProvider();
		this.isActive = isActive;
		this.isNew = true;
	}

	@Override
	@Nullable
	public UUID getId() {
		return this.accountId;
	}

	@Override
	public boolean isNew() {
		return this.isNew;
	}

}
