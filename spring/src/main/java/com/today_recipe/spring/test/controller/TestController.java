package com.today_recipe.spring.test.controller;

import com.today_recipe.spring.common.response.ApiResponse;
import com.today_recipe.spring.common.response.ResponseUtil;
import com.today_recipe.spring.test.dto.TestRequestDTO;
import com.today_recipe.spring.test.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@Tag(name = "99. Test API", description = "테스트용 API")
public class TestController {

    private final TestService testService;

    // 전체 데이터 목록 조회
    @GetMapping
    @Operation(summary = "테스트용 데이터 전체 조회", description = "테스트 데이터 전체 조회 입니다.")
    public ResponseEntity<ApiResponse<List<TestRequestDTO>>> getAllTests() {
        List<TestRequestDTO> testDTOs = testService.getAllTests();
        return ResponseUtil.successResponse("전체 데이터가 성공적으로 조회되었습니다", testDTOs);
    }

    // 단일 데이터 조회
    @GetMapping("/{id}")
    @Operation(summary = "테스트용 데이터 상세 조회", description = "테스트 데이터 상세 조회 입니다.")
    public ResponseEntity<ApiResponse<TestRequestDTO>> getTestById(@PathVariable Long id) {
        TestRequestDTO testDTO = testService.getTestById(id);
        return ResponseUtil.successResponse("데이터가 성공적으로 조회되었습니다", testDTO);
    }

    /* POST 요청 - 데이터 생성 */
    @PostMapping
    @Operation(summary = "테스트용 데이터 생성", description = "테스트 데이터 생성입니다.")
    public ResponseEntity<ApiResponse<TestRequestDTO>> createTest(@RequestParam String content) {
        TestRequestDTO createdTest = testService.createTest(content);
        return ResponseUtil.successResponse("데이터가 성공적으로 생성되었습니다", createdTest);
    }

    /* PUT 요청 - 데이터 수정 */
    @PutMapping("/{id}")
    @Operation(summary = "테스트용 데이터 수정", description = "테스트 데이터 수정입니다.")
    public ResponseEntity<ApiResponse<TestRequestDTO>> updateTest(@PathVariable Long id, @RequestParam String content) {
        TestRequestDTO updatedTest = testService.updateTest(id, content);
        return ResponseUtil.successResponse("데이터가 성공적으로 수정되었습니다", updatedTest);
    }

    /* DELETE 요청 - 데이터 삭제 */
    @DeleteMapping("/{id}")
    @Operation(summary = "테스트용 데이터 삭제", description = "테스트 데이터 삭제입니다.")
    public ResponseEntity<ApiResponse<Void>> deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
        return ResponseUtil.successResponse("데이터가 성공적으로 삭제되었습니다", null);
    }
}
