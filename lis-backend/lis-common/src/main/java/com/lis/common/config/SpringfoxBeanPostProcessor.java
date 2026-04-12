package com.lis.common.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import springfox.documentation.spring.web.plugins.WebFluxRequestHandlerProvider;
import springfox.documentation.spring.web.plugins.WebMvcRequestHandlerProvider;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class SpringfoxBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof WebMvcRequestHandlerProvider) {
            customizeWebMvcRequestHandlerProvider((WebMvcRequestHandlerProvider) bean);
        }
        if (bean instanceof WebFluxRequestHandlerProvider) {
            customizeWebFluxRequestHandlerProvider((WebFluxRequestHandlerProvider) bean);
        }
        return bean;
    }

    private void customizeWebMvcRequestHandlerProvider(WebMvcRequestHandlerProvider provider) {
        try {
            Field field = ReflectionUtils.findField(WebMvcRequestHandlerProvider.class, "handlerMappings");
            if (field != null) {
                field.setAccessible(true);
                @SuppressWarnings("unchecked")
                List<RequestMappingInfoHandlerMapping> mappings = (List<RequestMappingInfoHandlerMapping>) field.get(provider);
                if (mappings != null) {
                    List<RequestMappingInfoHandlerMapping> filtered = mappings.stream()
                            .filter(mapping -> mapping.getPatternParser() == null)
                            .collect(Collectors.toList());
                    field.set(provider, filtered);
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to customize WebMvcRequestHandlerProvider", e);
        }
    }

    private void customizeWebFluxRequestHandlerProvider(WebFluxRequestHandlerProvider provider) {
        try {
            Field field = ReflectionUtils.findField(WebFluxRequestHandlerProvider.class, "handlerMappings");
            if (field != null) {
                field.setAccessible(true);
                Object mappings = field.get(provider);
                if (mappings instanceof List) {
                    @SuppressWarnings("unchecked")
                    List<Object> list = (List<Object>) mappings;
                    List<Object> filtered = list.stream()
                            .filter(mapping -> {
                                try {
                                    Field parserField = ReflectionUtils.findField(mapping.getClass(), "patternParser");
                                    if (parserField != null) {
                                        parserField.setAccessible(true);
                                        return parserField.get(mapping) == null;
                                    }
                                } catch (Exception ignored) {
                                }
                                return true;
                            })
                            .collect(Collectors.toList());
                    field.set(provider, filtered);
                }
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to customize WebFluxRequestHandlerProvider", e);
        }
    }
}
