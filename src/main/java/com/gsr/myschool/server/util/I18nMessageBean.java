package com.gsr.myschool.server.util;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

@Component("i18nMessageBean")
public class I18nMessageBean implements MessageSourceAware {
	private static MessageSource messageSource;

	public void setMessageSource(MessageSource msgsource) {
		messageSource = msgsource;
	}
	
	public static MessageSource getMessageSource() {
		return messageSource;
	}
}
