package com.hotspots.publi_connect.iam.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.hotspots.publi_connect.iam.api.dto.personal_account.CreatePersonalAccountReq;
import com.hotspots.publi_connect.iam.api.dto.personal_account.CreatePersonalAccountRes;
import com.hotspots.publi_connect.iam.domain.entity.PersonalAccount;
import com.hotspots.publi_connect.iam.repository.PersonalAccountRepository;

import jakarta.validation.Valid;
import lombok.Data;
import reactor.core.publisher.Mono;

@Service
@Validated
@Data
public class PersonalAccountService {
	private final PersonalAccountRepository repo;

	public Mono<CreatePersonalAccountRes> createPersonalAccount(@Valid CreatePersonalAccountReq request) {
		PersonalAccount account = new PersonalAccount(request.uuidVo(), request.nameVo(), request.ageVo(), request.genderVo(), request.zipCodeVo(), request.authProviderVo(), request.isActiveVo());
		return repo.save(account).map(userSaved -> new CreatePersonalAccountRes(userSaved.getAccountId()));
	}

}
