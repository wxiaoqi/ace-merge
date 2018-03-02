package com.github.wxiaoqi.merge.configuration;

import com.github.wxiaoqi.merge.core.BeanFactoryUtils;
import com.github.wxiaoqi.merge.core.MergeCore;
import com.github.wxiaoqi.merge.facade.DefaultMergeResultParser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ace
 * @create 2018/2/3.
 */
@Configuration
@ComponentScan("com.github.wxiaoqi.merge.aspect")
@ConditionalOnProperty(name = "merge.enabled", matchIfMissing = true)
public class MergeAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean
    public MergeProperties mergeProperties() {
        return new MergeProperties();
    }

    @Bean
    @ConditionalOnMissingBean
    public BeanFactoryUtils beanFactoryUtils() {
        return new BeanFactoryUtils();
    }

    @Bean
    @ConditionalOnMissingBean
    public MergeCore mergeCore() {
        return new MergeCore(mergeProperties());
    }

    @Bean
    public DefaultMergeResultParser defaultMergeResultParser() {
        return new DefaultMergeResultParser();
    }
}
