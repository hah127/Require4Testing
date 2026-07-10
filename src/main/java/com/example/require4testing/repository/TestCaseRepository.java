package com.example.require4testing.repository;

import com.example.require4testing.entity.TestCase;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TestCaseRepository extends CrudRepository<TestCase, Long> {

    List<TestCase> findByRequirementId(Long requirementId);
    boolean existsByRequirement_IdAndTitle(Long requirementId, String title);


}
