package com.depromeet.archive.domain.archive.service;

import com.depromeet.archive.controller.dto.archive.ArchiveDto;
import com.depromeet.archive.controller.dto.archive.ArchiveListDto;
import com.depromeet.archive.domain.archive.ArchiveRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArchiveService {

    private final ArchiveRepository archiveRepository;

    public ArchiveListDto getAllArchive() {
        var archiveDtos = archiveRepository.findAll().stream()
                .map(ArchiveDto::simpleFrom)
                .collect(Collectors.toList());
        return ArchiveListDto.from(archiveDtos);
    }

    public ArchiveDto getOneArchiveById(Long id) {
        var archive = archiveRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 아카이브가 없습니다."));
        return ArchiveDto.specificFrom(archive);
    }

}