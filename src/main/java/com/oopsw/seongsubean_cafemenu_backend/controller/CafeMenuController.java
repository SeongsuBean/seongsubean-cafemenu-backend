package com.oopsw.seongsubean_cafemenu_backend.controller;

import com.oopsw.seongsubean_cafemenu_backend.dto.CafeMenuDto;
import com.oopsw.seongsubean_cafemenu_backend.service.CafeMenuService;
import com.oopsw.seongsubean_cafemenu_backend.vo.CafeMenuRequest;
import java.util.Map;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cafes")
public class CafeMenuController {


  private final CafeMenuService cafeMenuService;
  public CafeMenuController(CafeMenuService cafeMenuService) {
    this.cafeMenuService = cafeMenuService;
  }

  @PostMapping("/{cafeId}/menu")
  public ResponseEntity<Map<String,String>> addMenu(@PathVariable Long cafeId, @RequestBody CafeMenuRequest cafeMenuRequest) {
    //CafeMenuRequest로 받은 데이터 DTO로 변환
    CafeMenuDto cafeMenuDto = new ModelMapper().map(cafeMenuRequest, CafeMenuDto.class);
    cafeMenuDto.setCafeId(cafeId);
    //DTO를 Service에 전달
    cafeMenuService.addMenu(cafeMenuDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "생성이 완료되었습니다."));
  }

  @GetMapping("/{cafeId}/menu/{menuId}")
  public ResponseEntity getMenu(@PathVariable Long cafeId, @PathVariable Long menuId) {
    CafeMenuDto cafeMenuDto = new ModelMapper().map(cafeMenuService.getMenuInfo(cafeId, menuId), CafeMenuDto.class);

    return ResponseEntity.status(HttpStatus.OK).body(cafeMenuDto);
  }

  @PutMapping("/{cafeId}/menu/{menuId}")
  public ResponseEntity<Map<String,String>> editMenu(@PathVariable Long cafeId, @PathVariable Long menuId, @RequestBody CafeMenuRequest cafeMenuRequest ) {

    CafeMenuDto cafeMenuDto = new ModelMapper().map(cafeMenuRequest, CafeMenuDto.class);
    cafeMenuDto.setCafeId(cafeId);
    cafeMenuDto.setMenuId(menuId);
    cafeMenuService.editMenu(cafeId, menuId, cafeMenuDto);

    return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","수정이 완료되었습니다."));
  }

  @DeleteMapping("/{cafeId}/menu/{menuId}")
  public ResponseEntity<Map<String,String>> deleteMenu(@PathVariable Long cafeId, @PathVariable Long menuId) {
    boolean result = cafeMenuService.deleteMenu(cafeId,menuId);

    return ResponseEntity.status(HttpStatus.OK).body(Map.of("message","삭제가 완료되었습니다."));
  }


}