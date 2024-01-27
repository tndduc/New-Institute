package com.tnduck.newinstitute.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;
/**
 * @author ductn
 * @project The new institute
 * @created 26/01/2024 - 10:31 PM
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MessageSourceService {
    private final MessageSource messageSource;

    /**
     * Get message from message source by key.
     *
     * @param code   String
     * @param params Object[]
     * @param locale Locale
     * @return message String
     */
    public String get(final String code, final Object[] params, final Locale locale) {
        try {
            return messageSource.getMessage(code, params, locale);
        } catch (NoSuchMessageException e) {
            log.warn("Translation message not found ({}): {}", locale, code);
            return code;
        }
    }

    /**
     * Get message from message source by key.
     *
     * @param code   String
     * @param params Object[]
     * @return message String
     */
    public String get(final String code, final Object[] params) {
        return get(code, params, LocaleContextHolder.getLocale());
    }

    /**
     * Get message from message source by key.
     *
     * @param code   String
     * @param locale Locale
     * @return message String
     */
    public String get(final String code, final Locale locale) {
        return get(code, new Object[0], locale);
    }

    /**
     * Get message from message source by key.
     *
     * @param code String
     * @return message String
     */
    public String get(final String code) {
        return get(code, new Object[0], LocaleContextHolder.getLocale());
    }
}
