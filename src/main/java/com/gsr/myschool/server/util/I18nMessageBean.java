package com.gsr.myschool.server.util;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component("i18nMessageBean")
public class I18nMessageBean implements MessageSourceAware {
	private static MessageSource messageSource;

	public void setMessageSource(MessageSource msgsource) {
		messageSource = msgsource;
	}

    public String getMessage(String messageCode) {
        MessageBuilder builder = new MessageBuilder().code(messageCode);
        return builder.build().resolveMessage(messageSource, Locale.FRANCE).getText();
    }
	
	public static MessageSource getMessageSource() {
		return messageSource;
	}
}
