package com.goruna.spring.shop.entity;

import com.goruna.spring.common.aggregate.YnType;
import com.goruna.spring.common.aggregate.entity.BaseTimeEntity;
import com.goruna.spring.users.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;

@Entity
@Table(name = "shop")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@SQLDelete(sql = "UPDATE shop SET shop_del_status = 'N', mod_date = NOW() WHERE shop_seq = ?")
public class Shop extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_seq", nullable = false)
    private Long shopSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userSeq", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categorySeq", nullable = false)
    private ShopCategory shopCategory;

    @Column(name = "shop_name", nullable = false)
    private String shopName;

    @Column(name = "shop_tel", nullable = false)
    private String shopTel;

    @Column(name = "shop_address", nullable = false)
    private String shopAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "shop_appr_status", nullable = false)
    private YnType shopApprStatus = YnType.N;

    @Column(name = "shop_open_date", nullable = false)
    private LocalDateTime shopOpenDate;

    @Column(name = "shop_introduction", nullable = false)
    private String shopIntroduction;

    @Column(name = "shop_img_url", nullable = true)
    private String shopImgUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "shop_del_status", nullable = false)
    private YnType shopDelStatus = YnType.N;

    @Column(name = "shop_business_img_url", nullable = false)
    private String shopBusinessImgUrl;

    public void addUser(User user) {
        this.user = user;
    }

    public void approve(){
        this.shopApprStatus = YnType.Y;
    }

    public void disapprove(){
        this.shopApprStatus = YnType.N;
    }
}
