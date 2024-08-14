package org.savea.springbatch.configs;

import lombok.RequiredArgsConstructor;
import org.savea.springbatch.models.Student;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class StudentItemReader {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public FlatFileItemReader<Student> itemReader() {
        FlatFileItemReader<Student> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource("springbatch/src/main/resources/students.csv"));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Student> csvItemReader(@Value("{jobParameters['filePath']}") String filePath) {
        FlatFileItemReader<Student> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(filePath));
        itemReader.setName("csvReader");
        itemReader.setLinesToSkip(1);
        itemReader.setLineMapper(lineMapper());
        return itemReader;
    }

    @Bean
    public Step validateCsvHeaderStep() {
        return new StepBuilder("validateCsvHeaderStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    String filePath = (String) chunkContext.getStepContext().getJobParameters().get("filePath");
                    File file = new File(filePath);
                    if (!file.exists()) {
                        throw new IOException("File not found: " + filePath);
                    }

                    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                        String header = reader.readLine();
                        if (header == null || !header.equals("id,firstname,lastname,age")) {
                            throw new IOException("Invalid CSV header: " + header);
                        }
                    } catch (IOException e) {
                        throw new IOException("Failed to read the CSV file: " + filePath, e);
                    }

                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }

    private LineMapper<Student> lineMapper() {
        DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames("id", "firstname", "lastname", "age");

        BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Student.class);

        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        return lineMapper;
    }
}
