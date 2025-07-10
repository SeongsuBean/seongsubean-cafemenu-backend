package com.oopsw.seongsubean_cafemenu_backend.vo;

import lombok.Data;

@Data
public class CafeMenuRequest {
  private String menucategory;
  private String menuName;
  private String menuintroduction;
  private String price;
  private String image;
  private Long cafeId;

}
