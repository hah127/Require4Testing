package com.example.require4testing.service;

import com.example.require4testing.entity.TestCase;
import com.example.require4testing.entity.TestExecution;
import com.example.require4testing.entity.TestRun;
import com.example.require4testing.repository.TestRunRepository;
import com.example.require4testing.repository.TestExecutionRepository;
import com.example.require4testing.repository.TestCaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestRunService {

    private final TestRunRepository testRunRepository;
    private final TestCaseRepository testCaseRepository;
    private final TestExecutionRepository testExecutionRepository;

    public TestRunService(TestRunRepository testRunRepository, TestCaseRepository testCaseRepository, TestExecutionRepository testExecutionRepository){
        this.testRunRepository = testRunRepository;
        this.testCaseRepository = testCaseRepository;
        this.testExecutionRepository = testExecutionRepository;
    }

    public List<TestRun> getAllTestRuns(){
        return (List<TestRun>) testRunRepository.findAll();
    }

    public TestRun addTestRun(TestRun run){
        return testRunRepository.save(run);
    }

    public TestRun getTestRun(Long id) {
        return testRunRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Testrun nicht gefunden!"));
    }

    public void assignTestCases(Long runId, List<Long> TestCaseId){

        TestRun run = testRunRepository.findById(runId)
                .orElseThrow(() -> new IllegalArgumentException("Testcase nicht gefunden!"));

        for (Long caseId : TestCaseId){
            TestCase testCase = testCaseRepository.findById(caseId)
                    .orElseThrow(() -> new IllegalArgumentException("Testcase nicht gefunden!"));


        boolean alreadyAssigned = false;
        for (TestExecution te : testExecutionRepository.findByTestRunId(runId)) {
            if (te.getTestCase().getId().equals(caseId)) {
                alreadyAssigned = true;
                break;
            }
        }

        if (alreadyAssigned) {
            continue;}

        TestExecution exec = new TestExecution();
        exec.setTestRun(run);
        exec.setTestCase(testCase);
        exec.setResult("Not Executed");

        testExecutionRepository.save(exec);
        }
    }

    public void setTestRunResult(Long executionId, String result) {

        TestExecution exec = testExecutionRepository.findById(executionId)
                .orElseThrow(() -> new IllegalArgumentException("Testexecution nicht gefunden!"));

        exec.setResult(result);

        testExecutionRepository.save(exec);
    }

}

