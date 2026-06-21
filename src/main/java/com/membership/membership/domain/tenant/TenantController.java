package com.membership.membership.domain.tenant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.membership.membership.domain.tenant.dto.TenantMapper;
import com.membership.membership.domain.tenant.dto.TenantRequest;
import com.membership.membership.domain.tenant.dto.TenantResponse;
import com.membership.membership.domain.tenant.dto.TenantStatusRequest;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/tenant")
public class TenantController {

    @Autowired
    private TenantService tenantService;

    @GetMapping("/getAllTenant")
    public ResponseEntity<List<TenantResponse>> getAllTenant(){
        return new ResponseEntity<>(tenantService.getAllTenant().stream()
                .map(TenantMapper::toResponse)
                .collect(java.util.stream.Collectors.toList()), HttpStatus.OK);
    }

    @GetMapping("/getTenantById/{id}")
    public ResponseEntity<TenantResponse> getTenantById(@PathVariable Long id){
        return new ResponseEntity<>(TenantMapper.toResponse(tenantService.getTenantById(id)), HttpStatus.OK);
    }

    @PostMapping("/createTenant")
    public ResponseEntity<TenantResponse> createTenant(@Valid  @RequestBody TenantRequest tenant){
        return  new ResponseEntity<>(TenantMapper.toResponse(tenantService.createTenant(tenant)), HttpStatus.CREATED);
    }

    @PutMapping("/updateTenant/{id}")
    public ResponseEntity<TenantResponse> updateTenant(@PathVariable Long id, @RequestBody TenantRequest tenant){
        return new ResponseEntity<>(TenantMapper.toResponse(tenantService.updateTenant(id, tenant)), HttpStatus.OK);
    }

    @PutMapping("/activateDeactivateTenant/{id}")
    public ResponseEntity<String> activateDeactivateTenant(@Valid @PathVariable Long id, @RequestBody TenantStatusRequest status){
        tenantService.activateDeactivateTenant(id, status);
        return new ResponseEntity<>("Tenant status updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/deleteTenant/{id}")
    public ResponseEntity<String> deleteTenant(@PathVariable Long id){
        tenantService.deleteTenant(id);
        return new ResponseEntity<>("Successfully deleted!", HttpStatus.OK);
    }

}
