package com.oopsw.seongsubean_cafemenu_backend.service;

import com.oopsw.seongsubean_cafemenu_backend.dto.CafeMenuDto;
import com.oopsw.seongsubean_cafemenu_backend.jpa.CafeMenuEntity;
import com.oopsw.seongsubean_cafemenu_backend.repository.CafeMenuRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CafeMenuImpl implements CafeMenuService {

  public final CafeMenuRepository CafeMenuRepository;


  @Override
  public void addMenu(CafeMenuDto cafeMenuDto) {
    //Dto를 Entity로 바꿔
    CafeMenuEntity cafeMenuEntity = new ModelMapper().map(cafeMenuDto, CafeMenuEntity.class);
    //Entity로 바뀐 데이터를 Jpa가 저장
    CafeMenuRepository.save(cafeMenuEntity);
  }

  @Override
  public CafeMenuDto getMenuInfo(Long cafeId, Long menuId) {
  CafeMenuEntity cafeMenuEntity = CafeMenuRepository.getByCafeIdAndMenuId(cafeId, menuId);
  CafeMenuDto cafeMenuDto = new CafeMenuDto();
  cafeMenuDto.setCafeId(cafeId);
    return new ModelMapper().map(cafeMenuEntity, CafeMenuDto.class);
  }

  @Override
  public CafeMenuDto editMenu(Long cafeId, Long menuId, CafeMenuDto cafeMenuDto) {
    CafeMenuEntity cafeMenuEntity = new ModelMapper().map(cafeMenuDto, CafeMenuEntity.class);
    cafeMenuEntity.setCafeId(cafeId);
    cafeMenuEntity.setMenuId(menuId);
    CafeMenuRepository.save(cafeMenuEntity);
    return new ModelMapper().map(cafeMenuEntity, CafeMenuDto.class);

  }

  @Override
  public boolean deleteMenu(Long cafeId, Long menuId) {
    CafeMenuRepository.delete(CafeMenuRepository.getByCafeIdAndMenuId(cafeId, menuId));
    return true;
  }

}
