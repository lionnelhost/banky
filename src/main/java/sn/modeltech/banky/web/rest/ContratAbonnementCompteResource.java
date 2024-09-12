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
import sn.modeltech.banky.repository.ContratAbonnementCompteRepository;
import sn.modeltech.banky.service.ContratAbonnementCompteService;
import sn.modeltech.banky.service.dto.ContratAbonnementCompteDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.ContratAbonnementCompte}.
 */
@RestController
@RequestMapping("/api/contrat-abonnement-comptes")
public class ContratAbonnementCompteResource {

    private static final Logger LOG = LoggerFactory.getLogger(ContratAbonnementCompteResource.class);

    private static final String ENTITY_NAME = "contratAbonnementCompte";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContratAbonnementCompteService contratAbonnementCompteService;

    private final ContratAbonnementCompteRepository contratAbonnementCompteRepository;

    public ContratAbonnementCompteResource(
        ContratAbonnementCompteService contratAbonnementCompteService,
        ContratAbonnementCompteRepository contratAbonnementCompteRepository
    ) {
        this.contratAbonnementCompteService = contratAbonnementCompteService;
        this.contratAbonnementCompteRepository = contratAbonnementCompteRepository;
    }

    /**
     * {@code POST  /contrat-abonnement-comptes} : Create a new contratAbonnementCompte.
     *
     * @param contratAbonnementCompteDTO the contratAbonnementCompteDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contratAbonnementCompteDTO, or with status {@code 400 (Bad Request)} if the contratAbonnementCompte has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ContratAbonnementCompteDTO> createContratAbonnementCompte(
        @RequestBody ContratAbonnementCompteDTO contratAbonnementCompteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save ContratAbonnementCompte : {}", contratAbonnementCompteDTO);
        if (contratAbonnementCompteDTO.getId() != null) {
            throw new BadRequestAlertException("A new contratAbonnementCompte cannot already have an ID", ENTITY_NAME, "idexists");
        }
        contratAbonnementCompteDTO = contratAbonnementCompteService.save(contratAbonnementCompteDTO);
        return ResponseEntity.created(new URI("/api/contrat-abonnement-comptes/" + contratAbonnementCompteDTO.getId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, contratAbonnementCompteDTO.getId().toString())
            )
            .body(contratAbonnementCompteDTO);
    }

    /**
     * {@code PUT  /contrat-abonnement-comptes/:id} : Updates an existing contratAbonnementCompte.
     *
     * @param id the id of the contratAbonnementCompteDTO to save.
     * @param contratAbonnementCompteDTO the contratAbonnementCompteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contratAbonnementCompteDTO,
     * or with status {@code 400 (Bad Request)} if the contratAbonnementCompteDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contratAbonnementCompteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContratAbonnementCompteDTO> updateContratAbonnementCompte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContratAbonnementCompteDTO contratAbonnementCompteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ContratAbonnementCompte : {}, {}", id, contratAbonnementCompteDTO);
        if (contratAbonnementCompteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contratAbonnementCompteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contratAbonnementCompteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        contratAbonnementCompteDTO = contratAbonnementCompteService.update(contratAbonnementCompteDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contratAbonnementCompteDTO.getId().toString()))
            .body(contratAbonnementCompteDTO);
    }

    /**
     * {@code PATCH  /contrat-abonnement-comptes/:id} : Partial updates given fields of an existing contratAbonnementCompte, field will ignore if it is null
     *
     * @param id the id of the contratAbonnementCompteDTO to save.
     * @param contratAbonnementCompteDTO the contratAbonnementCompteDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contratAbonnementCompteDTO,
     * or with status {@code 400 (Bad Request)} if the contratAbonnementCompteDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contratAbonnementCompteDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contratAbonnementCompteDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContratAbonnementCompteDTO> partialUpdateContratAbonnementCompte(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContratAbonnementCompteDTO contratAbonnementCompteDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ContratAbonnementCompte partially : {}, {}", id, contratAbonnementCompteDTO);
        if (contratAbonnementCompteDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contratAbonnementCompteDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contratAbonnementCompteRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContratAbonnementCompteDTO> result = contratAbonnementCompteService.partialUpdate(contratAbonnementCompteDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contratAbonnementCompteDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /contrat-abonnement-comptes} : get all the contratAbonnementComptes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contratAbonnementComptes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ContratAbonnementCompteDTO>> getAllContratAbonnementComptes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ContratAbonnementComptes");
        Page<ContratAbonnementCompteDTO> page = contratAbonnementCompteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contrat-abonnement-comptes/:id} : get the "id" contratAbonnementCompte.
     *
     * @param id the id of the contratAbonnementCompteDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contratAbonnementCompteDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContratAbonnementCompteDTO> getContratAbonnementCompte(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ContratAbonnementCompte : {}", id);
        Optional<ContratAbonnementCompteDTO> contratAbonnementCompteDTO = contratAbonnementCompteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contratAbonnementCompteDTO);
    }

    /**
     * {@code DELETE  /contrat-abonnement-comptes/:id} : delete the "id" contratAbonnementCompte.
     *
     * @param id the id of the contratAbonnementCompteDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContratAbonnementCompte(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ContratAbonnementCompte : {}", id);
        contratAbonnementCompteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
