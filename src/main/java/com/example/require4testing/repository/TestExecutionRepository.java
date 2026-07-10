package com.example.require4testing.repository;

import com.example.require4testing.entity.TestExecution;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TestExecutionRepository extends CrudRepository<TestExecution, Long> {
    List<TestExecution> findByTestRunId(Long testRunId);
}
