


package com.clothingstore.service;

import com.clothingstore.dto.designer.*;
import com.clothingstore.entity.Designer;
import com.clothingstore.mapper.DesignerMapper;
import com.clothingstore.repository.DesignerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.UUID;

@Service
public class DesignerService {
    private final DesignerRepository designerRepository;

    public DesignerService(DesignerRepository designerRepository) {
        this.designerRepository = designerRepository;
    }

    public DesignerAdminResponse addDesigner(CreateDesignerRequest dto) {
        String email = dto.email().trim();
        if (designerRepository.existsByEmailIgnoreCase(email)) {
            throw new IllegalArgumentException("Email already in use");
        }
        Designer designer = DesignerMapper.toEntity(dto);
        Designer saved = designerRepository.save(designer);
        return DesignerMapper.toAdminDto(saved);

    }

    public DesignerAdminResponse getDesignerByIdAdmin(UUID id) {
        Designer designer = getDesignerOrThrow(id);
        return DesignerMapper.toAdminDto(designer);

    }

    public DesignerPublicResponse getDesignerByIdPublic(UUID id) {
        Designer designer = getDesignerOrThrow(id);
        return DesignerMapper.toDto(designer);

    }
    @Transactional
    public DesignerAdminResponse updateDesigner(
            UUID id,
            UpdateDesignerRequest dto) {

        Designer designer = getDesignerOrThrow(id);
        if (dto.email() != null && !dto.email().isBlank()) {
            String email = dto.email().trim();
            if (designerRepository.existsByEmailIgnoreCase(email)
                    && !email.equalsIgnoreCase(designer.getEmail())) {
                throw new IllegalArgumentException("Email already in use");
            }
        }

        DesignerMapper.updateEntity(designer, dto);

        return DesignerMapper.toAdminDto(designer);
    }


    public void deleteDesigner(UUID id) {
        Designer designer = getDesignerOrThrow(id);

        designerRepository.delete(designer);

    }
    public List<DesignerPublicResponse> getAllPublicDesigners(){
        return designerRepository.findAll().stream().map(DesignerMapper::toDto).toList();
    }

    public List<DesignerAdminResponse> getAllAdminDesigners(){
        return designerRepository.findAll().stream().map(DesignerMapper::toAdminDto).toList();
    }

    @Transactional
    public void archiveDesigner(UUID id) {
        Designer designer = getDesignerOrThrow(id);

        designer.archive();
    }


    @Transactional
    public void activateDesigner(UUID id) {
        Designer designer = getDesignerOrThrow(id);

        designer.activate();
    }

    public List<DesignerPublicResponse> getAllActiveDesignersPublic() {
        return designerRepository.findAllByActiveTrue()
                .stream()
                .map(DesignerMapper::toDto)
                .toList();
    }
    public List<DesignerAdminResponse> getAllActiveDesignersAdmin() {
        return designerRepository.findAllByActiveTrue()
                .stream()
                .map(DesignerMapper::toAdminDto)
                .toList();
    }

    public Designer getDesignerOrThrow(UUID id){
        return designerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Designer not found"));

    }

}