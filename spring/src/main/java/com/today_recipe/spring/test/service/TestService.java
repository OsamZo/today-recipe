package com.today_recipe.spring.test.service;

import com.today_recipe.spring.common.exception.CustomException;
import com.today_recipe.spring.common.exception.ErrorCodeType;
import com.today_recipe.spring.test.dto.TestRequestDTO;
import com.today_recipe.spring.test.entity.Test;
import com.today_recipe.spring.test.repository.TestRepository;
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
    public TestRequestDTO getTestById(Long id) {
        Test test = testRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));
        return modelMapper.map(test, TestRequestDTO.class);
    }

    // 전체 데이터 목록 조회
    @Transactional
    public List<TestRequestDTO> getAllTests() {
        List<Test> tests = testRepository.findAll();
        return tests.stream()
                .map(test -> modelMapper.map(test, TestRequestDTO.class))
                .collect(Collectors.toList());
    }

    /* Test Post 작업 */
    @Transactional
    public TestRequestDTO createTest(String content) {
        TestRequestDTO testDTO = new TestRequestDTO(content);
        Test test = modelMapper.map(testDTO, Test.class);
        testRepository.save(test);
        return testDTO;
    }

    /* Test Put 작업 */
    @Transactional
    public TestRequestDTO updateTest(Long testSeq, String content) {
        Test test = testRepository.findById(testSeq)
                .orElseThrow(() -> new CustomException(ErrorCodeType.DATA_NOT_FOUND));

        TestRequestDTO testDTO = new TestRequestDTO(content);
        modelMapper.map(testDTO, test); // 기존 test 객체에 testDTO의 필드만 매핑

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
