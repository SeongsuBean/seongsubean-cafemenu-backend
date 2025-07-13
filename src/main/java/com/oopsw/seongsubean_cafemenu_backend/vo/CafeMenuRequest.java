package com.oopsw.seongsubean_cafemenu_backend.vo;

import lombok.Data;

@Data
public class CafeMenuRequest {
  private String menuCategory;
  private String menuName;
  private String menuIntroduction;
  private String price;
  private String image;
  private Long cafeId;

}
