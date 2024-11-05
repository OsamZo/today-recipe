INSERT INTO `users`(
    `user_deleted_status`, `user_saved_money`, `user_usage_count`,
    `mod_date`, `reg_date`, `user_deleted_time`,
    `user_seq`, `user_email`, `user_nickname`, `user_role`
) VALUES (
             'N', 1000, 5,
             '2024-11-05 12:00:00', '2024-11-01 09:00:00', NULL,
             1, 'example@example.com', 'nickname123', 'USER'
         );

INSERT INTO `shop_category`(
    `category_seq`, `category_name`
) VALUES (
             1, 'Electronics'
         );

INSERT INTO `shop`(
    `category_seq`, `mod_date`, `reg_date`, `shop_open_date`,
    `shop_seq`, `user_seq`, `shop_address`, `shop_business_img_url`,
    `shop_img_url`, `shop_introduction`, `shop_name`, `shop_tel`,
    `shop_appr_status`, `shop_del_status`
) VALUES (
             1, '2024-11-05 12:00:00', '2024-11-01 09:00:00', '2024-11-10 09:00:00',
             1, 1, '123 Main St, City, Country', 'https://example.com/business.jpg',
             'https://example.com/shop.jpg', 'This is a great shop!', 'Best Shop', '123-456-7890',
             'N', 'N'
         );

INSERT INTO `product`(
    `product_original_price`, `product_qty`, `product_sale_price`,
    `mod_date`, `product_closed_at`, `product_seq`,
    `reg_date`, `shop_seq`, `product_description`,
    `product_img_url`, `product_name`
) VALUES (
             150.00, 100, 120.00,
             '2024-11-05 12:00:00', '2024-11-20 18:00:00', 1,
             '2024-11-01 09:00:00', 1, 'A high-quality smartphone',
             'https://example.com/product.jpg', 'Smartphone X'
         );

INSERT INTO `book`(
    `book_seq`, `mod_date`, `reg_date`, `book_is_product_received`,
    `book_qty`, `is_book_cancelled`, `product_seq`, `user_seq`
) VALUES (
             1, '2024-11-05 12:00:00', '2024-11-01 09:00:00', 'N',
             2, 'N', 1, 1
         );

INSERT INTO `review`(
    `book_seq`, `del_date`, `mod_date`, `reg_date`,
    `review_seq`, `review_content`, `review_status`
) VALUES (
             1, NULL, '2024-11-05 12:00:00', '2024-11-01 09:00:00',
             1, 'This is a great product!', 'Y'
         );

INSERT INTO `good`(
    `good_seq`, `review_seq`, `user_seq`
) VALUES (
             1, 1, 1
         );

INSERT INTO `bookmark`(
    `bookmark_seq`, `shop_seq`, `user_seq`
) VALUES (
             1, 1, 1
         );