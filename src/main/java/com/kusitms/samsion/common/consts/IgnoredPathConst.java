package com.kusitms.samsion.common.consts;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IgnoredPathConst {

	public static final String[] IGNORED_PATHS = {
			"/oauth2/**",
			"/info",
			"/reissue",
			"/album",
			"/funeral",
			"/docs/index.html",
			"/favicon.ico"};
}
