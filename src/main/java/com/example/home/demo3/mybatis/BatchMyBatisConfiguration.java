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
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
//@EnableBatchProcessing
//@ComponentScan
//@EnableAutoConfiguration
//@PropertySource("classpath:application.properties")
public class BatchMyBatisConfiguration {
    
    private static final Logger log = LoggerFactory.getLogger(BatchMyBatisConfiguration.class);

//    @Value("${database.driver}")
//    private String databaseDriver;
//    @Value("${database.url}")
//    private String databaseUrl;
//    @Value("${database.username}")
//    private String databaseUsername;
//    @Value("${database.password}")
//    private String databasePassword;

    @Bean
    public UserItemReader reader() {
        return new UserItemReader();
    }

//    @Bean
//    public UserItemProcessor processor() {
//        return new UserItemProcessor();
//    }
    
//    @Bean
//    public ItemProcessor<User, User> processor() {
//        return new ItemProcessor<User, User>() {
//            
//            @Override
//            public User process(User item) throws Exception {
//                log.info("||||||||||This is ItemProcessor.");
//                final Integer id = 1002;
//                final String name = "User1002";
//                final String email = "1002@sony.com"; 
//                
//                final User transformedUser = new User();
//                transformedUser.setId(id);
//                transformedUser.setName(name);
//                transformedUser.setEmail(email);
//
//                log.info("transformedUser is prepared: " + transformedUser);
//                return transformedUser;
//            }
//        };
//    }

//    @Bean
//    public JdbcBatchItemWriter<User> writer(DataSource dataSource) {
//        log.info("||||||||||This is ItemWriter.");
//        return new JdbcBatchItemWriterBuilder<User>()
//                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
//                .sql("INSERT INTO user (id, name, email) VALUES (:id, :name, :email)").dataSource(dataSource).build();
//    }
    
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
    
//    @Bean
//    public ItemWriter<User> writer() {
//        return new ItemWriter<User>() {
//            @Override
//            public void write(Chunk<? extends User> chunk) throws Exception {
//                log.info("||||||||||This is ItemWriter.");
//            }
//        };
//    }
    
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(databaseDriver);
//        dataSource.setUrl(databaseUrl);
//        dataSource.setUsername(databaseUsername);
//        dataSource.setPassword(databasePassword);
//        return dataSource;
//    }

//    @Bean
//    public Job writeMysqlJob(JobRepository jobRepository, Step step1) {
//        log.info("||||||||||This is Job.");
//        return new JobBuilder("writeMysqlJob", jobRepository).incrementer(new RunIdIncrementer()).flow(step1).end()
//                .build();
//    }
    
    @Bean
    public Job writeMysqlJob(JobRepository jobRepository, Step step1) {
        log.info("||||||||||This is Job.");
//        return new JobBuilder("writeMysqlJob", jobRepository).incrementer(new RunIdIncrementer()).flow(step1).end().build();
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
//                .processor(processor())
                .writer((writer))
                .build();
    }
    
//    @Bean
//    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        log.info("||||||||||This is Step.");
//        return new StepBuilder("step1", jobRepository).<User, User>chunk(2, transactionManager).reader(reader())
//                .processor(processor()).writer(writer()).build();
//    }
}
