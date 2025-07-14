package com.oopsw.seongsubean_cafemenu_backend.repository;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.oopsw.seongsubean_cafemenu_backend.jpa.CafeMenuEntity;
import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@Slf4j
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
@Transactional
public class CafeMenuRepositoryTests {

@Autowired
CafeMenuRepository cafeMenuRepository;

  @Test
  @Order(1)
  public void cafeAddMenuTest_Success() {
    //given
    CafeMenuEntity cafeMenuEntity = CafeMenuEntity.builder()
        .menuCategory("빵")
        .menuName("크루와상")
        .menuIntroduction("맛있는빵")
        .price(new BigDecimal(2100))
        .image("image1")
        .cafeId(1L)
        .build();
    //when
    CafeMenuEntity resultCafeMenuEntity = cafeMenuRepository.save(cafeMenuEntity);

    //then
    Assertions.assertThat(resultCafeMenuEntity).isNotNull();
  }

  @Test
  public void cafeAddMenuTest_Failure() {
    //given
    CafeMenuEntity cafeMenuEntity = CafeMenuEntity.builder()
        .menuCategory("빵")
        .menuName("크루와상")
        .menuIntroduction("맛있는빵")
        .image("image1")
        .cafeId(1L)
        .build();
    //when
    //then
    assertThatThrownBy(() -> cafeMenuRepository.save(cafeMenuEntity))
        .isInstanceOf(DataIntegrityViolationException.class);
  }

  @Test
  public void cafeGetMenuInfoTest_Success() {
    //given
    CafeMenuEntity cafeMenuEntity = CafeMenuEntity.builder()
        .menuCategory("디저트")
        .menuName("크로플")
        .menuIntroduction("맛있는크로플")
        .image("ddd")
        .price(new BigDecimal(12000))
        .cafeId(1L)
        .build();

    //when
    CafeMenuEntity resultCafeMenuEntity = cafeMenuRepository.save(cafeMenuEntity);
    //then
    Assertions.assertThat(cafeMenuRepository.getByCafeIdAndMenuId(resultCafeMenuEntity.getCafeId(),
        resultCafeMenuEntity.getMenuId())).isNotNull();
  }

  @Test
  public void cafeGetMenuInfoTest_failure() {
    //given
    CafeMenuEntity cafeMenuEntity = CafeMenuEntity.builder()
        .menuCategory("디저트")
        .menuName("크로플")
        .menuIntroduction("맛있는크로플")
        .price(null)
        .build();

    assertThatThrownBy(() -> {
      cafeMenuRepository.save(cafeMenuEntity);
      cafeMenuRepository.flush();           // 반드시 flush() 로 즉시 DB에 반영해야 예외 발생
    }).isInstanceOf(DataIntegrityViolationException.class);
    log.info(cafeMenuEntity.toString());
  }

  @Test
  @Order(2)
  public void cafeEditMenuTest_Success() {
    // 1) 미리 저장 (INSERT 역할)
    CafeMenuEntity seed = CafeMenuEntity.builder()
        .menuCategory("디저트")
        .menuName("초기크로플")
        .menuIntroduction("처음엔 이걸로")
        .image("init.jpg")
        .price(new BigDecimal(10000))
        .cafeId(1L)
        // menuId 는 IDENTITY 전략이면 빼거나 null 로 둡니다.
        .build();
    CafeMenuEntity saved = cafeMenuRepository.save(seed);

    // 2) 조회 (실제 업데이트 대상)
    CafeMenuEntity cafeMenuEntity = cafeMenuRepository.findById(saved.getMenuId())
        .orElseThrow(() -> new AssertionError("저장한 엔티티가 조회되지 않습니다!"));
    // when: 변경
    cafeMenuEntity.setMenuCategory("디저트");
    cafeMenuEntity.setMenuName("크로플");
    cafeMenuEntity.setMenuIntroduction("맛있는크로플");
    cafeMenuEntity.setImage("ddd");
    cafeMenuEntity.setPrice(new BigDecimal(12000));

    CafeMenuEntity resultCafeMenuEntity = cafeMenuRepository.save(cafeMenuEntity);
    //then
    Assertions.assertThat(resultCafeMenuEntity).isNotNull();

  }


  @Test
  public void cafeEditMenuTest_failure() {
    // 1) 미리 저장 (INSERT 역할)
    CafeMenuEntity seed = CafeMenuEntity.builder()
        .menuCategory("디저트")
        .menuName("초기크로플")
        .menuIntroduction("처음엔 이걸로")
        .image("init.jpg")
        .price(new BigDecimal(10000))
        .cafeId(1L)
        // menuId 는 IDENTITY 전략이면 빼거나 null 로 둡니다.
        .build();
    CafeMenuEntity saved = cafeMenuRepository.save(seed);

    // 2) 조회 (실제 업데이트 대상)
    CafeMenuEntity cafeMenuEntity = cafeMenuRepository.findById(saved.getMenuId())
        .orElseThrow(() -> new AssertionError("저장한 엔티티가 조회되지 않습니다!"));
    // when: 변경
    cafeMenuEntity.setMenuCategory(null);
    cafeMenuEntity.setMenuName(null);
    cafeMenuEntity.setMenuIntroduction(null);
    cafeMenuEntity.setImage(null);
    cafeMenuEntity.setPrice(null);
    cafeMenuEntity.setCafeId(null);

    CafeMenuEntity resultCafeMenuEntity = cafeMenuRepository.save(cafeMenuEntity);
//    then
    assertThatThrownBy(() -> cafeMenuRepository.saveAndFlush(cafeMenuEntity))
        .isInstanceOf(DataIntegrityViolationException.class);

  }


  @Test
  public void cafeDeleteMenuTest_Success() {
    // 1) 미리 저장 (INSERT 역할)
    CafeMenuEntity seed = CafeMenuEntity.builder()
        .menuCategory("디저트")
        .menuName("초기크로플")
        .menuIntroduction("처음엔 이걸로")
        .image("init.jpg")
        .price(new BigDecimal("10000"))
        .cafeId(1L)
        // menuId 는 IDENTITY 전략이면 빼거나 null 로 둡니다.
        .build();
    CafeMenuEntity saved = cafeMenuRepository.save(seed);
    CafeMenuEntity cafeMenuEntity = cafeMenuRepository.findById(saved.getMenuId())
        .orElseThrow(() -> new AssertionError("저장한 엔티티가 조회되지 않습니다!"));

    // 3) 삭제
    cafeMenuRepository.deleteById(cafeMenuEntity.getMenuId());
    // (optional) 즉시 DB 반영을 위해 flush
    cafeMenuRepository.flush();

    // 4) 검증: 더 이상 존재하지 않아야 한다
    Assertions.assertThat(cafeMenuRepository.findById(cafeMenuEntity.getMenuId())).isEmpty();
  }
}

