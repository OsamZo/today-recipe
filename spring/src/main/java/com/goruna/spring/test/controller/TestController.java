package com.goruna.spring.test.controller;

import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import com.goruna.spring.common.s3.FileUploadUtil;
import com.goruna.spring.test.dto.TestRequestDTO;
import com.goruna.spring.test.dto.TestResponseDTO;
import com.goruna.spring.test.service.TestService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@Tag(name = "99. Test API", description = "테스트용 API")
public class TestController {

    private final TestService testService;
    private final FileUploadUtil fileUploadUtil;

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

    /* 파일 업로드 테스트 컨트롤러 */
    /* consumes 속성을 통해 해당 요청을 json type에서 MultipartFormDataValue 속성으로 바꿔준다.
     *  이 속성을 사용하지 않으면 swagger에서 json type으로 값을 받아오려고 함 */
    @PostMapping(value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "파일 업로드", description = "S3에 파일을 업로드하고 URL을 반환합니다.")
    public ResponseEntity<Map<String, String>> uploadFile(
            @RequestPart("file") MultipartFile file) {
        try {
            // 파일 업로드 후 S3 URL 반환 (확장자 검증도 내부에서 수행)
            String fileUrl = fileUploadUtil.uploadFile(file);

            // URL을 JSON 형태로 반환
            Map<String, String> response = new HashMap<>();
            response.put("fileUrl", fileUrl);

            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            // 확장자 검증 실패 시 처리
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        } catch (IOException e) {
            // 파일 업로드 중 오류 발생 시 처리
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
