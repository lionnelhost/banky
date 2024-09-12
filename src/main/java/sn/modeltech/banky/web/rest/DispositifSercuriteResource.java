package sn.modeltech.banky.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sn.modeltech.banky.repository.DispositifSercuriteRepository;
import sn.modeltech.banky.service.DispositifSercuriteService;
import sn.modeltech.banky.service.dto.DispositifSercuriteDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.DispositifSercurite}.
 */
@RestController
@RequestMapping("/api/dispositif-sercurites")
public class DispositifSercuriteResource {

    private static final Logger LOG = LoggerFactory.getLogger(DispositifSercuriteResource.class);

    private static final String ENTITY_NAME = "dispositifSercurite";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DispositifSercuriteService dispositifSercuriteService;

    private final DispositifSercuriteRepository dispositifSercuriteRepository;

    public DispositifSercuriteResource(
        DispositifSercuriteService dispositifSercuriteService,
        DispositifSercuriteRepository dispositifSercuriteRepository
    ) {
        this.dispositifSercuriteService = dispositifSercuriteService;
        this.dispositifSercuriteRepository = dispositifSercuriteRepository;
    }

    /**
     * {@code POST  /dispositif-sercurites} : Create a new dispositifSercurite.
     *
     * @param dispositifSercuriteDTO the dispositifSercuriteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dispositifSercuriteDTO, or with status {@code 400 (Bad Request)} if the dispositifSercurite has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DispositifSercuriteDTO> createDispositifSercurite(@RequestBody DispositifSercuriteDTO dispositifSercuriteDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save DispositifSercurite : {}", dispositifSercuriteDTO);
        if (dispositifSercuriteDTO.getId() != null) {
            throw new BadRequestAlertException("A new dispositifSercurite cannot already have an ID", ENTITY_NAME, "idexists");
        }
        dispositifSercuriteDTO = dispositifSercuriteService.save(dispositifSercuriteDTO);
        return ResponseEntity.created(new URI("/api/dispositif-sercurites/" + dispositifSercuriteDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dispositifSercuriteDTO.getId().toString()))
            .body(dispositifSercuriteDTO);
    }

    /**
     * {@code PUT  /dispositif-sercurites/:id} : Updates an existing dispositifSercurite.
     *
     * @param id the id of the dispositifSercuriteDTO to save.
     * @param dispositifSercuriteDTO the dispositifSercuriteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispositifSercuriteDTO,
     * or with status {@code 400 (Bad Request)} if the dispositifSercuriteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dispositifSercuriteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DispositifSercuriteDTO> updateDispositifSercurite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DispositifSercuriteDTO dispositifSercuriteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DispositifSercurite : {}, {}", id, dispositifSercuriteDTO);
        if (dispositifSercuriteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dispositifSercuriteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispositifSercuriteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        dispositifSercuriteDTO = dispositifSercuriteService.update(dispositifSercuriteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dispositifSercuriteDTO.getId().toString()))
            .body(dispositifSercuriteDTO);
    }

    /**
     * {@code PATCH  /dispositif-sercurites/:id} : Partial updates given fields of an existing dispositifSercurite, field will ignore if it is null
     *
     * @param id the id of the dispositifSercuriteDTO to save.
     * @param dispositifSercuriteDTO the dispositifSercuriteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispositifSercuriteDTO,
     * or with status {@code 400 (Bad Request)} if the dispositifSercuriteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dispositifSercuriteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dispositifSercuriteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DispositifSercuriteDTO> partialUpdateDispositifSercurite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody DispositifSercuriteDTO dispositifSercuriteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DispositifSercurite partially : {}, {}", id, dispositifSercuriteDTO);
        if (dispositifSercuriteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, dispositifSercuriteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispositifSercuriteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DispositifSercuriteDTO> result = dispositifSercuriteService.partialUpdate(dispositifSercuriteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dispositifSercuriteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /dispositif-sercurites} : get all the dispositifSercurites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dispositifSercurites in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DispositifSercuriteDTO>> getAllDispositifSercurites(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of DispositifSercurites");
        Page<DispositifSercuriteDTO> page = dispositifSercuriteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dispositif-sercurites/:id} : get the "id" dispositifSercurite.
     *
     * @param id the id of the dispositifSercuriteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dispositifSercuriteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DispositifSercuriteDTO> getDispositifSercurite(@PathVariable("id") Long id) {
        LOG.debug("REST request to get DispositifSercurite : {}", id);
        Optional<DispositifSercuriteDTO> dispositifSercuriteDTO = dispositifSercuriteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dispositifSercuriteDTO);
    }

    /**
     * {@code DELETE  /dispositif-sercurites/:id} : delete the "id" dispositifSercurite.
     *
     * @param id the id of the dispositifSercuriteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDispositifSercurite(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete DispositifSercurite : {}", id);
        dispositifSercuriteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
