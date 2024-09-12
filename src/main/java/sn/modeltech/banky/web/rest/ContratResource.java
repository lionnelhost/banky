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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import sn.modeltech.banky.repository.ContratRepository;
import sn.modeltech.banky.service.ContratService;
import sn.modeltech.banky.service.dto.ContratDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.Contrat}.
 */
@RestController
@RequestMapping("/api/contrats")
public class ContratResource {

    private static final Logger LOG = LoggerFactory.getLogger(ContratResource.class);

    private static final String ENTITY_NAME = "contrat";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContratService contratService;

    private final ContratRepository contratRepository;

    public ContratResource(ContratService contratService, ContratRepository contratRepository) {
        this.contratService = contratService;
        this.contratRepository = contratRepository;
    }

    /**
     * {@code POST  /contrats} : Create a new contrat.
     *
     * @param contratDTO the contratDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contratDTO, or with status {@code 400 (Bad Request)} if the contrat has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ContratDTO> createContrat(@RequestBody ContratDTO contratDTO) throws URISyntaxException {
        LOG.debug("REST request to save Contrat : {}", contratDTO);
        if (contratRepository.existsById(contratDTO.getIdContrat())) {
            throw new BadRequestAlertException("contrat already exists", ENTITY_NAME, "idexists");
        }
        contratDTO = contratService.save(contratDTO);
        return ResponseEntity.created(new URI("/api/contrats/" + contratDTO.getIdContrat()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, contratDTO.getIdContrat()))
            .body(contratDTO);
    }

    /**
     * {@code PUT  /contrats/:idContrat} : Updates an existing contrat.
     *
     * @param idContrat the id of the contratDTO to save.
     * @param contratDTO the contratDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contratDTO,
     * or with status {@code 400 (Bad Request)} if the contratDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contratDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{idContrat}")
    public ResponseEntity<ContratDTO> updateContrat(
        @PathVariable(value = "idContrat", required = false) final String idContrat,
        @RequestBody ContratDTO contratDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Contrat : {}, {}", idContrat, contratDTO);
        if (contratDTO.getIdContrat() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idContrat, contratDTO.getIdContrat())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contratRepository.existsById(idContrat)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        contratDTO = contratService.update(contratDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contratDTO.getIdContrat()))
            .body(contratDTO);
    }

    /**
     * {@code PATCH  /contrats/:idContrat} : Partial updates given fields of an existing contrat, field will ignore if it is null
     *
     * @param idContrat the id of the contratDTO to save.
     * @param contratDTO the contratDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contratDTO,
     * or with status {@code 400 (Bad Request)} if the contratDTO is not valid,
     * or with status {@code 404 (Not Found)} if the contratDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the contratDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{idContrat}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContratDTO> partialUpdateContrat(
        @PathVariable(value = "idContrat", required = false) final String idContrat,
        @RequestBody ContratDTO contratDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Contrat partially : {}, {}", idContrat, contratDTO);
        if (contratDTO.getIdContrat() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idContrat, contratDTO.getIdContrat())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contratRepository.existsById(idContrat)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContratDTO> result = contratService.partialUpdate(contratDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contratDTO.getIdContrat())
        );
    }

    /**
     * {@code GET  /contrats} : get all the contrats.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contrats in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ContratDTO>> getAllContrats(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "filter", required = false) String filter
    ) {
        if ("client-is-null".equals(filter)) {
            LOG.debug("REST request to get all Contrats where client is null");
            return new ResponseEntity<>(contratService.findAllWhereClientIsNull(), HttpStatus.OK);
        }
        LOG.debug("REST request to get a page of Contrats");
        Page<ContratDTO> page = contratService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contrats/:id} : get the "id" contrat.
     *
     * @param id the id of the contratDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contratDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContratDTO> getContrat(@PathVariable("id") String id) {
        LOG.debug("REST request to get Contrat : {}", id);
        Optional<ContratDTO> contratDTO = contratService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contratDTO);
    }

    /**
     * {@code DELETE  /contrats/:id} : delete the "id" contrat.
     *
     * @param id the id of the contratDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContrat(@PathVariable("id") String id) {
        LOG.debug("REST request to delete Contrat : {}", id);
        contratService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
