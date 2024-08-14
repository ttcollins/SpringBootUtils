package org.savea.springbatch.configs;

import lombok.RequiredArgsConstructor;
import org.savea.springbatch.models.Student;
import org.savea.springbatch.repo.StudentRepository;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class StudentWriter {

    private final StudentRepository studentRepository;

    @Bean
    public RepositoryItemWriter<Student> write() {
        RepositoryItemWriter<Student> writer = new RepositoryItemWriter<>();
        writer.setRepository(studentRepository);
        writer.setMethodName("save");
        return writer;
    }

}
