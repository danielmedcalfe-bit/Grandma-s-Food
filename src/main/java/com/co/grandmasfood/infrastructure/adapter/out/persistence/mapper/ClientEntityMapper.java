package com.co.grandmasfood.infrastructure.adapter.out.persistence.mapper;

import com.co.grandmasfood.domain.model.Client;
import com.co.grandmasfood.domain.valueobject.DocumentNumber;
import com.co.grandmasfood.domain.valueobject.Email;
import com.co.grandmasfood.infrastructure.adapter.out.persistence.entity.ClientEntity;
import org.springframework.stereotype.Component;

@Component
public class ClientEntityMapper {

    public ClientEntity toEntity(Client domain) {
        return ClientEntity.builder()
                .document(domain.getDocument().getValue())
                .name(domain.getName())
                .email(domain.getEmail().getValue())
                .phone(domain.getPhone())
                .address(domain.getAddress())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    public Client toDomain(ClientEntity entity) {
        return Client.builder()
                .document(new DocumentNumber(entity.getDocument()))
                .name(entity.getName())
                .email(new Email(entity.getEmail()))
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}