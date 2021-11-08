package com.depromeet.archive.domain.archive.service;

import com.depromeet.archive.controller.dto.archive.ArchiveDto;
import com.depromeet.archive.domain.archive.entity.Archive;
import com.depromeet.archive.domain.archive.ArchiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArchiveService {

    private final ArchiveRepository archiveRepository;

    @Transactional(readOnly = true)
    public ArchiveDto findById(Long id) {
        Archive archive = archiveRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아카이브가 없습니다."));
        return ArchiveDto.from(archive);
    }
}