-- 사용자 정보 추가
INSERT INTO `users`(
    `user_deleted_status`, `user_saved_money`, `user_usage_count`,
    `mod_date`, `reg_date`, `user_deleted_time`,
    `user_seq`, `user_email`, `user_nickname`, `user_role`
) VALUES (
             'N', 3000, 15,
             '2024-11-06 15:00:00', '2024-11-01 08:30:00', NULL,
             1, 'breadlover@example.com', '빵덕후', 'USER'
         );

-- 카테고리 정보 추가
INSERT INTO `shop_category`(
    `category_seq`, `category_name`
) VALUES (
             1, '제과점'
         );

-- 매장 정보 추가
INSERT INTO `shop`(
    `category_seq`, `mod_date`, `reg_date`, `shop_open_date`,
    `shop_seq`, `user_seq`, `shop_address`, `shop_business_img_url`,
    `shop_img_url`, `shop_introduction`, `shop_name`, `shop_tel`,
    `shop_appr_status`, `shop_del_status`
) VALUES (
             1, '2024-11-06 15:00:00', '2024-11-01 08:30:00', '2024-11-10 07:00:00',
             1, 1, '서울특별시 마포구 맛있는길 45', 'https://example.com/bakery_license.jpg',
             'https://example.com/bakery.jpg', '신선한 빵을 매일 구워내는 제과점입니다!', '행복한 빵집', '02-9876-5432',
             'Y', 'N'
         );

-- 상품 정보 추가 (빵 예시)
INSERT INTO `product`(
    `product_original_price`, `product_qty`, `product_sale_price`,
    `mod_date`, `product_closed_at`, `product_seq`,
    `reg_date`, `shop_seq`, `product_description`,
    `product_img_url`, `product_name`
) VALUES (
             5000, 50, 4500,
             '2024-11-06 15:00:00', '2024-11-20 20:00:00', 1,
             '2024-11-01 08:30:00', 1, '부드럽고 달콤한 크림이 가득한 생크림 빵',
             'https://example.com/cream_bread.jpg', '생크림 빵'
         );

-- 예약 정보 추가
INSERT INTO `book`(
    `book_seq`, `mod_date`, `reg_date`, `book_is_product_received`,
    `book_qty`, `is_book_cancelled`, `product_seq`, `user_seq`
) VALUES (
             1, '2024-11-06 15:00:00', '2024-11-01 08:30:00', 'N',
             3, 'N', 1, 1
         );

-- 리뷰 정보 추가
INSERT INTO `review`(
    `book_seq`, `del_date`, `mod_date`, `reg_date`,
    `review_seq`, `review_content`, `review_status`
) VALUES (
             1, NULL, '2024-11-06 15:00:00', '2024-11-01 08:30:00',
             1, '생크림 빵이 정말 부드럽고 맛있어요!', 'Y'
         );

-- 좋아요 정보 추가
INSERT INTO `good`(
    `good_seq`, `review_seq`, `user_seq`
) VALUES (
             1, 1, 1
         );

-- 북마크 정보 추가
INSERT INTO `bookmark`(
    `bookmark_seq`, `shop_seq`, `user_seq`
) VALUES (
             1, 1, 1
         );
