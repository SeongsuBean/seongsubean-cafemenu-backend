package com.oopsw.seongsubean_cafemenu_backend.vo;

import lombok.Data;

@Data
public class CafeMenuResponse {
  private String menucategory;
  private String menuName;
  private String menuintroduction;
  private String price;
  private String image;
}
