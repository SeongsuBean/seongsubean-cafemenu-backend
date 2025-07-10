package com.oopsw.seongsubean_cafemenu_backend.repository;

import com.oopsw.seongsubean_cafemenu_backend.jpa.CafeMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CafeMenuRepository extends JpaRepository<CafeMenuEntity, Long> {
  CafeMenuEntity getByCafeIdAndMenuId(Long cafeId, Long menuId);
}
