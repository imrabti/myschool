package com.gsr.myschool.server.util;

import org.springframework.binding.message.MessageBuilder;
import org.springframework.context.MessageSource;

import javax.validation.MessageInterpolator;
import java.util.Locale;

public class SpringMessageInterpolator implements MessageInterpolator {
	public String interpolate(String messageTemplate, Context context) {
		MessageSource messageSource = I18nMessageBean.getMessageSource();
		String[] params = (String[])context.getConstraintDescriptor().getAttributes().get("params");
		
		MessageBuilder builder = new MessageBuilder().code(messageTemplate);
		if(params != null) {
			for(String param : params) {
				builder = builder.arg(param);
			}
        }

		return builder.build() .resolveMessage(messageSource, Locale.FRANCE).getText();
	}

	public String interpolate(String messageTemplate, Context context, Locale locale) {
		MessageSource messageSource = I18nMessageBean.getMessageSource();
		String[] params = (String[])context.getConstraintDescriptor().getAttributes().get("params");
		
		MessageBuilder builder = new MessageBuilder().code(messageTemplate);
		if(params != null) {
			builder = builder.args(params);
        }
		
		return builder.build().resolveMessage(messageSource, locale).getText();
	}
}
