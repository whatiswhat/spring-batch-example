package com.example.home.demo1.helloworld;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.support.JdbcTransactionManager;

@SpringBootApplication
@EnableBatchProcessing(dataSourceRef = "", transactionManagerRef = "")
@Configuration
public class HelloJob {

    @SuppressWarnings("unused")
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JobBuilder jobBuilder;

    @Autowired
    private StepBuilder stepBuilder;

    public Tasklet tasklet() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println("Hello Spring Batch.");
                return RepeatStatus.FINISHED;
            }
        };
    }

    @Bean
    public DataSource batchDataSource() {
        return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
                .addScript("/org/springframework/batch/core/schema-h2.sql")
                .generateUniqueName(true).build();
    }
    
    @Bean
    public JdbcTransactionManager batchTransactionManager(DataSource dataSource) {
        return new JdbcTransactionManager(dataSource);
    }

    @Bean
    public Step helloStep() {
        return stepBuilder.tasklet(tasklet(), null).build();
    }

    @Bean
    public Job h1Job(JobRepository jobRepository) {
//        return jobBuilder.start(step1()).build();
        return new JobBuilder("h2Job", jobRepository).start(helloStep()).build();
    }

//    public static void main(String[] args) {
//        SpringApplication.run(HelloJob.class, args);
//    }

}
