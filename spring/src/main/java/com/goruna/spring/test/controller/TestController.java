package com.goruna.spring.test.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.test.dto.TestRequestDTO;
import com.goruna.spring.test.dto.TestResponseDTO;
import com.goruna.spring.test.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@Tag(name = "99. Test API", description = "테스트용 API")
public class TestController {

    private final TestService testService;

    @GetMapping
    @Operation(summary = "테스트용 데이터 전체 조회", description = "테스트 데이터 전체 조회 입니다.")
    public ApiResponse<?> getAllTests() {
        List<TestResponseDTO> testAllDTO = testService.getAllTests();
        return ResponseUtil.successResponse("전체 데이터가 성공적으로 조회되었습니다", testAllDTO).getBody();
    }

    @GetMapping("/{testSeq}")
    @Operation(summary = "테스트용 데이터 상세 조회", description = "테스트 데이터 상세 조회 입니다.")
    public ApiResponse<?> getTestById(@PathVariable Long testSeq) {
        TestResponseDTO testDTO = testService.getTestById(testSeq);
        return ResponseUtil.successResponse("데이터가 성공적으로 조회되었습니다", testDTO).getBody();
    }

    @PostMapping
    @Operation(summary = "테스트용 데이터 생성", description = "테스트 데이터 생성입니다.")
    public ApiResponse<?> createTest(@RequestBody TestRequestDTO testRequestDTO) {
        TestRequestDTO createdTest = testService.createTest(testRequestDTO);
        return ResponseUtil.successResponse("데이터가 성공적으로 생성되었습니다", createdTest).getBody();
    }

    @PutMapping("/{testSeq}")
    @Operation(summary = "테스트용 데이터 수정", description = "테스트 데이터 수정입니다.")
    public ApiResponse<?> updateTest(@PathVariable Long testSeq, @RequestBody TestRequestDTO testRequestDTO) {
        TestRequestDTO updatedTest = testService.updateTest(testSeq, testRequestDTO);
        return ResponseUtil.successResponse("데이터가 성공적으로 수정되었습니다", updatedTest).getBody();
    }

    @DeleteMapping("/{testSeq}")
    @Operation(summary = "테스트용 데이터 삭제", description = "테스트 데이터 삭제입니다.")
    public ApiResponse<?> deleteTest(@PathVariable Long testSeq) {
        testService.deleteTest(testSeq);
        return ResponseUtil.successResponse("데이터가 성공적으로 삭제되었습니다").getBody();
    }
}
