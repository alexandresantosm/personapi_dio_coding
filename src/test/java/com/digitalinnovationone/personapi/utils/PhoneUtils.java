package com.digitalinnovationone.personapi.utils;

import com.digitalinnovationone.personapi.dto.request.PhoneDTO;
import com.digitalinnovationone.personapi.entity.Phone;
import com.digitalinnovationone.personapi.enums.PhoneType;

public class PhoneUtils {

    private static final long PHONE_ID = 1L;
    private static final String PHONE_NUMBER = "(11)999999999";
    private static final PhoneType PHONE_TYPE = PhoneType.MOBILE;

    public static PhoneDTO createFakePhoneDTO() {
        return PhoneDTO.builder()
                .number(PHONE_NUMBER)
                .type(PHONE_TYPE)
                .build();
    }

    public static final Phone createFakePhoneEntity() {
        return Phone.builder()
                .id(PHONE_ID)
                .number(PHONE_NUMBER)
                .type(PHONE_TYPE)
                .build();
    }
}
