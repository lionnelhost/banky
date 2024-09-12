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
import sn.modeltech.banky.repository.MessageErreurRepository;
import sn.modeltech.banky.service.MessageErreurService;
import sn.modeltech.banky.service.dto.MessageErreurDTO;
import sn.modeltech.banky.web.rest.errors.BadRequestAlertException;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link sn.modeltech.banky.domain.MessageErreur}.
 */
@RestController
@RequestMapping("/api/message-erreurs")
public class MessageErreurResource {

    private static final Logger LOG = LoggerFactory.getLogger(MessageErreurResource.class);

    private static final String ENTITY_NAME = "messageErreur";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessageErreurService messageErreurService;

    private final MessageErreurRepository messageErreurRepository;

    public MessageErreurResource(MessageErreurService messageErreurService, MessageErreurRepository messageErreurRepository) {
        this.messageErreurService = messageErreurService;
        this.messageErreurRepository = messageErreurRepository;
    }

    /**
     * {@code POST  /message-erreurs} : Create a new messageErreur.
     *
     * @param messageErreurDTO the messageErreurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messageErreurDTO, or with status {@code 400 (Bad Request)} if the messageErreur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<MessageErreurDTO> createMessageErreur(@RequestBody MessageErreurDTO messageErreurDTO) throws URISyntaxException {
        LOG.debug("REST request to save MessageErreur : {}", messageErreurDTO);
        if (messageErreurRepository.existsById(messageErreurDTO.getIdMessageErreur())) {
            throw new BadRequestAlertException("messageErreur already exists", ENTITY_NAME, "idexists");
        }
        messageErreurDTO = messageErreurService.save(messageErreurDTO);
        return ResponseEntity.created(new URI("/api/message-erreurs/" + messageErreurDTO.getIdMessageErreur()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, messageErreurDTO.getIdMessageErreur()))
            .body(messageErreurDTO);
    }

    /**
     * {@code PUT  /message-erreurs/:idMessageErreur} : Updates an existing messageErreur.
     *
     * @param idMessageErreur the id of the messageErreurDTO to save.
     * @param messageErreurDTO the messageErreurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageErreurDTO,
     * or with status {@code 400 (Bad Request)} if the messageErreurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messageErreurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{idMessageErreur}")
    public ResponseEntity<MessageErreurDTO> updateMessageErreur(
        @PathVariable(value = "idMessageErreur", required = false) final String idMessageErreur,
        @RequestBody MessageErreurDTO messageErreurDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update MessageErreur : {}, {}", idMessageErreur, messageErreurDTO);
        if (messageErreurDTO.getIdMessageErreur() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idMessageErreur, messageErreurDTO.getIdMessageErreur())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!messageErreurRepository.existsById(idMessageErreur)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        messageErreurDTO = messageErreurService.update(messageErreurDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, messageErreurDTO.getIdMessageErreur()))
            .body(messageErreurDTO);
    }

    /**
     * {@code PATCH  /message-erreurs/:idMessageErreur} : Partial updates given fields of an existing messageErreur, field will ignore if it is null
     *
     * @param idMessageErreur the id of the messageErreurDTO to save.
     * @param messageErreurDTO the messageErreurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageErreurDTO,
     * or with status {@code 400 (Bad Request)} if the messageErreurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the messageErreurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the messageErreurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{idMessageErreur}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MessageErreurDTO> partialUpdateMessageErreur(
        @PathVariable(value = "idMessageErreur", required = false) final String idMessageErreur,
        @RequestBody MessageErreurDTO messageErreurDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update MessageErreur partially : {}, {}", idMessageErreur, messageErreurDTO);
        if (messageErreurDTO.getIdMessageErreur() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(idMessageErreur, messageErreurDTO.getIdMessageErreur())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!messageErreurRepository.existsById(idMessageErreur)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<MessageErreurDTO> result = messageErreurService.partialUpdate(messageErreurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, messageErreurDTO.getIdMessageErreur())
        );
    }

    /**
     * {@code GET  /message-erreurs} : get all the messageErreurs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messageErreurs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<MessageErreurDTO>> getAllMessageErreurs(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of MessageErreurs");
        Page<MessageErreurDTO> page = messageErreurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /message-erreurs/:id} : get the "id" messageErreur.
     *
     * @param id the id of the messageErreurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messageErreurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<MessageErreurDTO> getMessageErreur(@PathVariable("id") String id) {
        LOG.debug("REST request to get MessageErreur : {}", id);
        Optional<MessageErreurDTO> messageErreurDTO = messageErreurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(messageErreurDTO);
    }

    /**
     * {@code DELETE  /message-erreurs/:id} : delete the "id" messageErreur.
     *
     * @param id the id of the messageErreurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMessageErreur(@PathVariable("id") String id) {
        LOG.debug("REST request to delete MessageErreur : {}", id);
        messageErreurService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
