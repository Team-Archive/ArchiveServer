package com.depromeet.archive.infra.cloud.aws;

public class FileInvalidException extends RuntimeException {

    public FileInvalidException(String message) {
        super(message);
    }

}
