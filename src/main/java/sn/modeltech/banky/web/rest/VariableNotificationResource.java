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
import sn.modeltech.banky.repository.VariableNotificationRepository;
import sn.modeltech.banky.service.VariableNotificationService;
import sn.modeltech.banky.service.dto.VariableNotificationDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.VariableNotification}.
 */
@RestController
@RequestMapping("/api/variable-notifications")
public class VariableNotificationResource {

    private static final Logger LOG = LoggerFactory.getLogger(VariableNotificationResource.class);

    private static final String ENTITY_NAME = "variableNotification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VariableNotificationService variableNotificationService;

    private final VariableNotificationRepository variableNotificationRepository;

    public VariableNotificationResource(
        VariableNotificationService variableNotificationService,
        VariableNotificationRepository variableNotificationRepository
    ) {
        this.variableNotificationService = variableNotificationService;
        this.variableNotificationRepository = variableNotificationRepository;
    }

    /**
     * {@code POST  /variable-notifications} : Create a new variableNotification.
     *
     * @param variableNotificationDTO the variableNotificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new variableNotificationDTO, or with status {@code 400 (Bad Request)} if the variableNotification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<VariableNotificationDTO> createVariableNotification(@RequestBody VariableNotificationDTO variableNotificationDTO)
        throws URISyntaxException {
        LOG.debug("REST request to save VariableNotification : {}", variableNotificationDTO);
        if (variableNotificationRepository.existsById(variableNotificationDTO.getIdVarNotification())) {
            throw new BadRequestAlertException("variableNotification already exists", ENTITY_NAME, "idexists");
        }
        variableNotificationDTO = variableNotificationService.save(variableNotificationDTO);
        return ResponseEntity.created(new URI("/api/variable-notifications/" + variableNotificationDTO.getIdVarNotification()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, variableNotificationDTO.getIdVarNotification())
            )
            .body(variableNotificationDTO);
    }

    /**
     * {@code PUT  /variable-notifications/:idVarNotification} : Updates an existing variableNotification.
     *
     * @param idVarNotification the id of the variableNotificationDTO to save.
     * @param variableNotificationDTO the variableNotificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated variableNotificationDTO,
     * or with status {@code 400 (Bad Request)} if the variableNotificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the variableNotificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{idVarNotification}")
    public ResponseEntity<VariableNotificationDTO> updateVariableNotification(
        @PathVariable(value = "idVarNotification", required = false) final String idVarNotification,
        @RequestBody VariableNotificationDTO variableNotificationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update VariableNotification : {}, {}", idVarNotification, variableNotificationDTO);
        if (variableNotificationDTO.getIdVarNotification() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idVarNotification, variableNotificationDTO.getIdVarNotification())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!variableNotificationRepository.existsById(idVarNotification)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        variableNotificationDTO = variableNotificationService.update(variableNotificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, variableNotificationDTO.getIdVarNotification()))
            .body(variableNotificationDTO);
    }

    /**
     * {@code PATCH  /variable-notifications/:idVarNotification} : Partial updates given fields of an existing variableNotification, field will ignore if it is null
     *
     * @param idVarNotification the id of the variableNotificationDTO to save.
     * @param variableNotificationDTO the variableNotificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated variableNotificationDTO,
     * or with status {@code 400 (Bad Request)} if the variableNotificationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the variableNotificationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the variableNotificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{idVarNotification}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<VariableNotificationDTO> partialUpdateVariableNotification(
        @PathVariable(value = "idVarNotification", required = false) final String idVarNotification,
        @RequestBody VariableNotificationDTO variableNotificationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update VariableNotification partially : {}, {}", idVarNotification, variableNotificationDTO);
        if (variableNotificationDTO.getIdVarNotification() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idVarNotification, variableNotificationDTO.getIdVarNotification())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!variableNotificationRepository.existsById(idVarNotification)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<VariableNotificationDTO> result = variableNotificationService.partialUpdate(variableNotificationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, variableNotificationDTO.getIdVarNotification())
        );
    }

    /**
     * {@code GET  /variable-notifications} : get all the variableNotifications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of variableNotifications in body.
     */
    @GetMapping("")
    public ResponseEntity<List<VariableNotificationDTO>> getAllVariableNotifications(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of VariableNotifications");
        Page<VariableNotificationDTO> page = variableNotificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /variable-notifications/:id} : get the "id" variableNotification.
     *
     * @param id the id of the variableNotificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the variableNotificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<VariableNotificationDTO> getVariableNotification(@PathVariable("id") String id) {
        LOG.debug("REST request to get VariableNotification : {}", id);
        Optional<VariableNotificationDTO> variableNotificationDTO = variableNotificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(variableNotificationDTO);
    }

    /**
     * {@code DELETE  /variable-notifications/:id} : delete the "id" variableNotification.
     *
     * @param id the id of the variableNotificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVariableNotification(@PathVariable("id") String id) {
        LOG.debug("REST request to delete VariableNotification : {}", id);
        variableNotificationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
