-- 사용자 정보 추가
INSERT INTO `users`(
    `user_deleted_status`, `user_saved_money`, `user_usage_count`,
    `mod_date`, `reg_date`, `user_deleted_time`,
    `user_seq`, `user_email`, `user_nickname`, `user_role`
) VALUES
      ('N', 5000, 20, '2024-11-07 10:00:00', '2024-11-02 09:00:00', NULL, 1, 'cakefan@example.com', '케이크매니아', 'USER'),
      ('N', 1500, 8, '2024-11-07 10:30:00', '2024-11-03 10:30:00', NULL, 2, 'cookiefan@example.com', '쿠키광', 'USER'),
      ('N', 2200, 12, '2024-11-07 11:00:00', '2024-11-04 14:00:00', NULL, 3, 'breadjunkie@example.com', '빵중독자', 'USER'),
      ('N', 8000, 18, '2024-11-07 11:30:00', '2024-11-05 11:30:00', NULL, 4, 'puddinglover@example.com', '푸딩사랑', 'USER'),
      ('N', 6000, 25, '2024-11-07 12:00:00', '2024-11-06 15:00:00', NULL, 5, 'pastrylover@example.com', '패스트리사랑', 'USER');

INSERT INTO `shop_category`(`category_seq`, `category_name`)
VALUES
                                                                 (1, '케이크'),
                                                                 (2, '쿠키'),
                                                                 (3, '빵'),
                                                                 (4, '푸딩'),
                                                                 (5, '패스트리');

-- 매장 정보 추가
INSERT INTO `shop`(
    `category_seq`, `mod_date`, `reg_date`, `shop_open_date`,
    `shop_seq`, `user_seq`, `shop_address`, `shop_business_img_url`,
    `shop_img_url`, `shop_introduction`, `shop_name`, `shop_tel`,
    `shop_appr_status`, `shop_del_status`
) VALUES
      (1, '2024-11-07 10:00:00', '2024-11-02 09:00:00', '2024-11-10 08:00:00', 1, 1, '서울특별시 강남구 사거리 45', 'https://example.com/cake_shop_license.jpg', 'https://goruna.s3.us-west-1.amazonaws.com/f8850735-3559-4212-91e8-d4cc9fa9a864_%EB%8F%84%EB%8B%B4%EC%9D%B4%EC%82%AC%EC%A7%84.png', '신선한 케이크를 매일 만드는 제과점입니다!', '달콤한 케이크', '02-1234-5678', 'APPROVE', 'N'),
      (1, '2024-11-07 10:30:00', '2024-11-03 10:30:00', '2024-11-12 09:00:00', 2, 2, '서울특별시 홍대 25', 'https://example.com/cookie_shop_license.jpg', 'https://goruna.s3.us-west-1.amazonaws.com/f8850735-3559-4212-91e8-d4cc9fa9a864_%EB%8F%84%EB%8B%B4%EC%9D%B4%EC%82%AC%EC%A7%84.png', '바삭한 쿠키를 제공하는 매장', '바삭쿠키집', '02-8765-4321', 'APPROVE', 'N'),
      (1, '2024-11-07 11:00:00', '2024-11-04 14:00:00', '2024-11-15 10:00:00', 3, 3, '서울특별시 여의도 30', 'https://example.com/bread_shop_license.jpg', 'https://goruna.s3.us-west-1.amazonaws.com/f8850735-3559-4212-91e8-d4cc9fa9a864_%EB%8F%84%EB%8B%B4%EC%9D%B4%EC%82%AC%EC%A7%84.png', '다양한 빵을 제공하는 제과점입니다.', '빵세상', '02-2345-6789', 'APPROVE', 'N'),
      (1, '2024-11-07 11:30:00', '2024-11-05 11:30:00', '2024-11-20 09:00:00', 4, 4, '서울특별시 서초구 12', 'https://example.com/pudding_shop_license.jpg', 'https://goruna.s3.us-west-1.amazonaws.com/f8850735-3559-4212-91e8-d4cc9fa9a864_%EB%8F%84%EB%8B%B4%EC%9D%B4%EC%82%AC%EC%A7%84.png', '푸딩 전문점입니다.', '부드러운 푸딩', '02-3456-7890', 'APPROVE', 'N'),
      (1, '2024-11-07 12:00:00', '2024-11-06 15:00:00', '2024-11-25 11:00:00', 5, 5, '서울특별시 성수동 44', 'https://example.com/pastry_shop_license.jpg', 'https://goruna.s3.us-west-1.amazonaws.com/f8850735-3559-4212-91e8-d4cc9fa9a864_%EB%8F%84%EB%8B%B4%EC%9D%B4%EC%82%AC%EC%A7%84.png', '다양한 패스트리를 제공합니다.', '패스트리 하우스', '02-4567-8901', 'APPROVE', 'N');

