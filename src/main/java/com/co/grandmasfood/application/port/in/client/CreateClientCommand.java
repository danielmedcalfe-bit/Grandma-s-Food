package com.co.grandmasfood.application.port.in.client;

import com.co.grandmasfood.domain.valueobject.DocumentNumber;
import com.co.grandmasfood.domain.valueobject.Email;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateClientCommand {
    DocumentNumber document;
    String name;
    Email email;
    String phone;
    String address;
}