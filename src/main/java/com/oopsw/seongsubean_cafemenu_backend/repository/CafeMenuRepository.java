package com.oopsw.seongsubean_cafemenu_backend.repository;

import com.oopsw.seongsubean_cafemenu_backend.jpa.CafeMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CafeMenuRepository extends JpaRepository<CafeMenuEntity, Long> {
  CafeMenuEntity getByCafeIdAndMenuId(Long cafeId, Long menuId);
  List<CafeMenuEntity> findAllByCafeId(Long cafeId);


  /** ① 메인 카테고리(커피/빵/케이크/파이)와 정확히 일치하는 경우 */
  @Query("SELECT DISTINCT c.cafeId " +
      "FROM   CafeMenuEntity c " +
      "WHERE  c.menuCategory = :menuCategory")
  List<Long> findCafeIdsByMenuCategory(@Param("menuCategory") String menuCategory);

  /** ② 메인 네 카테고리를 제외한 ‘기타’ */
  @Query("SELECT DISTINCT c.cafeId " +
      "FROM   CafeMenuEntity c " +
      "WHERE  c.menuCategory NOT IN :mainCategories")
  List<Long> findCafeIdsForOthers(@Param("mainCategories") List<String> mainCategories);

  /** ③ 단일 메서드로 ‘기타’/기타 카테고리 처리 */
  @Query("SELECT DISTINCT c.cafeId " +
      "FROM   CafeMenuEntity c " +
      "WHERE  ( :menuCategory = '기타' AND c.menuCategory NOT IN :mainCategories ) " +
      "   OR ( :menuCategory <> '기타' AND c.menuCategory = :menuCategory )")
  List<Long> findCafeIdsByCategoryConditional(
      @Param("menuCategory")    String menuCategory,
      @Param("mainCategories") List<String> mainCategories
  );

}
