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
import sn.modeltech.banky.repository.ParametrageGlobalRepository;
import sn.modeltech.banky.service.ParametrageGlobalService;
import sn.modeltech.banky.service.dto.ParametrageGlobalDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.ParametrageGlobal}.
 */
@RestController
@RequestMapping("/api/parametrage-globals")
public class ParametrageGlobalResource {

    private static final Logger LOG = LoggerFactory.getLogger(ParametrageGlobalResource.class);

    private static final String ENTITY_NAME = "parametrageGlobal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParametrageGlobalService parametrageGlobalService;

    private final ParametrageGlobalRepository parametrageGlobalRepository;

    public ParametrageGlobalResource(
        ParametrageGlobalService parametrageGlobalService,
        ParametrageGlobalRepository parametrageGlobalRepository
    ) {
        this.parametrageGlobalService = parametrageGlobalService;
        this.parametrageGlobalRepository = parametrageGlobalRepository;
    }

    /**
     * {@code POST  /parametrage-globals} : Create a new parametrageGlobal.
     *
     * @param parametrageGlobalDTO the parametrageGlobalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parametrageGlobalDTO, or with status {@code 400 (Bad Request)} if the parametrageGlobal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ParametrageGlobalDTO> createParametrageGlobal(@RequestBody ParametrageGlobalDTO parametrageGlobalDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save ParametrageGlobal : {}", parametrageGlobalDTO);
        if (parametrageGlobalRepository.existsById(parametrageGlobalDTO.getIdParamGlobal())) {
            throw new BadRequestAlertException("parametrageGlobal already exists", ENTITY_NAME, "idexists");
        }
        parametrageGlobalDTO = parametrageGlobalService.save(parametrageGlobalDTO);
        return ResponseEntity.created(new URI("/api/parametrage-globals/" + parametrageGlobalDTO.getIdParamGlobal()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, parametrageGlobalDTO.getIdParamGlobal()))
            .body(parametrageGlobalDTO);
    }

    /**
     * {@code PUT  /parametrage-globals/:idParamGlobal} : Updates an existing parametrageGlobal.
     *
     * @param idParamGlobal the id of the parametrageGlobalDTO to save.
     * @param parametrageGlobalDTO the parametrageGlobalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametrageGlobalDTO,
     * or with status {@code 400 (Bad Request)} if the parametrageGlobalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parametrageGlobalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{idParamGlobal}")
    public ResponseEntity<ParametrageGlobalDTO> updateParametrageGlobal(
        @PathVariable(value = "idParamGlobal", required = false) final String idParamGlobal,
        @RequestBody ParametrageGlobalDTO parametrageGlobalDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ParametrageGlobal : {}, {}", idParamGlobal, parametrageGlobalDTO);
        if (parametrageGlobalDTO.getIdParamGlobal() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idParamGlobal, parametrageGlobalDTO.getIdParamGlobal())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parametrageGlobalRepository.existsById(idParamGlobal)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        parametrageGlobalDTO = parametrageGlobalService.update(parametrageGlobalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametrageGlobalDTO.getIdParamGlobal()))
            .body(parametrageGlobalDTO);
    }

    /**
     * {@code PATCH  /parametrage-globals/:idParamGlobal} : Partial updates given fields of an existing parametrageGlobal, field will ignore if it is null
     *
     * @param idParamGlobal the id of the parametrageGlobalDTO to save.
     * @param parametrageGlobalDTO the parametrageGlobalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametrageGlobalDTO,
     * or with status {@code 400 (Bad Request)} if the parametrageGlobalDTO is not valid,
     * or with status {@code 404 (Not Found)} if the parametrageGlobalDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the parametrageGlobalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{idParamGlobal}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ParametrageGlobalDTO> partialUpdateParametrageGlobal(
        @PathVariable(value = "idParamGlobal", required = false) final String idParamGlobal,
        @RequestBody ParametrageGlobalDTO parametrageGlobalDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ParametrageGlobal partially : {}, {}", idParamGlobal, parametrageGlobalDTO);
        if (parametrageGlobalDTO.getIdParamGlobal() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idParamGlobal, parametrageGlobalDTO.getIdParamGlobal())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parametrageGlobalRepository.existsById(idParamGlobal)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParametrageGlobalDTO> result = parametrageGlobalService.partialUpdate(parametrageGlobalDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametrageGlobalDTO.getIdParamGlobal())
        );
    }

    /**
     * {@code GET  /parametrage-globals} : get all the parametrageGlobals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parametrageGlobals in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ParametrageGlobalDTO>> getAllParametrageGlobals(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ParametrageGlobals");
        Page<ParametrageGlobalDTO> page = parametrageGlobalService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /parametrage-globals/:id} : get the "id" parametrageGlobal.
     *
     * @param id the id of the parametrageGlobalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parametrageGlobalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ParametrageGlobalDTO> getParametrageGlobal(@PathVariable("id") String id) {
        LOG.debug("REST request to get ParametrageGlobal : {}", id);
        Optional<ParametrageGlobalDTO> parametrageGlobalDTO = parametrageGlobalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parametrageGlobalDTO);
    }

    /**
     * {@code DELETE  /parametrage-globals/:id} : delete the "id" parametrageGlobal.
     *
     * @param id the id of the parametrageGlobalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParametrageGlobal(@PathVariable("id") String id) {
        LOG.debug("REST request to delete ParametrageGlobal : {}", id);
        parametrageGlobalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
