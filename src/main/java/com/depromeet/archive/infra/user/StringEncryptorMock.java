package com.depromeet.archive.infra.user;

import com.depromeet.archive.domain.user.StringEncryptor;

public class StringEncryptorMock implements StringEncryptor {

    @Override
    public String encrypt(String unencrypted) {
        return unencrypted;
    }
}
