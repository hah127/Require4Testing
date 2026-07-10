package com.example.require4testing.repository;

import com.example.require4testing.entity.TestRun;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TestRunRepository extends CrudRepository<TestRun, Long>{
    List<TestRun>findByTester(String testerName);
}
