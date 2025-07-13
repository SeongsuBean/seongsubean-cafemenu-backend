package com.oopsw.seongsubean_cafemenu_backend.service;

import com.oopsw.seongsubean_cafemenu_backend.dto.CafeMenuDto;
import com.oopsw.seongsubean_cafemenu_backend.jpa.CafeMenuEntity;
import com.oopsw.seongsubean_cafemenu_backend.repository.CafeMenuRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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
  public List<CafeMenuDto> getMenuListByCafeId(Long cafeId) {
    List<CafeMenuEntity> menuEntities = CafeMenuRepository.findAllByCafeId(cafeId);
    ModelMapper mapper = new ModelMapper();
    return menuEntities.stream()
            .map(entity -> mapper.map(entity, CafeMenuDto.class))
            .collect(Collectors.toList());
  }


  @Override
  public void editMenu(Long cafeId, Long menuId, CafeMenuDto dto) {
    CafeMenuEntity entity = CafeMenuRepository.findById(menuId)
            .orElseThrow(() -> new EntityNotFoundException("해당 메뉴가 존재하지 않습니다."));

    // DTO의 필드들을 Entity에 반영
    entity.setMenuCategory(dto.getMenuCategory());
    entity.setMenuName(dto.getMenuName());
    entity.setMenuIntroduction(dto.getMenuIntroduction());

    // BigDecimal로 변환 (DTO의 price는 문자열이라고 가정)
    entity.setPrice(new BigDecimal(String.valueOf(dto.getPrice())));

    entity.setImage(dto.getImage());
    entity.setCafeId(cafeId);  // 명시적 설정

    CafeMenuRepository.save(entity);
  }

  @Override
  public boolean deleteMenu(Long cafeId, Long menuId) {
    CafeMenuRepository.delete(CafeMenuRepository.getByCafeIdAndMenuId(cafeId, menuId));
    return true;
  }

}