-- 상품 정보 추가
INSERT INTO `product`(
    `product_original_price`, `product_qty`, `product_sale_price`,
    `mod_date`, `product_closed_at`, `product_seq`,
    `reg_date`, `shop_seq`, `product_description`,
    `product_img_url`, `product_name`
) VALUES
      (6000, 30, 5500, '2024-11-07 10:00:00', '2024-11-20 20:00:00', 1, '2024-11-02 09:30:00', 1, '달콤한 생크림이 가득한 케이크', 'https://example.com/sweet_cake.jpg', '딸기 생크림 케이크'),
      (4000, 50, 3500, '2024-11-07 10:30:00', '2024-11-22 20:00:00', 2, '2024-11-03 10:45:00', 2, '바삭하고 고소한 초코 쿠키', 'https://example.com/choco_cookie.jpg', '초코 쿠키'),
      (5000, 20, 4500, '2024-11-07 11:00:00', '2024-11-29 18:00:00', 3, '2024-11-04 14:15:00', 3, '맛있는 통밀빵', 'https://example.com/wheat_bread.jpg', '통밀 빵'),
      (8000, 15, 7000, '2024-11-07 11:30:00', '2024-11-25 19:00:00', 4, '2024-11-05 11:00:00', 4, '부드러운 바닐라 푸딩', 'https://example.com/vanilla_pudding.jpg', '바닐라 푸딩'),
      (10000, 25, 9000, '2024-11-07 12:00:00', '2024-11-30 17:00:00', 5, '2024-11-06 15:30:00', 5, '다양한 과일이 들어간 패스트리', 'https://example.com/fruit_pastry.jpg', '과일 패스트리');

-- 예약 정보 추가
INSERT INTO `book`(
    `book_seq`, `mod_date`, `reg_date`, `book_is_product_received`,
    `book_qty`, `is_book_cancelled`, `product_seq`, `user_seq`
) VALUES
      (1, '2024-11-07 10:00:00', '2024-11-02 09:30:00', 'N', 2, 'N', 1, 1),
      (2, '2024-11-07 10:30:00', '2024-11-03 10:45:00', 'N', 1, 'N', 1, 2),
      (3, '2024-11-07 11:00:00', '2024-11-04 14:15:00', 'Y', 3, 'Y', 3, 3),
      (4, '2024-11-07 11:30:00', '2024-11-05 11:00:00', 'N', 2, 'N', 4, 4),
      (5, '2024-11-07 12:00:00', '2024-11-06 15:30:00', 'N', 4, 'N', 5, 5);

-- 리뷰 정보 추가
INSERT INTO `review`(
    `book_seq`, `del_date`, `mod_date`, `reg_date`,
    `review_seq`, `review_content`, `review_status`
) VALUES
      (1, NULL, '2024-11-07 10:05:00', '2024-11-02 09:35:00', 1, '케이크가 정말 맛있었어요!', 'Y'),
      (2, NULL, '2024-11-07 10:35:00', '2024-11-03 10:50:00', 2, '쿠키가 바삭하고 정말 좋았어요!', 'Y'),
      (3, NULL, '2024-11-07 11:05:00', '2024-11-04 14:20:00', 3, '통밀빵이 너무 건강해요!', 'Y'),
      (4, NULL, '2024-11-07 11:35:00', '2024-11-05 11:10:00', 4, '부드러운 푸딩이 정말 맛있어요!', 'Y'),
      (5, NULL, '2024-11-07 12:05:00', '2024-11-06 15:40:00', 5, '패스트리가 정말 맛있고 신선해요!', 'Y');

-- 좋아요 정보 추가
INSERT INTO `good`(
    `good_seq`, `review_seq`, `user_seq`
) VALUES
      (1, 1, 1),
      (2, 1, 2),
      (3, 3, 2),
      (4, 4, 3),
      (5, 5, 4);

-- 북마크 정보 추가
INSERT INTO `bookmark`(
    `bookmark_seq`, `shop_seq`, `user_seq`
) VALUES
      (1, 1, 1),
      (2, 2, 3),
      (3, 3, 4),
      (4, 4, 5),
      (5, 5, 2);
