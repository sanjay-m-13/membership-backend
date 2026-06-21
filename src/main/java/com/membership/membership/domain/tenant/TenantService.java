package com.membership.membership.domain.tenant;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.membership.membership.domain.tenant.dto.TenantRequest;
import com.membership.membership.domain.tenant.dto.TenantStatusRequest;
import com.membership.membership.infrastructure.exception.DuplicateResourceException;
import com.membership.membership.infrastructure.exception.ResourceNotFoundException;

@Service
public class TenantService {

    @Autowired
    private TenantRepo tenantRepo;

    public List<Tenant> getAllTenant(){
        return tenantRepo.findAll();
    }

    public Tenant getTenantById(Long id){
        return tenantRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException(
                        "Tenant not found with id: " + id
                )
        );
    }

    public Tenant createTenant(TenantRequest tenant){

        if (tenantRepo.existsByName(tenant.getName())){
            throw new DuplicateResourceException(
                    "Name already exists: " + tenant.getName()
            );
        }

        Tenant tenant1 = new Tenant();
        tenant1.setName(tenant.getName());
        tenant1.setSubDomain(tenant.getSubDomain());
        tenant1.setStatus(tenant.getStatus());

        return tenantRepo.save(tenant1);
    }

    public Tenant updateTenant(Long id, TenantRequest request) {

        Tenant tenant = tenantRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Tenant not found with id: " + id));

        if (!Objects.equals(tenant.getName(), request.getName())
                && tenantRepo.existsByNameAndIdNot(request.getName(), id)){
            throw new DuplicateResourceException("Tenant name already exists: " + request.getName());
        }

        tenant.setName(request.getName());
        tenant.setSubDomain(request.getSubDomain());
        tenant.setStatus(request.getStatus());

        return tenantRepo.save(tenant);
    }

    public void activateDeactivateTenant(Long id, TenantStatusRequest status) {

        Tenant tenant = getTenantById(id);


        if (!status.getStatus().equalsIgnoreCase("ACTIVE")
                && !status.getStatus().equalsIgnoreCase("INACTIVE")) {
            throw new IllegalArgumentException(
                    "Status must be ACTIVE or INACTIVE");
        }

        tenant.setStatus(status.getStatus().toUpperCase());

        tenantRepo.save(tenant);
    }

    public void deleteTenant(Long id){
        getTenantById(id);
        tenantRepo.deleteById(id);
    }

}
