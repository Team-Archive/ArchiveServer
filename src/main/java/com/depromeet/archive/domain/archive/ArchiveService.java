package com.depromeet.archive.domain.archive;

import com.depromeet.archive.api.dto.archive.ArchiveDto;
import com.depromeet.archive.api.dto.archive.ArchiveListDto;
import com.depromeet.archive.common.exception.ResourceNotFoundException;
import com.depromeet.archive.domain.user.info.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArchiveService {

    private final ArchiveRepository archiveRepository;

    public ArchiveListDto getAllArchive(UserInfo info) {
        var authorId = info.getUserId();
        var archiveDtos = archiveRepository.findAllByAuthorId(authorId)
                .stream()
                .map(ArchiveDto::simpleFrom)
                .collect(Collectors.toList());
        return ArchiveListDto.from(archiveDtos);
    }

    public ArchiveDto getOneArchiveById(Long archiveId) {
        var archive = archiveRepository.findById(archiveId)
                .orElseThrow(() -> new ResourceNotFoundException("조회하려는 아카이브가 존재하지 않습니다"));
        return ArchiveDto.specificFrom(archive);
    }

    @Transactional
    public void delete(Long id) {
        var archive = archiveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("삭제하려는 아카이브가 존재하지 않습니다"));
        archiveRepository.delete(archive);
    }

    @Transactional
    public void save(ArchiveDto archiveDto) {
        var archive = archiveRepository.save(archiveDto.toEntity());
        archiveDto.getImages().stream()
                .map(archiveImageDto -> archiveImageDto.toEntity(archive))
                .forEach(archive::addImage);
    }

    public long countArchive(UserInfo info) {
        var authorId = info.getUserId();
        return archiveRepository.countArchiveByAuthorId(authorId);
    }

}