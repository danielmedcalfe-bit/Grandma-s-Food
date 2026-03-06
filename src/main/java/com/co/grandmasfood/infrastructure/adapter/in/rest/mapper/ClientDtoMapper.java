package com.co.grandmasfood.infrastructure.adapter.in.rest.mapper;

import com.co.grandmasfood.application.port.in.client.CreateClientCommand;
import com.co.grandmasfood.application.port.in.client.UpdateClientCommand;
import com.co.grandmasfood.domain.model.Client;
import com.co.grandmasfood.domain.valueobject.DocumentNumber;
import com.co.grandmasfood.domain.valueobject.Email;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ClientRequestDto;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.ClientResponseDto;
import com.co.grandmasfood.infrastructure.adapter.in.rest.dto.UpdateClientRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ClientDtoMapper {

    public CreateClientCommand toCreateCommand(ClientRequestDto dto) {
        return CreateClientCommand.builder()
                .document(new DocumentNumber(dto.getDocument()))
                .name(dto.getName())
                .email(new Email(dto.getEmail()))
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .build();
    }


    public UpdateClientCommand toUpdateCommand(UpdateClientRequestDto dto) {


        return UpdateClientCommand.builder()
                .name(dto.getName())
                .email(dto.getEmail() != null ? new Email(dto.getEmail()) : null)
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .build();
    }


    public ClientResponseDto toResponseDto(Client client) {
        return ClientResponseDto.builder()
                .document(client.getDocument().getValue())
                .name(client.getName())
                .email(client.getEmail().getValue())
                .phone(client.getPhone())
                .address(client.getAddress())
                .build();
    }

}