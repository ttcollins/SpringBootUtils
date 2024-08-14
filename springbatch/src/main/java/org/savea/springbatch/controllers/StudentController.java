package org.savea.springbatch.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final JobLauncher jobLauncher;
    private final Job runJob;
    private final Job runCsvJob;

    @PostMapping
    public ResponseEntity<String> importCsvToDBJob() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        return runJob(runJob, jobParameters);
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<String> importCsvToDBJob(@RequestParam("file") MultipartFile file) {
        Path tempDir = Paths.get(System.getProperty("java.io.tmpdir"));
        Path tempFile = tempDir.resolve(Objects.requireNonNull(file.getOriginalFilename()));
        try {
            Files.write(tempFile, file.getBytes());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save the uploaded file.");
        }

        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .addString("filePath", tempFile.toString())
                .toJobParameters();
        return runJob(runCsvJob, jobParameters);
    }

    private ResponseEntity<String> runJob(Job job, JobParameters jobParameters) {
        try {
            jobLauncher.run(job, jobParameters);
            return ResponseEntity.ok("Job started successfully.");
        } catch (JobExecutionAlreadyRunningException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Job is already running.");
        } catch (JobRestartException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Job restart failed.");
        } catch (JobInstanceAlreadyCompleteException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Job instance is already complete.");
        } catch (JobParametersInvalidException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid job parameters.");
        }
    }
}