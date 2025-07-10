package com.oopsw.seongsubean_cafemenu_backend.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.oopsw.seongsubean_cafemenu_backend.dto.CafeMenuDto;
import com.oopsw.seongsubean_cafemenu_backend.jpa.CafeMenuEntity;
import com.oopsw.seongsubean_cafemenu_backend.repository.CafeMenuRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
@Transactional
public class CafeMenuServiceTests {
  @Autowired
  private CafeMenuImpl cafeMenu;
  @Autowired
  private CafeMenuRepository cafeMenuRepository;
  @Autowired
  private CafeMenuService cafeMenuService;

  @Test
  public void CafeMenuServiceTests_Success() {
    //given
    CafeMenuDto cafeMenuDto = CafeMenuDto.builder()
        .menuCategory("빵")
        .menuName("마늘빵")
        .menuIntroduction("맛있는마늘빵")
        .price("1000")
        .image("image")
        .cafeId(1L)
        .build();
    //when
    cafeMenu.addMenu(cafeMenuDto);

    //then
    CafeMenuEntity entity = cafeMenuRepository.findById(1L)
        .orElseThrow(() -> new AssertionError("저장된 엔티티를 못 찾음"));
    assertThat(entity.getMenuName()).isEqualTo("마늘빵");
  }


  @Test
  public void CafeMenuServiceTests_Failure() {
    //given
    CafeMenuDto cafeMenuDto = CafeMenuDto.builder()
        .menuCategory("고기")
        .menuName("돼지고기")
        .menuIntroduction("도야지고기굿")
        .price(null)
        .build();

    assertThatThrownBy(() ->  cafeMenu.addMenu(cafeMenuDto))
        .isInstanceOf(DataIntegrityViolationException.class);
  }


  @Test
  public void CafeGetMenuServiceTests_Success(){
    CafeMenuEntity seed = CafeMenuEntity.builder()
        .menuCategory("빵")
        .menuName("마늘빵")
        .menuIntroduction("맛있는마늘빵")
        .price("1000")
        .image("image")
        .cafeId(1L)
        .build();
    CafeMenuEntity saved = cafeMenuRepository.save(seed);

    // 2) when: 서비스 메서드에 올바른 파라미터 전달
    CafeMenuDto resultDto = cafeMenu.getMenuInfo(
        saved.getCafeId(),
        saved.getMenuId()
    );

    Assertions.assertThat(resultDto).isNotNull();

  }


  @Test
  public void CafeGetMenuServiceTests_Failure(){
    long noCafe = 9999L;
    long noMenu = 8888L;

    assertThatThrownBy(() ->
        cafeMenu.getMenuInfo(noCafe, noMenu)
    )
        .isInstanceOf(IllegalArgumentException.class);
}


  @Test
  public void CafeMenuEditServiceTests_Success() {
    //given
    // 1) 미리 저장 (INSERT 역할)
    CafeMenuEntity seed = CafeMenuEntity.builder()
        .menuCategory("디저트")
        .menuName("초기크로플")
        .menuIntroduction("처음엔 이걸로")
        .image("init.jpg")
        .price("10000")
        .cafeId(1L)
        .build();
    seed = cafeMenuRepository.saveAndFlush(seed);
    //when
    // 2) 수정 요청용 DTO 준비
    CafeMenuDto dto = new CafeMenuDto();
    dto.setMenuCategory("디저트");
    dto.setMenuName("크로플");
    dto.setMenuIntroduction("맛있는크로플");
    dto.setImage("updated.jpg");
    dto.setPrice("12000");
    // (dto 에 cafeId, menuId 필드가 있다면 세팅해도 되고, service 메서드 파라미터로 넘겨도 됩니다)

    // 3) 서비스 호출
    CafeMenuDto result = cafeMenuService.editMenu(
        seed.getCafeId(),
        seed.getMenuId(),
        dto
    );
    //then
    // 4) 리턴된 DTO 검증
    Assertions.assertThat(result).isNotNull();
    Assertions.assertThat(result.getMenuName()).isEqualTo(dto.getMenuName());

    // 5) DB 에 진짜 반영되었는지 확인
    CafeMenuEntity updated = cafeMenuRepository.findById(seed.getMenuId())
        .orElseThrow(() -> new AssertionError("업데이트된 엔티티가 없습니다!"));
    Assertions.assertThat(updated.getMenuName()).isEqualTo("크로플");
    Assertions.assertThat(updated.getPrice()).isEqualTo("12000");
  }


  @Test
  public void CafeMenuDeleteServiceTests_Success() {
    //given
    // 1) 미리 저장 (INSERT 역할)
    CafeMenuEntity seed = CafeMenuEntity.builder()
        .menuCategory("디저트")
        .menuName("초기크로플")
        .menuIntroduction("처음엔 이걸로")
        .image("init.jpg")
        .price("10000")
        .cafeId(1L)
        .build();
    seed = cafeMenuRepository.saveAndFlush(seed);

    boolean result = cafeMenuService.deleteMenu(seed.getCafeId(),seed.getMenuId());


  }

}
