package com.kusitms.samsion.domain.user.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPet {

	private String petName;
	private String petImageUrl;
	private int petAge;
	private String petType;
	private String description;

	@Builder
	public MyPet(String petName, String petImageUrl, int petAge, String petType,
		String description) {
		this.petName = petName;
		this.petImageUrl = petImageUrl;
		this.petAge = petAge;
		this.petType = petType;
		this.description = description;
	}

	public static MyPet defaultValue() {
		return MyPet.builder()
			.petName("익명이")
			.petAge(0)
			.petType("익명이")
			.petImageUrl("")
			.description("익명이 설명이에요!")
			.build();
	}

	public void updateInfo(MyPet myPet) {
		updatePetName(myPet.getPetName());
		updatePetImageUrl(myPet.getPetImageUrl());
		updateDescription(myPet.getDescription());
		updatePetAge(myPet.getPetAge());
		updatePetType(myPet.getPetType());
	}

	private void updatePetName(String petName) {
		if (!Objects.equals(petName, this.petName)&& StringUtils.hasText(petName))
			this.petName = petName;
	}

	private void updatePetImageUrl(String petImageUrl) {
		if (!Objects.equals(petImageUrl, this.petImageUrl) && StringUtils.hasText(petImageUrl))
			this.petImageUrl = petImageUrl;
	}

	private void updateDescription(String description) {
		if (!Objects.equals(description, this.description)&&StringUtils.hasText(description))
			this.description = description;
	}

	private void updatePetAge(int petAge) {
		if (!Objects.equals(petAge, this.petAge)&&petAge>=0)
			this.petAge = petAge;
	}

	private void updatePetType(String petType) {
		if (!Objects.equals(petType, this.petType)&&StringUtils.hasText(petType))
			this.petType = petType;
	}

}
