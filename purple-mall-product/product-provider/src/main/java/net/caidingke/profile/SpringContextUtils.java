package net.caidingke.profile;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author bowen
 */
@Component
public class SpringContextUtils implements BeanFactoryPostProcessor {

    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(
            ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        SpringContextUtils.beanFactory = configurableListableBeanFactory;
    }

    public static <T> T getBean(String name) throws BeansException {
        return (T) beanFactory.getBean(name);
    }

    public static <T> T getBean(Class<T> clz) throws BeansException {
        return beanFactory.getBean(clz);
    }

    public static <T> List<T> getBeansOfType(Class<T> type) {
        return new ArrayList<>(beanFactory.getBeansOfType(type).values());
    }

    public static List<Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
        Map<String, Object> beansWithAnnotation = beanFactory
                .getBeansWithAnnotation(annotationType);
        return new ArrayList<>(beansWithAnnotation.values());
    }
}
