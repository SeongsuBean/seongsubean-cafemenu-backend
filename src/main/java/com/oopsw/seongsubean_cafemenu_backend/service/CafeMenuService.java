package com.oopsw.seongsubean_cafemenu_backend.service;

import com.oopsw.seongsubean_cafemenu_backend.dto.CafeMenuDto;

import java.util.List;

public interface CafeMenuService {
  void addMenu(CafeMenuDto cafeMenuDto);

  CafeMenuDto getMenuInfo(Long cafeId, Long menuId);

  List<CafeMenuDto> getMenuListByCafeId(Long cafeId);

  void editMenu(Long cafeId, Long menuId, CafeMenuDto cafeMenuDto);

  boolean deleteMenu(Long cafeId, Long menuId);

  /**
   * @param menuCategory "커피" | "빵" | "케이크" | "파이" | "기타"
   * @return 해당 카테고리에 속하는 cafeId 리스트
   */
  List<Long> getCafeIdsByCategory(String menuCategory);
}
