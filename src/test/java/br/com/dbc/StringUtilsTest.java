package br.com.dbc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.com.dbc.util.StringUtils;

class StringUtilsTest {

	@Test
	void testReplaceStringWithTraceByEmpty() {

		String value = "123454-8";
		String result = StringUtils.replaceByEmpty(value);
		
		assertEquals("1234548", result);
	}

	@Test
	void testReplaceStringWithCommaByPoint() {

		String value = "123,454";
		String result = StringUtils.replaceByPoint(value);
		
		assertEquals("123.454", result);
	}
	
	@Test
	void testReplaceStringNullByEmpty() {

		String value = null;
		String result = StringUtils.replaceByPoint(value);
		
		assertEquals("", result);
	}
}
