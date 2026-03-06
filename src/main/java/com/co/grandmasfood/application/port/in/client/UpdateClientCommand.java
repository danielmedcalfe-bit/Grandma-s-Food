package com.co.grandmasfood.application.port.in.client;

import com.co.grandmasfood.domain.valueobject.Email;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateClientCommand {

    String name;
    Email email;
    String phone;
    String address;

    public boolean hasChanges() {
        return name != null || email != null || phone != null || address != null;
    }
}