package com.oopsw.seongsubean_cafemenu_backend.service;

import com.oopsw.seongsubean_cafemenu_backend.dto.CafeMenuDto;

import java.util.List;

public interface CafeMenuService {
  void addMenu(CafeMenuDto cafeMenuDto);

  CafeMenuDto getMenuInfo(Long cafeId, Long menuId);

  List<CafeMenuDto> getMenuListByCafeId(Long cafeId);

  void editMenu(Long cafeId, Long menuId, CafeMenuDto cafeMenuDto);

  boolean deleteMenu(Long cafeId, Long menuId);

}
