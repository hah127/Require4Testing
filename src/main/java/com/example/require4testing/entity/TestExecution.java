package com.example.require4testing.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "testexecution", uniqueConstraints = @UniqueConstraint(columnNames = {"testrun_id", "testcase_id"}))

public class TestExecution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String result = "Not Executed";

    @ManyToOne(optional = false)
    @JoinColumn(name = "testrun_id")
    private TestRun testRun;

    @ManyToOne(optional = false)
    @JoinColumn(name = "testcase_id")
    private TestCase testCase;
}
