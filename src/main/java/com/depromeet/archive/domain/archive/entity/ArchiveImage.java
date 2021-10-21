package com.depromeet.archive.domain.archive.entity;

import com.depromeet.archive.domain.common.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "archive_image")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ArchiveImage extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "archive_image_id")
    private Long id;

    @Column(name = "image")
    private String image;

    @Column(name = "review")
    private String review;

}