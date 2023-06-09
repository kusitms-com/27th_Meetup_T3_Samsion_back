package com.kusitms.samsion.domain.user.domain.entity;

import com.kusitms.samsion.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

	@Id@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	private Long id;

	private String nickname;
	private String email;
	private String profileImageUrl;

	@Embedded
	private MyPet mypet;


	@Builder
	public User(String nickname, String email, String profileImageUrl) {
		this.nickname = nickname;
		this.email = email;
		this.profileImageUrl = profileImageUrl;
		this.mypet = MyPet.defaultValue();
	}

	public void updateMyPet(MyPet mypet){
		this.mypet.updateInfo(mypet);
	}

	public void updateUserInfo(String userNickname,String profileImageUrl){
		updateProfileImageUrl(profileImageUrl);
		updateNickname(userNickname);
	}

	public void updateNickname(String nickname){
		if(!Objects.equals(nickname, this.nickname)&& StringUtils.hasText(nickname))
			this.nickname = nickname;
	}

	private void updateProfileImageUrl(String profileImageUrl) {
		if(!Objects.equals(profileImageUrl, this.profileImageUrl)&&Objects.nonNull(profileImageUrl))
			this.profileImageUrl = profileImageUrl;
	}

}
