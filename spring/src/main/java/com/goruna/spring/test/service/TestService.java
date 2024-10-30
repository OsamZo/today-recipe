package com.goruna.spring.test.service;

import com.goruna.spring.common.exception.CustomException;
import com.goruna.spring.common.exception.ErrorCodeType;
import com.goruna.spring.test.dto.TestResponseDTO;
import com.goruna.spring.test.repository.TestRepository;
import com.goruna.spring.test.dto.TestRequestDTO;
import com.goruna.spring.test.entity.Test;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TestService {

    private final TestRepository testRepository;
    private final ModelMapper modelMapper;

    // 단일 데이터 조회
    @Transactional
    public TestResponseDTO getTestById(Long testSeq) {
        Test test = testRepository.findById(testSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));
        return modelMapper.map(test, TestResponseDTO.class);
    }

    // 전체 데이터 목록 조회
    @Transactional
    public List<TestResponseDTO> getAllTests() {
        List<Test> tests = testRepository.findAll();
        return tests.stream()
                .map(test -> modelMapper.map(test, TestResponseDTO.class))
                .collect(Collectors.toList());
    }

    /* Test 추가 작업 */
    @Transactional
    public TestRequestDTO createTest(TestRequestDTO testRequestDTO) {
        Test test = modelMapper.map(testRequestDTO, Test.class);
        testRepository.save(test);
        return testRequestDTO;
    }

    @Transactional
    public TestRequestDTO updateTest(Long testSeq, TestRequestDTO testRequestDTO) {
        Test test = testRepository.findById(testSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        // 기존 객체에 testRequestDTO의 필드를 매핑
        modelMapper.map(testRequestDTO, test);

        return modelMapper.map(test, TestRequestDTO.class); // 업데이트된 객체를 반환
    }

    // Test Delete
    @Transactional
    public boolean deleteTest(Long testSeq) {
        if (testRepository.existsById(testSeq)) {
            testRepository.deleteById(testSeq);
            return true;
        } else {
            throw new CustomException(ErrorCodeType.DATA_NOT_FOUND);
        }
    }
}
