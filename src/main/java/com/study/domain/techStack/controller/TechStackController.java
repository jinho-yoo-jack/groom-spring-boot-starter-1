package com.study.domain.techStack.controller;

import com.study.domain.techStack.dto.request.TechStackCreateRequest;
import com.study.domain.techStack.dto.request.TechStackUpdateRequest;
import com.study.domain.techStack.dto.response.TechStackResponse;
import com.study.domain.techStack.service.TechStackService;
import com.study.global.common.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profiles/{profileId}/tech-stacks")
public class TechStackController {

    private final TechStackService service;

    public TechStackController(TechStackService service) {
        this.service = service;
    }

    // POST /api/v1/profiles/{profileId}/tech-stacks
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TechStackResponse create(
            @PathVariable Long profileId,
            @RequestBody TechStackCreateRequest request
    ) {
        return service.createTechStack(profileId, request);
    }

    // GET /api/v1/profiles/{profileId}/tech-stacks (paging)
    @GetMapping
    public Page<TechStackResponse> list(
            @PathVariable Long profileId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return service.getTechStacks(profileId, page, size);
    }

    // GET /api/v1/profiles/{profileId}/tech-stacks/{id}
    @GetMapping("/{id}")
    public TechStackResponse getOne(
            @PathVariable Long profileId,
            @PathVariable Long id
    ) {
        return service.getTechStack(profileId, id);
    }

    // PUT /api/v1/profiles/{profileId}/tech-stacks/{id}
    @PutMapping("/{id}")
    public TechStackResponse update(
            @PathVariable Long profileId,
            @PathVariable Long id,
            @RequestBody TechStackUpdateRequest request
    ) {
        return service.updateTechStack(profileId, id, request);
    }

    // DELETE /api/v1/profiles/{profileId}/tech-stacks/{id}
    @DeleteMapping("/{id}")
    public TechStackResponse delete(
            @PathVariable Long profileId,
            @PathVariable Long id
    ) {
        return service.deleteTechStack(profileId, id);
    }
}
