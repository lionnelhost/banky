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
import sn.modeltech.banky.repository.CanalRepository;
import sn.modeltech.banky.service.CanalService;
import sn.modeltech.banky.service.dto.CanalDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.Canal}.
 */
@RestController
@RequestMapping("/api/canals")
public class CanalResource {

    private static final Logger LOG = LoggerFactory.getLogger(CanalResource.class);

    private static final String ENTITY_NAME = "canal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CanalService canalService;

    private final CanalRepository canalRepository;

    public CanalResource(CanalService canalService, CanalRepository canalRepository) {
        this.canalService = canalService;
        this.canalRepository = canalRepository;
    }

    /**
     * {@code POST  /canals} : Create a new canal.
     *
     * @param canalDTO the canalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new canalDTO, or with status {@code 400 (Bad Request)} if the canal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CanalDTO> createCanal(@RequestBody CanalDTO canalDTO) throws URISyntaxException {
        LOG.debug("REST request to save Canal : {}", canalDTO);
        if (canalRepository.existsById(canalDTO.getIdCanal())) {
            throw new BadRequestAlertException("canal already exists", ENTITY_NAME, "idexists");
        }
        canalDTO = canalService.save(canalDTO);
        return ResponseEntity.created(new URI("/api/canals/" + canalDTO.getIdCanal()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, canalDTO.getIdCanal()))
            .body(canalDTO);
    }

    /**
     * {@code PUT  /canals/:idCanal} : Updates an existing canal.
     *
     * @param idCanal the id of the canalDTO to save.
     * @param canalDTO the canalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated canalDTO,
     * or with status {@code 400 (Bad Request)} if the canalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the canalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{idCanal}")
    public ResponseEntity<CanalDTO> updateCanal(
        @PathVariable(value = "idCanal", required = false) final String idCanal,
        @RequestBody CanalDTO canalDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Canal : {}, {}", idCanal, canalDTO);
        if (canalDTO.getIdCanal() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idCanal, canalDTO.getIdCanal())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!canalRepository.existsById(idCanal)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        canalDTO = canalService.update(canalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, canalDTO.getIdCanal()))
            .body(canalDTO);
    }

    /**
     * {@code PATCH  /canals/:idCanal} : Partial updates given fields of an existing canal, field will ignore if it is null
     *
     * @param idCanal the id of the canalDTO to save.
     * @param canalDTO the canalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated canalDTO,
     * or with status {@code 400 (Bad Request)} if the canalDTO is not valid,
     * or with status {@code 404 (Not Found)} if the canalDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the canalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{idCanal}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CanalDTO> partialUpdateCanal(
        @PathVariable(value = "idCanal", required = false) final String idCanal,
        @RequestBody CanalDTO canalDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Canal partially : {}, {}", idCanal, canalDTO);
        if (canalDTO.getIdCanal() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idCanal, canalDTO.getIdCanal())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!canalRepository.existsById(idCanal)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CanalDTO> result = canalService.partialUpdate(canalDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, canalDTO.getIdCanal())
        );
    }

    /**
     * {@code GET  /canals} : get all the canals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of canals in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CanalDTO>> getAllCanals(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of Canals");
        Page<CanalDTO> page = canalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /canals/:id} : get the "id" canal.
     *
     * @param id the id of the canalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the canalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CanalDTO> getCanal(@PathVariable("id") String id) {
        LOG.debug("REST request to get Canal : {}", id);
        Optional<CanalDTO> canalDTO = canalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(canalDTO);
    }

    /**
     * {@code DELETE  /canals/:id} : delete the "id" canal.
     *
     * @param id the id of the canalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCanal(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Canal : {}", id);
        canalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
