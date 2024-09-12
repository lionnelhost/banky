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
import sn.modeltech.banky.repository.AgenceRepository;
import sn.modeltech.banky.service.AgenceService;
import sn.modeltech.banky.service.dto.AgenceDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.Agence}.
 */
@RestController
@RequestMapping("/api/agences")
public class AgenceResource {

    private static final Logger LOG = LoggerFactory.getLogger(AgenceResource.class);

    private static final String ENTITY_NAME = "agence";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgenceService agenceService;

    private final AgenceRepository agenceRepository;

    public AgenceResource(AgenceService agenceService, AgenceRepository agenceRepository) {
        this.agenceService = agenceService;
        this.agenceRepository = agenceRepository;
    }

    /**
     * {@code POST  /agences} : Create a new agence.
     *
     * @param agenceDTO the agenceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agenceDTO, or with status {@code 400 (Bad Request)} if the agence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<AgenceDTO> createAgence(@RequestBody AgenceDTO agenceDTO) throws URISyntaxException {
        LOG.debug("REST request to save Agence : {}", agenceDTO);
        if (agenceRepository.existsById(agenceDTO.getIdAgence())) {
            throw new BadRequestAlertException("agence already exists", ENTITY_NAME, "idexists");
        }
        agenceDTO = agenceService.save(agenceDTO);
        return ResponseEntity.created(new URI("/api/agences/" + agenceDTO.getIdAgence()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, agenceDTO.getIdAgence()))
            .body(agenceDTO);
    }

    /**
     * {@code PUT  /agences/:idAgence} : Updates an existing agence.
     *
     * @param idAgence the id of the agenceDTO to save.
     * @param agenceDTO the agenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agenceDTO,
     * or with status {@code 400 (Bad Request)} if the agenceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{idAgence}")
    public ResponseEntity<AgenceDTO> updateAgence(
        @PathVariable(value = "idAgence", required = false) final String idAgence,
        @RequestBody AgenceDTO agenceDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Agence : {}, {}", idAgence, agenceDTO);
        if (agenceDTO.getIdAgence() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idAgence, agenceDTO.getIdAgence())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agenceRepository.existsById(idAgence)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        agenceDTO = agenceService.update(agenceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agenceDTO.getIdAgence()))
            .body(agenceDTO);
    }

    /**
     * {@code PATCH  /agences/:idAgence} : Partial updates given fields of an existing agence, field will ignore if it is null
     *
     * @param idAgence the id of the agenceDTO to save.
     * @param agenceDTO the agenceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agenceDTO,
     * or with status {@code 400 (Bad Request)} if the agenceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the agenceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the agenceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{idAgence}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AgenceDTO> partialUpdateAgence(
        @PathVariable(value = "idAgence", required = false) final String idAgence,
        @RequestBody AgenceDTO agenceDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Agence partially : {}, {}", idAgence, agenceDTO);
        if (agenceDTO.getIdAgence() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idAgence, agenceDTO.getIdAgence())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!agenceRepository.existsById(idAgence)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AgenceDTO> result = agenceService.partialUpdate(agenceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agenceDTO.getIdAgence())
        );
    }

    /**
     * {@code GET  /agences} : get all the agences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agences in body.
     */
    @GetMapping("")
    public ResponseEntity<List<AgenceDTO>> getAllAgences(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Agences");
        Page<AgenceDTO> page = agenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /agences/:id} : get the "id" agence.
     *
     * @param id the id of the agenceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agenceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AgenceDTO> getAgence(@PathVariable("id") String id) {
        LOG.debug("REST request to get Agence : {}", id);
        Optional<AgenceDTO> agenceDTO = agenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agenceDTO);
    }

    /**
     * {@code DELETE  /agences/:id} : delete the "id" agence.
     *
     * @param id the id of the agenceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAgence(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Agence : {}", id);
        agenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
