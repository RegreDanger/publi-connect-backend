package com.hotspots.publi_connect.iam.domain.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.hotspots.publi_connect.iam.vo.AgeVo;
import com.hotspots.publi_connect.iam.vo.AuthProviderVo;
import com.hotspots.publi_connect.iam.vo.EmailVo;
import com.hotspots.publi_connect.iam.vo.GenderVo;
import com.hotspots.publi_connect.iam.vo.PhoneNoVo;
import com.hotspots.publi_connect.iam.vo.ZipCodeVo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Getter
@Table("individual_user")
/* Just for reading on DB */
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class User {

	@Id
	private UUID userId;

	private String name;
	private int age;
	private String gender;
	private String email;
	private String phoneNo;
	private String zipCode;
	private String authProvider;
	private boolean isActive;

	public User(String name, AgeVo age, GenderVo gender, EmailVo email, PhoneNoVo phoneNo, ZipCodeVo zipCode, AuthProviderVo authProvider, boolean isActive) {
		this.name = name;
		this.age = age.age();
		this.gender = gender.gender();
		this.email = email.email();
		this.phoneNo = phoneNo.phoneNo();
		this.zipCode = zipCode.zipCode();
		this.authProvider = authProvider.authProvider();
		this.isActive = isActive;
	}

}
