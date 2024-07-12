package org.savea.springbatch.repo;

import org.savea.springbatch.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}