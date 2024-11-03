package com.goruna.spring.shop.controller;

import com.goruna.spring.shop.service.OwnerShopService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
@Tag(name = "", description = "사장용 api")    // name 설정 해야함
public class OwnerShopController {

    private final OwnerShopService ownerShopService;

}
