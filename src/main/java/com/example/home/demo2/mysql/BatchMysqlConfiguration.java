package com.example.home.demo2.mysql;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class BatchMysqlConfiguration {
    
    private static final Logger log = LoggerFactory.getLogger(BatchMysqlConfiguration.class);

    @Bean
    public UserItemReader reader() {
        return new UserItemReader();
    }

    @Bean
    public JdbcBatchItemWriter<User> writer(DataSource dataSource) {
        log.info("||||||||||This is ItemWriter.");
        return new JdbcBatchItemWriterBuilder<User>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO user (id, name, email) VALUES (:id, :name, :email)").dataSource(dataSource).build();
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
            JdbcBatchItemWriter<User> writer) {
        log.info("||||||||||This is Step.");
        return new StepBuilder("step1", jobRepository)
                .allowStartIfComplete(true)
                .<User, User>chunk(10, transactionManager)
                .reader(reader())
                .writer((writer))
                .build();
    }
    
}
