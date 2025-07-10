package com.oopsw.seongsubean_cafemenu_backend.service;

import com.oopsw.seongsubean_cafemenu_backend.dto.CafeMenuDto;

public interface CafeMenuService {
  void addMenu(CafeMenuDto cafeMenuDto);

  CafeMenuDto getMenuInfo(Long cafeId, Long menuId);

  CafeMenuDto editMenu(Long cafeId, Long menuId, CafeMenuDto cafeMenuDto);

  boolean deleteMenu(Long cafeId, Long menuId);

}
