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
import sn.modeltech.banky.repository.DispositifSignatureRepository;
import sn.modeltech.banky.service.DispositifSignatureService;
import sn.modeltech.banky.service.dto.DispositifSignatureDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.DispositifSignature}.
 */
@RestController
@RequestMapping("/api/dispositif-signatures")
public class DispositifSignatureResource {

    private static final Logger LOG = LoggerFactory.getLogger(DispositifSignatureResource.class);

    private static final String ENTITY_NAME = "dispositifSignature";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DispositifSignatureService dispositifSignatureService;

    private final DispositifSignatureRepository dispositifSignatureRepository;

    public DispositifSignatureResource(
        DispositifSignatureService dispositifSignatureService,
        DispositifSignatureRepository dispositifSignatureRepository
    ) {
        this.dispositifSignatureService = dispositifSignatureService;
        this.dispositifSignatureRepository = dispositifSignatureRepository;
    }

    /**
     * {@code POST  /dispositif-signatures} : Create a new dispositifSignature.
     *
     * @param dispositifSignatureDTO the dispositifSignatureDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dispositifSignatureDTO, or with status {@code 400 (Bad Request)} if the dispositifSignature has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<DispositifSignatureDTO> createDispositifSignature(@RequestBody DispositifSignatureDTO dispositifSignatureDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save DispositifSignature : {}", dispositifSignatureDTO);
        if (dispositifSignatureRepository.existsById(dispositifSignatureDTO.getIdDispositif())) {
            throw new BadRequestAlertException("dispositifSignature already exists", ENTITY_NAME, "idexists");
        }
        dispositifSignatureDTO = dispositifSignatureService.save(dispositifSignatureDTO);
        return ResponseEntity.created(new URI("/api/dispositif-signatures/" + dispositifSignatureDTO.getIdDispositif()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, dispositifSignatureDTO.getIdDispositif()))
            .body(dispositifSignatureDTO);
    }

    /**
     * {@code PUT  /dispositif-signatures/:idDispositif} : Updates an existing dispositifSignature.
     *
     * @param idDispositif the id of the dispositifSignatureDTO to save.
     * @param dispositifSignatureDTO the dispositifSignatureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispositifSignatureDTO,
     * or with status {@code 400 (Bad Request)} if the dispositifSignatureDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dispositifSignatureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{idDispositif}")
    public ResponseEntity<DispositifSignatureDTO> updateDispositifSignature(
        @PathVariable(value = "idDispositif", required = false) final String idDispositif,
        @RequestBody DispositifSignatureDTO dispositifSignatureDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update DispositifSignature : {}, {}", idDispositif, dispositifSignatureDTO);
        if (dispositifSignatureDTO.getIdDispositif() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idDispositif, dispositifSignatureDTO.getIdDispositif())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispositifSignatureRepository.existsById(idDispositif)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        dispositifSignatureDTO = dispositifSignatureService.update(dispositifSignatureDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dispositifSignatureDTO.getIdDispositif()))
            .body(dispositifSignatureDTO);
    }

    /**
     * {@code PATCH  /dispositif-signatures/:idDispositif} : Partial updates given fields of an existing dispositifSignature, field will ignore if it is null
     *
     * @param idDispositif the id of the dispositifSignatureDTO to save.
     * @param dispositifSignatureDTO the dispositifSignatureDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dispositifSignatureDTO,
     * or with status {@code 400 (Bad Request)} if the dispositifSignatureDTO is not valid,
     * or with status {@code 404 (Not Found)} if the dispositifSignatureDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the dispositifSignatureDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{idDispositif}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<DispositifSignatureDTO> partialUpdateDispositifSignature(
        @PathVariable(value = "idDispositif", required = false) final String idDispositif,
        @RequestBody DispositifSignatureDTO dispositifSignatureDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update DispositifSignature partially : {}, {}", idDispositif, dispositifSignatureDTO);
        if (dispositifSignatureDTO.getIdDispositif() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idDispositif, dispositifSignatureDTO.getIdDispositif())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!dispositifSignatureRepository.existsById(idDispositif)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<DispositifSignatureDTO> result = dispositifSignatureService.partialUpdate(dispositifSignatureDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dispositifSignatureDTO.getIdDispositif())
        );
    }

    /**
     * {@code GET  /dispositif-signatures} : get all the dispositifSignatures.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dispositifSignatures in body.
     */
    @GetMapping("")
    public ResponseEntity<List<DispositifSignatureDTO>> getAllDispositifSignatures(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of DispositifSignatures");
        Page<DispositifSignatureDTO> page = dispositifSignatureService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dispositif-signatures/:id} : get the "id" dispositifSignature.
     *
     * @param id the id of the dispositifSignatureDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dispositifSignatureDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DispositifSignatureDTO> getDispositifSignature(@PathVariable("id") String id) {
        LOG.debug("REST request to get DispositifSignature : {}", id);
        Optional<DispositifSignatureDTO> dispositifSignatureDTO = dispositifSignatureService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dispositifSignatureDTO);
    }

    /**
     * {@code DELETE  /dispositif-signatures/:id} : delete the "id" dispositifSignature.
     *
     * @param id the id of the dispositifSignatureDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDispositifSignature(@PathVariable("id") String id) {
        LOG.debug("REST request to delete DispositifSignature : {}", id);
        dispositifSignatureService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
