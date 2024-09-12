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
import sn.modeltech.banky.repository.CompteBancaireRepository;
import sn.modeltech.banky.service.CompteBancaireService;
import sn.modeltech.banky.service.dto.CompteBancaireDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.CompteBancaire}.
 */
@RestController
@RequestMapping("/api/compte-bancaires")
public class CompteBancaireResource {

    private static final Logger LOG = LoggerFactory.getLogger(CompteBancaireResource.class);

    private static final String ENTITY_NAME = "compteBancaire";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompteBancaireService compteBancaireService;

    private final CompteBancaireRepository compteBancaireRepository;

    public CompteBancaireResource(CompteBancaireService compteBancaireService, CompteBancaireRepository compteBancaireRepository) {
        this.compteBancaireService = compteBancaireService;
        this.compteBancaireRepository = compteBancaireRepository;
    }

    /**
     * {@code POST  /compte-bancaires} : Create a new compteBancaire.
     *
     * @param compteBancaireDTO the compteBancaireDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new compteBancaireDTO, or with status {@code 400 (Bad Request)} if the compteBancaire has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CompteBancaireDTO> createCompteBancaire(@RequestBody CompteBancaireDTO compteBancaireDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save CompteBancaire : {}", compteBancaireDTO);
        if (compteBancaireRepository.existsById(compteBancaireDTO.getIdCompteBancaire())) {
            throw new BadRequestAlertException("compteBancaire already exists", ENTITY_NAME, "idexists");
        }
        compteBancaireDTO = compteBancaireService.save(compteBancaireDTO);
        return ResponseEntity.created(new URI("/api/compte-bancaires/" + compteBancaireDTO.getIdCompteBancaire()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, compteBancaireDTO.getIdCompteBancaire()))
            .body(compteBancaireDTO);
    }

    /**
     * {@code PUT  /compte-bancaires/:idCompteBancaire} : Updates an existing compteBancaire.
     *
     * @param idCompteBancaire the id of the compteBancaireDTO to save.
     * @param compteBancaireDTO the compteBancaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compteBancaireDTO,
     * or with status {@code 400 (Bad Request)} if the compteBancaireDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the compteBancaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{idCompteBancaire}")
    public ResponseEntity<CompteBancaireDTO> updateCompteBancaire(
        @PathVariable(value = "idCompteBancaire", required = false) final String idCompteBancaire,
        @RequestBody CompteBancaireDTO compteBancaireDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CompteBancaire : {}, {}", idCompteBancaire, compteBancaireDTO);
        if (compteBancaireDTO.getIdCompteBancaire() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idCompteBancaire, compteBancaireDTO.getIdCompteBancaire())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compteBancaireRepository.existsById(idCompteBancaire)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        compteBancaireDTO = compteBancaireService.update(compteBancaireDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compteBancaireDTO.getIdCompteBancaire()))
            .body(compteBancaireDTO);
    }

    /**
     * {@code PATCH  /compte-bancaires/:idCompteBancaire} : Partial updates given fields of an existing compteBancaire, field will ignore if it is null
     *
     * @param idCompteBancaire the id of the compteBancaireDTO to save.
     * @param compteBancaireDTO the compteBancaireDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated compteBancaireDTO,
     * or with status {@code 400 (Bad Request)} if the compteBancaireDTO is not valid,
     * or with status {@code 404 (Not Found)} if the compteBancaireDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the compteBancaireDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{idCompteBancaire}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompteBancaireDTO> partialUpdateCompteBancaire(
        @PathVariable(value = "idCompteBancaire", required = false) final String idCompteBancaire,
        @RequestBody CompteBancaireDTO compteBancaireDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CompteBancaire partially : {}, {}", idCompteBancaire, compteBancaireDTO);
        if (compteBancaireDTO.getIdCompteBancaire() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idCompteBancaire, compteBancaireDTO.getIdCompteBancaire())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!compteBancaireRepository.existsById(idCompteBancaire)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CompteBancaireDTO> result = compteBancaireService.partialUpdate(compteBancaireDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, compteBancaireDTO.getIdCompteBancaire())
        );
    }

    /**
     * {@code GET  /compte-bancaires} : get all the compteBancaires.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of compteBancaires in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CompteBancaireDTO>> getAllCompteBancaires(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of CompteBancaires");
        Page<CompteBancaireDTO> page = compteBancaireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /compte-bancaires/:id} : get the "id" compteBancaire.
     *
     * @param id the id of the compteBancaireDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the compteBancaireDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CompteBancaireDTO> getCompteBancaire(@PathVariable("id") String id) {
        LOG.debug("REST request to get CompteBancaire : {}", id);
        Optional<CompteBancaireDTO> compteBancaireDTO = compteBancaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(compteBancaireDTO);
    }

    /**
     * {@code DELETE  /compte-bancaires/:id} : delete the "id" compteBancaire.
     *
     * @param id the id of the compteBancaireDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompteBancaire(@PathVariable("id") String id) {
        LOG.debug("REST request to delete CompteBancaire : {}", id);
        compteBancaireService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
