package com.example.home.demo3.mybatis;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.mybatis.spring.batch.builder.MyBatisBatchItemWriterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchMyBatisConfiguration {
    
    private static final Logger log = LoggerFactory.getLogger(BatchMyBatisConfiguration.class);

    @Bean
    public UserItemReader reader() {
        return new UserItemReader();
    }
    
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis/mybatis-config.xml"));
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:/mybatis/mapper/*.xml"));
        return factoryBean.getObject();
    }
    
    @Bean
    public MyBatisBatchItemWriter<User> writer(DataSource dataSource, ApplicationContext applicationContext) throws Exception {
        return new MyBatisBatchItemWriterBuilder<User>()
                .sqlSessionFactory(sqlSessionFactory(dataSource, applicationContext))
                .statementId("com.example.insertDemo3A")
                .build();
    }
    
    @Bean
    public Job writeMysqlJob(JobRepository jobRepository, Step step1) {
        log.info("||||||||||This is Job.");
        return new JobBuilder("writeMysqlJob", jobRepository)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager,
            MyBatisBatchItemWriter<User> writer) {
        log.info("||||||||||This is Step.");
        return new StepBuilder("step1", jobRepository)
                .allowStartIfComplete(true)
                .<User, User>chunk(10, transactionManager)
                .reader(reader())
                .writer((writer))
                .build();
    }
    
}
