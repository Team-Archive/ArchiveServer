package com.depromeet.archive.controller;

import com.depromeet.archive.controller.dto.archive.ArchiveDto;
import com.depromeet.archive.controller.dto.archive.ArchiveListDto;
import com.depromeet.archive.domain.archive.service.ArchiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/archive")
@RequiredArgsConstructor
public class ArchiveController {

    private final ArchiveService archiveService;

    @GetMapping
    public ResponseEntity<ArchiveListDto> archiveListView() {
        return ResponseEntity.ok(archiveService.getAllArchive());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArchiveDto> archiveSpecificView(@PathVariable Long id) {
        return ResponseEntity.ok(archiveService.getOneArchiveById(id));
    }

}