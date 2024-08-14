package org.savea.springbatch.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.savea.springbatch.models.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;
    private final StudentWriter studentWriter;
    private final StudentItemReader studentItemReader;

    @Bean
    public StudentProcessor processor() {
        return new StudentProcessor();
    }

    @Bean
    public Step importStep() {
        return new StepBuilder("csvImport", jobRepository)
                .<Student, Student>chunk(10, platformTransactionManager)
                .reader(studentItemReader.itemReader())
                .processor(processor())
                .writer(studentWriter.write())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step importStepForCsv() {
        return new StepBuilder("uploadedCsvImport", jobRepository)
                .<Student, Student>chunk(10, platformTransactionManager)
                .reader(studentItemReader.csvItemReader(null))
                .processor(processor())
                .writer(studentWriter.write())
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Step cleanupStep() {
        return new StepBuilder("cleanupStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    String filePath = (String) chunkContext.getStepContext().getJobParameters().get("filePath");
                    File file = new File(filePath);
                    if (file.exists()) {
                        boolean deleted = file.delete();
                        if (deleted) {
                            return RepeatStatus.FINISHED;
                        } else {
                            throw new IOException("Failed to delete the file: " + filePath);
                        }
                    } else {
                        throw new IOException("File not found: " + filePath);
                    }
                }, platformTransactionManager)
                .build();
    }

    @Bean
    public Job runJob() {
        return new JobBuilder("importStudents", jobRepository)
                .start(importStep())
                .build();
    }

    @Bean
    public Job runCsvJob() {
        return new JobBuilder("importStudentsFromCsv", jobRepository)
                .start(studentItemReader.validateCsvHeaderStep())
                .next(importStep())
                .next(cleanupStep())
                .build();
    }

    /**
     * Task executor meant to help with handling concurrent tasks while utilizing the threads available in the computer.
     *
     * @return TaskExecutor
     */
    @Bean
    public TaskExecutor taskExecutor() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        log.info("Available processors: {}", availableProcessors);
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(availableProcessors);
        return asyncTaskExecutor;
    }
}
