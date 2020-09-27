package br.com.dbc.util;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StringUtils {

	private static final String POINT = ".";
	private static final String REGEX_COMMA = ",";
	private static final String REGEX_TRACE = "-";
	private static final String EMPTY = "";

	public static String replaceByEmpty(String value) {
		return Optional.ofNullable(value).orElse(EMPTY).replace(REGEX_TRACE, EMPTY);
	}

	public static String replaceByPoint(String value) {
		return Optional.ofNullable(value).orElse(EMPTY).replace(REGEX_COMMA, POINT);
	}

}
