package com.oopsw.seongsubean_cafemenu_backend.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cafe_menu")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CafeMenuEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long menuId;

  @Column(name = "MENU_CATEGORY", nullable = false, length = 50)
  private String menuCategory;

  @Column(name = "MENU_NAME", nullable = false, length = 100)
  private String menuName;

  @Column(name = "MENU_INTRODUCTION", columnDefinition = "TEXT")
  private String menuIntroduction;

  @Column(name = "PRICE", nullable = false, precision = 10, scale = 2)
  private String price;

  @Column(name = "IMEAGE", length = 500)
  private String image;

  @Column(name = "CAFE_ID", nullable = false)
  private Long cafeId;

}