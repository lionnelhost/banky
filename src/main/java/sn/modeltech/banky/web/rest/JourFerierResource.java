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
import sn.modeltech.banky.repository.JourFerierRepository;
import sn.modeltech.banky.service.JourFerierService;
import sn.modeltech.banky.service.dto.JourFerierDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.JourFerier}.
 */
@RestController
@RequestMapping("/api/jour-feriers")
public class JourFerierResource {

    private static final Logger LOG = LoggerFactory.getLogger(JourFerierResource.class);

    private static final String ENTITY_NAME = "jourFerier";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JourFerierService jourFerierService;

    private final JourFerierRepository jourFerierRepository;

    public JourFerierResource(JourFerierService jourFerierService, JourFerierRepository jourFerierRepository) {
        this.jourFerierService = jourFerierService;
        this.jourFerierRepository = jourFerierRepository;
    }

    /**
     * {@code POST  /jour-feriers} : Create a new jourFerier.
     *
     * @param jourFerierDTO the jourFerierDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jourFerierDTO, or with status {@code 400 (Bad Request)} if the jourFerier has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<JourFerierDTO> createJourFerier(@RequestBody JourFerierDTO jourFerierDTO) throws URISyntaxException {
        LOG.debug("REST request to save JourFerier : {}", jourFerierDTO);
        if (jourFerierRepository.existsById(jourFerierDTO.getIdJourFerie())) {
            throw new BadRequestAlertException("jourFerier already exists", ENTITY_NAME, "idexists");
        }
        jourFerierDTO = jourFerierService.save(jourFerierDTO);
        return ResponseEntity.created(new URI("/api/jour-feriers/" + jourFerierDTO.getIdJourFerie()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, jourFerierDTO.getIdJourFerie()))
            .body(jourFerierDTO);
    }

    /**
     * {@code PUT  /jour-feriers/:idJourFerie} : Updates an existing jourFerier.
     *
     * @param idJourFerie the id of the jourFerierDTO to save.
     * @param jourFerierDTO the jourFerierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jourFerierDTO,
     * or with status {@code 400 (Bad Request)} if the jourFerierDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jourFerierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{idJourFerie}")
    public ResponseEntity<JourFerierDTO> updateJourFerier(
        @PathVariable(value = "idJourFerie", required = false) final String idJourFerie,
        @RequestBody JourFerierDTO jourFerierDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update JourFerier : {}, {}", idJourFerie, jourFerierDTO);
        if (jourFerierDTO.getIdJourFerie() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idJourFerie, jourFerierDTO.getIdJourFerie())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jourFerierRepository.existsById(idJourFerie)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        jourFerierDTO = jourFerierService.update(jourFerierDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jourFerierDTO.getIdJourFerie()))
            .body(jourFerierDTO);
    }

    /**
     * {@code PATCH  /jour-feriers/:idJourFerie} : Partial updates given fields of an existing jourFerier, field will ignore if it is null
     *
     * @param idJourFerie the id of the jourFerierDTO to save.
     * @param jourFerierDTO the jourFerierDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jourFerierDTO,
     * or with status {@code 400 (Bad Request)} if the jourFerierDTO is not valid,
     * or with status {@code 404 (Not Found)} if the jourFerierDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the jourFerierDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{idJourFerie}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<JourFerierDTO> partialUpdateJourFerier(
        @PathVariable(value = "idJourFerie", required = false) final String idJourFerie,
        @RequestBody JourFerierDTO jourFerierDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update JourFerier partially : {}, {}", idJourFerie, jourFerierDTO);
        if (jourFerierDTO.getIdJourFerie() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idJourFerie, jourFerierDTO.getIdJourFerie())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jourFerierRepository.existsById(idJourFerie)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JourFerierDTO> result = jourFerierService.partialUpdate(jourFerierDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, jourFerierDTO.getIdJourFerie())
        );
    }

    /**
     * {@code GET  /jour-feriers} : get all the jourFeriers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jourFeriers in body.
     */
    @GetMapping("")
    public ResponseEntity<List<JourFerierDTO>> getAllJourFeriers(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of JourFeriers");
        Page<JourFerierDTO> page = jourFerierService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /jour-feriers/:id} : get the "id" jourFerier.
     *
     * @param id the id of the jourFerierDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jourFerierDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<JourFerierDTO> getJourFerier(@PathVariable("id") String id) {
        LOG.debug("REST request to get JourFerier : {}", id);
        Optional<JourFerierDTO> jourFerierDTO = jourFerierService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jourFerierDTO);
    }

    /**
     * {@code DELETE  /jour-feriers/:id} : delete the "id" jourFerier.
     *
     * @param id the id of the jourFerierDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJourFerier(@PathVariable("id") String id) {
        LOG.debug("REST request to delete JourFerier : {}", id);
        jourFerierService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
