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
import sn.modeltech.banky.repository.ContratAbonnementRepository;
import sn.modeltech.banky.service.ContratAbonnementService;
import sn.modeltech.banky.service.dto.ContratAbonnementDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.ContratAbonnement}.
 */
@RestController
@RequestMapping("/api/contrat-abonnements")
public class ContratAbonnementResource {

    private static final Logger LOG = LoggerFactory.getLogger(ContratAbonnementResource.class);

    private static final String ENTITY_NAME = "contratAbonnement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContratAbonnementService contratAbonnementService;

    private final ContratAbonnementRepository contratAbonnementRepository;

    public ContratAbonnementResource(
        ContratAbonnementService contratAbonnementService,
        ContratAbonnementRepository contratAbonnementRepository
    ) {
        this.contratAbonnementService = contratAbonnementService;
        this.contratAbonnementRepository = contratAbonnementRepository;
    }

    /**
     * {@code POST  /contrat-abonnements} : Create a new contratAbonnement.
     *
     * @param contratAbonnementDTO the contratAbonnementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contratAbonnementDTO, or with status {@code 400 (Bad Request)} if the contratAbonnement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ContratAbonnementDTO> createContratAbonnement(@RequestBody ContratAbonnementDTO contratAbonnementDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ContratAbonnement : {}", contratAbonnementDTO);
        if (contratAbonnementDTO.getId() != null) {
            throw new BadRequestAlertException("A new contratAbonnement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        contratAbonnementDTO = contratAbonnementService.save(contratAbonnementDTO);
        return ResponseEntity.created(new URI("/api/contrat-abonnements/" + contratAbonnementDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, contratAbonnementDTO.getId().toString()))
            .body(contratAbonnementDTO);
    }

    /**
     * {@code PUT  /contrat-abonnements/:id} : Updates an existing contratAbonnement.
     *
     * @param id the id of the contratAbonnementDTO to save.
     * @param contratAbonnementDTO the contratAbonnementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contratAbonnementDTO,
     * or with status {@code 400 (Bad Request)} if the contratAbonnementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contratAbonnementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContratAbonnementDTO> updateContratAbonnement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContratAbonnementDTO contratAbonnementDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ContratAbonnement : {}, {}", id, contratAbonnementDTO);
        if (contratAbonnementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contratAbonnementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contratAbonnementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        contratAbonnementDTO = contratAbonnementService.update(contratAbonnementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contratAbonnementDTO.getId().toString()))
            .body(contratAbonnementDTO);
    }

    /**
     * {@code PATCH  /contrat-abonnements/:id} : Partial updates given fields of an existing contratAbonnement, field will ignore if it is null
     *
     * @param id the id of the contratAbonnementDTO to save.
     * @param contratAbonnementDTO the contratAbonnementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contratAbonnementDTO,
     * or with status {@code 400 (Bad Request)} if the contratAbonnementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contratAbonnementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contratAbonnementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContratAbonnementDTO> partialUpdateContratAbonnement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContratAbonnementDTO contratAbonnementDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ContratAbonnement partially : {}, {}", id, contratAbonnementDTO);
        if (contratAbonnementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contratAbonnementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contratAbonnementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContratAbonnementDTO> result = contratAbonnementService.partialUpdate(contratAbonnementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contratAbonnementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /contrat-abonnements} : get all the contratAbonnements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contratAbonnements in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ContratAbonnementDTO>> getAllContratAbonnements(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ContratAbonnements");
        Page<ContratAbonnementDTO> page = contratAbonnementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contrat-abonnements/:id} : get the "id" contratAbonnement.
     *
     * @param id the id of the contratAbonnementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contratAbonnementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContratAbonnementDTO> getContratAbonnement(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ContratAbonnement : {}", id);
        Optional<ContratAbonnementDTO> contratAbonnementDTO = contratAbonnementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contratAbonnementDTO);
    }

    /**
     * {@code DELETE  /contrat-abonnements/:id} : delete the "id" contratAbonnement.
     *
     * @param id the id of the contratAbonnementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContratAbonnement(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ContratAbonnement : {}", id);
        contratAbonnementService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
