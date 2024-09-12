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
import sn.modeltech.banky.repository.ParametrageNotificationRepository;
import sn.modeltech.banky.service.ParametrageNotificationService;
import sn.modeltech.banky.service.dto.ParametrageNotificationDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.ParametrageNotification}.
 */
@RestController
@RequestMapping("/api/parametrage-notifications")
public class ParametrageNotificationResource {

    private static final Logger LOG = LoggerFactory.getLogger(ParametrageNotificationResource.class);

    private static final String ENTITY_NAME = "parametrageNotification";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParametrageNotificationService parametrageNotificationService;

    private final ParametrageNotificationRepository parametrageNotificationRepository;

    public ParametrageNotificationResource(
        ParametrageNotificationService parametrageNotificationService,
        ParametrageNotificationRepository parametrageNotificationRepository
    ) {
        this.parametrageNotificationService = parametrageNotificationService;
        this.parametrageNotificationRepository = parametrageNotificationRepository;
    }

    /**
     * {@code POST  /parametrage-notifications} : Create a new parametrageNotification.
     *
     * @param parametrageNotificationDTO the parametrageNotificationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new parametrageNotificationDTO, or with status {@code 400 (Bad Request)} if the parametrageNotification has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ParametrageNotificationDTO> createParametrageNotification(
        @RequestBody ParametrageNotificationDTO parametrageNotificationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to save ParametrageNotification : {}", parametrageNotificationDTO);
        if (parametrageNotificationRepository.existsById(parametrageNotificationDTO.getIdParamNotif())) {
            throw new BadRequestAlertException("parametrageNotification already exists", ENTITY_NAME, "idexists");
        }
        parametrageNotificationDTO = parametrageNotificationService.save(parametrageNotificationDTO);
        return ResponseEntity.created(new URI("/api/parametrage-notifications/" + parametrageNotificationDTO.getIdParamNotif()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, parametrageNotificationDTO.getIdParamNotif()))
            .body(parametrageNotificationDTO);
    }

    /**
     * {@code PUT  /parametrage-notifications/:idParamNotif} : Updates an existing parametrageNotification.
     *
     * @param idParamNotif the id of the parametrageNotificationDTO to save.
     * @param parametrageNotificationDTO the parametrageNotificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametrageNotificationDTO,
     * or with status {@code 400 (Bad Request)} if the parametrageNotificationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the parametrageNotificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{idParamNotif}")
    public ResponseEntity<ParametrageNotificationDTO> updateParametrageNotification(
        @PathVariable(value = "idParamNotif", required = false) final String idParamNotif,
        @RequestBody ParametrageNotificationDTO parametrageNotificationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update ParametrageNotification : {}, {}", idParamNotif, parametrageNotificationDTO);
        if (parametrageNotificationDTO.getIdParamNotif() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idParamNotif, parametrageNotificationDTO.getIdParamNotif())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parametrageNotificationRepository.existsById(idParamNotif)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        parametrageNotificationDTO = parametrageNotificationService.update(parametrageNotificationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametrageNotificationDTO.getIdParamNotif()))
            .body(parametrageNotificationDTO);
    }

    /**
     * {@code PATCH  /parametrage-notifications/:idParamNotif} : Partial updates given fields of an existing parametrageNotification, field will ignore if it is null
     *
     * @param idParamNotif the id of the parametrageNotificationDTO to save.
     * @param parametrageNotificationDTO the parametrageNotificationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated parametrageNotificationDTO,
     * or with status {@code 400 (Bad Request)} if the parametrageNotificationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the parametrageNotificationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the parametrageNotificationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{idParamNotif}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ParametrageNotificationDTO> partialUpdateParametrageNotification(
        @PathVariable(value = "idParamNotif", required = false) final String idParamNotif,
        @RequestBody ParametrageNotificationDTO parametrageNotificationDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ParametrageNotification partially : {}, {}", idParamNotif, parametrageNotificationDTO);
        if (parametrageNotificationDTO.getIdParamNotif() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idParamNotif, parametrageNotificationDTO.getIdParamNotif())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!parametrageNotificationRepository.existsById(idParamNotif)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ParametrageNotificationDTO> result = parametrageNotificationService.partialUpdate(parametrageNotificationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, parametrageNotificationDTO.getIdParamNotif())
        );
    }

    /**
     * {@code GET  /parametrage-notifications} : get all the parametrageNotifications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of parametrageNotifications in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ParametrageNotificationDTO>> getAllParametrageNotifications(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ParametrageNotifications");
        Page<ParametrageNotificationDTO> page = parametrageNotificationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /parametrage-notifications/:id} : get the "id" parametrageNotification.
     *
     * @param id the id of the parametrageNotificationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the parametrageNotificationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ParametrageNotificationDTO> getParametrageNotification(@PathVariable("id") String id) {
        LOG.debug("REST request to get ParametrageNotification : {}", id);
        Optional<ParametrageNotificationDTO> parametrageNotificationDTO = parametrageNotificationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(parametrageNotificationDTO);
    }

    /**
     * {@code DELETE  /parametrage-notifications/:id} : delete the "id" parametrageNotification.
     *
     * @param id the id of the parametrageNotificationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParametrageNotification(@PathVariable("id") String id) {
        LOG.debug("REST request to delete ParametrageNotification : {}", id);
        parametrageNotificationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
