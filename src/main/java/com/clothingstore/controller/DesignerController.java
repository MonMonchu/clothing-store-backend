package com.clothingstore.controller;

import com.clothingstore.dto.designer.*;
import com.clothingstore.dto.product.ProductResponse;
import com.clothingstore.service.DesignerService;
import com.clothingstore.service.ProductService;


import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;


import java.util.UUID;

@RestController
@RequestMapping("/designers")
public class DesignerController {
    private final DesignerService designerService;
    private final ProductService productService;

    public DesignerController(DesignerService designerService, ProductService productService) {
        this.designerService = designerService;
        this.productService = productService;
    }




    @PostMapping("/admin")
    @ResponseStatus(HttpStatus.CREATED)
    public DesignerAdminResponse addDesigner(@Valid @RequestBody CreateDesignerRequest dto) {
        return designerService.addDesigner(dto);
    }
    @PatchMapping("/admin/{id}")
    public DesignerAdminResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateDesignerRequest dto){
        return designerService.updateDesigner(id,dto);
    }

    @DeleteMapping("/admin/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        designerService.deleteDesigner(id);
    }

    @PatchMapping("/admin/{id}/archive")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void archive(@PathVariable UUID id) {
        designerService.archiveDesigner(id);
    }

    @PatchMapping("/admin/{id}/activate")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void activate(@PathVariable UUID id) {
        designerService.activateDesigner(id);
    }

    @GetMapping("/active")
    public List<DesignerPublicResponse> getAllActiveDesignersPublic() {
        return designerService.getAllActiveDesignersPublic();
    }

    @GetMapping("/{id}")
    public DesignerPublicResponse getByIdPublic(@PathVariable UUID id) {
        return designerService.getDesignerByIdPublic(id);
    }

    @GetMapping("/admin")
    public List<DesignerAdminResponse> getAllAdminDesigners() {
        return designerService.getAllAdminDesigners();
    }

    @GetMapping("/admin/active")
    public List<DesignerAdminResponse> getAllActiveDesignersAdmin() {
        return designerService.getAllActiveDesignersAdmin();
    }

    @GetMapping("/admin/{id}")
    public DesignerAdminResponse getByIdAdmin(@PathVariable UUID id){
        return designerService.getDesignerByIdAdmin(id);

    }

    @GetMapping("/{id}/products")
    public List<ProductResponse> getProductsByDesigner(@PathVariable UUID id) {
        return productService.getProductsByDesignerId(id);
    }

}









