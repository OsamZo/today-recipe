package com.goruna.spring.book.controller;

import com.goruna.spring.book.dto.UpdateBookInfoReqDTO;
import com.goruna.spring.book.dto.OwnerShopBooksResDTO;
import com.goruna.spring.book.service.OwnerBookService;
import com.goruna.spring.common.response.ApiResponse;
import com.goruna.spring.common.response.ResponseUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
@Tag(name = "OwnerBook API", description = "사장용 예약 api")
public class OwnerBookController {

    private final OwnerBookService ownerBookService;

    @PutMapping("/book/{bookSeq}")
    @Operation(summary = "(사장) 내 매장 예약 정보 수정", description = "(사장) 내 매장 예약 정보를 수정합니다.")
    public ApiResponse<?> updateBookInfo(@PathVariable Long bookSeq, @RequestBody UpdateBookInfoReqDTO updateBookInfoReqDTO) {
        ownerBookService.updateBookInfo(bookSeq, updateBookInfoReqDTO);
        return ResponseUtil.successResponse("예약 정보가 성공적으로 수정되었습니다.").getBody();
    }

    @GetMapping("/book")
    @Operation(summary = "(사장) 내 매장 예약 목록 조회", description = "(사장) 내 매장 예약 목록을 조회합니다.")
    public ApiResponse<?> getOwnerShopBooks() {
        List<OwnerShopBooksResDTO> MyShopBooks = ownerBookService.getOwnerShopBooks();
        return ResponseUtil.successResponse("내 매장 예약 내역 조회가 성공적으로 조회되었습니다.", MyShopBooks).getBody();
    }

}
