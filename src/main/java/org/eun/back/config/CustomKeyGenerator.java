package org.eun.back.config;

import org.springframework.cache.interceptor.KeyGenerator;

public class CustomKeyGenerator implements KeyGenerator {

    private static final String CACHE_KEY_PREFIX = "eun_reports:"; // Change this to your desired prefix

    @Override
    public Object generate(Object target, java.lang.reflect.Method method, Object... params) {
        StringBuilder key = new StringBuilder();
        key.append(CACHE_KEY_PREFIX);
        key.append(target.getClass().getName());
        key.append(".");
        key.append(method.getName());
        for (Object param : params) {
            key.append(param.toString());
        }
        return key.toString();
    }
}
