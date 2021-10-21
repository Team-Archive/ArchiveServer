package com.depromeet.archive.domain.archive.entity;

import com.depromeet.archive.domain.common.BaseTimeEntity;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ArchiveImageEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long archiveImageId;

    private String image;
    private String review;
}
