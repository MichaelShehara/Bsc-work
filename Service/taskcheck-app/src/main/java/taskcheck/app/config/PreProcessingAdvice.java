/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.config;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import taskcheck.app.dto.AuthUser;
import taskcheck.data.entity.BaseEntity;
import taskcheck.data.entity.User;

/**
 * The Class RestPreProcessingAdvice.
 */
@RestControllerAdvice
public class PreProcessingAdvice implements RequestBodyAdvice {

	/**
	 * Supports.
	 *
	 * @param methodParameter the method parameter
	 * @param targetType      the target type
	 * @param converterType   the converter type
	 * @return true, if successful
	 */
	@Override
	public boolean supports(final MethodParameter methodParameter, final Type targetType,
			final Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
	}

	/**
	 * Handle empty body.
	 *
	 * @param body          the body
	 * @param inputMessage  the input message
	 * @param parameter     the parameter
	 * @param targetType    the target type
	 * @param converterType the converter type
	 * @return the object
	 */
	@Override
	public Object handleEmptyBody(final Object body, final HttpInputMessage inputMessage,
			final MethodParameter parameter, final Type targetType,
			final Class<? extends HttpMessageConverter<?>> converterType) {

		return body;
	}

	/**
	 * Before body read.
	 *
	 * @param inputMessage  the input message
	 * @param parameter     the parameter
	 * @param targetType    the target type
	 * @param converterType the converter type
	 * @return the http input message
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@Override
	public HttpInputMessage beforeBodyRead(final HttpInputMessage inputMessage, final MethodParameter parameter,
			final Type targetType, final Class<? extends HttpMessageConverter<?>> converterType) throws IOException {

		return inputMessage;
	}

	/**
	 * After body read.
	 *
	 * @param body          the body
	 * @param inputMessage  the input message
	 * @param parameter     the parameter
	 * @param targetType    the target type
	 * @param converterType the converter type
	 * @return the object
	 */
	@Override
	public Object afterBodyRead(final Object body, final HttpInputMessage inputMessage, final MethodParameter parameter,
			final Type targetType, final Class<? extends HttpMessageConverter<?>> converterType) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (auth != null) {

			Object principal = auth.getPrincipal();

			if (principal != null && principal instanceof AuthUser) {

				AuthUser user = (AuthUser) auth.getPrincipal();

				String method = null;

				if (parameter != null && parameter.getMethod() != null) {
					method = parameter.getMethod().getName();
				}

				if (body instanceof User) {

					User entity = (User) body;

					if (method.contains("create")) {
						entity.setCreatedAt(new Date());
						entity.setCreatedBy(user.getUser().getUsername());
					}

					entity.setUpdatedAt(new Date());
					entity.setUpdatedBy(user.getUser().getUsername());

				} else if (body instanceof BaseEntity) {

					BaseEntity entity = (BaseEntity) body;

					if (method.contains("create")) {
						entity.setCreatedAt(new Date());
						entity.setCreatedBy(user.getUser());
					}

					entity.setUpdatedAt(new Date());
					entity.setUpdatedBy(user.getUser());
				}
			}
		}

		return body;
	}
}