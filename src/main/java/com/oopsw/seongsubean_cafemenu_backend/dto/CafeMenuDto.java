package com.oopsw.seongsubean_cafemenu_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CafeMenuDto {
  private Long menuId;
  private String menuCategory;
  private String menuName;
  private String menuIntroduction;
  private String price;
  private String image;
  private Long cafeId;


}
