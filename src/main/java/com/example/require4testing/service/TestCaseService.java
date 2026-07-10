package com.example.require4testing.service;

import com.example.require4testing.entity.Requirement;
import com.example.require4testing.entity.TestCase;
import com.example.require4testing.repository.RequirementRepository;
import com.example.require4testing.repository.TestCaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseService {
    private final TestCaseRepository testCaseRepository;
    private final RequirementRepository requirementRepository;

    public TestCaseService(TestCaseRepository testCaseRepository, RequirementRepository requirementRepository) {
        this.testCaseRepository = testCaseRepository;
        this.requirementRepository = requirementRepository;
    }

    public List<TestCase> getTestCasesbyRequirement(Long requirementId) {
        return testCaseRepository.findByRequirementId(requirementId);

    }

    public List<TestCase> getAllTestCases() {
        return (List<TestCase>) testCaseRepository.findAll();
    }

    public TestCase addTestCase(Long requirementId, TestCase newTestCase) {
        Requirement requirement = requirementRepository.findById(requirementId)
                .orElseThrow(() -> new IllegalArgumentException("Requirement mit Id " + requirementId + " existiert nicht."));
            newTestCase.setRequirement(requirement);

            return testCaseRepository.save(newTestCase);
    }
}
